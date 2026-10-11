import com.game.entity.Cow;
import com.game.entity.Entity;
import com.game.entity.poster.PacketPoster;
import com.game.server.GameRoll;

public class MainTest {
    @org.junit.jupiter.api.Test
    public void test() {
        //在此处测试
        Cow.summon(Entity.EntityType.COW, 30, "temp");
        Cow.summon(Entity.EntityType.COW, 30, "temp");
        Cow.summon(Entity.EntityType.COW, 30, "temp");
        Cow.summon(Entity.EntityType.COW, 30, "temp");
        Cow.summon(Entity.EntityType.COW, 30, "temp");
        Cow.summon(Entity.EntityType.COW, 30, "temp");
        Cow.summon(Entity.EntityType.COW, 30, "temp");
        Cow.summon(Entity.EntityType.COW, 30, "temp");
        PacketPoster.summon(Entity.EntityType.POSTER, 20, "poster");
        new GameRoll().fight();
    }
}
