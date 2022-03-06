import java.awt.image.*;
import javax.swing.*;
import java.awt.*;

public class JImageDisplay extends JComponent {
    private BufferedImage bufImage; //сохраненное в буфере изображение

    public JImageDisplay (int width, int height, int TYPE_INT_RGB) {
        this.bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        super.setPreferredSize(new Dimension(width, height));
    }

    public BufferedImage getImage() {
        return bufImage;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufImage, 0, 0, bufImage.getWidth(), bufImage.getHeight(), null);
    }
    public void clearImage(){
        //int[] blankArray = new int[getWidth() * getHeight()];
        //bufImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
        for (int x = 0; x < this.getWidth(); x++) 
            for (int y = 0; y < this.getHeight(); y++)
                bufImage.setRGB(x, y, 0);
    }

    public void drawPixel(int x, int y, int rgbColor) {
        bufImage.setRGB(x, y, rgbColor);
    }
}