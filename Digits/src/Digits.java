public class Digits {
    public static int mod ( int a, int b )
    {
        // \u000a
        // //System.out.println("HIIIII");
        return ( a - ( a / b ) * b  );
    }
    public static void main( String [] args )
    {
        System.out.println ( "Task 10" );
        System.out.println ( mod ( 5, 2 ) );
        System.out.println ( mod ( 218, 5 ) );
        System.out.println ( mod ( 6, 3 ) );
    }
}