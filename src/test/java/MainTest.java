import com.game.entity.Entity;
import com.game.place.Place;

import java.io.File;

public class MainTest {
    @org.junit.jupiter.api.Test
    public void test() {
        //在此处测试
        Entity entity = new Entity(0, Entity.EntityType.CHINESE, 50, "test") {
            @Override
            public void addToPlace(Place place) {
                super.addToPlace(place);
            }

            @Override
            public void die() {
                System.out.println("我没死");
            }
        };
    }
}
