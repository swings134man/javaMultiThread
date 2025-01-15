package race.condition.locking_deadlock;

import java.util.Random;

/**
 * @package : race.condition.locking_deadlock
 * @name : DeadLock_sample1.java
 * @date : 2025. 1. 15. 오후 7:04
 * @author : lucaskang(swings134man)
 * @Description: Sample 1번에서 발생한 DeadLock 문제를 해결하는 방법 - 4번 방법 사용(순환대기 방지)
 *
 * - 데드락을 발생시키는데에는 4가지 요소가 존재함.
 * 1. Mutual Exclusion(상호배타): 자원은 한번에 한 Thread 만 사용할 수 있어야 한다.
 * 2. Hold and Wait(보유 및 대기): Thread 가 자원을 가지고 있으면서, 다른 Thread 가 가지고 있는 자원을 기다리는 상황이 발생하면 안된다.
 * 3. No Preemption(비선점): Thread 가 다른 Thread 가 가지고 있는 자원을 강제로 빼앗을 수 없다.
 * 4. Circular Wait(순환대기): 두개 이상의 Thread 가 서로를 기다리는 상황이 발생하면 안된다.
 *  -> A 를 점유하여 B 를 기다리고, B 를 점유하여 A 를 기다리는 상황
 *  --> 동일한 순서로 공유 리소스를 잠그고, 모든 코드에 해당 순서를 유지하면 된다.
 *  --> 예를들어, 모든 Thread 가 roadA -> roadB 순서로 Lock 을 잡는다면, 데드락을 방지할 수 있다.
 *  ---> A,B 2개 내부에서 A 가 Lock 이면 B 는 Lock 이 안되게 하고, B 가 Lock 이면 A 는 Lock 이 안되게 하는 방법. -> 즉 서로의 자원을 기다리며 무한대기 하지 않음.
 *  * 여기서 Lock 을 해제하는 순서는 중요하지 않다
 *
 *  즉 락킹 순서를 유지하면 데드락을 방지할 수 있는것이다.
 *
 * - 일반적으로 4가지 조건중 하나라도 충족하지 않게하는것으로 데드락을 해결할 수 있다.
 * - 가장 쉬운 방법은 4번조건인 순환대기를 예방하는것.
 *
**/
public class DeadLock_sample1_solution {

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
            synchronized (roadA){ // RoadB Locking
                System.out.println("Road B is locked by Thread: " + Thread.currentThread().getName());

                synchronized (roadB){
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
