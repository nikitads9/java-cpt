import java.awt.image.*;
import javax.swing.*;
import java.awt.*;

/**
 * Класс изображения. Содержит поле с изображением
 * При инициализации создается и передается в поле объект с изображением. Для этого изображения устанавливается его размер.
 */
public class JImageDisplay extends JComponent {
    /**
     * Экземпляр буферизованного изображения.
     * Управляет изображением, содержимое которого можно отрисовывать.
     */ 
    private BufferedImage bufImage; 

    /**
     * Конструктор класса JImageDisplay.
     * Принимает ширину и высоту изображения,
     * а также тип цветового пространства.
     */ 
    public JImageDisplay (int width, int height, int TYPE_INT_RGB) {
        this.bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //создается объект изображения
        super.setPreferredSize(new Dimension(width, height)); // установление размера JComponent
    }
    /**
    * Метод для получения изображения.
    */
    public BufferedImage getImage() {
        return bufImage;
    }
    /**
    * Метод позволяет вывести изображение на экран. Этот метод переопределяет соответствующий в родительском классе.
    */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufImage, 0, 0, bufImage.getWidth(), bufImage.getHeight(), null);
    }

    /**
    * Метод установления всех пикселей в черный цвет.
    */
    public void clearImage(){
        //int[] blankArray = new int[getWidth() * getHeight()];
        //bufImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
        for (int x = 0; x < this.getWidth(); x++) 
            for (int y = 0; y < this.getHeight(); y++)
                bufImage.setRGB(x, y, 0);
    }

    /**
    * Метод устанавливает цвет указанного пикселя в соответствии с переданным  значением цвета.
    */
    public void drawPixel(int x, int y, int rgbColor) {
        bufImage.setRGB(x, y, rgbColor);
    }
}