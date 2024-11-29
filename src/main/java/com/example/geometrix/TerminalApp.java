package com.example.geometrix;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalApp {

    private List<String> commandHistory = new ArrayList<>();
    private int historyIndex = -1; // Track the current position in the history

    public static void main(String[] args) {
        TerminalApp terminal = new TerminalApp();
        terminal.start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Continuously prompt for commands until exit
        while (true) {
            System.out.print("> "); // Print the prompt
            String command = scanner.nextLine(); // Read the input command

            // If the command is not empty, save it to history
            if (!command.isEmpty()) {
                addToHistory(command);
            }

            // If the command is "exit", stop the program
            if (command.equalsIgnoreCase("exit")) {
                break;
            }

            // Process the command (execute it)
            executeCommand(command);
        }
    }

    private void addToHistory(String command) {
        if (commandHistory.isEmpty() || !commandHistory.get(commandHistory.size() - 1).equals(command)) {
            commandHistory.add(command);
        }
        historyIndex = commandHistory.size(); // Reset to the end of the list
    }

    private void executeCommand(String command) {
        System.out.println("Executing command: " + command);

        // Here you can add more logic to process specific commands
    }

    // Simulate arrow key navigation using 'UP' and 'DOWN'
    private String getHistoryCommand(boolean upArrow) {
        if (upArrow) {
            // Navigate up in the history (previous command)
            if (historyIndex > 0) {
                historyIndex--;
            }
        } else {
            // Navigate down in the history (next command)
            if (historyIndex < commandHistory.size() - 1) {
                historyIndex++;
            }
        }

        return commandHistory.size() > 0 ? commandHistory.get(historyIndex) : "";
    }
}
