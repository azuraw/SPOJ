import static java.lang.Math.*;
import static java.util.Arrays.*;

import java.io.*;
import java.util.*;

class DOUGHNUT
{
    private static int t, c, k, w;
    Scanner sc = new Scanner(System.in);

    void solve()
    {
        c = sc.nextInt();
        k = sc.nextInt();
        w = sc.nextInt();

        if (c * w <= k) {
            System.out.printf("yes\n");
        } else {
            System.out.printf("no\n");
        }
    }

    void run()
    {
        int caseN = sc.nextInt();
        for (int caseID = 1; caseID <= caseN; caseID++) {
            solve();
            System.out.flush();
        }
    }

    void debug(Object... os)
    {
        System.err.println(deepToString(os));
    }

    public static void main(String[] args)
    {
        new DOUGHNUT().run();
    }
}
