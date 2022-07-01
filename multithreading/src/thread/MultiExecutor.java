package thread;

import java.util.ArrayList;
import java.util.List;

/************
* @info : 스레드 생성 : MultiExecutor 솔루션
* @name : MultiExecutor
* @date : 2022/06/29 11:32 PM
* @author : SeokJun Kang(swings134@gmail.com)
* @version : 1.0.0
************/
public class MultiExecutor {

    private final List<Runnable> tasks;

    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    /**
     * Executes all the tasks concurrently
     */
    public void executeAll() {
        List<Thread> threads = new ArrayList<>(tasks.size());

        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            threads.add(thread);
        }

        for(Thread thread : threads) {
            thread.start();
        }
    }
}
/**
 * 개요
 *
 * 해당 class의 Client는 Runnable 작업의 목록 생성 -> 해당 목록을 MultiExecutor의 생성자에게 제공
 *
 * Client가 executeAll() 실행 -> MultiExecutor가 주어진 모든 작업 실행
 *
 * 각 작업을 서로 다른 Thread로 전달하여 MultiExecutor가 모든 작업을 동시에 진행.
 */