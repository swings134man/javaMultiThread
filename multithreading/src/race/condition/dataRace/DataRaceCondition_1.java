package race.condition.dataRace;

/************
 * @info :
 * @name : DataRaceCondition_1
 * @date : 2024. 6. 24. 오후 6:12
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description : CPU와 컴파일러는 코드를 최적화하기 위해 노력함. -> 코드를 재배치함.
 * 그 결과 코드의 실행 순서가 예상과 다를 수 있음.
 ************/
public class DataRaceCondition_1 {
    int x = 0;
    int y = 0;


    // 두개의 메서드는 CPU, 컴파일러의 관점에서 논리가 동일하므로,같다고 판단. -> 재배치 가능.
    public void order1() {
        x++;
        y++;
    }
    public void order2() {
        x++;
        y++;
    }

    /**
     * 비순차적으로 실행되지 않는 코드.
     * 각변수가 이전결과 값에 의존하기 때문. -> 순차적으로 실행되어야 함.
     */
    public void notOrdering() {
        int x = 1;
        int y = x + 2;
        int z = y + 3;
    }
}
