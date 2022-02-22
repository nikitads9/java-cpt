import java.math.BigDecimal;
import java.math.RoundingMode;
public class Point3d {
    private double xCoord;
    private double yCoord;
    private double zCoord;

    public Point3d(double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    public Point3d() {
        this(0, 0, 0);
    }
    public double getX () {
        return xCoord;
    }

    public double getY () {
        return yCoord;
    }

    public double getZ () {
        return zCoord;
    }

    public void setX (double value) {
        xCoord = value;
    }

    public void setY (double value) {
        yCoord = value;
    }

    public void setZ ( double value) {
        zCoord = value;
    }

    public boolean equals (Point3d point) {
        if (point.xCoord == xCoord && point.yCoord == yCoord && point.zCoord == zCoord) {
            return true;
        }
        return false;
    }
    public double distanceTo (Point3d point) {
        return round(Math.sqrt(Math.pow(point.xCoord - xCoord, 2) + Math.pow(point.yCoord - yCoord, 2) + Math.pow(point.zCoord - zCoord, 2)), 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
     
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}