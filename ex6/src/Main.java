import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.zip.Inflater;

import static java.lang.Math.min;
import static java.lang.Math.negateExact;

/**
 * Created by Admin on 23.10.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        PrintWriter out = new PrintWriter(new FileWriter("output.txt"));

        System.out.println("Выберите:");
        System.out.println("1. Решение уравнения Пелля");
        System.out.println("2. Решение систем линейных уравнений над простым конечным полем");
        System.out.println("3. Представление числа в виде суммы двух квадратов");
        int met = sc.nextInt();

        if (met == 1) {

            long b = Integer.parseInt(in.readLine());
            long sq = (int) Math.sqrt(b);
            long k = 1;
            List<Long> chis = new ArrayList<>();
            List<Long> znam = new ArrayList<>();
            List<Long> ans = new ArrayList<>();
            ans.add(sq);
            chis.add((long) 1);
            znam.add(ans.get(0) * (-1));
            boolean fl = true;

            while (fl) {
                long p1 = znam.get(znam.size() - 1) * (-1);
                long p2 = b - znam.get(znam.size() - 1) * znam.get(znam.size() - 1);
                k = chis.get(chis.size() - 1);
                if (gcd(k, p2) > 1) {
                    long g = gcd(k, p2);
                    k /= g;
                    p2 /= g;
                }
                ans.add(k * (sq + p1) / p2);
                chis.add(p2);
                znam.add(p1 - (p2 * ans.get(ans.size() - 1)));

                if (ans.get(ans.size() - 1) == ans.get(0) * 2)
                    break;
            }

            int sz = ans.size() - 1;
            if (ans.size() % 2 == 0) {
                for (int ii = 0; ii < sz; ii++) {
                    long p1 = znam.get(znam.size() - 1) * (-1);
                    long p2 = b - znam.get(znam.size() - 1) * znam.get(znam.size() - 1);
                    k = chis.get(chis.size() - 1);
                    if (gcd(k, p2) > 1) {
                        long g = gcd(k, p2);
                        k /= g;
                        p2 /= g;
                    }
                    ans.add(k * (sq + p1) / p2);
                    chis.add(p2);
                    znam.add(p1 - (p2 * ans.get(ans.size() - 1)));
                }
            }

            List<Long> P = new ArrayList<>(), Q = new ArrayList<>();
            P.add((long) 0);
            P.add((long) 1);
            Q.add((long) 1);
            Q.add((long) 0);
            for (int i = 0; i < ans.size(); i++) {
                long pi = (ans.get(i) * P.get(i + 1)) + P.get(i);
                long qi = (ans.get(i) * Q.get(i + 1)) + Q.get(i);
                P.add(pi);
                Q.add(qi);
            }

            out.print("(" + P.get(P.size() - 2) + ", " + Q.get(Q.size() - 2) + ")");

        } else if (met == 2) {
            int p = Integer.parseInt(in.readLine());
            String[] nm = in.readLine().split(" ");
            int m = Integer.parseInt(nm[0]); // количество уравнений
            int n = Integer.parseInt(nm[1]); // количество неизвестных
            ArrayList<ArrayList<Integer>> a = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                String[] s = in.readLine().split(" ");
                ArrayList<Integer> aa = new ArrayList<>();
                for (int j = 0; j < s.length; j++)
                    aa.add(Integer.parseInt(s[j]));
                a.add(aa);
            }

            int numb = min(m, n);

            //boolean fl = true;
            for (int j = 0; j < numb; j++) {
                if (a.get(j).get(j) != 1) {
                    int x = 1;
                    try {
                        x = BigInteger.valueOf(a.get(j).get(j)).modInverse(BigInteger.valueOf(p)).intValue();
                    } catch (ArithmeticException e) {
                        out.print("Решений нет");
                        out.close();
                        return;
                        /*fl = false;
                        for (int i = 0; i < numb; i++) {
                            if (a.get(i).get(j) != 0 && a.get(i).get(j) != 0) {
                                List<Integer> tmp;
                                tmp = a.get(i);
                                a.set(i, a.get(j));
                                a.set(j, (ArrayList<Integer>) tmp);
                                j = -1;
                            }
                        }*/
                    }
                    //if (fl) {
                        for (int ii = 0; ii < n + 1; ii++) {
                            a.get(j).set(ii, a.get(j).get(ii) * x % p);
                            while (a.get(j).get(ii) < 0)
                                a.get(j).set(ii, a.get(j).get(ii) + p);
                        }
                    //}
                }
                //if (fl) {
                    for (int jj = j + 1; jj < m; jj++) {
                        if (a.get(jj).get(j) != 0) {
                            int xx = a.get(jj).get(j);
                            for (int ii = 0; ii < n + 1; ii++) {
                                a.get(jj).set(ii, (a.get(jj).get(ii) - a.get(j).get(ii) * xx) % p);
                                while (a.get(jj).get(ii) < 0)
                                    a.get(jj).set(ii, a.get(jj).get(ii) + p);
                            }
                        }
                    }
                //}
                //fl = true;
            }

            for (int i = 0; i < numb; i++) {
                for (int j = i + 1; j < numb; j++) {
                    if (a.get(i).get(j) != 0) {
                        int xx = a.get(i).get(j);
                        for (int ii = 0; ii < n + 1; ii++) {
                            a.get(i).set(ii, (a.get(i).get(ii) - a.get(j).get(ii) * xx) % p);
                            while (a.get(i).get(ii) < 0)
                                a.get(i).set(ii, a.get(i).get(ii) + p);
                        }
                    }
                }
            }

            for (int i = 0; i < numb; i++) {
                int k = 0;
                for (int j = 0; j < n; j++) {
                    if (a.get(i).get(j) == 0)
                        k++;
                }
                if (k == n - 1)
                    out.println("x" + (i + 1) + " = " + a.get(i).get(n));
                else {
                    out.print("x" + (i + 1) + " = " + a.get(i).get(n));
                    for (int jj = 0; jj < n; jj++) {
                        if (jj != i && a.get(i).get(jj) != 0) {
                            if (a.get(i).get(jj) > 0)
                                out.print(" - " + a.get(i).get(jj) + "*x" + (jj + 1));
                            else
                                out.print(" + " + a.get(i).get(jj) + "*x" + (jj + 1));
                        }
                    }
                    out.println();
                }
            }
        }
        if (met == 3) {
            int a = Integer.parseInt(in.readLine());
            int a1 = a * a + 1;
            List<Integer> p = new ArrayList<>();
            int i = 2;
            while (a1 != 1 && i != a * a + 1) {
                if (a1 % i == 0) {
                    p.add(i);
                    a1 /= i;
                } else
                    i++;
            }
            if (p.size() == 0) {
                out.print("У числа " + a1 + " нет собственных делителей");
                out.close();
                in.close();
                return;
            }

            List<Integer> D = new ArrayList<>();
            int x = p.get(0);
            D.add(x);
            for (int j = 1; j < p.size(); j++) {
                if (p.get(j) == x)
                    D.add(D.get(j - 1) * x);
                else {
                    x = p.get(j);
                    D.add(x);
                }
            }

            List<Integer> X = new ArrayList<>();
            for (int j = 0; j < D.size(); j++) {
                X.add(D.get(j));
            }

            for (int j = 0; j < X.size(); j++) {
                for (int k = j + 1; k < X.size(); k++) {
                    int newel = X.get(j) * X.get(k);
                    boolean fl = true;
                    for (int l = 0; l < D.size(); l++) {
                        if (D.get(l) == newel)
                            fl = false;
                    }
                    if (newel == a * a + 1 || newel > a * a + 1 || (a * a + 1) % newel != 0)
                        fl = false;
                    if (fl)
                        D.add(newel);
                }
            }
            Collections.sort(D);

            for (int j = 0; j < D.size(); j++) {
                int d = D.get(j);
                List<Integer> q = Euk(a, d);
                out.print("d" + (j + 1) + " = " + d + " = ");
                if (a < 0 && d > 0 || a > 0 && d < 0)
                    q.set(0, q.get(0) * (-1));
                List<Integer> P = new ArrayList<>(), Q = new ArrayList<>();
                P.add(0);
                P.add(1);
                Q.add(1);
                Q.add(0);
                for (int jjj = 0; jjj < q.size(); jjj++) {
                    int pi = (q.get(jjj) * P.get(jjj + 1)) + P.get(jjj);
                    int qi = (q.get(jjj) * Q.get(jjj + 1)) + Q.get(jjj);
                    P.add(pi);
                    Q.add(qi);
                }
                double sqrtd = Math.sqrt(d);
                for (int jjj = 0; jjj < Q.size(); jjj++) {
                    if (sqrtd >= Q.get(jjj) && sqrtd <= Q.get(jjj + 1)) {
                        int x1 = a * Q.get(jjj) - d * P.get(jjj);
                        int x2 = Q.get(jjj);
                        out.print(x1 + "^2 + " + x2 + "^2 = " + x1 * x1 + " + " + x2 * x2);
                        break;
                    }
                }
                out.println();
            }
        }

        in.close();
        out.close();
    }

    public static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (b > a) {
            long tmp = a;
            a = b;
            b = tmp;
        }
        while (b != 0) {
            a %= b;
            long tmp = a;
            a = b;
            b = tmp;
        }
        return a;
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
}
