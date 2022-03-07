/**
 * Класс для вычисления итераций преобразования комплексного 
 * числа для вычисления фрактала Мандельброта.
 */
public class ComplexNums {
    private double x;
    private double y;
    public double Zreal;
    public double Zimaginary;
    /**
     * Конструктор инициализации.
     * Устанаваливает значения по умолчанию.
     */
    public ComplexNums(double x, double y){
        this.x = x;
        this.y = y;
        this.Zreal = 0;
        this.Zimaginary = 0;
    }
    /**
     * Действия на каждой итерации.
     */
    public void iteration(){
        double Zrealupdated = (Zreal * Zreal) - (Zimaginary * Zimaginary) + x;
        double Zimaginaryupdated = 2 * Zreal * Zimaginary + y;
        Zreal = Zrealupdated;
        Zimaginary = Zimaginaryupdated;
    }
}