package race.condition.locking_deadlock;

import java.util.Random;

/**
 * @package : race.condition.locking_deadlock
 * @name : DeadLock_sample1.java
 * @date : 2025. 1. 15. 오후 7:04
 * @author : lucaskang(swings134man)
 * @Description: DeadLock Sample 1 Class -> 교차로 교통체계를 통해 !!!!DeadLock 발생시키는 예시!!!!
 *
 * - 예시상황: Train A 는 x 축, Train B 는 y 축을 이동하며 교차하는 상황이 존재한다.
 * 이때 Train A 와 Train B 는 각각의 축을 이동하는 동안, 다른 축을 이동하는 Train 을 기다려야 한다.
 *
 * - 이러한 상황에서, Train A 와 Train B 가 서로를 기다리는 상황이 발생할 수 있다.
 * - 이러한 상황을 DeadLock 이라고 한다.
 *
 * - DeadLock: 두 개 이상의 Thread 가 서로 상대방의 작업이 끝나기만을 기다리고 있기 때문에, 결과적으로 아무것도 완료되지 못하는 상태.
 *
 * - 실행하게 되면 얼마 지나지 않아, DeadLock 이 발생하게 된다. -> 두개의 Train 이 서로를 기다리는 상황이 발생.
 * -> 예를들면 Thread-1 번에 의해 RoadB 가 Lock 되고, Thread-2 번에 의해 RoadA 가 Lock 되었을때, 서로 Lock 을 풀지 못하고 대기하는 상황이 발생.
**/
public class DeadLock_sample1 {

    public static void main(String[] args) {
        InterSection interSection = new InterSection(); // 교차로 객체 생성
        Thread trainAThread = new Thread(new TrainA(interSection)); // Train A 객체 생성
        Thread trainBThread = new Thread(new TrainB(interSection)); // Train A 객체 생성

        trainAThread.start();
        trainBThread.start();
    }


    /**
     * TrainA Class: Train A 를 정의
     * - x 축을 이동하는 Train
     * - 모든 Train 이 필수로 가져야 하는 로직(법규)인 Intersection 객체를 포함한다.
     */
    public static class TrainA implements Runnable {
        private InterSection interSection;
        private Random random = new Random(); // 임의의 Train 이동 스케줄

        public TrainA(InterSection interSection) {
            this.interSection = interSection;
        }

        // Train A 가 x 축을 계속 이동하는 Logic
        @Override
        public void run() {
            while (true){
                long sleepTime = random.nextInt(5); // 0~5 사이의 임의의 수: 다음 기차 오기까지 걸리는 시간
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
                // 기차가 오면(Thread가 실행되면)
                interSection.takeRoadA(); // Road A 사용하여 교차로 통과
            }
        }
    }// Train A

    // TrainB Class: Train B 를 정의
    public static class TrainB implements Runnable {
        private InterSection interSection;
        private Random random = new Random(); // 임의의 Train 이동 스케줄

        public TrainB(InterSection interSection) {
            this.interSection = interSection;
        }

        // Train A 가 x 축을 계속 이동하는 Logic
        @Override
        public void run() {
            while (true){
                long sleepTime = random.nextInt(5); // 0~5 사이의 임의의 수: 다음 기차 오기까지 걸리는 시간
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
                // 기차가 오면(Thread가 실행되면)
                interSection.takeRoadB(); // Road B 사용하여 교차로 통과
            }
        }
    }// Train A

    /**
     * Intersection: 교차로 정의
     * - A,B 가 충돌하지 않게하는 교차로 로직
     */
    public static class InterSection{
        private Object roadA = new Object();
        private Object roadB = new Object();

        // roadA 를 Locking 생성하는 Method
        public void takeRoadA() {
            synchronized (roadA){ // RoadA Locking
                System.out.println("Road A is locked by Thread: " + Thread.currentThread().getName());

                /**
                 * A 가 사용중일땐 B 가 사용못하게 함.
                 * -> A 는 자유롭게 사용 가능
                 * --> 다른 THREAD 가 roadB 를 사용하는것을 막음.(while A 가 사용중일때까지)
                 */
                synchronized (roadB){
                    System.out.println("Train is passing through Road A");
                    try {
                        // 1s 대기: 1초동안 기차 통과 중단(TrainA 가 RoadA 를 사용중일때까지 == 1s)
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
           }
        }

        public void takeRoadB() {
            synchronized (roadB){ // RoadB Locking
                System.out.println("Road B is locked by Thread: " + Thread.currentThread().getName());

                synchronized (roadA){
                    System.out.println("Train is passing through Road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }// Intersecion Class END

}
