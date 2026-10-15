import com.toolkit.util.*;

public class MainTest {
    @org.junit.jupiter.api.Test
    public void test() {
        //在此处测试
        String blueText = ColorUtil.blue("text");
        System.out.println(blueText);
        System.out.println(ColorUtil.bold(ColorUtil.red(MathUtil.isInvalid(Double.NaN) ? "不合法" : "合法")));
    }
}
