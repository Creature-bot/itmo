public class laba {
    public static double func1(float x) {
        return Math.atan(Math.cos(Math.pow(Math.pow((x + 1), 3) / 2, 3)));
    }

    public static double func2(float x) {
        return Math.cos(Math.sin(Math.pow((x - (double)1/4) / x, 2)));
    }

    public static double func3(float x) {
        return Math.tan(Math.cbrt(Math.asin(Math.sin(x))));
    }

    public static void func4(double[][] f) {
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[i].length; j++) {
                System.out.printf("%8.4f ", f[i][j]);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        // 1. Создать одномерный массив e типа long
        long[] e = new long[15]; //массив числами от 4 до 18 включительно в порядке убывания
        int n = 18;
        for (int i = 0; i < e.length; i++) {
            e[i] = n - 1;
            --n;
        }

        // 2. Создать одномерный массив x типа float
        float[] x = new float[10];
        for (int i = 0; i < x.length; i++) {
            x[i] = (float) ((Math.random() * (16.0)-6));
        }

        // 3. Создать двумерный массив f размером 15x10 и вычислить его элементы
        double[][] f = new double[15][10];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                if (e[i] == 15.0) {
                    f[i][j] = func1(x[j]);
                } else if (e[i]==4 | e[i]==6 | e[i]==9 | e[i]==10 | e[i]== 12 | e[i]==16 | e[i]==18) {
                    f[i][j] = func2(x[j]);
                } else {
                    f[i][j] = func3(x[j]);
                }
            }
        }
        func4(f);
    }
}
