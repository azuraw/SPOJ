import java.util.*;

import junit.framework.*;
import junit.textui.*;

public class MAIN112_test
    extends TestCase
{
    public MAIN112_test(String name)
    {
        super(name);
    }

    public static void main(String[] args)
    {
        TestRunner.run(MAIN112_test.class);
    }

    private int[] initArray(String a)
    {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        Scanner sc = new Scanner(a);
        while (sc.hasNext()) {
            temp.add(sc.nextInt());
        }
        int[] result = new int[temp.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = temp.get(i);
        }
        return result;
    }

    public void testOne()
        throws Exception
    {
        String a = "1 8 3 6 5", c = "1 2 3 4 5";
        assertEquals(24, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testTwo()
    {
        String a = "123 11 131 19", c = "110 148 119 7";
        assertEquals(13616, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testThree()
    {
        String a = "136 24 145 33 3 41 12", c = "20 59 29 67 38 76 46";
        assertEquals(5490, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testFour()
    {
        String a = "93 63 102 72", c = "81 119 89 128";
        assertEquals(4092, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testFive()
    {
        String a = "77 115 85 124 94 132", c = "141 111 150 120 8 128";
        assertEquals(5854, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testSix()
    {
        String a = "25 146 34 4 43 13 51", c = "60 30 68 39 77 47 86";
        assertEquals(5776, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testSeven()
    {
        String a = "65 103 73 111 82 52", c = "61 99 69 108 78 116";
        assertEquals(5345, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testEight()
    {
        String a = "95 133 104 142 112 1 121", c = "130 18 138 27 147 35 5";
        assertEquals(4022, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testNine()
    {
        String a = "52 23 61 31", c = "40 78 49 19";
        assertEquals(1824, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testTen()
    {
        String a = "66 36 74 45 83 53 92", c = "100 71 109 79 117 88 126";
        assertEquals(5400, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testEleven()
    {
        String a = "105 143 114 2 122 10", c = "19 139 28 148 36 7";
        assertEquals(5539, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testTwelve()
    {
        String a = "1 20 21 40", c = "0 500 1 500";
        assertEquals(19001, new MAIN112().solve(initArray(a), initArray(c)));
    }

    public void testThirteen()
    {
        String a = "1 20 21 40", c = "0 500 1 1";
        assertEquals(558, new MAIN112().solve(initArray(a), initArray(c)));
    }
}
