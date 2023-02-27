package example.local;

/************
 * @info : Java - Thread Local
 * @name : AboutThreadLocal
 * @date : 2023/02/28 2:55 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 스레드 로컬(Java 1.2 이후)
 *
 * - 스레드 로컬 변수를 제공.
 *      - 로컬 변수는 get, set 메서드를 통해 엑세스, 변경 가능.
 *      - 스레드 마다 독립적으로 갖고 있는 변수를 뜻함
 *
 * - 주의할점
 *  1. Spring 에서 사용할 때 Thread Local 사용 후 바로 삭제 해주어야함.
 *      - 클라이언트의 요청이 들어오면 ThreadPool에 만들어둔 쓰레드를 사용하고 반납함.
 *      - 이전에 처리되었던 Thread로 부터 생성된 ThreadLocal에 저장된 값이 남아 있을수 있음.
 ************/
public class AboutThreadLocal {

    /**
     * Thread Local 정의
     *
     * @param Thread Local.set()
     */
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) {
        THREAD_LOCAL.set("local Thread-1");
        // main Thread
        System.out.println("Thread name = " + Thread.currentThread());
        System.out.println("Thread Local variable1 = " + THREAD_LOCAL.get());

        // 별도의 Thread
        Thread thread = new Thread(() -> {
            System.out.println("Thread name2 = " + Thread.currentThread());
            System.out.println("Thread Local variable2 = " + THREAD_LOCAL.get()); // Main Thread 이외에는 접근 불가능, 결과 = null
        });
        thread.start();
    }
}
