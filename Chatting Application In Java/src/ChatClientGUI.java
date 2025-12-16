import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI for the chat client using Swing.
 * Features:
 * - Message display area
 * - Active user list
 * - Search functionality for messages and users
 * - Send messages
 */
public class ChatClientGUI extends JFrame {
    private ChatClient client;
    private JTextArea messageArea;
    private JTextField messageField;
    private JButton sendButton;
    private JButton connectButton;
    private JButton disconnectButton;
    private JTextField usernameField;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> searchTypeCombo;
    private List<String> chatHistory;

    public ChatClientGUI() {
        chatHistory = new ArrayList<>();
        initializeUI();
        client = new ChatClient();
        setupClientListener();
    }

    /**
     * Initialize the user interface
     */
    private void initializeUI() {
        setTitle("Chat Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Top panel - Connection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        topPanel.add(usernameField);
        connectButton = new JButton("Connect");
        disconnectButton = new JButton("Disconnect");
        disconnectButton.setEnabled(false);
        topPanel.add(connectButton);
        topPanel.add(disconnectButton);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(15);
        searchPanel.add(searchField);
        searchTypeCombo = new JComboBox<>(new String[]{"Messages", "Users"});
        searchPanel.add(searchTypeCombo);
        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        JPanel topCombinedPanel = new JPanel(new BorderLayout());
        topCombinedPanel.add(topPanel, BorderLayout.NORTH);
        topCombinedPanel.add(searchPanel, BorderLayout.SOUTH);

        // Center panel - Messages and Users
        JPanel centerPanel = new JPanel(new BorderLayout(10, 0));

        // Message area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setBorder(BorderFactory.createTitledBorder("Messages"));

        // User list
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setBorder(BorderFactory.createTitledBorder("Active Users"));
        userScrollPane.setPreferredSize(new Dimension(150, 0));

        centerPanel.add(messageScrollPane, BorderLayout.CENTER);
        centerPanel.add(userScrollPane, BorderLayout.EAST);

        // Bottom panel - Send message
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 0));
        messageField = new JTextField();
        messageField.setEnabled(false);
        sendButton = new JButton("Send");
        sendButton.setEnabled(false);
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Add panels to main panel
        mainPanel.add(topCombinedPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Event listeners
        connectButton.addActionListener(e -> connectToServer());
        disconnectButton.addActionListener(e -> disconnectFromServer());
        sendButton.addActionListener(e -> sendMessage());
        searchButton.addActionListener(e -> performSearch());

        messageField.addActionListener(e -> sendMessage());
        usernameField.addActionListener(e -> connectToServer());
        searchField.addActionListener(e -> performSearch());

        // Window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client != null && client.isConnected()) {
                    client.disconnect();
                }
            }
        });
    }

    /**
     * Setup client message listener
     */
    private void setupClientListener() {
        client.setMessageListener(new ChatClient.MessageListener() {
            @Override
            public void onMessageReceived(String message) {
                SwingUtilities.invokeLater(() -> {
                    messageArea.append(message + "\n");
                    messageArea.setCaretPosition(messageArea.getDocument().getLength());
                    chatHistory.add(message);
                });
            }

            @Override
            public void onUserListReceived(String[] users) {
                SwingUtilities.invokeLater(() -> {
                    userListModel.clear();
                    // Sort users using MergeSort
                    String[] sortedUsers = MergeSort.sort(users.clone());
                    for (String user : sortedUsers) {
                        if (user != null && !user.trim().isEmpty()) {
                            userListModel.addElement(user);
                        }
                    }
                });
            }

            @Override
            public void onConnectionStatusChanged(boolean connected) {
                SwingUtilities.invokeLater(() -> {
                    updateConnectionStatus(connected);
                });
            }
        });
    }

    /**
     * Connect to server
     */
    private void connectToServer() {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        connectButton.setEnabled(false);
        connectButton.setText("Connecting...");

        new Thread(() -> {
            boolean success = client.connect(username);

            SwingUtilities.invokeLater(() -> {
                if (success) {
                    messageArea.append("Connected to server as " + username + "\n");
                    messageArea.append("Type your messages below. Enjoy chatting!\n\n");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Failed to connect. Username may be taken or server unavailable.",
                            "Connection Error",
                            JOptionPane.ERROR_MESSAGE);
                    connectButton.setEnabled(true);
                    connectButton.setText("Connect");
                }
            });
        }).start();
    }

    /**
     * Disconnect from server
     */
    private void disconnectFromServer() {
        client.disconnect();
        messageArea.append("\nDisconnected from server.\n");
    }

    /**
     * Update connection status
     */
    private void updateConnectionStatus(boolean connected) {
        connectButton.setEnabled(!connected);
        disconnectButton.setEnabled(connected);
        messageField.setEnabled(connected);
        sendButton.setEnabled(connected);
        usernameField.setEnabled(!connected);

        if (connected) {
            connectButton.setText("Connect");
        }
    }

    /**
     * Send message to server
     */
    private void sendMessage() {
        String message = messageField.getText().trim();

        if (!message.isEmpty() && client.isConnected()) {
            client.sendMessage(message);
            messageField.setText("");
        }
    }

    /**
     * Perform search operation
     */
    private void performSearch() {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term.", "Search", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String searchType = (String) searchTypeCombo.getSelectedItem();

        if ("Messages".equals(searchType)) {
            searchMessages(keyword);
        } else {
            searchUsers(keyword);
        }
    }

    /**
     * Search messages using MergeSort search functionality
     */
    private void searchMessages(String keyword) {
        String[] messages = chatHistory.toArray(new String[0]);
        List<String> results = MergeSort.searchMessages(messages, keyword);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No messages found containing: " + keyword,
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder resultText = new StringBuilder("Found " + results.size() + " message(s):\n\n");
            for (String result : results) {
                resultText.append(result).append("\n");
            }

            JTextArea textArea = new JTextArea(resultText.toString());
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(this, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Search users using MergeSort search functionality
     */
    private void searchUsers(String keyword) {
        List<String> users = new ArrayList<>();
        for (int i = 0; i < userListModel.size(); i++) {
            users.add(userListModel.getElementAt(i));
        }

        List<String> results = MergeSort.searchUsers(users, keyword);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No users found matching: " + keyword,
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            String resultText = "Found " + results.size() + " user(s):\n\n" + String.join("\n", results);
            JOptionPane.showMessageDialog(this, resultText, "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Main method to start the GUI
     */
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            ChatClientGUI gui = new ChatClientGUI();
            gui.setVisible(true);
        });
    }
}