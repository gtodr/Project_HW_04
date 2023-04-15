package multiple_of_prime_perfect;

public class PerfectNum extends Thread {
    int sum = 0;

    @Override
    public void run() {
        //计算1000内的完全数
        for (int i = 1; i <= 10000; i++) {
            int factorCount = 0;
            for (int j = 1; j <= i / 2; j++) {
                if (i % j == 0) {
                    factorCount += j;
                }
            }
            if (factorCount == i) {
                sum += i;
            }
        }
    }
}
