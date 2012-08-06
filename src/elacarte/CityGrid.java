package elacarte;

import static java.lang.Math.*;
import java.io.*;
import java.util.*;

public class CityGrid
{
    private int[][] grid;
    private String MAP;
    private Point best_ll = new Point(0, 0);
    private Point best_ur = new Point(0, 0);

    private int numLinesInFile = -1;
    private int numCharsPerLineInFile = -1;

    @SuppressWarnings("unused")
    private void countLines()
        throws IOException
    {
        String filename = "city_grid.txt";
        LineNumberReader reader = new LineNumberReader(new FileReader(filename));
        int count = 0;
        String lineRead = "";
        while ((lineRead = reader.readLine()) != null) {
            if (numCharsPerLineInFile < 0)
                numCharsPerLineInFile = lineRead.replaceAll(" ", "").length();
        }
        count = reader.getLineNumber();
        reader.close();

        numLinesInFile = count;
    }

    public static void main(String[] args)
        throws IOException
    {
        CityGrid cg = new CityGrid();
        cg.countLines();
        cg.run();
    }

    private void run()
        throws FileNotFoundException
    {
        int TESTCASE = 2;

        switch (TESTCASE) {
        case 1:
            grid = new int[7][4];
            MAP = "sample";
            break;
        default:
            grid = new int[numLinesInFile][numCharsPerLineInFile];
            MAP = "city_grid";
            break;
        }

        Scanner sc = new Scanner(new FileInputStream(MAP + ".txt"));

        int lineNo = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Scanner scLine = new Scanner(line);
            int i = 0;
            while (scLine.hasNext()) {
                grid[lineNo][i] = scLine.next().equals("P") ? 1 : 0;
                i++;
            }
            lineNo++;
        }

        long start = System.nanoTime();

        int M = grid.length, N = grid[0].length;
        // Naive O(M^3 * N^3) algorithm implementation //
        // listing_one(M, N); // 133 955 microsec, or 138.773 ms

        // Efficient O(MN) algorithm implementation //
        listing_four(M, N); // 12 217 microsec, or 4.297 ms

        long end = System.nanoTime();

        // print statistics
        /**
         * best_ll: (9, 2)
         * best_ur: (19, 3)
         * area = 22
         */
        System.out.println("Execution time was   "
                        + ((end - start) / pow(10, 6)) + " ms.");
        System.out.println("top-left corner:     " + best_ll);
        System.out.println("bottom-right corner: " + best_ur);
        System.out.println("area =               " + area(best_ll, best_ur));
        System.out.println("(0,0) is top-leftmost corner; right-down are positive x-y directions");
        System.out.println();

        print_before(best_ll, best_ur);
        System.out.println();

        mark_rect_region(best_ll, best_ur);

        print_after();
    }

    private void listing_one(int M, int N)
    {
        System.out.println("=== Method 1 === ");
        for (int llx = 0; llx < N; llx++) {
            for (int lly = 0; lly < M; lly++) {
                for (int urx = llx; urx < N; urx++) {
                    for (int ury = lly; ury < M; ury++) {
                        Point ll = new Point(llx, lly);
                        Point ur = new Point(urx, ury);

                        if (area(ll, ur) > area(best_ll, best_ur)
                                        && all_ones(ll, ur)) {
                            System.out.println(ll + " " + ur + " "
                                            + area(ll, ur));
                            best_ll = ll;
                            best_ur = ur;
                        }
                    }
                }
            }
        }
    }

    private boolean all_ones(Point ll, Point ur)
    {
        for (int y = min(ll.y, ur.y); y <= max(ll.y, ur.y); y++) {
            for (int x = min(ll.x, ur.x); x <= max(ll.x, ur.x); x++) {
                if (grid[y][x] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void listing_four(int M, int N)
    {
        System.out.println("=== Method 4 === ");
        int[] c = new int[M + 1]; // extra element to close all rectangles
        // initialise
        for (int i = 0; i < c.length; i++) {
            c[i] = 0;
        }

        Stack<Point> rects = new Stack<Point>();
        // for each column R-to-L
        for (int x = N - 1; x >= 0; x--) {
            // track number of 1's to right of this column for each row
            update_cache(x, M, c);

            // width of widest opened rectangle
            int width = 0;

            // for each single column
            for (int y = 0; y < c.length; y++) {
                if (c[y] > width) {
                    rects.push(new Point(y, width));
                    width = c[y];
                } else if (c[y] < width) {
                    int y0 = -1, w0 = -1;
                    do {
                        Point pt = rects.pop();
                        y0 = pt.x;
                        w0 = pt.y;
                        if ((width * abs(y - y0)) > area(best_ll, best_ur)) {
                            best_ll.x = x;
                            best_ll.y = y0;

                            best_ur.x = abs(x + width - 1);
                            best_ur.y = abs(y - 1);
                        }
                        width = w0;
                    } while (c[y] < width);

                    width = c[y];
                    // popped an active "opening"?
                    if (width != 0) {
                        rects.push(new Point(y0, w0));
                    }
                }
            }
        }
    }

    private void print_after()
    {
        System.out.println(" === After === ");
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (grid[y][x] == 2) {
                    System.out.print(" _ ");
                } else
                    System.out.print(" " + grid[y][x] + " ");
            }
            System.out.println();
        }
    }

    private void mark_rect_region(Point best_ll, Point best_ur)
    {
        for (int y = min(best_ll.y, best_ur.y); y <= max(best_ll.y, best_ur.y); y++) {
            for (int x = min(best_ll.x, best_ur.x); x <= max(best_ll.x,
                best_ur.x); x++) {
                grid[y][x] = 2;
            }
        }
    }

    private void print_before(Point best_ll, Point best_ur)
    {
        System.out.println(" === Before === ");
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {

                int maxy = max(best_ll.y, best_ur.y);
                int miny = min(best_ll.y, best_ur.y);
                int maxx = max(best_ll.x, best_ur.x);
                int minx = min(best_ll.x, best_ur.x);

                if (miny <= y && y <= maxy && minx <= x && x <= maxx) {
                    System.out.print("[" + grid[y][x] + "]");
                } else
                    System.out.print(" " + grid[y][x] + " ");
            }
            System.out.println();
        }
    }

    private int area(Point ll, Point ur)
    {
        return (abs(ur.x - ll.x) + 1) * (abs(ur.y - ll.y) + 1);
    }

    private void update_cache(int x, int M, int[] c)
    {
        for (int y = M - 1; y >= 0; y--) {
            if (grid[y][x] != 0) {
                c[y]++;
            } else {
                c[y] = 0;
            }
        }
    }
}

class Point
{
    public int x, y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return String.format("(%d, %d)", x, y);
    }
}
