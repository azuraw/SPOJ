import java.math.*;
import java.util.*;

class FCTRL2
{
    public static void main(String[] args)
    {
        new FCTRL2().run();
    }

    private void run()
    {
        Scanner sc = new Scanner(System.in);
        int lineCount = sc.nextInt();
        for (int i = 0; i < lineCount; i++) {
            System.out.println(factorials[sc.nextInt()]);
        }
    }

    public static final int MAX_SIZE = 100;
    public static BigInteger[] factorials = new BigInteger[MAX_SIZE + 1];
    static {
        factorials[0] = BigInteger.ONE;
        init();
    }

    public static void init()
    {
        for (int i = 1; i <= MAX_SIZE; i++) {
            factorials[i] = factorials[i - 1].multiply(new BigInteger("" + i));
        }
    }
}
