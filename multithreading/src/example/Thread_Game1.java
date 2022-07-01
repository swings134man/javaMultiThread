package example;

public class Thread_Game1 {

    public static boolean inputCheck = false;

    public static void main(String[] args) {

        GameTimer gt = new GameTimer();

        // 난수 사용
        String[] three = {"가위","바위","보"};
        int index = (int)(Math.random() *3);
        String com = three[index];

        //Thread Start
        gt.start();



    }//main

}//class

/**
 * Timer(Thread)
 */
class GameTimer extends Thread {
    @Override
    public void run() {
        for (int i = 5; i >= 1; i--) {
            if (Thread_Game1.inputCheck == true){return;}

            System.out.println("남은 시간 :" + i);

            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {}

            System.out.println("시간초과. 사용자 패배.");
            System.exit(0);
        }

    }//run
}