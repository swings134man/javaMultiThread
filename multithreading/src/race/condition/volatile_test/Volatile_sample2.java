package race.condition.volatile_test;

/**
 * @package : race.condition.volatile_test
 * @name : Volatile_sample2.java
 * @date : 2025. 1. 14. 오후 9:45
 * @author : lucaskang(swings134man)
 * @Description: Volatile Keyword 샘플 2번쨰, 일반적인 Bean 객체 예시
 *
**/
public class Volatile_sample2 {

    /**
     * sample class 1번: 수정 X
     * - 상황: singleton 객체가 없을떄, 동시에 getInstance() 메소드를 호출할 경우, 동시성 문제가 발생할 수 있음.
     */
    public static class SingletonBefore{
        private static SingletonBefore singleton;

        private SingletonBefore() {}

        public static SingletonBefore getInstance(){
            if(singleton == null){
                singleton = new SingletonBefore();
            }
            return singleton;
        }
    }


    /**
     * sample class 2번: 수정 O
     * - singleton 객체를 volatile 키워드를 사용하여, 변수의 가시성을 보장
     * - Singleton 인스턴스가 여러 Thread 에서 동시에 접근될때 발생할 수 있는 문제를 방지함.
     * - synchronized 키워드를 사용하여, Singleton Class 자체를 동기화 했다.
     * - 인스턴스가 없을때 synchronized 블록내에서 한번에 하나의 Thread 만이 인스턴스를 생성할 수 있도록 함.
     *   -> 여러 쓰레드가 동시에 인스턴스 생성하는 것 방지.
     */
    public static class Singleton {
        private volatile static Singleton singleton;

        private Singleton() {}

        public static Singleton getInstance() {
            if (singleton == null) {
                synchronized (Singleton.class) {
                    if (singleton == null) {
                        singleton = new Singleton();
                    }
                }
            }
            return singleton;
        }
    }

}
