package race.condition.dataRace;

/************
 * @info : 데이터 경쟁 테스트
 * @name : DataRaceCondition_example
 * @date : 2024. 6. 24. 오후 6:20
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : 
 ************/
public class DataRaceCondition_example {

    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        });

        thread1.start();
        thread2.start();
    }


    public static class SharedClass {

        // volatile 키워드를 사용하면, 데이터 경쟁이 발생하지 않음. -> 순서를 보장하고 데이터 경쟁을 해결해준다.
//        private int x = 0;
//        private int y = 0;

        private volatile int x = 0;
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) {
                System.out.println("y > x - Data Race is detected");
            }
        }
    }
}


