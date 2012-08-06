import static java.lang.Math.*;
import static java.util.Arrays.*;

import java.io.*;
import java.util.*;

class TEST
{
    private final Scanner sc = new Scanner(System.in);

    void run()
    {
        while (sc.hasNext()) {
            int number = sc.nextInt();
            if (number == 42) {
                return;
            }
            System.out.println(number);
        }
    }

    void debug(Object... os)
    {
        System.err.println(deepToString(os));
    }

    public static void main(String[] args)
    {
        new TEST().run();
    }
}
