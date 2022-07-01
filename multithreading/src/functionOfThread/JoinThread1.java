package functionOfThread;

public class JoinThread1 {
    static long startTime = 0;

    public static void main(String[] args) {

        Thread th1 = new Thread_A();
        Thread th2 = new Thread_B();

        th1.start();
        th2.start();
        startTime = System.currentTimeMillis(); // 시작시간

        try {
            th1.join(); // main thread가 th1 작업이 끝날때 까지 기다린다.
            th2.join(); // main thread가 th2 작업이 끝날때 까지 기다린다.
        } catch (InterruptedException e) {}

        // th1. th2 작업이 종료되면 main thread가 실행됨. 아래 code
        System.out.println("소요시간 : " + (System.currentTimeMillis() - startTime));
    }//main

    private static class Thread_A extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 300; i++) {
                System.out.print("-");
            }
        }
    }

    private static class Thread_B extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 300; i++) {
                System.out.print("|");
            }
        }
    }

}//class
