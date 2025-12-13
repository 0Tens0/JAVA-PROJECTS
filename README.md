# Multi-Client Chat Application

This project implements a multi-client chat application with advanced features and data structure integration for efficiency and modular design. The system ensures a cohesive codebase where every module works seamlessly with others.

## Key Features

1. **Multi-Client Chat**:
   - Clients can connect to the server to send and receive messages in real-time.
   - Server handles multiple simultaneous connections efficiently.

2. **Active User List** (GUI Feature):
   - Displays the list of currently connected users dynamically in the GUI.
   - User list is automatically sorted using MergeSort for easy navigation.

3. **Search Messages and Users**:
   - Clients can search for specific messages or users.
   - Utilizes `MergeSort` for sorting and efficient search algorithms.
   - Supports partial matching for flexible searches.

4. **Server Features**:
   - Efficient user management using custom `SimpleHashMap`.
   - FIFO message handling using `MessageQueue` with a dedicated message processor thread.
   - Stores chat history in a text file (`chat_history.txt`) to ensure message persistence.
   - Broadcasts messages to all connected clients.
   - Notifies all users when someone joins or leaves the chat.

5. **Data Structures**:
   - **MergeSort**: Used for sorting messages and user lists with O(n log n) complexity.
   - **MessageQueue**: Ensures FIFO message delivery with thread-safe operations.
   - **SimpleHashMap**: Provides fast user storage and retrieval with O(1) average case complexity.

## File Structure

```
JAVA-PROJECTS/
│
├── ChatServer.java          # Server implementation
├── ChatClient.java          # Handles communication with the server
├── ChatClientGUI.java       # GUI for the client
├── MergeSort.java           # MergeSort implementation for sorting
├── MessageQueue.java        # FIFO queue implementation for message handling
├── SimpleHashMap.java       # Custom HashMap implementation for user management
├── chat_history.txt         # Chat history file (generated at runtime)
└── README.md                # This documentation file
```

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- A terminal or command prompt
- A text editor or IDE (optional)

### 1. Clone the Repository
```bash
git clone https://github.com/0Tens0/JAVA-PROJECTS.git
cd JAVA-PROJECTS
```

### 2. Compile the Project
Ensure you have Java installed. Then, compile all Java files:
```bash
javac *.java
```

### 3. Run the Server
Start the chat server first:
```bash
java ChatServer
```

You should see:
```
Chat Server started on port 12345
Message processor thread started
```

### 4. Run the Clients
Open multiple terminals (or command prompts) and run the GUI client for each user:
```bash
java ChatClientGUI
```

Alternatively, you can run the console-based client:
```bash
java ChatClient
```

## How to Use

### Server
1. Run `ChatServer` - it will start listening on port 12345.
2. The server will automatically:
   - Accept new client connections
   - Broadcast messages to all connected clients
   - Maintain a list of active users
   - Save all messages to `chat_history.txt`

### Client (GUI)
1. Launch `ChatClientGUI`.
2. Enter your desired username in the "Username" field.
3. Click "Connect" to join the chat.
4. Once connected:
   - Type messages in the bottom text field and press Enter or click "Send"
   - View all chat messages in the main area
   - See active users in the right panel
   - Use the search feature to find messages or users

### Search Functionality
- **Search Messages**: Select "Messages" from the dropdown, enter a keyword, and click "Search" to find all messages containing that keyword.
- **Search Users**: Select "Users" from the dropdown, enter a name, and click "Search" to find matching usernames.

## Architecture

### Server Side
- **ChatServer**: Main server class that manages client connections
  - Uses `SimpleHashMap` to store client handlers by username
  - Uses `MessageQueue` for FIFO message processing
  - Runs a dedicated message processor thread
  - Saves messages to file for persistence

### Client Side
- **ChatClient**: Handles server communication
  - Manages socket connections
  - Sends and receives messages
  - Notifies listeners of events
- **ChatClientGUI**: Swing-based user interface
  - Displays messages and user lists
  - Provides search functionality
  - User-friendly interface with intuitive controls

### Data Structures
- **SimpleHashMap**: Custom hash table implementation
  - Separate chaining for collision resolution
  - Dynamic resizing when load factor exceeds 0.75
  - Thread-safe operations
- **MessageQueue**: Thread-safe FIFO queue
  - Blocking operations for producer-consumer pattern
  - Prevents overflow with configurable max size
- **MergeSort**: Efficient sorting algorithm
  - O(n log n) time complexity
  - Stable sort implementation
  - Binary search support

## Demo Examples

### Example 1: User Joins
```
[2025-12-13 10:30:15] Alice has joined the chat.
```

### Example 2: User Sends Message
```
[2025-12-13 10:30:20] Alice: Hello everyone!
[2025-12-13 10:30:25] Bob: Hi Alice, welcome!
```

### Example 3: User Leaves
```
[2025-12-13 10:35:00] Alice has left the chat.
```

### Example 4: Search Messages
Search for "hello" returns:
```
Found 3 message(s):
[2025-12-13 10:30:20] Alice: Hello everyone!
[2025-12-13 10:31:15] Charlie: hello there
[2025-12-13 10:32:00] Bob: Hello Charlie!
```

## Technical Details

### Server Port
- Default port: 12345
- Can be modified in `ChatServer.java`

### Message Format
- All messages are timestamped: `[YYYY-MM-DD HH:MM:SS] message`
- System messages indicate user joins/leaves
- User messages include the sender's name

### Thread Safety
- All shared data structures are synchronized
- MessageQueue uses wait/notify for thread coordination
- SimpleHashMap methods are synchronized

### File Persistence
- Chat history is saved to `chat_history.txt`
- Each message is appended to the file in real-time
- File is created automatically if it doesn't exist

## Troubleshooting

### Connection Issues
- **Problem**: Cannot connect to server
- **Solution**: Ensure the server is running before starting clients

### Username Taken
- **Problem**: "Username already taken" error
- **Solution**: Choose a different username

### Port Already in Use
- **Problem**: Server won't start due to port conflict
- **Solution**: Stop any process using port 12345 or change the port in the code

### GUI Not Displaying
- **Problem**: GUI doesn't show up
- **Solution**: Ensure you're running on a system with display capabilities (not headless)

## Future Enhancements

Possible improvements for this chat application:
- Private messaging between users
- Chat rooms or channels
- File sharing capabilities
- Emoji support
- User authentication and profiles
- Message encryption for security
- Rich text formatting
- Voice/video call integration

## Contributing

Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.

## License

This project is open source and available for educational purposes.

---

Enjoy your seamless chat experience with advanced data structures!
