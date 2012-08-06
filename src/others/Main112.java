package others;

import java.util.*;

import static java.util.Arrays.*;
import static java.lang.Math.*;

public class Main112
{
    private static int[] A, C;
    private static int total = Integer.MAX_VALUE;

    private void run()
    {
        final long startTime = System.nanoTime();
        A = new int[] { 1, 20, 21, 40, 1, 20, 21, 40, 1, 20, 21, 40, 13, 14, 15 };
        C = new int[] { 0, 500, 1, 1, 0, 500, 1, 1, 0, 500, 1, 1, 2, 2, 2 };
        sort(A);

        bruteforce(null, A, 0);
        final long endTime = System.nanoTime();

        final long duration = endTime - startTime;
        System.out.println(total + " / time taken: " + (duration / 10E6)
                        + " ms");
    }

    public static void main(String[] args)
    {
        new Main112().run();
    }

    private void bruteforce(int[] currentPerm, int[] nextItems, int totalSoFar)
    {
        int n = nextItems.length;
        if (n == 0) {
            int thisTotal = abs(currentPerm[currentPerm.length - 1]
                            - currentPerm[currentPerm.length - 2])
                            * C[currentPerm.length - 1];
            total = min(total, totalSoFar + thisTotal);

            // System.err.println(total + ": " + Arrays.toString(currentPerm));
        } else {
            for (int i = 0; i < n; i++) {
                int[] newPerm = resize(currentPerm, nextItems[i]);

                int thisTotal = 0;
                if (currentPerm != null && currentPerm.length >= 2) {
                    thisTotal += abs(currentPerm[currentPerm.length - 1]
                                    - currentPerm[currentPerm.length - 2])
                                    * C[currentPerm.length - 1];
                }

                if (totalSoFar + thisTotal > total) continue;

                bruteforce(
                    newPerm,
                    concat(Arrays.copyOfRange(nextItems, 0, i),
                        Arrays.copyOfRange(nextItems, i + 1, n)), totalSoFar
                                    + thisTotal);
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
}