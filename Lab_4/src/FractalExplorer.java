import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class FractalExplorer {
    private int screenSize;
    private JImageDisplay image;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    public FractalExplorer(int size) {
        this.screenSize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        image = new JImageDisplay(size, size, 1);
    }

    public void createAndShowGUI() {
        image.setLayout(new BorderLayout());
        JFrame frame = new JFrame("Fractal explorer");
        frame.add(this.image, BorderLayout.CENTER);
        JButton resetButton = new JButton("Reset");
        frame.add(resetButton, BorderLayout.SOUTH);

        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);
        MouseHandler click = new MouseHandler();
        image.addMouseListener(click);

        JPanel myPanel = new JPanel();
        frame.add(myPanel, BorderLayout.NORTH);
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(resetButton);
        frame.add(myBottomPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack ();
        frame.setVisible (true);
        frame.setResizable (false);
    }

    private void drawFractal(){
        for (int x = 0; x < screenSize; x++){
            for (int y = 0; y < screenSize; y++){
                double xCoord = FractalGenerator.getCoord (range.x, range.x + range.width, screenSize, x);
                double yCoord =  FractalGenerator.getCoord (range.y, range.y + range.height, screenSize, y);
                int num = fractal.numIterations(xCoord, yCoord);
                if (num == -1) {
                    this.image.drawPixel(x, y, 0);
                } else {
                    float hue = 0.7f + (float) num / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    this.image.drawPixel(x, y, rgbColor);
                }
            }
        }
        image.repaint();
    }

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
                      
           if (command.equals("Reset")) {
                fractal.getInitialRange(range);
                drawFractal();
            }
           
        }
    }
    
    private class MouseHandler extends MouseAdapter
    {

        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, screenSize, x);
            int y = e.getY();
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, screenSize, y);
            
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            
            drawFractal();
        }
    }
    
    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(800);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}