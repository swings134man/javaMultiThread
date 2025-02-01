package test.multi;

public class MainThread {
    public static void main(String[] args) {
        Thread sub1 = new Thread(new SubThread1());
        Thread sub2 = new Thread(new SubThread2());

        sub1.start();
        sub2.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            sub1.interrupt();
            sub2.interrupt();
            try {
                sub1.join();
                sub2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MainThread -----  Stopped");
        }));

        // Main
        while (true) {
            System.out.println("MainThread -----  is Running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
