package race.condition;

/************
 * @info : 병행성 문제 - 스레드간 자원공유
 * @name : InventoryCounter
 * @date : 2023/08/12 5:15 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 물류창고 예시
 * - 2개의 스레드가 동시 작업을 수행함. -> 1: 10000개 추가, 2: 10000개 제거
 * - 물건을 추가하거나 제거하는 작업을 수행함.
 * - 물건의 갯수를 카운트함.
 *
 * join() : thread 간 동기화를 위해 사용됨. -> 하나의 스레드가 다른 스레드의 실행을 기다림.
 *      -> 예) t1.join() -> t1 스레드가 완료될때 까지 main thread가 종료되지 않고 대기.
 ************/
public class InventoryCounter {
    public static void main(String[] args) throws InterruptedException{
        InventoryCounter_1 inventoryCounter1 = new InventoryCounter_1();
        Thread1 t1 = new Thread1(inventoryCounter1);
        Thread2 t2 = new Thread2(inventoryCounter1);

        /**
         * 예제 1번 -> 결과 0
         * - 스레드를 순차적으로 실행.
         */
        // 1. 증가 thread1 실행 및 종료
//        t1.start();
//        t1.join();
//
//        // 2. 감소 thread2 실행 및 종료
//        t2.start();
//        t2.join();
//
//        // 3. 현재 보유하고 있는 잔여 item 갯수 -> 결과 0
//        System.out.println("items count = " + inventoryCounter1.getItems());

        /**
         * 예제 2번 -> random -> 그때 그때 마다 달라진다. 스레드의 동작순서가 보장되지 않기 때문., -> 실행 결과를 예측할 수 없음.
         * - 스레드를 순차적으로 실행하지 않고, 동시 실행.
         */

        // 1.
        t1.start();
        t2.start();

        // 2.
        t1.join();
        t2.join();

        // 3.
        System.out.println("items count = " + inventoryCounter1.getItems());
    }

    // Increase Thread
    public static class Thread1 extends Thread{
        private InventoryCounter_1 inventoryCounter;

        public Thread1(InventoryCounter_1 inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();;
            }
        }
    }

    // Decrement Thread
    public static class Thread2 extends Thread{
        private InventoryCounter_1 inventoryCounter;

        public Thread2(InventoryCounter_1 inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    private static class InventoryCounter_1 {
        private int items = 0;

        public void increment () {
            items++;
        }

        public void decrement () {
            items--;
        }

        public int getItems() {
            return items;
        }
    }// static class
}//class
