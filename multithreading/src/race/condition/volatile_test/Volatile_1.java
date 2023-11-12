package race.condition.volatile_test;

import java.util.Random;

/************
 * @info :
 * @name : Volatile_1
 * @date : 11/12/23 5:22 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 
 ************/
public class Volatile_1 {

    public static void main(String[] args) {
        Metrics metrics = new Metrics();

        BusinessLogic businessLogicThread1 = new BusinessLogic(metrics); // 연산 수행
        BusinessLogic businessLogicThread2 = new BusinessLogic(metrics); // 연산 수행
        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics); // 연산 출력

        businessLogicThread1.start();
        businessLogicThread2.start();
        metricsPrinter.start();
    }

    /**
     * this Class는 BusinessLogic Class 와 병렬실행.
     * BusinessLogic의 평균 시간을 캡쳐 후 다음 화면에 출력한다.
     */
    public static class MetricsPrinter extends Thread{
        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(100); //매 100ms 마다 평균값을 출력
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                double currentAverage = metrics.getAverage();
                System.out.println("Current Average is " + currentAverage);
            }
        }
    }


    /**
     * Business Logic Thread Class
     * Metrics 객체를 가져와, 생성자로 전달 하는 역할
     *
     */
    public static class BusinessLogic extends Thread{
        // Thread 를 임의로 ms 동안 sleep(지연)
        private Metrics metrics;
        private Random random = new Random();

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }


        @Override
        public void run() {
            // This Task repeat
            while (true){
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(random.nextInt(10)); // 0~10ms 사이의 임의의 지연
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long end = System.currentTimeMillis();

                metrics.addSample(end - start);
            }
        }
    }

    public static class Metrics {
        private long count = 0;
        private volatile double average = 0.0;

        /**
         * N 개의 Thread 가 해당 Metrics 객체에 Sample 을 추가 할 수 있음.
         * - 여러 Thread 가 객체를 공유하고 있음.
         * - count, average 는 공유 자원임.
         * - volatile 이 없으면 원자적인 연산이 아님
         *
         * synchronized 를 사용하여, 동시 실행으로 부터 보호.
         * @param sample
         */
        public synchronized void addSample(long sample) {
            // 모든 샘플 현재 총합 값 계산
            double currentSum = average * count;
            count++; // 샘플 추가
            average = (currentSum + sample) / count;// 새로운 평균 값 계산 //-> volaitile 이 없으면 원자적인 연산이 아님
        }

        /**
         * 현재 샘플의 평균값을 반환
         * - 동기화, volatile 을 사용하지 않으면, 다른 Thread 가 현재 평균값을 읽을 수 없음. -> return 변수가 double 이기 때문에(Reference Type)
         * @return
         */
        public double getAverage() {
            return average;
        }
    }

}
