package thread;

public class MakeThread1 {

    public static void main(String[] args) {

        Thread thread = new NewThread();
        thread.start();

    }

    private static class NewThread extends Thread {
        @Override
        public void run() {
            System.out.println("New Thread Name : " + this.getName());
        }
    }
}//class

/**
 * Thread 생성시 확장하는 방법.
 * 객체 생성시 우측의 타입을 class 로 생성시 Thread를 확장한 객체 사용 가능.
 *
 * NewThread 내에서는 Thread. ~ 정적 메서드를 호출하는 대신에
 * -> this. 로 접근하여 사용 가능하다.
 * 모든 정보에 접근할 수 있다.
*
 */