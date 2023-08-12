package race.condition;

/************
 * @info : 병행성 문제 - 스레드간 자원 공유 문제
 * @name : InventoryCounter
 * @date : 2023/08/12 5:15 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : synchronized 키워드를 통한 동시성 제어
 *
 * 공유 객체에 2개의 Thread 가 접근함에도 각각의 스레드가 1개씩 임계 영역을 실행함 -> 결과를 보장할 수 있는 상태가 됨.
 ************/
public class InventoryCounter2 {
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

        public synchronized void increment () {
            items++;
        }

        public synchronized void decrement () {
            items--;
        }

        public synchronized int getItems() {
            return items;
        }
    }// static class
}//class
