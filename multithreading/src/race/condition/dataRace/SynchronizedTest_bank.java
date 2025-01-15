package race.condition.dataRace;

/**
 * @package : race.condition
 * @name : SynchronizedTest_bank.java
 * @date : 2025. 1. 16. 오전 1:38
 * @author : lucaskang(swings134man)
 * @Description: synchronized 키워드를 사용하여 데이터 경쟁을 해결하는 방법 - 예제는 Bank 예시(출금)
**/
public class SynchronizedTest_bank {

    public static void main(String[] args) {
        Runner runner = new Runner();

        Thread thread1 = new Thread(runner);
        Thread thread2 = new Thread(runner);

        thread1.start();
        thread2.start();
    }


    public static class Account {
        private int balance = 1000;

        public int getBalance() {
            return balance;
        }

        public synchronized void withdraw(int amount) {
            if(balance >= amount){
                try {
                    System.out.println(Thread.currentThread().getName() + " : 출금 금액 >>> " + amount);

                    Thread.sleep(500); // 0.5s standby
                    balance -= amount;

                    System.out.println(Thread.currentThread().getName() + " : Balance === " + balance);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Account 에서 출금 기능 수행
     */
    public static class Runner implements Runnable{
        Account account = new Account();

        @Override
        public void run() {
            while (account.getBalance() > 0){
                // 100, 200, 300 중 임의의 금액을 출금
                int amount = (int)(Math.random() * 3 + 1) * 100;
                account.withdraw(amount);
            }
        }
    }
}
