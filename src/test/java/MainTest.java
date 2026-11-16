import com.game.achievement.Achievement;
import com.game.archive.Archive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainTest {
    @Test
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

    @Test
    public void testArchive() {
        Archive archive = new Archive();
        archive.showEntityArchive("cow", 20);
    }

    @Test
    public void testCommand(){

    }
}
