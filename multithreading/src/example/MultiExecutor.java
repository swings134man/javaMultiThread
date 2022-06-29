package example;

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
