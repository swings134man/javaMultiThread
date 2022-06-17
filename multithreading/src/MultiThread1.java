public class MultiThread1 {

    public static void main(String[] args) throws InterruptedException{

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 코드는 새로운 thread 내에서 실행됨.
                System.out.println("Thread 객체 : " + Thread.currentThread().getName());
                System.out.println("현재 Thread Prioity " + Thread.currentThread().getPriority());
            }
        }); // Thread 객체는 기본 empty

        thread.setName("new Thread nm1"); // Thread 객체 이름 설정.

        System.out.println("Thread 객체 : " + thread.currentThread().getName());
        thread.start();
        System.out.println("Thread 객체  after : " + thread.currentThread().getName());

        Thread.sleep(100); // 일정시간동안 Thread 정지.

        thread.stop();

    }//main


}
