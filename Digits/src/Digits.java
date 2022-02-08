public class Digits {
    public static int mod ( int a, int b )
    {
        // \u000a
        // //System.out.println("HIIIII");
        return ( a - ( a / b ) * b  );
    }
    public static void main( String [] args )
    {
        System.out.println ( "Hello World!!!" );
    }
}