import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

public class GamePanel extends JPanel {
    private final JLabel imageLabel;
    private final JComboBox<String> animalComboBox;
    private final JButton submitButton;
    private final LinkedList<Player> players;
    private int currentPlayerIndex;
    private final String[] animalOptions;
    private final String correctAnswer;
    private final String correctAnswerFullName;

    public GamePanel(LinkedList<Player> playersList) {
        players = playersList;
        currentPlayerIndex = 0;
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        String[] animalNames = {"cat", "dog", "lion", "frog", "horse", "bunny", "kangaroo", "monkey", "fox", "wolf"};
        Random rand = new Random();
        int selectedIndex = rand.nextInt(animalNames.length);
        correctAnswer = animalNames[selectedIndex];
        correctAnswerFullName = correctAnswer + "-full";

        // Load the corresponding image for the selected animal
        ImageIcon imageIcon = new ImageIcon("./photos/" + correctAnswer + ".png");
        imageLabel = new JLabel(imageIcon);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        add(imageLabel, constraints);

        animalOptions = animalNames;
        animalComboBox = new JComboBox<>(animalOptions);
        constraints.gridy = 1;
        add(animalComboBox, constraints);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        constraints.gridy = 2;
        add(submitButton, constraints);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedOption = (String) animalComboBox.getSelectedItem();
            Player currentPlayer = players.get(currentPlayerIndex);

            if (selectedOption.equals(correctAnswer)) {
                ImageIcon fullImageIcon = new ImageIcon("./photos/" + correctAnswerFullName + ".png");
                imageLabel.setIcon(fullImageIcon);

                Random rand = new Random();
                int reward = rand.nextInt(501) + 500;
                currentPlayer.addMoney(reward);
                JOptionPane.showMessageDialog(GamePanel.this, currentPlayer.getName() + ", correct! You have been awarded $" + reward + ".", "Correct Answer", JOptionPane.INFORMATION_MESSAGE);

                // Add any logic for moving on to the next question or ending the game here
            } else {
                currentPlayer.addMoney(-100);
                JOptionPane.showMessageDialog(GamePanel.this, currentPlayer.getName() + ", incorrect. You lost $100. Your current balance is $" + currentPlayer.getMoney() + ".", "Incorrect Answer", JOptionPane.ERROR_MESSAGE);
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            }
        }
    }
}
