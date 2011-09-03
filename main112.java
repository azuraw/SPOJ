import static java.util.Arrays.*;
import static java.lang.Math.*;

import java.io.*;
import java.lang.*;
import java.util.*;

class main112 {
    private int[] A, C;
    private int N;
    private TreeMap<Pair, Integer> cache = new TreeMap<Pair, Integer>();
    private int total = Integer.MAX_VALUE;
    private long startTime, endTime;

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

            //sort(A);
            //assert(A.length == C.length) : "Error: #A != #C";

            //solve();
            int result = Integer.MAX_VALUE;
            //System.out.print("answer: ");
            for (int j=0; j<A.length; j++) {
                boolean[] used = new boolean[A.length];
                used[j] = true;
                int answer = solve(used, A[j]);

                result = min(result, answer);
                //System.out.print(answer + " ");
            }
            //System.out.println();
            System.out.println(result);
        }
    }


    private int solve(boolean[] used, int last)
    {
        Pair p = new Pair(used, last);
        if (cache.containsKey(p))
        {
            return cache.get(p);
        }

        int current_position = countUsed(used);
        if (current_position == A.length)
            return 0;

        int mn = Integer.MAX_VALUE;
        for (int i=0; i<A.length; i++) {
            if (!used[i])
            {
                boolean[] next_used = new boolean[used.length];
                System.arraycopy(used, 0, next_used, 0, used.length);
                next_used[i] = true;

                int subcost = solve(next_used, A[i]);
                int additional = C[current_position] * abs(last - A[i]);
                mn = min(mn, subcost + additional);
            }
        }

        cache.put(p, mn);
        //System.out.println(mn);
        return mn;
    }

    class Pair implements Comparable<Pair> {
        boolean[] used;
        Integer last;

        public Pair(boolean[] used, int last) {
            this.used = used;
            this.last = last;
        }

        public int compareTo(Pair that) {
            // lexicographical order
            if (Arrays.equals(used, that.used))
            {
                return last.compareTo(that.last);
            }
            else
            {
                Integer a = countUsed(used);
                Integer b = countUsed(that.used);

                if (a.equals(b))
                {
                    for (int i=0; i<used.length; i++ ) {
                        if (!used[i] && that.used[i])
                        {
                            return -1;
                        }
                        else if (used[i] && !that.used[i])
                        {
                            return 1;
                        }
                    }
                    return 0;
                }
                else
                {
                    return a.compareTo(b);
                }
            }
        }
    }

    private int countUsed(boolean[] used)
    {
        int result = 0;
        for(int i=0; i<used.length; i++)
            if (used[i])
            {
                result++;
            }
        return result;
    }


    public void solveOld() {
        startTime = System.nanoTime();
        //System.out.println("#integers = " + A.length);
        Integer[] idx = new Integer[C.length];
        for (int i = 0; i < idx.length; ++i)
            idx[i] = i;
        Arrays.sort(idx, new Comparator<Integer>() {
            @Override
            public int compare(final Integer o1, final Integer o2)
            {
                return new Integer(C[o2]).compareTo(C[o1]);
            }
        });
//        System.out.println(Arrays.toString(idx));
        int[][][] X = new int[A.length][A.length][A.length];
        for(int i=0; i<A.length; i++)
            for(int j=0; j<A.length; j++)
                X[i][j][j] = abs(A[i] - A[j]) * C[j];
        for(int i=0; i<A.length; i++)
            for(int j=0; j<A.length; j++)
                for(int k=0; k<A.length; k++)
                    if (X[i][j][k] > 0)
                    {
                        //System.out.println(String.format("X[%d][%d][%d] = %d", i,j,k,X[i][j][k]));
                        continue;
                    }
        int[] result = new int[A.length];
        boolean[] used = new boolean[A.length];
        int lowestDiff = Integer.MAX_VALUE, lowestI = -1, lowestJ = -1;
        for(int i=0; i<A.length; i++)
            for(int j=0; j<A.length; j++)
                if (0 < X[i][j][idx[0]] && X[i][j][idx[0]] < lowestDiff)
                {
                    lowestDiff = X[i][j][idx[0]];
                    lowestI = i;
                    lowestJ = j;
                }
        used[lowestI] = true;
        used[lowestJ] = true;
        result[lowestI] = A[lowestI];
        result[lowestJ] = A[lowestJ];
        for(int a=1; a<A.length-1; a++)
        {
            lowestDiff = Integer.MAX_VALUE;
            lowestI = lowestJ;
            lowestJ = -1;
            for(int i=0; i<A.length; i++)
                for(int j=0; j<A.length; j++)
                {
                    int first = min(lowestI, j), second = max(lowestI, j);

                    if ( X[first][second][ idx[a] ] < lowestDiff && !used[j])
                    {
                        lowestDiff = X[first][second][ idx[a] ];
                        lowestJ = j;
                    }
                }
            used[lowestJ] = true;
            result[lowestJ] = A[lowestJ];
        }
        printTotal(result);
    }

    private void printTotal(int[] result)
    {
        /*
        for(int i=0; i<result.length; i++)
        {
            System.out.print(result[i] + " ");
        }
        */
        int total = 0;
        for(int i=1; i<result.length; i++)
        {
            total += abs(result[i] - result[i-1]) * C[i];
        }
        System.out.println(total);
        endTime = System.nanoTime();

        final long duration = endTime - startTime;
        //System.out.println("time taken: 10 * " + (duration / 10E6) + " ms");
    }

    private void spawnChildren(int level, int secondLast, int parentTotal)
    {
        for(int i=0; i<A.length; i++)
        {
            boolean[] used = new boolean[A.length];
            used[i] = true;

            int thisTotal = parentTotal;
            if (level > 0)
            {
                thisTotal += abs(A[secondLast] - A[i]) * C[level];
            }

            compute(level+1, used, secondLast, i, thisTotal);
        }
    }

    private void compute(int level, boolean[] used, int secondLast, int last, int parentTotal)
    {
        if (parentTotal > total)
        {
            // alpha-beta pruning
            return;
        }
        else if (level == A.length-1)
        {
            // single complete permutation
            int thisTotal = parentTotal + abs(A[last] - A[secondLast]) * C[level];
            total = min(total, thisTotal);
            return;
        }
        else
        {
            spawnChildren(level, last, parentTotal);
            return;
        }
    }
}