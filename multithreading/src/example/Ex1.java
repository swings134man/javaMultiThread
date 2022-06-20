package example;
/************
* @info : java Thread 연습1
* @name : Ex1
* @date : 2022/06/17 6:34 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
public class Ex1 {

    static class RunAll implements Runnable {
        @Override
        public void run() {
            System.out.println("Thread A Start");

            for (int i = 0; i < 10; i++) {
                System.out.println("Thread ..." + i);
            }
        }
    }

    public static void main(String[] args)  {

        Thread mainThread = new Thread();
        System.out.println("Current : " + mainThread.getName());

        // Thread A Start
        Thread ThreadA = new Thread(new RunAll());
        ThreadA.setName("A");
        ThreadA.start();
        System.out.println("Thread A : " + ThreadA.getName());


        // Thread B
        Thread ThreadB = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread B Start");
            }
        });
        ThreadB.setName("B");
        ThreadB.start();
        System.out.println("Thread B : " + ThreadB.getName());

        // Thread 종료
        try {
            Thread.sleep(3000); //3초 후 스레드 종료
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // interrupt 방식
        /*
            interrupt(); 메서드는 종료하고자 하는 메서드가 일시 정지 상태일 때 정지 된다.
            -Thread가 실행, 실행대기 상태일때 interrupt() 메서드 실행되면 즉시 Exception 발생 x,
            Thread가 미래에 일시정지 상태가 되면 InterruptedException 예외가 발생함.

            *즉 Thread가 일시 정지 상태가 되지 않으면 interrupt() 메서드 호출은 의미 x

            * 예외 : isInterrupted(), interrupted()는 일시정지 상태가 필요 없음.
         */

        ThreadA.interrupt();
        ThreadB.interrupt();

        if(!ThreadA.isInterrupted()) {
            System.out.println("Thread A intrrupted");
        }
        if(!ThreadB.isInterrupted()){
            System.out.println("Thread B intrrupted");
        }

//        ThreadA.stop();
//        ThreadB.stop();

    }//main

}//class
