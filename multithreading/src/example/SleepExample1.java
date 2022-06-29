package example;

public class SleepExample1 {

    /**
     * sleep code 사용시 try/catch를 계속 해줘야 하기 때문에 아래처럼 만들고 호출해서 사용하게 함.
     */
    static void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
    }//delay

    public static void main(String[] args) {

        Thread th1 = new Thread_A();
        Thread th2 = new Thread_B();

        th1.start();
        th2.start();

        delay(2000); // 이 코드가 없으면 main종료가 제일 먼저 실행. -> main Thread에서 thread 1,2를 실행 하는것이기 때문에
        // Thread실행 후 syso 실행되는것 이기 떄문.

        System.out.println("<<Main 종료>>");
    }

    private static class Thread_A extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 300; i++) {
                System.out.print("-");
            }
            System.out.println("<<A 종료>>");
        }
    }//A

    private static class Thread_B extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 300; i++) {
                System.out.print("|");
            }
            System.out.println("<<B 종료>>");
        }
    }//B

}
