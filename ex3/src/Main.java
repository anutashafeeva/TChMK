import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        PrintWriter out = new PrintWriter(new FileWriter("output.txt"));

        String s = in.readLine();
        String[] ss = s.split(" ");
        int a = Integer.parseInt(ss[0]), p = Integer.parseInt(ss[1]);

        if (a % p == 0)
            out.print(0);
        else
            out.print(L(a, p));

        in.close();
        out.close();
    }

    public static int L(int a, int p) {

        int res = 1;

        if (a < 0 && p % 4 == 3)
            res *= -1;

        while (true) {

            a %= p;

            int k = 0;
            while (a % 2 == 0) {
                a /= 2;
                k++;
            }
            if (k % 2 == 1 && (p % 8 == 3 || p % 8 == 5))
                res *= -1;

            if (a == 1)
                break;

            int tmp = a;
            a = p;
            p = tmp;

            if (((p - 1) * (a - 1) / 4) % 2 == 1)
                res *= -1;
        }

        return res;
    }
}
