import static java.lang.Math.*;
import static java.util.Arrays.*;

import java.io.*;
import java.util.*;

class SAMER08D
{
    private int solve(int k, String s1, String s2)
    {
        int[][] dp = new int[1001][1001];
        int[][] w = new int[1001][1001];

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    w[i][j] = w[i - 1][j - 1] + 1;
                }

                if (w[i][j] >= k) {
                    for (int z = k; z <= w[i][j]; z++) {
                        dp[i][j] = max(dp[i][j], dp[i - z][j - z] + z);
                    }
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    private void run()
        throws IOException
    {
        long start = System.currentTimeMillis();

        Scanner sc = new Scanner(new FileReader(
                        "/Users/lwy08/Downloads/sample.in"));
        PrintWriter pw = new PrintWriter(new FileWriter(
                        "/Users/lwy08/Downloads/sample.out"));

        // Scanner sc = new Scanner(System.in);
        // PrintWriter pw = new PrintWriter(System.out);

        int K = -1;
        while ((K = sc.nextInt()) != 0) {
            String s1 = sc.next();
            String s2 = sc.next();

            int result = solve(K, s1, s2);
            System.out.println(result);
            pw.println(result);
        }

        sc.close();
        pw.close();

        System.err.println((System.currentTimeMillis() - start) / 1000.0f);
    }

    private void debug(Object... os)
    {
        System.err.println(deepToString(os));
    }

    public static void main(String[] args)
        throws IOException
    {
        new SAMER08D().run();
    }
}
