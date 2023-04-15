import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimalGuessingGameGUI {
    // This my Swing element
    private JFrame frame;
    private JButton startGameButton;

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
    }

    private void initialize() {
        // This is my Layout Manager with a JFrame
        frame = new JFrame("Animal Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel();
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        // This is my second Swing element
        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new StartGameButtonListener());
        mainPanel.add(startGameButton);
    }

    private class StartGameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Code to start the game goes here
            System.out.println("Game started!");
        }
    }
}
