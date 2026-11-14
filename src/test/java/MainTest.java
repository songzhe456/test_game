import com.game.achievement.Achievement;
import com.game.entity.Entity;
import com.game.place.Place;
import org.junit.jupiter.api.Assertions;

import java.io.File;

public class MainTest {
    @org.junit.jupiter.api.Test
    public void test() {
        //在此处测试
        Achievement ach = new Achievement("hi");
        ach.tryAchieve(true);
        ach.revoke();
        Assertions.assertFalse(ach.isAchieved(),"achieved应为false");
        ach.tryAchieve(true);
        Assertions.assertTrue(ach.isAchieved(),"achieved应为true");
    }
}
