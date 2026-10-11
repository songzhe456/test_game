import com.game.entity.Cow;
import com.game.entity.Entity;
import com.game.entity.Man;
import com.game.entity.func.Direction;
import com.game.item.Coke;
import com.game.server.GameRoll;
import org.junit.jupiter.api.Assertions;

public class MainTest {
    @org.junit.jupiter.api.Test
    public void test() {
        //在此处测试
        GameRoll.addItem(new Coke("coke"));
        System.out.println("当前测试物品" + GameRoll.getItems());
        Cow cow = (Cow) Cow.summon(Entity.EntityType.COW,20,"cow");
        cow.move(Direction.Directions.UP,"cow");
        new Man(20,"man").attack(cow,20);
        new Man(20,"man").attack(cow,20);
        new Man(20,"man").attack(cow,20);
        new Man(20,"man").attack(cow,20);
        new Man(20,"man").attack(cow,20);
        Assertions.assertFalse(cow.isFirstSummon());
    }
}
