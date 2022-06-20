package thread;

/************
* @info :
* @name : ThreadExceptionHandler
* @date : 2022/06/20 5:06 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
public class ThreadExceptionHandler {

    public static void main(String[] args) throws InterruptedException{

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                     throw new RuntimeException("Runtime Error"); // Exception 발생.
            }
        });
        thread.setName("MALIBU");

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Error in Thread : " + t.getName() + ", Error is : " + e.getMessage()); // Thread name, Message 출력.
            }
        });

        thread.start();
    }//main

}

/**
 * Java 에서 체크하지 않은 예외(Exception)은 개발자가 특정 방법으로 처리하지 않으면!  !!!!!!전체 Thread Down 됨!!!!!!
 *
 * thread.setUncaughtExceptionHandler(); 메서드를 통해 처음부터 !!전체 THread에 해당하는 예외 Handler 지정 가능
 *
 * -> Thread 내에서 Exception 발생시 catch가 되지 않으면 Handler 호출됨.
 *
 * 해당 class에서는 try/catch가 없기 때문에 error 발생.  또한 throw new 를 통하여 Exception을 발생시킴.
 *
 * CODE 설명
 *
 * Error 핸들링을 하기위해서 thread에 이름을 지정.(확인하기 위함)
 * run 오버라이드 메서드에서 던진 에러 메세지 확인.
 * Handler 에서 Error가 발생한 Thread의 이름과, 메세지 확인
 *
 * 업무에서는 상항에 따른 여러가지 Exception 메세지와 throw를 적절하게 사용.
 *
 */
