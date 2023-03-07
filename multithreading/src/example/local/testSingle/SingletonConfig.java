package example.local.testSingle;

/************
 * @info : Singleton 클래스
 * @name : SingletonConfig
 * @date : 2023/03/07 5:47 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
public class SingletonConfig {

    private static SingletonConfig singletonInstance = null; // singleton instance
    private static String name = "class 1";

    private ThreadLocal<Customer> threadLocal = new ThreadLocal<>();

    // Thread Local set
    public void setThreadLocal() {
    }

    private SingletonConfig(){} //private Constructor

    // 외부로 return
    public static SingletonConfig getInstance() {
        if(singletonInstance == null) {
            singletonInstance = new SingletonConfig();
        }
        return singletonInstance;
    }

    // Getter/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}//class
