import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Admin on 09.10.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        PrintWriter out = new PrintWriter(new FileWriter("output.txt"));

        System.out.println("Выберите действие:");
        System.out.println("1. Разложение рационального числа в конечную обыкновенную непрерывную дробь");
        System.out.println("2. Разложение квадратичной иррациональности в периодическую обыкновенную непрерывную дробь");
        System.out.println("3. Решение диофантова уравнения");

        int met = sc.nextInt();

        if (met == 1) {

            String[] s = in.readLine().split(" ");

            int a = Integer.parseInt(s[0]);
            int b = Integer.parseInt(s[1]);

            List<Integer> q = Euk(a, b);

            if (a < 0 && b > 0 || a > 0 && b < 0)
                q.set(0, q.get(0) * (-1));
            out.print("[ " + q.get(0) + "; ");
            for (int i = 1; i < q.size(); i++) {
                out.print(q.get(i) + " ");
            }
            out.print("]");
            out.println("\n" + "Подходящие дроби:");
            out.println("(1, 0)");
            List<Integer> P = new ArrayList<>(), Q = new ArrayList<>();
            P.add(0);
            P.add(1);
            Q.add(1);
            Q.add(0);
            for (int i = 0; i < q.size(); i++) {
                int pi = (q.get(i) * P.get(i + 1)) + P.get(i);
                int qi = (q.get(i) * Q.get(i + 1)) + Q.get(i);
                P.add(pi);
                Q.add(qi);
                out.println("(" + pi + ", " + qi + ")");
            }
        } else if (met == 2) {
            String[] s = in.readLine().split(" ");
            int a = Integer.parseInt(s[0]);
            int b = Integer.parseInt(s[1]);
            int sq = (int) Math.sqrt(b);
            int k = 1;
            List<Integer> chis = new ArrayList<>();
            List<Integer> znam = new ArrayList<>();
            List<Integer> ans = new ArrayList<>();
            ans.add(sq + a);
            chis.add(1);
            znam.add((ans.get(0) - a) * (-1));
            int idx = 0;
            boolean fl = true;
            while (fl){
                int p1 = znam.get(znam.size() - 1) * (-1);
                int p2 = b - znam.get(znam.size() - 1) * znam.get(znam.size() - 1);
                k = chis.get(chis.size() - 1);
                if (gcd(k, p2) > 1){
                    int g = gcd(k, p2);
                    k /= g;
                    p2 /= g;
                }
                ans.add(k * (sq + p1) / p2);
                chis.add(p2);
                znam.add(p1 - (p2 * ans.get(ans.size() - 1)));

                for (int i = 0; i < chis.size() - 1; i++) {
                    if (chis.get(chis.size() - 1) == chis.get(i) && znam.get(znam.size() - 1) == znam.get(i)) {
                        fl = false;
                        idx = i;
                        break;
                    }
                }
            }
            out.print("[" + ans.get(0) + " ; ");
            for (int i = 1; i <= idx; i++) {
                out.print(ans.get(i) + " ");
            }
            out.print("{ ");
            for (int i = idx + 1; i < ans.size(); i++) {
                out.print(ans.get(i) + " ");
            }
            out.print("} " + "]");

            out.println("\n" + "Подходящие дроби:");
            out.println("(1, 0)");
            List<Integer> P = new ArrayList<>(), Q = new ArrayList<>();
            P.add(0);
            P.add(1);
            Q.add(1);
            Q.add(0);
            for (int i = 0; i < ans.size(); i++) {
                int pi = (ans.get(i) * P.get(i + 1)) + P.get(i);
                int qi = (ans.get(i) * Q.get(i + 1)) + Q.get(i);
                P.add(pi);
                Q.add(qi);
                out.println("(" + pi + ", " + qi + ")");
            }
        } else if (met == 3) {
            String[] s = in.readLine().split(" ");
            int a = Integer.parseInt(s[0]), b = Integer.parseInt(s[1]), c = Integer.parseInt(s[2]);
            int bb = b;
            if (b < 0)
                bb = -b;
            int d = Integer.parseInt(String.valueOf(BigInteger.valueOf(a).gcd(BigInteger.valueOf(bb))));
            if (!BigInteger.valueOf(c).mod(BigInteger.valueOf(d)).equals(BigInteger.ZERO))
                out.print("Решений нет");
            else {
                int a1 = a / d, b1 = bb / d, c1 = c / d;

                List<Integer> q = Euk(a1, b1);

                List<Integer> P = new ArrayList<>(), Q = new ArrayList<>();
                P.add(0);
                P.add(1);
                Q.add(1);
                Q.add(0);
                for (int i = 0; i < q.size() - 1; i++) {
                    int pi = (q.get(i) * P.get(i + 1)) + P.get(i);
                    int qi = (q.get(i) * Q.get(i + 1)) + Q.get(i);
                    P.add(pi);
                    Q.add(qi);
                }
                int st;
                if ((q.size() - 2) % 2 == 0)
                    st = 1;
                else
                    st = -1;
                int tmp = 1;
                if (b > 0)
                    tmp = -1;
                int tmp1 = 1;
                if (a < 0)
                    tmp1 = -1;
                int x = ((st * tmp1 * Q.get(Q.size() - 1) * c) / d);
                int y = ((st * tmp * P.get(P.size() - 1) * c) / d);

                if (b < 0)
                    b1 *= -1;
                out.print("x = " + x);
                if (b1 > 0)
                    out.println(" + " + b1 + "*t");
                else
                    out.println(" - " + Math.abs(b1) + "*t");
                out.print("y = " + y);
                if (a1 > 0)
                    out.print(" - " + a1 + "*t");
                else
                    out.print(" + " + Math.abs(a1) + "*t");
            }
        }

        in.close();
        out.close();
    }

    public static List<Integer> Euk(int a, int b) {
        List<Integer> ans = new ArrayList<>();
        if (a < 0 || b < 0) {
            if (a < 0)
                a = -a;
            if (b < 0)
                b = -b;
        }
        int r = 1;
        while (r != 0) {
            ans.add(a / b);
            r = a % b;
            int tmp = b;
            b = r;
            a = tmp;
        }
        return ans;
    }

    public static int gcd(int a, int b){
        a = Math.abs(a);
        b = Math.abs(b);
        if (b > a){
            int tmp = a;
            a = b;
            b = tmp;
        }
        while (b != 0) {
            a %= b;
            int tmp = a;
            a = b;
            b = tmp;
        }
        return a;
    }
}


