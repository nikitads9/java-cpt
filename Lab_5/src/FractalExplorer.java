import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.image.*;

/**
 * Класс позволяет исследовать различные части фрактала с помощью создания и отображения
 * графического интерфейса Swing и обработки событий, вызванных различными взаимодействиями 
 * с пользователем.
 */
public class FractalExplorer {
    /** Целочисленный размер отображения - это ширина и высота отображения в пикселях. **/
    private int screenSize;
     /**
     * Ссылка JImageDisplay для обновления отображения с помощью различных методов как
     * фрактал вычислен.
     */
    private JImageDisplay image;
    /** Объект FractalGenerator для каждого типа фрактала.**/
    private FractalGenerator fractal;
    /**
     * Объект Rectangle2D.Double, который определяет диапазон
     * то, что мы в настоящее время показываем. 
     */
    private Rectangle2D.Double range;
    /**
     * Конструктор, который принимает размер отображения, сохраняет его и
     * инициализирует объекты диапазона и фрактал-генератора.
     */
    public FractalExplorer(int size) {
        this.screenSize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        image = new JImageDisplay(size, size, 1);
    }

    /**
     * Этот метод инициализирует графический интерфейс Swing с помощью JFrame, содержащего
     * Объект JImageDisplay и кнопку для очистки дисплея
     */
    public void createAndShowGUI() {
        image.setLayout(new BorderLayout());
        JFrame frame = new JFrame("Fractal explorer");
        frame.add(this.image, BorderLayout.CENTER);
        JButton resetButton = new JButton("Reset"); //Создание объекта кнопки сброса


        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);
        MouseHandler click = new MouseHandler();
        image.addMouseListener(click);

        JComboBox<FractalGenerator> myComboBox = new JComboBox<>();
        /** Добавляем элементы в ComboBox **/
        FractalGenerator mandelbrotFractal = new Mandelbrot();
        myComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal = new Tricorn();
        myComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        myComboBox.addItem(burningShipFractal);
        
        /** Обработчиком нажатия на элемент комбобокс будет ButtonHandler **/
        ButtonHandler fractalChooser = new ButtonHandler();
        myComboBox.addActionListener(fractalChooser);
        /**
         * Создаем панель и добавляем в нее комбобокс с пояснительной надписью Fractal
         * Расположение выпаающего списка - верхняя часть окна.
         */
        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel("Fractal:");
        myPanel.add(myLabel);
        myPanel.add(myComboBox);
        frame.add(myPanel, BorderLayout.NORTH);
        /**
         * Создаем кнопку Save 
         * располагаем ее в нижней части окна.
         */
        JButton saveButton = new JButton("Save");
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton);
        myBottomPanel.add(resetButton);
        frame.add(myBottomPanel, BorderLayout.SOUTH);
        
        /** Обработчиком события нажатия на кнопку выбирается ButtonHandler **/
        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack ();
        frame.setVisible (true);
        frame.setResizable (false);
    }

    /**
     * Приватный вспомогательный метод для отображения фрактала. Этот метод проходит
     * через каждый пиксель на дисплее и вычисляет количество
     * итераций для соответствующих координат во фрактале
     * Область отображения. Если количество итераций равно -1, установит цвет пикселя.
     * в черный. В противном случае выберет значение в зависимости от количества итераций.
     * Обновит дисплей цветом для каждого пикселя и перекрасит
     * JImageDisplay, когда все пиксели нарисованы.
     */
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

    /**
     * Внутренний класс для обработки событий ActionListener.
     */
    @SuppressWarnings("unchecked")
    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
            if (e.getSource() instanceof JComboBox) {
                JComboBox<String> source = (JComboBox<String>) e.getSource();
                fractal = (FractalGenerator) source.getSelectedItem();
                fractal.getInitialRange(range);
                drawFractal();    
            } else {
           switch (command) {

            case "Reset":
                fractal.getInitialRange(range);
                drawFractal();
                break;

            case "Save":
                JFileChooser save = new JFileChooser();
                FileFilter extensionFilter = new FileNameExtensionFilter("PNG Images", "png");
                save.setFileFilter(extensionFilter);
                /**
                 * Убирает опцию AllFiles при сохранении,
                 * чтобы не допустить сохранение в ином формате.
                 */
                save.setAcceptAllFileFilterUsed(false);
                int saved = save.showSaveDialog(image);
                if (saved == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = save.getSelectedFile();
                    if(!file.getAbsolutePath().endsWith(".png")){
                        file = new java.io.File(save.getSelectedFile() + ".png");
                    }
                    try {
                        BufferedImage displayImage = image.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                        JOptionPane.showMessageDialog(image, "Image saved");
                    }
                    catch (Exception ex){
                        JOptionPane.showMessageDialog(image, ex.getMessage(), "Cannot Save Image",
                        JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    break;
                }
            }
        }
        }
    }
    
    /**
     * Внутренний класс для обработки событий MouseAdapter.
     */
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
    
    /**
     * Точка входа
     */
    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(800);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}