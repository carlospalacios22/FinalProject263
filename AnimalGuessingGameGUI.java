import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class AnimalGuessingGameGUI {
    
    private JFrame frame;                   // Main JFrame for the application
    private JButton startGameButton;        // JButton to start the game
    private JMenuBar menuBar;               // JMenuBar for the menu at the top of the application
    private JMenu fileMenu;                 // JMenu for "File" options
    private JMenuItem openLogMenuItem;      // JMenuItem to open the log file
    private JMenuItem saveLogMenuItem;      // JMenuItem to save the log file
    private JMenu gameMenu;                 // JMenu for "Game" options
    private JMenuItem newGameMenuItem;      // JMenuItem to start a new game
    private JMenuItem exitMenuItem;         // JMenuItem to exit the application
    private JMenuItem deleteLogMenuItem;    // JMenuItem to delete the log file
    private LinkedList<Player> players;     // LinkedList to store Player objects
    private WindowSettings windowSettings;  // WindowSettings object to set up the application window
    


// Below is the main method
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

    // Constructor
    public AnimalGuessingGameGUI() {
        initialize();
        players = new LinkedList<>();
    }
// Below is the initialize method
    private void initialize() {
        frame = new JFrame("Animal Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        windowSettings = new WindowSettings("/photos/background.png");

        windowSettings.setLayout(new GridBagLayout());
        frame.setContentPane(windowSettings);
    // Creates the menu bar
        createMenu();
        frame.setJMenuBar(menuBar);
    
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;

        // Creates the start game button
        startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size and style
        startGameButton.setForeground(Color.BLUE); // Set font color
        startGameButton.setToolTipText("Click to start the game");
        startGameButton.addActionListener(new StartGameButtonListener());
        startGameButton.setPreferredSize(new Dimension(200, 100)); // Set button size
        windowSettings.add(startGameButton, constraints);
    }

    private void createMenu() {
        menuBar = new JMenuBar();

        // File menu
        fileMenu = new JMenu("File");
        openLogMenuItem = new JMenuItem("Open Log");
        openLogMenuItem.addActionListener(new OpenLogMenuItemListener());
        fileMenu.add(openLogMenuItem);
        // Save log menu item
        saveLogMenuItem = new JMenuItem("Save Log");
        saveLogMenuItem.addActionListener(new SaveLogMenuItemListener());
        fileMenu.add(saveLogMenuItem);
        menuBar.add(fileMenu);
        // Delete log menu item
        deleteLogMenuItem = new JMenuItem("Delete Log");
        deleteLogMenuItem.addActionListener(new DeleteLogMenuItemListener());
        fileMenu.add(deleteLogMenuItem);

        // Game menu
        gameMenu = new JMenu("Game");
        // New game menu item
        newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(new NewGameMenuItemListener());
        gameMenu.add(newGameMenuItem);
        // Exit menu item
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ExitMenuItemListener());
        gameMenu.add(exitMenuItem);
        // Add the game menu to the menu bar
        menuBar.add(gameMenu);
    }

    // Start game button listener

private class StartGameButtonListener implements ActionListener {
    @Override
    // When the Start Game button is pressed, the number of players is entered
    public void actionPerformed(ActionEvent e) {
        String numPlayersStr = JOptionPane.showInputDialog(frame, "Enter the number of players:", "Number of Players", JOptionPane.QUESTION_MESSAGE);
        
        if (numPlayersStr != null) {
            try { // Try to parse the number of players
                ExceptionErrors.checkNumberOfPlayers(numPlayersStr);
                int numPlayers = Integer.parseInt(numPlayersStr);
                 // If the number of players is valid, enter the names of the players
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

                // Code to start the game g
                System.out.println("Game started with " + numPlayers + " players.");
            } catch (EmptyInputException ex) {
                ExceptionErrors.showMessage(ex.getMessage());
            } catch (NumberFormatException ex) {
                ExceptionErrors.showMessage("Invalid number format! Please enter a valid number.");
            }

            if (players.size() > 0) {
                GamePanel gamePanel = new GamePanel(players); // Pass the list of players to the GamePanel
                frame.setContentPane(gamePanel);
                frame.revalidate();
                frame.repaint();
            }
        }
    }
}


    
     // Below is the DeleteLogMenuItemListener class
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
          //oPEn log menu item listener
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

//save log menu item listener
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
                }//end of try catch
            }//end of if
        }//end of action performed
    }// end of delete log menu item listener


}//END OF CLASS


