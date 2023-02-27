package example.local;

/************
 * @info : JAVA - Inheritable Thread Local
 * @name : AboutInheritableThreadLocal
 * @date : 2023/02/28 3:01 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 *
 * - inheritable Thread Local 은 Thread Local을 확장한 클래스임.
 *
 * - 상위 Thread 에서, 하위 Thread로 값의 상속을 제공.
 *      - 하위 스레드가 생성되면, 상위 스레드에 저장된 값을 상속받아서 사용 가능.
 *      - 일반적으로 하위 스레드의 값은 상위 스레드와 동일하지만.
 *          - childValue 메서드를 재정의하여 상위 스레드의 값이 아닌 다른 임의의 값을 반환 가능.
 ************/
public class AboutInheritableThreadLocal {

    private static final ThreadLocal<String> INHERITABLE_THREAD_LOCAL = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        INHERITABLE_THREAD_LOCAL.set("IThreadLocal-1");
        // main Thread
        System.out.println("Thread name1 = " + Thread.currentThread());
        System.out.println("Thread Local variable1 = " + INHERITABLE_THREAD_LOCAL.get());

        Thread thread = new Thread(() -> {
            System.out.println("Thread name2 = " + Thread.currentThread());
            System.out.println("Thread Local variable2 = " + INHERITABLE_THREAD_LOCAL.get()); // 상위 thread(main)의 값을 상속받기 때문에 결과 존재 = IThreadLocal-1
        });
        thread.start();
    }
}
