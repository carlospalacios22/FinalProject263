import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class AnimalGuessingGameGUI {
    private JFrame frame;
    private JButton startGameButton;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openLogMenuItem;
    private JMenuItem saveLogMenuItem;
    private JMenu gameMenu;
    private JMenuItem newGameMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem deleteLogMenuItem;
    private LinkedList<Player> players;


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AnimalGuessingGameGUI gui = new AnimalGuessingGameGUI();
                gui.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AnimalGuessingGameGUI() {
        initialize();
        players = new LinkedList<>();
    }

    private void initialize() {
        frame = new JFrame("Animal Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        createMenu();
        frame.setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel();
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        startGameButton = new JButton("Start Game");
    startGameButton.setToolTipText("Click to start the game"); // Add a tooltip to the Start Game button
    startGameButton.addActionListener(new StartGameButtonListener());
    mainPanel.add(startGameButton);
    }

    private void createMenu() {
        menuBar = new JMenuBar();

        // File menu
        fileMenu = new JMenu("File");
        openLogMenuItem = new JMenuItem("Open Log");
        openLogMenuItem.addActionListener(new OpenLogMenuItemListener());
        fileMenu.add(openLogMenuItem);

        saveLogMenuItem = new JMenuItem("Save Log");
        saveLogMenuItem.addActionListener(new SaveLogMenuItemListener());
        fileMenu.add(saveLogMenuItem);
        menuBar.add(fileMenu);

        deleteLogMenuItem = new JMenuItem("Delete Log");
        deleteLogMenuItem.addActionListener(new DeleteLogMenuItemListener());
        fileMenu.add(deleteLogMenuItem);

        // Game menu
        gameMenu = new JMenu("Game");

        newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(new NewGameMenuItemListener());
        gameMenu.add(newGameMenuItem);

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ExitMenuItemListener());
        gameMenu.add(exitMenuItem);

        menuBar.add(gameMenu);
    }

     // AnimalGuessingGameGUI.java

private class StartGameButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String numPlayersStr = JOptionPane.showInputDialog(frame, "Enter the number of players:", "Number of Players", JOptionPane.QUESTION_MESSAGE);
        int numPlayers = Integer.parseInt(numPlayersStr);

        for (int i = 0; i < numPlayers; i++) {
            String playerName = JOptionPane.showInputDialog(frame, "Enter the name of player " + (i + 1) + ":", "Player Name", JOptionPane.QUESTION_MESSAGE);
            players.add(new Player(playerName));

            // Save a log entry when a player is entered
            String logEntry = FileActions.createLogEntry("Player entered: " + playerName);
            try {
                FileActions.saveLog(logEntry);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(frame, "Error saving log file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Save a log entry when the Start Game button is pressed
        String logEntry = FileActions.createLogEntry("Game started with " + numPlayers + " players.");
        try {
            FileActions.saveLog(logEntry);
        } catch (IOException ioException) {
            JOptionPane.showMessageDialog(frame, "Error saving log file.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Code to start the game goes here
        System.out.println("Game started with " + numPlayers + " players.");
    }
}

    

    private class NewGameMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Code to start a new game goes here
            System.out.println("New game started!");
        }
    }

    private class ExitMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Code to exit the game goes here
            System.exit(0);
        }
    }

    private class OpenLogMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ArrayList<String> logEntries = FileActions.readLog();
                JOptionPane.showMessageDialog(frame, String.join("\n", logEntries), "Log", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(frame, "Error reading log file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SaveLogMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String logEntry = FileActions.createLogEntry("Log saved.");
            try {
                FileActions.saveLog(logEntry);
                JOptionPane.showMessageDialog(frame, "Log saved.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(frame, "Error saving log file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private class DeleteLogMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the log file?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
    
            if (response == JOptionPane.YES_OPTION) {
                try {
                    FileActions.deleteLog();
                    JOptionPane.showMessageDialog(frame, "Log file deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(frame, "Error deleting log file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


}
