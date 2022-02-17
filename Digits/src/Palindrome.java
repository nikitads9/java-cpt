public class Palindrome {
    public static void main(String[] args)  {
        //String[] initial = {"meme", "lol", "tenet"};

        for (int i = 0; i < args.length; i++) { //цикл по элеметам массива строк, переданному при вызове функции в терминале

                String s = args[i]; //запписываем в s новое слово
                String reversed = reverseString(s);
                System.out.println(s);
                /// System.out.println(reversed);
                boolean check = isPalindrome(s, reversed);
                String res = check? "Palindrome": "Not a palindrome" ;
                System.out.println(res);
        } 
    }
    public static String reverseString(String s) { //функция возвращает инвертированную строку
        String reversed = ""; //Пустая строка в начале
        for (int i = s.length() - 1; i >= 0; i--) { //обратный цикл с постдекрементом
            reversed += s.charAt(i); //в строку реверсд записываются char'ы. Так как строка - массив чаров, то можно при помощи конкатенации собрать строку.
        }
        return reversed;
    }
    public static boolean isPalindrome(String s, String reversed) { //метод возвращает правда или нет
        if (s.equals(reversed)) { //метод проверки равенства применяется к объекту s, аршументом передается сравниваемая строка
            return true;
        }
        return false;  

    }
}