package example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/************
* @info : Thread 문제 예제(금고털이)
* @name : Ex2Question
* @date : 2022/06/27 4:36 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
 * @add : Thread Class 계층은
 * Runnable(최상위) <- Thread <- Police, (Hacker <- asc, desc) <- vault(금고 HackerThread에서 참조
 *
 * 즉 : asc, desc는 Hacker 스레드와 Thread를 확장하고, Runnable을 실행.
 * police 는 Thread를 직접 확장 하여 runnable 실행
 *
 * HcakerThread는 금고 객체에 대한 레퍼런스를 갖고 있음 -> asc, desc 모두 금고에 대한 레퍼런스를 갖고있음.
 *
 * 매번 실행시 마다 console의 결과가 다르게 나타남.
 *
 * JAVA 의 모든 Thread 관련 기능을 캡슐화 함.
 *
 * 1. Runnable 인터페이스를 실행해 class의 객체를 새로운 Thread 객체에 전달하는방법.
 * 2. Thread Class를 직접 확장하는 class 를 만들고 해당 클래스의 객체를 대신 만듬.
 * 두방법 사용가능.
************/
public class Ex2Question {

    public static final int MAX_PASSWORD = 999; // 비밀번호 최대 정수값.
    public static void main(String[] args) {

        Random random = new Random(); // 금고의 랜덤 비밀번호 생성.

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD)); // 금고 객체 생성 후 0 ~ MAX번호를 금고 password에게 부여.

        //Thread 객체 목록
        // 모두 Thread를 상속 받았기 때문에 Type THread 지정 가능. -> 다형성(객체 지향 프로그래밍)
        List<Thread> threads = new ArrayList<>();

        threads.add(new AscendingHackerThread(vault)); //vault에 대한 레퍼런스 떄문에 변수에 넣어줘야함.
        threads.add(new DescendingHackerThread(vault)); // 위와 동일.
        threads.add(new PoliceThread());

        // Code 실행 (최종단계)
        // 모든 정보를 읽고 start.
        for(Thread thread : threads) {
            thread.start();
        }
    } //main

    /**
     * 금고 class
     * 패스워드 부여
     * 패스워드는 생성자를 통해 클래스에 전달.
     * isCorrect 메서드에서는 int guess를 입력받아 맞으면 true 반환.
     * -> 해커의 속도를 늦추기 위해 응답 지연 시킴.
     */
    private static class Vault {
        private int password;

        public Vault(int password){
            this.password = password;
        }

        public boolean isCorrectPassword(int guess){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }

            return this.password == guess;
        }
    }//Vault

    /**
     * abstract Hacker Class(제네릭 해커 스레드) -> Hacker들이 공통적으로 기능을 가져다 사용하게 하기 위함.
     *
     * Vault는 Protected이며 생성자를 통해 전달. (HackerThread 생성자.)
     *
     * Hacker Thread에서 상속될 각각의 구체적인 Hacker Thread는
     * class이름을 Thread이름으로 사용. 우선순위 최댓값 설정
     *
     * 해당 class는 Thread를 extends 함으로 메서드를 Override 가능. -> start
     */
    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault){
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Thread Start : " + this.getName());
            super.start();
        }
    }//HackThread

    /**
     * HackerThread를 확장하는 class
     * 해당 Thread는 비밀번호 추측시 모든 숫자를 오름차순으로 평가.
     * 모든 HackerThread와 Thread의 기능을 가져옴.
     *
     * TODO : run 메서드 override, class에 특정 로직 작성.
     */
    private static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault){
            super(vault);
        }

        /**
         * Thread run 시
         * 0 부터 비밀번호 최댓값임 999 까지 반복
         * Vault의 isCorrect 메서드로 비밀번호를 올바르게 추측했는지 확인.
         * -> abstract class의 미리 설정된 Thread이름 출력 및 프로그램 종료(System.exit)
         */
        @Override
        public void run(){
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if(vault.isCorrectPassword(guess)){
                    System.out.println(this.getName() + "PassWord find : " + guess);
                    System.exit(0);
                }
            }
        }
    } //Ascending Class

    /**
     * HackerThread를 확장하는 Class
     * 비밀번호를 내림차순으로 평가.
     * 위의 class와 동일.
     */
    private static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if(vault.isCorrectPassword(guess)){
                    System.out.println(this.getName() + "PassWord find : " + guess);
                    System.exit(0);
                }
            }
        }
    } // Descending Class

    /**
     * 경찰 class -> Thread를 직접 확장함.
     *
     * 캡슐화된 모든 기능을 해커 스레드에 가져올 수 없음. -> 즉 해당 class 의 기능을 Hacker에서 가져가지 못함.
     *
     * 10부터 0까지 카운트하며 각 카운트 사이 1초씩 일시정지.
     * Hacker를 잡기 전까지 몇초가 남았는지 출력.
     * 10초가 지나면 Hacker를 잡고 프로그램 종료.
     */
    private static class PoliceThread extends Thread {

        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(i);
            }

            System.out.println("Game Over for Hacker");
            System.exit(0);
        }
    }// Police Class
}//class
