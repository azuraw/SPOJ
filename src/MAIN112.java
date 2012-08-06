import static java.lang.Math.*;
import static java.util.Arrays.*;

import java.io.*;
import java.util.*;

class MAIN112
{
    private int[] A, C;
    private int N, P;

    int solve(int[] A, int[] C)
    {
        P = Integer.MAX_VALUE;

        // smart(A, C);
        bruteforce(null, A, C);

        System.out.println(P);
        return P;
    }

    private void smart(int[] A)
    {
        // quicksort in increasing numerical order
        Arrays.sort(A);

        for (int i = 1; i < A.length - 1; i++) {
            int total1 = abs(A[i + 1] - A[i]) * C[i + 1];
            int total2 = abs(A[i + 1] - A[i - 1]) * C[i + 1];

            if (total2 < total1) {
                // swap
                int temp = A[i];
                A[i] = A[i - 1];
                A[i - 1] = temp;
            }
        }

        // compute total cost
        int total = 0;
        for (int i = 1; i < A.length; i++) {
            total += abs(A[i - 1] - A[i]) * C[i];
        }
        P = min(P, total);

        System.err.println(total + ": " + Arrays.toString(A));
    }

    private void bruteforce(int[] currentPerm, int[] nextItems, int[] C)
    {
        int n = nextItems.length;
        if (n == 0) {
            int total = 0;
            for (int i = 1; i < currentPerm.length; i++) {
                total += abs(currentPerm[i] - currentPerm[i - 1]) * C[i];
            }
            P = min(P, total);
            // System.err.println(P + ": " + Arrays.toString(currentPerm));
        } else {
            for (int i = 0; i < n; i++) {
                bruteforce(
                    resize(currentPerm, nextItems[i]),
                    concat(Arrays.copyOfRange(nextItems, 0, i),
                        Arrays.copyOfRange(nextItems, i + 1, n)), C);
            }
        }
    }

    int[] resize(int[] array, int elem)
    {
        if (array == null) return new int[] { elem };
        int[] Es = Arrays.copyOf(array, array.length + 1);
        Es[array.length] = elem;
        return Es;
    }

    int[] concat(int[] A, int[] B)
    {
        int[] Cs = new int[A.length + B.length];
        System.arraycopy(A, 0, Cs, 0, A.length);
        System.arraycopy(B, 0, Cs, A.length, B.length);
        return Cs;
    }

    void run()
        throws IOException
    {
        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);
        String line = in.readLine();
        Scanner sc = new Scanner(line);
        int numCases = sc.nextInt();
        for (int i = 0; i < numCases; i++) {
            line = in.readLine();
            sc = new Scanner(line);
            N = sc.nextInt();

            line = in.readLine();
            sc = new Scanner(line);
            A = new int[N];
            for (int j = 0; j < N; j++) {
                A[j] = sc.nextInt();
            }

            line = in.readLine();
            sc = new Scanner(line);
            C = new int[N];
            for (int j = 0; j < N; j++) {
                C[j] = sc.nextInt();
            }

            solve(A, C); // A, C included for unit tests
        }
    }

    void debug(Object... os)
    {
        System.err.println(deepToString(os));
    }

    public static void main(String[] args)
        throws IOException
    {
        new MAIN112().run();
    }
}
