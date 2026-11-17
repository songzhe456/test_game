import com.game.achievement.Achievement;
import com.game.archive.Archive;
import com.game.entity.Entities;
import com.game.func.Command;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    public void testArchive() {
        Archive archive = new Archive();
        archive.showEntityArchive("cow", 20);
    }

    @Test
    public void testCommand(){
        //new GameRoll();
        new Entities().summon();
        Assertions.assertDoesNotThrow(() -> new Command().handlePreCommand("show_archive man]"),"无法在未创建GameRoll实例时使用show_archive");
    }
}
