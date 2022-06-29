package functionOfThread;

public class ThreadInterrupt2 {

    public static void main(String[] args) {

        Thread thread = new Thread(new BlockingTask());

        thread.start();

        thread.interrupt(); // BlockingThread를 종료하기 위한것.
    }//main

    /**
    * @info    : 잘못된 시간을 차단하는 작업을 수행하는 메서드
    * @name    : BlcokingTask
    * @date    : 2022/06/30 1:06 AM
    * @author  : SeokJun Kang(swings134@gmail.com)
    * @version : 1.0.0
    * @param   :
    * @return  :
    */
    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            // 실행 부
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                //InterruptedException 발생 시 처리 로직.
                // 현재 Thread가 외부에서 interrupt 된다면 현재 catch 부분으로 동작.
                System.out.println("Thread Blocked");
            }
        }
    }



}
