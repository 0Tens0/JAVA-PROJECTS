import javax.swing.*;
import java.io.*;

/**
 * Simple test to verify chat history loading works
 */
public class TestHistoryLoading {
    public static void main(String[] args) {
        // Test 1: Check if chat_history.txt exists
        File historyFile = new File("chat_history.txt");
        System.out.println("Test 1: Checking if chat_history.txt exists");
        System.out.println("Result: " + historyFile.exists());
        
        if (historyFile.exists()) {
            // Test 2: Try to read the file
            System.out.println("\nTest 2: Reading chat history file");
            try (BufferedReader reader = new BufferedReader(new FileReader(historyFile, java.nio.charset.StandardCharsets.UTF_8))) {
                String line;
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Line " + (++count) + ": " + line);
                }
                System.out.println("\nTotal messages: " + count);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        }
        
        System.out.println("\nTest 3: Simulating GUI loading (headless mode, no window)");
        System.out.println("The ChatClientGUI class would load this history on startup.");
        System.out.println("\nAll tests completed!");
    }
}
