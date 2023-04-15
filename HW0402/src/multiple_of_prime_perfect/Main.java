package multiple_of_prime_perfect;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //创建两个线程分别计算完全数和素数
        PerfectNum t1=new PerfectNum();
        PrimeNum t2=new PrimeNum();

        t1.start();
        t2.start();

        //等待线程t1,t2运行完
        t1.join();
        t2.join();

        BigInteger res1=new BigInteger(String.valueOf(t1.sum));
        BigInteger res2=new BigInteger(String.valueOf(t2.sum));
        BigInteger res=res1.multiply(res2);

        System.out.println("10000以内素数之和与完全数之和的乘积:"+res);
    }
}


