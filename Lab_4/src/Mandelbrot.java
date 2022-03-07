import java.awt.geom.Rectangle2D;

/**
* Этот класс является делегатом абстрактного класса FractalGenerator.
* В нем реализованы методы:  
* getInitialRange 
* numIterations
*/
public class Mandelbrot extends FractalGenerator {

    public static final int MAX_ITERATIONS = 2000;

    /**
    * Метод устанавливает базовые значения x, y для формулы Мандельброта,
    * а также ширину и высоту увеличения области фрактала.
    */
    public void getInitialRange(Rectangle2D.Double range){
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    /**
    * Данный метод вычисляет количество итераций для каждого пикселя
    * в соответствии с итерациями фрактала Мандельброта. При этом 
    * модуль комплексного числа мандельброта не должен превышать 2.
    */
    public int numIterations (double x, double y) {
        int i = 0;
        ComplexNums num = new ComplexNums(x, y);
        while ( i < MAX_ITERATIONS && ((num.Zreal * num.Zreal) + (num.Zimaginary * num.Zimaginary)) < 4) {
            num.iteration();
            i++;
        }
        if (i == MAX_ITERATIONS)
            return -1;
        else 
            return i; 
    }
}