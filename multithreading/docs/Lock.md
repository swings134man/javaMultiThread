## Lock 

### Reentrant Lock
- 가장 일반적인 Lock 이.  java.util.concurrent.locks 패키지의 lock 클래스 중 하나이다.
- Reentrant Lock 은 객체에 적용된 Synchronized 키워드처럼 동작한다.
  - Synchronized 의 wait() & notify() 처럼 특정 조건에서 Lock 을 획득하고 해제할 수 있다.
- <b>즉 명확한 Locking, UnLocking 이 필요하다</b>
- Unlocking 을 하지 않으면 DeadLock 이 발생될 수 있다.
  - Unlocking 을 하기전에 Exception 이 발생하면, Unlock 이 안되기에 try/finally 블록으로 감싸서 임계영역에 대한 Locking/Unlocking 을 해줘야 한다.

```java
public class Sample {
    Lock lockObj = new ReentrantLock();
    
    public void method() throws SomeException{
        // Locking 
        lockObj.lock();

        // throw Exception 이 발생하면 Unlocking 이 안되기에 finally 블록에서 Unlocking 을 해줘야 한다.
        try {
            // 보호해야할 리소스에 대한 작업
            someOperation();
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            // Unlocking
            lockObj.unlock();
        }
    }
}
```