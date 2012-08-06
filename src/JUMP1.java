import static java.lang.Math.*;
import static java.util.Arrays.*;

import java.util.*;

/**
 * 1 2 3 1 2 3 00:00:00 00:00:01
 * 3 4 5 1 1 1 09:00:00 18:00:00
 * -1
 * Expected output:
 * 0.08
 * 2826.27
 */
class JUMP1
{
    private final Scanner sc = new Scanner(System.in);

    private double l1, l2, l3, m1, m2, m3;
    private final int[] startTime = new int[3], endTime = new int[3];

    private void solve()
    {
        double answer = 0.0f;
        int startTicks = startTime[0] * 3600 + startTime[1] * 60 + startTime[2];
        int endTicks = endTime[0] * 3600 + endTime[1] * 60 + endTime[2];
        double oldX = -1, oldY = -1, newX, newY;
        for (int time = startTicks; time <= endTicks; time++) {
            Point hour = new Point(l1 / 2, (double) time / 43200 * 2 * PI);
            Point minute = new Point(l2 / 2, (double) time / 3600 * 2 * PI);
            Point second = new Point(l3 / 2, (double) time / 60 * 2 * PI);

            // weighted average by mass
            newX = 1.0 / (m1 + m2 + m3)
                            * (second.x * m3 + minute.x * m2 + hour.x * m1);
            newY = 1.0 / (m1 + m2 + m3)
                            * (second.y * m3 + minute.y * m2 + hour.y * m1);

            if (time > startTicks) {
                answer += sqrt(pow(newX - oldX, 2) + pow(newY - oldY, 2));
            }

            oldX = newX;
            oldY = newY;
        }

        System.out.printf("%.2f\n", answer);
    }

    private void run()
    {
        for (;;) {
            int firstNum = sc.nextInt();
            if (firstNum == -1) break;

            l1 = firstNum;
            l2 = sc.nextDouble();
            l3 = sc.nextDouble();
            m1 = sc.nextDouble();
            m2 = sc.nextDouble();
            m3 = sc.nextDouble();

            String[] start = sc.next().split(":");
            for (int i = 0; i < start.length; i++) {
                startTime[i] = Integer.parseInt(start[i]);
            }
            String[] end = sc.next().split(":");
            for (int i = 0; i < end.length; i++) {
                endTime[i] = Integer.parseInt(end[i]);
            }

            solve();
            System.out.flush();
        }
    }

    private void debug(Object... os)
    {
        System.err.println(deepToString(os));
    }

    public static void main(String[] args)
    {
        new JUMP1().run();
    }
}

class Point
{
    public double x, y;

    public Point(double length, double angle)
    {
        this.x = length * sin(angle);
        this.y = length * cos(angle);
    }

    @Override
    public String toString()
    {
        return String.format("(%f, %f)", x, y);
    }
}
