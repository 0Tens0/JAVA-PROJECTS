import java.io.*;
import java.net.*;

/**
 * Chat client that handles communication with the server.
 * Manages sending and receiving messages.
 */
public class ChatClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private boolean connected;
    private MessageListener messageListener;
    
    /**
     * Interface for receiving messages
     */
    public interface MessageListener {
        void onMessageReceived(String message);
        void onUserListReceived(String[] users);
        void onConnectionStatusChanged(boolean connected);
    }
    
    public ChatClient() {
        connected = false;
    }
    
    /**
     * Set message listener
     */
    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }
    
    /**
     * Connect to the server
     */
    public boolean connect(String username) {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            this.username = username;
            
            // Read welcome message
            String welcome = in.readLine();
            System.out.println(welcome);
            
            // Send username
            out.println(username);
            
            // Check if username was accepted
            String response = in.readLine();
            if (response != null && response.contains("already taken")) {
                disconnect();
                return false;
            }
            
            connected = true;
            
            // Notify listener
            if (messageListener != null) {
                messageListener.onConnectionStatusChanged(true);
            }
            
            // Start listening for messages
            new Thread(new MessageReceiver()).start();
            
            return true;
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            connected = false;
            if (messageListener != null) {
                messageListener.onConnectionStatusChanged(false);
            }
            return false;
        }
    }
    
    /**
     * Disconnect from the server
     */
    public void disconnect() {
        connected = false;
        
        try {
            if (out != null) {
                out.println("/quit");
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error disconnecting: " + e.getMessage());
        }
        
        if (messageListener != null) {
            messageListener.onConnectionStatusChanged(false);
        }
    }
    
    /**
     * Send a message to the server
     */
    public void sendMessage(String message) {
        if (connected && out != null) {
            out.println(message);
        }
    }
    
    /**
     * Check if connected
     */
    public boolean isConnected() {
        return connected;
    }
    
    /**
     * Get username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Message receiver thread
     */
    private class MessageReceiver implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while (connected && (message = in.readLine()) != null) {
                    // Check if it's a user list update
                    if (message.startsWith("USERLIST:")) {
                        String userListStr = message.substring(9);
                        String[] users = userListStr.split(",");
                        
                        if (messageListener != null) {
                            messageListener.onUserListReceived(users);
                        }
                    } else {
                        // Regular message
                        if (messageListener != null) {
                            messageListener.onMessageReceived(message);
                        }
                    }
                }
            } catch (IOException e) {
                if (connected) {
                    System.err.println("Error receiving message: " + e.getMessage());
                }
            } finally {
                connected = false;
                if (messageListener != null) {
                    messageListener.onConnectionStatusChanged(false);
                }
            }
        }
    }
    
    /**
     * Main method for testing (console-based client)
     */
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        
        client.setMessageListener(new MessageListener() {
            @Override
            public void onMessageReceived(String message) {
                System.out.println(message);
            }
            
            @Override
            public void onUserListReceived(String[] users) {
                System.out.println("Active users: " + String.join(", ", users));
            }
            
            @Override
            public void onConnectionStatusChanged(boolean connected) {
                System.out.println("Connection status: " + (connected ? "Connected" : "Disconnected"));
            }
        });
        
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            System.out.print("Enter your username: ");
            String username = console.readLine();
            
            if (client.connect(username)) {
                System.out.println("Connected! Type messages (or /quit to exit):");
                
                String message;
                while ((message = console.readLine()) != null) {
                    if (message.equalsIgnoreCase("/quit")) {
                        break;
                    }
                    client.sendMessage(message);
                }
            } else {
                System.out.println("Failed to connect to server.");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            client.disconnect();
        }
    }
}
