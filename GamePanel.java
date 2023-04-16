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
    private final JTextArea playersInfo;
    private final LinkedList<Player> players;
    private int currentPlayerIndex;
    private final String[] animalOptions;
    private final String correctAnswer;
    private final String correctAnswerFullName;
    private int selectedAnimalIndex;

    public GamePanel(LinkedList<Player> playersList) {
        players = playersList;
        currentPlayerIndex = 0;
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Animal options
        animalOptions = new String[]{"cat", "dog", "lion", "frog", "horse", "bunny", "kangaroo", "monkey", "fox", "wolf"};
        selectedAnimalIndex = new Random().nextInt(animalOptions.length);

        // Load the animal image
        correctAnswer = "./animals/" + animalOptions[selectedAnimalIndex] + ".png";
        correctAnswerFullName = "./animals/" + animalOptions[selectedAnimalIndex] + "-full.png";
        ImageIcon animalImage = new ImageIcon(correctAnswer);
        imageLabel = new JLabel(new ImageIcon(animalImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        constraints.gridy = 1;
        add(imageLabel, constraints);

        // Animal combo box
        animalComboBox = new JComboBox<>(animalOptions);
        constraints.gridy = 2;
        add(animalComboBox, constraints);

        constraints.insets = new Insets(10, 0, 10, 0);

        playersInfo = new JTextArea(10, 20);
        playersInfo.setEditable(false);
        updatePlayersInfo();
        constraints.gridy = 3;
        add(playersInfo, constraints);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        constraints.gridy = 4;
        add(submitButton, constraints);
    }

    private void updatePlayersInfo() {
        StringBuilder sb = new StringBuilder();
        for (Player player : players) {
            sb.append(player.getName()).append(" - $").append(player.getMoney()).append("\n");
        }
        playersInfo.setText(sb.toString());
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedAnimal = (String) animalComboBox.getSelectedItem();
            Player currentPlayer = players.get(currentPlayerIndex);
    
            if (selectedAnimal.equals(animalOptions[selectedAnimalIndex]))  {
                ImageIcon fullImage = new ImageIcon(correctAnswerFullName);
                JLabel fullImageLabel = new JLabel(new ImageIcon(fullImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                JOptionPane.showMessageDialog(GamePanel.this, fullImageLabel, "Correct!", JOptionPane.INFORMATION_MESSAGE);
                int reward = new Random().nextInt(500) + 500;
                currentPlayer.addMoney(reward);
                JOptionPane.showMessageDialog(GamePanel.this, "Congratulations, " + currentPlayer.getName() + "! You won $" + reward + ".", "You won!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                currentPlayer.subtractMoney(100);
                JOptionPane.showMessageDialog(GamePanel.this, currentPlayer.getName() + ", you guessed wrong. You lost $100.", "Wrong answer", JOptionPane.ERROR_MESSAGE);
                if (currentPlayer.getMoney() == 0) {
                    JOptionPane.showMessageDialog(GamePanel.this, currentPlayer.getName() + " is out of the game.", "Out of game", JOptionPane.ERROR_MESSAGE);
                    players.remove(currentPlayer);
                    if (players.size() == 0) {
                        JOptionPane.showMessageDialog(GamePanel.this, "All players are out of the game. Game over.", "Game over", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }
                }
            }
        
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            updatePlayersInfo();
        
            // Check if all players have completed their turns and ask to continue playing or not
            if (currentPlayerIndex == 0) {
                int response = JOptionPane.showConfirmDialog(GamePanel.this, "All players won/lost something. Keep playing?", "Continue?", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        }
    }
    
}
