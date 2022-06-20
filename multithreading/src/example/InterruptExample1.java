package example;

/************
* @info : Thread의 Interrupt 종료 방식 예제 클래스
* @name : InterruptExample1
* @date : 2022/06/20 4:47 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
public class InterruptExample1 extends Thread{

    static class TestThread extends Thread{

        @Override
        public void run(){
            try {
                while(true){
                    System.out.println("연속적인 무한 실행");
                    Thread.sleep(500); // InterruptedExcpetion 발생됨 ==> 예외처리 (catch) 단계로 이동
                }
            }catch (InterruptedException e){
                System.out.println("실행 종료.");
            }
        } //run
    }// TestThread Class

    public static void main(String[] args) {

        TestThread thread = new TestThread();
        thread.start();

        //0.5초 후 Thread 종료
        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        thread.interrupt();

        System.out.println("Thread Status : " + thread.isInterrupted());
    } //main

}//class
/**
 * TestThread의 interrupt() 메서드 실행 -> TestThread가 sleep() 메서드로 일시 정지 상태가 되면
 * TestThread에서 interruptException이 발생됨 -> 예외 처리(catch) 블록으로 이동
 * 결과 : TestThread는 while 문을 빠져나옴. -> run()메서드를 정상 종료.
 *
 * 간편 : (mainThread) - TestThread.interrupt() --> (TestThread) -- 일시정지 상태 시 --> InterruptedException 발생 -> catch블록으로 이동
 *
 */
