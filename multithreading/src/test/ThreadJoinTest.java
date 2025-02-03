package test;

/**
 * @package : test
 * @name : ThreadJoinTest.java
 * @date : 2025. 2. 3. 오후 3:33
 * @author : lucaskang(swings134man)
 * @Description: Thread 의 JOIN 테스트
 * - ThreadA 나, StandbyThread 가 실행된 후 ThreadA 가 종료된 후 StandbyThread 가 실행되도록 하는 예제
 * - Thread.join() 은 해당 Thread 가 종료될 때 까지 대기하도록 하는 메소드
**/
public class ThreadJoinTest {
    public static void main(String[] args) {
        Thread threadA = new ThreadA();

        // Standby Thread 생성
        Thread standByThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " ---- StandbyThread 시작");
            try {
                threadA.join();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " ---- StandbyThread Interrupted");
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " ---- StandbyThread 종료");
        });

        threadA.start();
        standByThread.start();
    }

    // Standby Thread 가 해당 Thread 종료 이후 실행 하기 위한 타겟 Thread
    private static class ThreadA extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " ---- ThreadA 시작");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " ---- Thread Interrupted");
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " ---- ThreadA 종료");
        }
    }

//    private static class StandbyThread extends Thread {
//        @Override
//        public void run() {
//
//        }
//    }

}
