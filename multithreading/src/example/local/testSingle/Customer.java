package example.local.testSingle;

/************
 * @info : Customer - 고객 정보 클래스
 * @name : Customer
 * @date : 2023/03/07 6:32 PM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 ************/
public class Customer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public Customer() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
