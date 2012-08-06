import java.util.*;

class GNYR09F
{
    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        new GNYR09F().run();
    }

    private void run()
    {
        int numCases = sc.nextInt();
        for (int i = 0; i < numCases; i++) {
            int caseNum = sc.nextInt();
            int n = sc.nextInt();
            int k = sc.nextInt();

            System.out.println(caseNum + " " + solve(n, k));
            System.out.flush();
        }
    }

    private static int solve(int n, int k)
    {
        int[] row1 = new int[k + 1];
        int[] row2 = new int[k + 1];
        int[] temp;

        row1[0] = 2;
        row2[0] = 3;
        row2[1] = 1;

        for (int i = 0; i < n - 2; i++) {
            for (int j = k; j > 0; j--) {
                row1[j] = row2[j] + row2[j - 1] + row1[j] - row1[j - 1];
            }
            row1[0] = row1[0] + row2[0];

            temp = row1;
            row1 = row2;
            row2 = temp;
        }

        return row2[k];
    }
}
