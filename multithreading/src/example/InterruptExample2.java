package example;

import javax.swing.*;

public class InterruptExample2 {

    public static void main(String[] args) {

        Thread thread = new Thread_A();
        thread.start();

        System.out.println("status : " + thread.isInterrupted()); //false

        String input = JOptionPane.showInputDialog("아무 값 입력.");
        System.out.println("입력값 : " + input);

        thread.interrupt(); // interrupt() 호출시 interrupted 상태 = true로 변경
        System.out.println("After status : " + thread.isInterrupted()); //true

        // main thread가 interrupt 되었는지 확인 하는 코드 .
        System.out.println("interrupted() : " + Thread.interrupted());
    }//main

    private static class Thread_A extends Thread {
        @Override
        public void run() {
            int i = 10;

            while (i !=0 && !isInterrupted()) {
                System.out.println(i--);
                for (long x = 0; x < 2500000000L; x++); // 시간 지연
            }
            System.out.println("카운트가 종료됬습니다.");
        }
    }
}
