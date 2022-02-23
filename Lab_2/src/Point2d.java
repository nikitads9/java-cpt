// класс точки в двумерном пространстве
public class Point2d {
    //координата X
        protected double xCoord;
    // координата Y 
        protected double yCoord;
    // Конструктор инициализации
        public Point2d ( double x, double y) {
            xCoord = x;
            yCoord = y;
        }
    // Конструктор объявления.
        public Point2d () {
    //заполняет поля нулевыми значениями
            this(0, 0);
        }
    // вызов координаты X 
        public double getX () {
            return xCoord;
        }
    // вызов координаты Y 
        public double getY () {
            return yCoord;
        }
    // установка значения координаты X.
        public void setX ( double val) {
            xCoord = val;
        }
    //  установка значения координаты Y. 
        public void setY ( double val) {
            yCoord = val;
        }
    }
