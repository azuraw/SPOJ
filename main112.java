import static java.util.Arrays.*;
import static java.lang.Math.*;

import java.io.*;
import java.lang.*;
import java.util.*;

class main112 {
    private int[] A, C;
    private int N;
    private int[] cache;

	public static void main(String[] args) throws IOException
	{
	    new main112().run();
	}
	
	private void run() throws IOException
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
			
			cache = new int[1 << (A.length+5)];

            int result = Integer.MAX_VALUE;
            for (int j=0; j<A.length; j++) {
                boolean[] used = new boolean[A.length];
                used[j] = true;
                result = min(result, solve(used, A[j]));
            }

            System.out.println(result);
        }
    }

	private int solve(boolean[] used, int last)
    {
        int numSet = 0;
        int bitset = 0;
        for (int i=0; i<used.length; i++) {
            bitset |= (used[i]?1:0) << (i+4);
            if (used[i])
            {
                numSet++;
            }
        }
        bitset += last;

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
