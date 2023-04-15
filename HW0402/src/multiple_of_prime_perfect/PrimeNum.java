package multiple_of_prime_perfect;

public class PrimeNum extends Thread {
    int sum = 0;

    @Override
    public void run() {
        //求1000内素数和
        for (int i = 2; i <= 10000; i++) {
            boolean flag = true;
            for (int j = 2; j <= i / 2; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                sum += i;
            }
        }
    }
}
