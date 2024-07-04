package test;

/************
 * @info : Synchronized 키워드 테스트
 * @name : SynchronizedKeyWordTest
 * @date : 2024/07/04 15:44 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
public class SynchronizedKeyWordTest {
    public static void main(String[] args) {
        SharedObject sharedObject = new SharedObject();
        SharedObjectNot sharedObjectNot = new SharedObjectNot();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedObject.increment();
//                sharedObjectNot.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedObject.decrement();
//                sharedObjectNot.decrement();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(sharedObject.getCount());
//        System.out.println(sharedObjectNot.getCount());
    }
}

class SharedObject {
    int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized void decrement() {
        count--;
    }

    public synchronized int getCount() {
        return count;
    }

}

class SharedObjectNot {
    int count = 0;

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public int getCount() {
        return count;
    }

}
