package test.multi;

public class SubThread2 implements Runnable{

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println("SubThread2 -----  is Running");
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("SubThread2 -----  Stopped");
    }
}
