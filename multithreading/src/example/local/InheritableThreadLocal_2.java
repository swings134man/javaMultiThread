package example.local;

/************
 * @info : JAVA - Inheritable Thread Local 예제 2번
 * @name : InheritableThreadLocal_2
 * @date : 2023/02/28 3:09 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 *
 *  - Inheritable Thread Local
 *  - 하위 쓰레드에서 상위 쓰레드에 존재하는 값이 아닌, 다른 값을 얻도록 set
 *
 *  - 상위 클래스 내부에서 @Override = childValue
 *
 *  - ThreadLocal 과, ITL는 내부적으로 ' Thread Local Map ' 이라는 클래스에서 <K,V> 로 관리됨.
 ************/
public class InheritableThreadLocal_2 {
    public static void main(String[] args) {
        SuperThread su = new SuperThread();
        su.start();
    }
}// main Class


/**
 * This is SuperThread.class
 * started at main of InheritableThreadLocal_2.class
 *
 * -> 슈퍼클래스, main 스레드에서 실행되며,
 * SubThread.class 를 실행시킨다(run)
 * - SubThread.class에 "this is for sub" 라는 thread 값을 return 한다.
 * @return this is for sub(Thread Local Value)
 */
class SuperThread extends Thread {
    public static final ThreadLocal<String> INHERITABLE_THREAD_LOCAL = new InheritableThreadLocal() {
        // 임의의 값 set/get
        @Override
        public String childValue(Object parentValue) {
            return "this is for Sub";
        }
    };

    @Override
    public void run() {
        INHERITABLE_THREAD_LOCAL.set("Super Thread-1");

        System.out.println("Super Thread : " + Thread.currentThread());
        System.out.println("Super Thread var : " + INHERITABLE_THREAD_LOCAL.get()); // super Thread-1

        SubThread st = new SubThread();
        st.start();
    }
}


/**
 * This is SubThread Class, This class belong to SuperThread.class
 * - 상위 클래스인 SuperThread에서 실행.
 */
class SubThread extends Thread {
    @Override
    public void run() {
        System.out.println("Sub Thread : " + Thread.currentThread());
        System.out.println("Sub Thread : " + SuperThread.INHERITABLE_THREAD_LOCAL.get()); // 예상되는결과 -> this is~
    }
}
