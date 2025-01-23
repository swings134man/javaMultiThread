package race.condition.reentrant_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @package : race.condition.reentrant_lock
 * @name : SimpleReentrantLockSample.java
 * @date : 2025. 1. 23. 오후 5:59
 * @author : lucaskang(swings134man)
 * @Description: Reentrant Lock 사용 예제
 *
 * - Reentrant Lock 객체 생성하여 여러 Thread 간 공유자원에 대한 접근 제어
 * - tryLock() 으로 잠금 가능 시 {@code true}, 아닐 경우 {@code false} 반환
 * - tryLock() 을 얻지 못한 Thread 는 대체작업 진행
 * - 임계영역간 Exception 발생시, unlock() 호출 불가하기에 try-finally 로 unlock() 호출
 * -
**/
public class SimpleReentrantLockSample {

    private static final ReentrantLock lock = new ReentrantLock();
    private static int sharedResource = 0;

    public static void main(String[] args) {
//        Thread thread1 = new Thread(() -> accessResource("Thread-1"));
//        Thread thread2 = new Thread(() -> accessResource("Thread-2"));
        Thread thread1 = new Thread(() -> accessResource2("Thread-1"));
        Thread thread2 = new Thread(() -> accessResource2("Thread-2"));

        thread1.start();
        thread2.start();
    }

    // Lock 획득 실패시 대체 작업 지정 동작 메서드: Lock 획득 실패시 대기 X
    private static void accessResource(String threadName) {
        if(lock.tryLock()) { // lock을 획득하면 true, 아니면 false
            try {
                System.out.println(threadName + " 잠금 획득");
                sharedResource++;
                System.out.println(threadName + " update sharedResource: " + sharedResource);
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt(); // 현재 Thread interrupt(스레드가 정지상태일때 interrupt를 호출하면 interruptException이 발생)
                System.out.println(threadName + " 인터럽트 발생");
            }finally {
                lock.unlock();
                System.out.println(threadName + " 잠금 해제");
            }
        }else {
            System.out.println(threadName + " Lock 획득 실패");
            performAlternativeTask(threadName);
        }
    }// accessResource

    // lock() 을 활용: Lock 을 얻을때 까지 대기
    private static void accessResource2(String threadName) {
        System.out.println(threadName + " Lock 획득 시도");
        lock.lock();
        try {
            System.out.println(threadName + " Lock 획득");
            sharedResource++;
            System.out.println(threadName + " update sharedResource: " + sharedResource);
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(threadName + " 인터럽트 발생");
        }finally {
            lock.unlock();
            System.out.println(threadName + " Lock 해제");
        }
    }

    private static void performAlternativeTask(String threadName) {
        System.out.println(threadName + " is performing an alternative task.");
    }
}
