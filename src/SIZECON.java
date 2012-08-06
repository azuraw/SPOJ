import java.util.*;

// (PERL) 49: `$l=<>;while(<>){$i+=$_*($_>0&&$l>0);$l--;}print$i`
class SIZECON
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int result = 0;
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            String num = sc.next();
            if (num.charAt(0) != '-') result += Integer.parseInt(num);
        }
        System.out.println(result);
    }
}
