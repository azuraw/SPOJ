import static java.util.Arrays.*;
import static java.lang.Math.*;

import java.io.*;
import java.lang.*;
import java.util.*;

class main112 {
    private int[] A, C;
    private int[] cache = new int[(int) pow(2,15)];

    public static void main(String[] args) {
        new main112().run();
    }

    private void run() {
        A = new int[] {1,20,21,40};
        C = new int[] {0,500,1,1};

        int result = Integer.MAX_VALUE;
        for (int i=0; i<A.length; i++) {
            boolean[] used = new boolean[A.length];
            used[i] = true;
            int answer = solve(used, A[i]);
            result = min(result, answer);
        }
        System.out.println(result);
    }

    private int solve(boolean[] used, int last)
    {
        int numSet = 0;
        int bitset = 0;
        for (int i=0; i<used.length; i++) {
            bitset |= (used[i]?1:0) << i;
            if (used[i])
                numSet++;
        }
        bitset |= last << used.length;

        if (cache[bitset] > 0)
        {
            return cache[bitset];
        }

        if (numSet == A.length)
            return 0;

        int mn = Integer.MAX_VALUE;
        for (int i=0; i<A.length; i++) {
            if (!used[i])
            {
                boolean[] next_used = new boolean[used.length];
                System.arraycopy(used, 0, next_used, 0, used.length);
                next_used[i] = true;

                int subcost = solve(next_used, A[i]);
                int additional = C[numSet] * abs(last - A[i]);
                mn = min(mn, subcost + additional);
            }
        }
        cache[bitset] = mn;
        return mn;
    }
}
