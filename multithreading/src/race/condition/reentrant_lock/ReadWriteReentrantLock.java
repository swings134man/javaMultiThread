package race.condition.reentrant_lock;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @package : race.condition.reentrant_lock
 * @name : ReadWriteReentrantLock.java
 * @date : 2025. 1. 24. 오전 3:45
 * @author : lucaskang(swings134man)
 * @Description: Read Write Reentrant Lock 사용 예제
 * - Database 의 Inventory 같은 공유자원에 대한 접근 제어
 *
 * - 일반적인 Reentrant Lock 은 1529ms 가량 소요
 * - ReadWriteReentrantLock 은 524ms 가량 소요
 *
 * - Reader Thread 여러개가 동시에 공유 리소스에 접근시 효율적이지 못한방법(Lock, ReentrantLock)
 * - 즉 읽기 작업을 주로 실행하는 작업이라면, ReadWriteLock 을 사용하여 읽기 작업을 효율적으로 처리
**/
public class ReadWriteReentrantLock {
    public static final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) throws InterruptedException {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        Thread writer = new Thread(() -> {
           while (true){
               inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE)); // 임의의 가격으로 상품 추가
               inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE)); // 임의의 가격으로 상품 제거
               try {
                   Thread.sleep(10); // 10ms 대기
               } catch (InterruptedException e) {
               }
           }
        });

        writer.setDaemon(true); // 데몬 Thread로 설정
        writer.start();

        // Read
        int numberOfThreads = 7;
        List<Thread> readers = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread reader = new Thread(() -> {
                for (int j = 0; j < 100000; j++) { // 100000번 반복 읽기작업
                    int upperBound = random.nextInt(HIGHEST_PRICE);
                    int lowerBound = upperBound > 0 ? random.nextInt(upperBound) : 0;
                    inventoryDatabase.getNumberOrItemsInPriceRange(lowerBound, upperBound);
                }
            });
            reader.setDaemon(true);
            readers.add(reader);
        }

        // TimeStamp
        long startReadingTime = System.currentTimeMillis();
        for (Thread reader : readers) {
            reader.start();
        }

        // join : 모든 Reader Thread 가 실행완료 될때 까지 대기
        for (Thread reader : readers) {
            reader.join();
        }

        long endReadingTime = System.currentTimeMillis();
        System.out.println(String.format("Reading took %d ms", endReadingTime - startReadingTime)); // Reader Thread 실행시간 출력
    }

    public static class InventoryDatabase {
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>(); // 가격을 key로, 수량을 value로 가지는 TreeMap
        private ReentrantLock lock = new ReentrantLock();

        private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = readWriteLock.readLock();
        private Lock writeLock = readWriteLock.writeLock();

        // Read Thread
        // 가겨범위내의 아이템 수를 반환
        public int getNumberOrItemsInPriceRange(int lowerBound, int upperBound) {
            readLock.lock();
            try {
                Integer fromKey = priceToCountMap.ceilingKey(lowerBound); // 최저가 found
                Integer toKey = priceToCountMap.floorKey(upperBound); // 최고가 found

                if(fromKey == null || toKey == null)
                    return 0;

                // subMap() 메서드를 사용하여 범위내의 아이템 수를 반환: fromKey ~ toKey
                NavigableMap<Integer, Integer> rangeOfPrices = priceToCountMap.subMap(fromKey, true, toKey, true);

                int sum = 0;
                for (int numberOfItemsForPrice : rangeOfPrices.values()) { // 범위내의 아이템 수를 합산
                    sum += numberOfItemsForPrice;
                }

                return sum;
            } finally {
                readLock.unlock();
            }
        }

        // Writer Thread
        // 주어진 가격에 해당하는 상품 추가: Param 가격에 맞는 상품이 없으면 가격을 키로 추가해서 값으로 정함.
        public void addItem(int price) {
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if(numberOfItemsForPrice == null) {
                    priceToCountMap.put(price, 1);
                }else {
                    priceToCountMap.put(price, numberOfItemsForPrice + 1); // 해당가격에 해당하는 상품수 + 1
                }
            } finally {
                writeLock.unlock();
            }
        }

        // Writer Thread
        // 주어진 가격에 해당하는 상품 제거: Param 가격에 맞는 상품이 없으면 가격을 키로 추가해서 값으로 정함.
        public void removeItem(int price) {
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if(numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    priceToCountMap.remove(price); // 해당가격에 해당하는 상품수가 1개이면 해당 상품 제거
                }else {
                    priceToCountMap.put(price, numberOfItemsForPrice - 1); //가격에 해당하는 상품수 -1
                }
            } finally {
                writeLock.unlock();
            }
        }
    }// InventoryDatabase
}
