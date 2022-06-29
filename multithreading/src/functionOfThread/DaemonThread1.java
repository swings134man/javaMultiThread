package functionOfThread;

public class DaemonThread1 implements Runnable{
    static boolean autosave = false;
    static int number = 0;

    public static void main(String[] args) {

        Thread thread = new Thread(new DaemonThread1()); // Thread(Runnable r)
        thread.setDaemon(true); // Daemon setting 안해주면 종료되지 않음.
        thread.start();

        for (int number = 1; number <= 10; number++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            System.out.println(number);
            if(number == 5){
                autosave = true;
            }
        }
        System.out.println("프로그램을 종료합니다.");
        System.out.println(Thread.currentThread().getState());

        /**
         *  Thread 객체 생성 (line 9) ~ line(23) 까지는 main Thread
         *  main Thread가 종료되면 Daemon Thread인 run도 종료된다.
         */
    }//main


    public void run() {
        while(true){ // 무한루프
            try {
                Thread.sleep(3 * 1000); // 3초마다
            } catch (InterruptedException e) {}

            // autosave의 값이 true이면 autosave()를 호출
            if(autosave){
                autosave();
                autosave = false;
            }
        }
    }

    public void autosave() {
        System.out.println("자동 저장 되었습니다.");
    }

}
