import static java.util.Arrays.*;

import java.util.*;

class ONP
{
    private final Scanner sc = new Scanner(System.in);

    // lowest to highest priority
    private final String[] precedence = new String[] { "+", "-", "*", "/", "^" };

    private boolean isIn(String s, String[] ss)
    {
        for (int i = 0; i < ss.length; i++) {
            if (ss[i].equals(s)) {
                return true;
            }
        }
        return false;
    }

    private int getPrecedence(String op)
    {
        for (int i = 0; i < precedence.length; i++) {
            if (precedence[i].equals(op)) {
                return i;
            }
        }
        return -1;
    }

    void run()
    {
        int numCases = sc.nextInt();

        for (int i = 0; i < numCases; i++) {
            // infix string
            char[] line = sc.next().toCharArray();

            // initialise empty stack
            Stack<Character> operator = new Stack<Character>();

            for (Character c : line) {
                if ('a' <= c && c <= 'z') {
                    // c = operand
                    System.out.print(c);
                    continue;
                } else {
                    // (l|r)paren
                    switch (c) {
                    case '(':
                        operator.push('(');
                        break;
                    case ')':
                        while (!operator.isEmpty() && operator.peek() != '(') {
                            System.out.print(operator.pop());
                            operator.pop(); // for left paren '('
                        }
                        break;
                    default:
                        break;
                    }

                    String s = c.toString();
                    if (isIn(s, precedence)) {
                        // token is an operator
                        if (operator.isEmpty()) {
                            operator.push(c);
                            continue;
                        } else {
                            // operator stack is non-empty
                            while (!operator.isEmpty()) {
                                Character topStack = operator.peek();
                                int predTopStack = getPrecedence(topStack
                                                .toString());
                                int predScannedChar = getPrecedence(c
                                                .toString());
                                if (predTopStack > predScannedChar) {
                                    System.out.print(operator.pop());
                                    continue;
                                } else {
                                    operator.push(c);
                                    break;
                                }
                            }
                        }
                    }
                }
            } // finished scanning string

            // empty operator stack
            while (!operator.isEmpty()) {
                System.out.print(operator.pop());
            }

            System.out.println();
        }
    }

    void debug(Object... os)
    {
        System.err.println(deepToString(os));
    }

    public static void main(String[] args)
    {
        new ONP().run();
    }
}
