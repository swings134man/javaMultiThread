package example.local.testSingle;


/************
 * @info : Singleton class를 Thread 동시성문제 main class
 * @name : TestMainOfLocal
 * @date : 2023/03/07 5:51 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 여러개의 thread에서 동시에
 ************/
public class TestMainOfLocal {

    public static void main(String[] args) {
//        SingletonConfig con1 = SingletonConfig.getInstance();
//        SingletonConfig con2 = SingletonConfig.getInstance();
//
//        con1.setName("test");
//
//        System.out.println("con1=" + con1 + ", getName=" + con1.getName());
//        System.out.println("con2=" + con2 + ", getName=" + con2.getName());
        // ------------------------------------

        ThreadA a = new ThreadA();
        ThreadB b = new ThreadB();

        a.start();
        b.start();

    }

    // Thread A
    private static class ThreadA extends Thread {
        @Override
        public void run() {
            SingletonConfig con1 = SingletonConfig.getInstance();
            Customer customer = new Customer();

            System.out.println("Thread A get=" + con1.getName());


        }
    } //threadA

    // Thread B
    private static class ThreadB extends Thread {
        @Override
        public void run() {
            SingletonConfig con2 = SingletonConfig.getInstance();
            Customer customer = new Customer();

            System.out.println("Thread B get=" + con2.getName());
            System.out.println("threadLocal=" + con2);
        }
    } //threadB

}
