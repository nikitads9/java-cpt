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
     * Количество строк, оставшихся до полной отрисовки фрактала
     * необходимо для выключения интерфейса на время отрисовки
     */
    private int rowsRemaining;
    /**
     * Поле, содержащее объект с выпадающим списком 
     * для доступа к нему в разных методах класса.
     */
    private JComboBox<FractalGenerator> myComboBox;
    /**
     * Поле, содержащее объект с кнопкой сброса 
     * для доступа к ней в разных методах класса.
     */
    private JButton resetButton;
    /**
     * Поле, содержащее объект с кнопкой сохранения 
     * для доступа к ней в разных методах класса.
     */
    private JButton saveButton;
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

        MouseHandler click = new MouseHandler();
        image.addMouseListener(click);

        myComboBox = new JComboBox<>();
        /** Добавляем элементы в ComboBox **/
        FractalGenerator mandelbrotFractal = new Mandelbrot();
        myComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal = new Tricorn();
        myComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        myComboBox.addItem(burningShipFractal);
        
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
        saveButton = new JButton("Save");
        resetButton = new JButton("Reset"); //Создание объекта кнопки сброса


        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton);
        myBottomPanel.add(resetButton);
        frame.add(myBottomPanel, BorderLayout.SOUTH);
        
        /** Обработчиком события нажатия на кнопки выбирается ButtonHandler **/
        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);
        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);
        /** Обработчиком нажатия на элемент комбобокс будет также ButtonHandler **/
        ButtonHandler fractalChooser = new ButtonHandler();
        myComboBox.addActionListener(fractalChooser);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack ();
        frame.setVisible (true);
        frame.setResizable (false);
    }

    /**
     * Приватный вспомогательный метод для отображения фрактала. Этот метод проходит по каждой строке
     * изображения и вызывает работу FractalWorker для каждой строки.
     */
    private void drawFractal(){
        enableUI(false);
        rowsRemaining = screenSize;
        for (int x = 0; x < screenSize; x++){
            FractalWorker drawRow = new FractalWorker(x);
            drawRow.execute();
        }
    }

    public void enableUI(boolean b) {
        saveButton.setEnabled(b);
        resetButton.setEnabled(b);
        myComboBox.setEnabled(b);
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
            if (rowsRemaining == 0) {
            int x = e.getX();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, screenSize, x);
            int y = e.getY();
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, screenSize, y);
            
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            
            drawFractal();
            }
        }
    }

    /*
    * Класс, наследующий абстрактный класс SwingWorker.
    * Необходим для многопоточных вычислений, совершающихся
    * фоновом режиме.
    */
    private class FractalWorker extends SwingWorker<Object, Object> {
        private int yCoord;
        private int[] rgb;

        public FractalWorker(int y) {
            this.yCoord = y;

        }
        /*
        * Реализация метода из абстрактного родительского класса SwingWorker,
        * действия по вычислению значения фрактала для каждого пикселя строки,
        * описанные в этом методе, будут совершаться в отдельном потоке.
        */
        @Override
        public Object doInBackground() throws Exception {
            rgb = new int[screenSize];
            for (int i = 0; i < screenSize; i++){
                int num = fractal.numIterations(FractalGenerator.getCoord (range.x, range.x + range.width, screenSize, i), FractalGenerator.getCoord (range.y, range.y + range.height, screenSize, yCoord));
                if (num == -1) {
                    rgb[i] = 0;
                } else {
                    float hue = 0.7f + (float) num / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    rgb[i] = rgbColor;
                }
                image.drawPixel(i, yCoord, rgb[i]);
            }
            return null;
        }
        /*
        * Реализация метода из абстрактного родительского класса SwingWorker,
        * описывает действия, выполняемые по завершении doInBackground
        */
        @Override
        protected void done() {
            image.repaint(0, 0, yCoord, screenSize, 1); //добавляет отрисованную строку в объект
            rowsRemaining--;
            if (rowsRemaining == 0)
                enableUI(true);
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