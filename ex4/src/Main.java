import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
        Scanner sc = new Scanner(System.in);

        System.out.println("Выберите действие:");
        System.out.println("1. Вычисление символа Якоби");
        System.out.println("2. Решение сравнения второй степени по простому модулю");

        int met = sc.nextInt();
        if (met == 1) {
            String s = in.readLine();
            String ss[] = s.split(" ");
            int a = Integer.parseInt(ss[0]), n = Integer.parseInt(ss[1]);
            out.print(Y(a, n));
        } else if (met == 2) {
            String s = in.readLine();
            String ss[] = s.split(" ");
            long a = Long.parseLong(ss[0]), p = Long.parseLong(ss[1]);
            if (Sol(a, p) == -1)
                out.print("Нет решений");
            else
                out.print("+-" + Sol(a, p));
        }

        in.close();
        out.close();
    }

    public static int Y(int a, int n) {

        int res = 1;
        int s = 0;

        while (true) {
            if (a == 0)
                return 0;

            if (a == 1)
                return res;

            int k = 0;
            int a1 = a;
            while (a1 % 2 == 0) {
                a1 /= 2;
                k++;
            }

            if (k % 2 == 0) {
                s = 1;
            } else {
                if (n % 8 == 1 || n % 8 == 7)
                    s = 1;
                else if (n % 8 == 3 || n % 8 == 5)
                    s = -1;
            }

            if (a1 == 1)
                return res * s;

            if (n % 4 == 3 && a1 % 4 == 3)
                s = -s;

            a = n % a1;
            n = a1;
            res = res * s;
        }
    }

    public static long Sol(long a, long p) {
        if (Y((int) a, (int) p) == -1)
            return -1;
        int n = 0;
        if (Y((int) a, (int) p) == 1) {
            for (int i = 2; i < p; i++) {
                if (Y(i, (int) p) == -1) {
                    n = i;
                    break;
                }
            }
        }

        long h = p - 1;
        long k = 0;
        while (h % 2 == 0) {
            h /= 2;
            k++;
        }

        long a1 = Long.parseLong(String.valueOf(BigInteger.valueOf(a).modPow(BigInteger.valueOf((h + 1) / 2), BigInteger.valueOf(p))));

        long a2 = Long.parseLong(String.valueOf(BigInteger.valueOf(a).modInverse(BigInteger.valueOf(p))));
        long n1 = Long.parseLong(String.valueOf(BigInteger.valueOf(n).modPow(BigInteger.valueOf(h), BigInteger.valueOf(p))));
        long n2 = 1;

        for (int i = 0; i < k - 1; i++) {
            long b = (a1 * n2) % p;
            long c = (a2 * b * b) % p;
            long d = Long.parseLong(String.valueOf(BigInteger.valueOf(c).modPow(BigInteger.valueOf((int) Math.pow(2, k - 2 - i)), BigInteger.valueOf(p))));
            int ji = 0;
            if (d == 1)
                ji = 0;
            else if (d == p - 1)
                ji = 1;
            n2 = n2 * Long.parseLong(String.valueOf(BigInteger.valueOf(n1).pow(((int) Math.pow(2, i) * ji)))) % p;
        }
        return (a1 * n2) % p;
    }
}