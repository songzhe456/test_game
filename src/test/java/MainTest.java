import com.game.entity.Cow;
import com.game.item.Coke;
import com.game.server.GameRoll;

public class MainTest {
    @org.junit.jupiter.api.Test
    public void test() {
        //在此处测试
        GameRoll.addItem(new Coke("coke"));
        System.out.println(GameRoll.getItems());
        new Cow(20,"cow").selectFoodThenEat("coke",null);
    }
}
