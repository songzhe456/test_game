import com.game.entity.Entity;
import com.game.func.Fight;
import com.game.server.GameRoll;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainTest {
    @org.junit.jupiter.api.Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //在此处测试
        GameRoll.addEntity(0, Entity.summon(Entity.EntityType.COW,20,"test"));
        GameRoll.addEntity(1, Entity.summon(Entity.EntityType.COW,5,"test"));
        Method method = GameRoll.class.getDeclaredMethod("doAutoFight");
        method.setAccessible(true);
        method.invoke(new GameRoll());
    }
}
