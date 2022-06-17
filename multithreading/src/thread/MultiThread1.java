package thread;

/************
* @info : 자바 멀티 스레드1
* @name : MultiThread1
* @date : 2022/06/17 6:23 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
public class MultiThread1 {

    public static void main(String[] args) throws InterruptedException{

        // main 을 시작하기 위하여 기본적으로 main 이라는 Thread 생성됨.
        Thread mainThread = Thread.currentThread(); // 현재 Thread.
        System.out.println(mainThread.getName());

        // 새로운 작업 Thread A
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                // 코드는 새로운 thread 내에서 실행됨.
                System.out.println("Thread 객체 1 : " + Thread.currentThread().getName());
                System.out.println("현재 Thread Prioity " + Thread.currentThread().getPriority());
            }
        }); // Thread 객체는 기본 empty

        threadA.setName("new Thread nm1"); // Thread 객체 이름 설정.

        // 우선 순위, 시스템이 busy 시 우선순위가 높은 Thread에게 우선적 CPU 할당.
        // 1 ~ 10 설정값 존재. 기본값 5
        // 3개의 static 변수 제공. MIN, NORM, MAX_PRIORITY
        // set으로 설정, getPriority() 로 설정값 확인.
        threadA.setPriority(Thread.MAX_PRIORITY);

        System.out.println("Thread 객체 2 : " + threadA.currentThread().getName());

        threadA.start();
        System.out.println("Thread 객체  after : " + threadA.currentThread().getName());
        Thread.sleep(100); // 일정시간동안 Thread 정지.
        threadA.stop();

    }//main


}
