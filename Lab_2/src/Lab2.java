import java.util.Scanner;

public class Lab2 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in); 

        double input [] = readInput(sc);
        Point3d point1 = new Point3d (input[0], input[1], input[2]);
        input = readInput(sc);
        Point3d point2 = new Point3d (input[0], input[1], input[2]);
        input = readInput(sc);
        Point3d point3 = new Point3d (input[0], input[1], input[2]);
        sc.close();
        if ( point1.equals(point2) || point1.equals(point3) || point2.equals(point3) ) {
            System.out.println("you have entered same point more than once");
        } else {
            System.out.println(computeArea(point1, point2, point3));
        }

    }
    public static double computeArea (Point3d point1, Point3d point2, Point3d point3) {
        double a = point1.distanceTo(point2);
        double b = point2.distanceTo(point3);
        double c = point3.distanceTo(point1);
        double p = (a + b + c) / 2;
        return Point3d.round(Math.sqrt(p*(p - a)*(p - b)*(p - c)), 2);
    }
    public static double[] readInput(Scanner sc) {
        double arr [] = new double[3];

        for (int i = 0; i < 3; i++) {
            arr[i] = sc.nextDouble();
        }
        return arr;
    }
}