package example.local.testThreadLocal;

/************
 * @info : Thread Local Test no.1 class
 * @name : ThreadLocal_test_1
 * @date : 2023/03/07 6:55 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
public class ThreadLocal_test_1 {

    // 각각의 thread 가 나눠서 사용한다고 가정.
    private static ThreadLocal<String> name = new ThreadLocal<>();

    private static void setThreadLocal() {
        if(name.get() == null) {
            name.set("test1");
        }
    }

    public static void main(String[] args) {
        setThreadLocal();

        // main thread
        name.set("main Thread");
        System.out.println("(main)name=" + name.get());

        // 별도 thread
        Thread thread = new Thread(() -> {
            setThreadLocal();
            System.out.println("(thread1)name=" + name.get());
        });
        thread.start();
    }

}
