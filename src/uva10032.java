import java.util.Scanner;

/** Rename class to Main before submission to UVA **/
public class uva10032
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int tc = in.nextInt();
        while (tc-- > 0) {
            int n = in.nextInt();
            int[] arr = new int[n];
            int loop = n / 2 + 1;
            int sum = 0;
            for (int i = 0; i < arr.length; i++) {
                arr[i] = in.nextInt();
                sum += arr[i];
            }
            long[] dp = new long[sum + 1];
            dp[0] = 1L;
            for (int i = 0; i < n; i++)
                for (int j = sum; j >= arr[i]; j--)
                    dp[j] |= (dp[j - arr[i]]) << 1;

            for (int i = 0; i < dp.length; i++) {
                System.out.print(dp[i] + " ");
            }

            int min = 0;
            int max = Integer.MAX_VALUE;
            for (int i = 0; i < dp.length; i++) {
                for (int j = 0; j <= loop; j++) {
                    if ((dp[i] & (1L << j)) != 0L && Math.abs(2 * j - n) <= 1) {
                        if (Math.abs(sum - 2 * i) < max - min) {
                            max = Math.max(sum - i, i);
                            min = Math.min(sum - i, i);
                        }
                    }
                }
            }

            System.out.println(min + " " + max);
            if (tc != 0) System.out.println();
        }
    }
}
