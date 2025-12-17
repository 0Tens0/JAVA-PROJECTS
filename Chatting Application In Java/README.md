# Chatting Application in Java - Project Report

## Table of Contents
1. [Objective](#objective)
2. [Description](#description)
3. [Concepts Implemented](#concepts-implemented)
4. [Project Features](#project-features)
5. [Source Code](#source-code)
6. [Output](#output)
7. [Conclusion](#conclusion)

---

## Objective

To develop a robust, multi-client chatting application using Java that demonstrates advanced programming concepts including socket programming, multi-threading, custom data structures, and GUI development.  The application enables real-time communication between multiple users through a client-server architecture while implementing efficient data management using custom-built data structures. 

---

## Description

The Chatting Application is a comprehensive Java-based messaging system that facilitates real-time communication between multiple clients through a centralized server. The application is built on TCP/IP socket programming and features a Swing-based graphical user interface for intuitive user interaction.

The system architecture consists of three main components:

1. **ChatServer**: A multi-threaded server that handles multiple client connections simultaneously, manages user sessions, broadcasts messages, and maintains chat history.

2. **ChatClient**: A client-side communication handler that manages the connection to the server, sends messages, and receives updates in real-time.

3. **ChatClientGUI**: A user-friendly Swing-based interface that provides message display, user list management, and search functionality. 

The application implements custom data structures from scratch, including a hash map for user management, a FIFO message queue for orderly message processing, and merge sort for efficient searching capabilities. All chat conversations are persisted to `chat_history.txt` for future reference.

---

## Concepts Implemented

### 1. **Network Programming**
- TCP/IP Socket Programming
- Client-Server Architecture
- ServerSocket and Socket implementation
- Multi-client connection handling

### 2. **Multi-Threading**
- Concurrent client connections
- Thread-safe message processing
- Synchronized operations on shared resources
- Producer-Consumer pattern with message queue

### 3. **Custom Data Structures**
- **SimpleHashMap**: Custom hash map implementation with separate chaining for collision resolution
- **MessageQueue**: FIFO queue using linked list for message ordering
- **MergeSort**: Custom sorting algorithm for message and user search functionality

### 4. **Object-Oriented Programming**
- Encapsulation and abstraction
- Interface implementation (MessageListener)
- Inner classes for handler threads
- Modular design with clear separation of concerns

### 5. **GUI Development**
- Java Swing components (JFrame, JPanel, JTextArea, JList, etc.)
- Event-driven programming
- Action listeners and event handlers
- Real-time UI updates

### 6. **File I/O Operations**
- Persistent chat history storage
- BufferedReader and PrintWriter for efficient I/O
- Timestamped message logging

### 7. **Design Patterns**
- Observer Pattern (MessageListener interface)
- Producer-Consumer Pattern (MessageQueue)
- Thread Pool Pattern (ClientHandler threads)

### 8. **Synchronization and Thread Safety**
- Synchronized methods for concurrent access
- wait() and notifyAll() for thread coordination
- Thread-safe data structure operations

---

## Project Features

### Server Features
1. **Multi-Client Support**:  Handles multiple simultaneous client connections using individual threads
2. **User Session Management**: Tracks active users using custom SimpleHashMap
3. **Message Broadcasting**: Distributes messages to all connected clients
4. **FIFO Message Queue**: Ensures messages are processed in the order they are received
5. **Username Validation**: Prevents duplicate usernames
6. **Connection Logging**: Monitors client connections and disconnections
7. **Persistent Chat History**:  Saves all messages with timestamps to file
8. **User List Broadcasting**: Updates all clients with the current active user list

### Client Features
1. **Real-Time Messaging**: Instant message delivery and reception
2. **Graphical User Interface**: Clean, intuitive Swing-based interface
3. **Active User Display**: Shows list of currently connected users
4. **Search Functionality**: 
   - Search messages by keyword
   - Search users by name
5. **Connection Management**: Easy connect/disconnect controls
6. **Message History Display**:  Scrollable message area with timestamps
7. **Username Customization**: User-defined usernames
8. **Auto-Scroll**: Message area automatically scrolls to show latest messages

### Technical Features
1. **Custom Hash Map**: O(1) average case lookup for user management
2. **Custom Message Queue**: Thread-safe FIFO queue with blocking operations
3. **Merge Sort Implementation**: O(n log n) sorting for efficient search
4. **Exception Handling**: Robust error handling for network and I/O operations
5. **Resource Cleanup**: Proper socket and stream closure on disconnect

---

## Source Code

### Project Structure

```
Chatting Application In Java/
├── src/
│   ├── ChatServer.java          # Multi-client server implementation
│   ├── ChatClient.java          # Client communication handler
│   ├── ChatClientGUI.java       # Swing-based user interface
│   ├── SimpleHashMap.java       # Custom HashMap implementation
│   ├── MessageQueue.java        # FIFO queue for message handling
│   └── MergeSort.java           # Sorting and search utilities
├── out/
│   └── production/              # Compiled . class files
├── chat_history.txt             # Persistent chat history
└── Chatting Application In Java. iml  # IntelliJ IDEA project file
```

### Key Components

#### 1. ChatServer.java
```java
- PORT: 12345
- Manages SimpleHashMap of connected clients
- Implements MessageQueue for FIFO message processing
- ClientHandler inner class for each client connection
- MessageProcessor thread for queue processing
- Broadcasts messages to all connected clients
- Saves chat history with timestamps
```

#### 2. ChatClient.java
```java
- Connects to server on localhost:12345
- Implements MessageListener interface
- MessageReceiver thread for incoming messages
- Handles username registration
- Processes user list updates
- Manages connection lifecycle
```

#### 3. ChatClientGUI.java
```java
- Swing-based user interface
- Connection controls (username, connect/disconnect)
- Message display area with scrolling
- Active user list display
- Search functionality (messages and users)
- Message input field and send button
```

#### 4. SimpleHashMap.java
```java
- Custom hash map with separate chaining
- Default capacity:  16, Load factor: 0.75
- Synchronized methods for thread safety
- Auto-resize on load threshold
- O(1) average case operations
```

#### 5. MessageQueue.java
```java
- Linked list-based FIFO queue
- Thread-safe with synchronized methods
- Blocking enqueue/dequeue operations
- Maximum size:  1000 messages
- wait() and notifyAll() for coordination
```

#### 6. MergeSort.java
```java
- Merge sort implementation for strings
- Case-insensitive comparison
- Message search by keyword
- User search by name
- O(n log n) time complexity
```

### Repository Location
**GitHub**: [0Tens0/JAVA-PROJECTS - Chatting Application](https://github.com/0Tens0/JAVA-PROJECTS/tree/main/Chatting%20Application%20In%20Java)

---

## Output

### 1. Server Console Output

<img width="1196" height="458" alt="Server Console" src="https://github.com/user-attachments/assets/cda797c5-ee1a-4b22-bc23-14d09fb25458" />


### 2. Client GUI Interface

**Connection Panel:**
- Username input field
- Connect button (active when disconnected)
- Disconnect button (active when connected)

**Message Display Area:**
**Active Users Panel:**
**Message Input Area:**
- Text field for typing messages
- Send button

<img width="789" height="596" alt="GUI" src="https://github.com/user-attachments/assets/0a33e185-0082-4ba0-9c25-58c04de83baf" />


**Search Panel:**
- Search text field
- Dropdown for search type (Messages/Users)
- Search button


### 3. Chat History File (chat_history.txt)
 
<img width="772" height="482" alt="chat history" src="https://github.com/user-attachments/assets/1cd806d8-8c9e-4972-ac9a-9bd00e03e94f" />


### 4. Search Functionality Output

**Message Search:**
- Input: "hello"
- Output:  Displays all messages containing "hello" in the chat history

<img width="791" height="594" alt="GUI Search" src="https://github.com/user-attachments/assets/3ffdd595-fb2d-4383-9a1d-1d54c18ec32e" />


**User Search:**
- Input: "wah"
- Output: Displays "waheed" from active users list

<img width="787" height="594" alt="GUI search1" src="https://github.com/user-attachments/assets/b94b4148-1edb-47f8-8da1-e144c772512c" />


---

## Conclusion

The Chatting Application in Java successfully demonstrates the implementation of advanced programming concepts and data structures from scratch. The project showcases a deep understanding of network programming, concurrent programming, and GUI development in Java.

### Key Achievements

1. **Custom Data Structures**: Successfully implemented HashMap, Queue, and MergeSort without using Java Collections Framework, demonstrating strong understanding of data structure internals.

2. **Scalable Architecture**: The server efficiently handles multiple clients through multi-threading and maintains thread safety using synchronization.

3. **Robust Communication**:  Implemented reliable message delivery using FIFO queue ensuring messages are processed in order.

4. **User-Friendly Interface**: Created an intuitive GUI that makes the application accessible to non-technical users.

5. **Data Persistence**: Chat history is preserved across sessions, allowing users to review past conversations.

6. **Efficient Search**: Implemented merge sort for O(n log n) search capabilities in messages and users.

### Technical Highlights

- **Zero External Dependencies**: All core data structures built from scratch
- **Thread-Safe Operations**: Proper synchronization prevents race conditions
- **Memory Efficient**: Custom HashMap with dynamic resizing and load factor management
- **Blocking Queue**: Efficient producer-consumer pattern with wait/notify mechanism
- **Clean Code**: Well-documented, modular design following OOP principles

### Learning Outcomes

1. Deep understanding of TCP/IP socket programming
2. Practical experience with multi-threaded application design
3. Implementation of custom data structures from first principles
4. Thread synchronization and concurrent programming techniques
5. Event-driven GUI programming with Swing
6. File I/O and data persistence strategies

### Potential Enhancements

1. **Security Features**:
   - User authentication with password
   - Message encryption (SSL/TLS)
   - Secure password storage

2. **Advanced Features**:
   - Private messaging between users
   - File sharing capabilities
   - Emoji and rich text support
   - Message editing and deletion
   - Typing indicators

3. **User Experience**:
   - Modern UI using JavaFX
   - Dark/Light theme support
   - Custom notification sounds
   - Message timestamps in different time zones
   - User avatars

4. **Scalability**:
   - Database integration for persistent storage
   - Support for chat rooms/channels
   - Message pagination for large histories
   - Server clustering for load balancing

5. **Cross-Platform**:
   - Mobile client applications
   - Web-based client interface
   - Desktop notifications

### Project Impact

This project serves as an excellent portfolio piece demonstrating:
- Strong fundamentals in Java programming
- Understanding of computer networking
- Ability to implement complex algorithms and data structures
- Software architecture and design skills
- Problem-solving and debugging capabilities

The application is production-ready for small-scale deployments and provides a solid foundation for building more advanced messaging systems.

---

**Project Information:**
- **Repository**: [0Tens0/JAVA-PROJECTS](https://github.com/0Tens0/JAVA-PROJECTS)
- **Project Path**: Chatting Application In Java
- **Language**: 100% Java
- **Developer**: 0Tens0
- **Last Updated**: December 2025

---

*This project was developed as part of a Java programming course to demonstrate proficiency in advanced programming concepts, data structures, and network programming.*
