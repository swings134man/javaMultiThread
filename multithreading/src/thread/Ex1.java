package thread;
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
