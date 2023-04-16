// WindowSettings.java

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;


// WindowSettings.java
// Child class 2: WindowSettings extends JPanel
// Incorporates images

public class WindowSettings extends JPanel {
    private Image backgroundImage;

    public WindowSettings(String imagePath) {
        try {
            URL imageURL = getClass().getResource(imagePath);
            backgroundImage = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //  // Overridden method: paintComponent (from JPanel)
    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}


