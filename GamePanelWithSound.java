import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;


// GamePanelWithSound.java
// Child class 1: GamePanelWithSound extends GamePanel
// Incorporates audio

public class GamePanelWithSound extends GamePanel {

    public GamePanelWithSound(LinkedList<Player> playersList) {
        super(playersList);

        String soundFileName = "./animals/" + animalOptions[selectedAnimalIndex] + ".wav";
        File soundFile = new File(soundFileName);

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playSound(soundFile);
            }
        });
    }


      // Method that plays audio
    private void playSound(File soundFile) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
