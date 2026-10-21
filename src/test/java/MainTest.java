import com.game.entity.Cow;
import com.game.entity.Entities;
import com.game.entity.Entity;
import com.game.server.GameRoll;
import com.toolkit.util.*;

public class MainTest {
    @org.junit.jupiter.api.Test
    public void test() {
        //在此处测试
        Entity cow = Cow.summon(Entity.EntityType.COW,20,"cow");
        cow.addToPlace(Entities.sea);
        new GameRoll(null,null).fight();
    }
}
