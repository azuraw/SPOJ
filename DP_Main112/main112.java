import static java.util.Arrays.*;
import static java.lang.Math.*;

import java.io.*;
import java.lang.*;
import java.util.*;

class main112 {
    private int[][] cache;
    private int N;
    private int[] A, C;

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
			
			cache = new int[1<<N][N];
			for (int j=0; j<cache.length; j++) {
			    for (int k=0; k<cache[0].length; k++) {
			        cache[j][k] = 0;
		        }
		    }

            int result = Integer.MAX_VALUE;
            for (int j=0; j<A.length; j++) {
                result = min(result, solve(1<<j, j));
            }

            System.out.println(result);
        }
    }

    private int countSetBits(int v){
        int c;           // c accumulates the total bits set in v
        for (c = 0; v != 0; c++)
        {
            v &= v - 1;  // clear the least significant bit set
        }
        return c;
    }

    private boolean isBitPresent(int var, int pos)
    {
        return (var & (1<<pos)) != 0;
    }

    /**
        last is the index to last used value in A[],
        ie. range(element in A)<150(8 bits)
    */
	private int solve(int used, int last)
    {
        if (cache[used][last] > 0)
            return cache[used][last];

        int cur_pos = countSetBits(used);
        if (cur_pos == A.length)
            return 0;

        int mn = Integer.MAX_VALUE;
        for (int i=0; i<A.length; i++) {
            if (!isBitPresent(used, i))
            {
                int next_used = used | (1<<i);
                int subcost = solve(next_used, i);
                int additional = C[cur_pos] * abs(A[last] - A[i]);
                mn = min(mn, subcost + additional);
            }
        }

        cache[used][last] = mn;
        return mn;
    }
}
