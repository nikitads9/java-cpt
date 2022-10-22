import java.util.*;
public class Palindrome { //Программа считывает полученные через терминал слова и проверяет является ли каждое слово палиндромом
    public static void main(String[] args)  {
        String[][] s = {{"Nice", "942208"}, {"Abu Dhabi", "1482816"}, {"Naples", "2186853"}, {"Vatican City", "572"}};
        millionsRounding(s);

        double[] res= otherSides(1);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }

        System.out.println(rps("paper", "paper"));
    }
    public static void millionsRounding (String[][] s){
        for (int i=0; i < s.length; i++){
            double number = Double.parseDouble(s[i][1]);
            number = Math.round(number / 1e6);
            number = number == 0? 0: number * 1e6;
            s[i][1] = String.valueOf((int) number);
            System.out.println(s[i][0] + "\s" + s[i][1]);
        }
    }
    public static double[]otherSides(int x) {
        double[] res = {(double)x*2, Math.sqrt(3) * x};
        return res;
    }

    public static String rps (String p1, String p2) {
        if (p1.equals(p2)) return "TIE";

        if (p1.equals("rock")) return p2.equals("paper") ? "Player 2 wins!" : "Player 1 wins!";
        if (p1.equals("paper")) return p2.equals("scissors") ? "Player 2 wins!" : "Player 1 wins!";
        if (p1.equals("scissors")) return p2.equals("rock") ? "Player 2 wins!" : "Player 1 wins!";
        return "";
    }
}