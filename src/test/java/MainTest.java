import com.game.achievement.Achievement;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

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
        Map<String,Achievement> achievements = new HashMap<>();
        achievements.get("oh").tryAchieve(true);
    }
}
