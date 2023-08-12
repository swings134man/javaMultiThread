package race.condition;

/************
 * @info : 병행성 문제 - 스레드간 자원 공유 문제
 * @name : InventoryCounter
 * @date : 2023/08/12 5:15 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : synchronized 블락 내부 정의로 동시성 제어
 *
 * 메서드 전체를 synchronized 로 접근을 막는 방법이 아닌,
 * 특정 구간을 임계 영역으로 설정하여, 공유 자원에 대한 동기화를 진행함.
 *
 * -> 임계영역내부의 코드는 최소한으로, 필요한것만 실행.
 * -> thread 간 동기화가 필요한 코드가 더 적은 방법.
 * -> lock 의 역할을 하게될 동기화할 객체를 만들어야 함 -> Object
 *      -> 실제로는 다른 동작을 하지 않음. 다만 lock 객체로서 동시 접근을 제어하게 함.
 ************/
public class InventoryCounter3 {
    public static void main(String[] args) throws InterruptedException{
        InventoryCounter_1 inventoryCounter1 = new InventoryCounter_1();
        Thread1 t1 = new Thread1(inventoryCounter1);
        Thread2 t2 = new Thread2(inventoryCounter1);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

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

        final Object lock = new Object();

        public void increment () {
            synchronized (this.lock){
                items++;
            }
        }

        public void decrement () {
            synchronized (this.lock){
                items--;
            }
        }

        public int getItems() {
            synchronized (this.lock){
                return items;
            }
        }
    }// static class
}//class
