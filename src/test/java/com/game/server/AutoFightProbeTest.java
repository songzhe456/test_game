package com.game.server;

import com.game.entity.Entities;
import com.game.entity.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 探针：直接调用真实的 GameRoll.checkPlaceEffect() 与 doAutoFight()，
 * 验证“自动战斗到底有没有打到活人”。
 */
public class AutoFightProbeTest {

    static class CaptureAppender extends AppenderBase<ILoggingEvent> {
        final java.util.List<String> messages = new java.util.ArrayList<>();
        @Override
        protected void append(ILoggingEvent e) {
            messages.add(e.getFormattedMessage());
        }
    }

    private CaptureAppender appender;

    @BeforeEach
    void cleanStaticState() {
        GameRoll.getEntities().clear();
    }

    private void attachCapture() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        appender = new CaptureAppender();
        appender.start();
        root.addAppender(appender);
    }

    private Map<String, Double> snapshot() {
        Map<String, Double> m = new LinkedHashMap<>();
        for (Entity e : GameRoll.getEntities()) {
            if (e != null) m.put(e.getId(), e.getHealth());
        }
        return m;
    }

    /** 在 doAutoFight 之前清空已捕获日志，使计数只反映自动战斗本身 */
    private void resetCapture() {
        appender.messages.clear();
    }

    private void printDiff(Map<String, Double> before, Map<String, Double> after, String tag) {
        System.out.println("===== " + tag + " =====");
        boolean anyHit = false;
        for (String id : before.keySet()) {
            Double b = before.get(id);
            Double a = after.get(id);
            boolean lost = (a != null && b != null && a < b - 1e-9);
            if (lost) anyHit = true;
            String flag = lost ? "  <-- 掉血" : "";
            System.out.printf("  %-12s %.2f -> %s%s%n", id, b, a, flag);
        }
        long deducted = appender.messages.stream().filter(m -> m.contains("扣除了")).count();
        System.out.println("  [自动战斗造成的 扣除了 日志] = " + deducted);
        System.out.println("  [自动战斗是否真的打到活人(有掉血)] = " + anyHit);
        System.out.println();
    }

    /** 逐对打印自动战斗的出手条件判定（用当前血量） */
    private void printConditionTable(String tag) {
        System.out.println("----- 自动战斗出手条件逐对判定 (" + tag + ") -----");
        List<Entity> list = GameRoll.getEntities();
        for (int i = 0; i < list.size() - 1; i++) {
            Entity e = list.get(i);
            Entity n = list.get(i + 1);
            if (e == null || n == null) continue;
            double cv = e.getCritValue();
            double eh = e.getHealth();
            double nh = n.getHealth();
            boolean cond = (cv > nh) && (eh > nh);
            String verdict = cond ? "会出手" : "不出手";
            String why = cond ? "" :
                    (cv <= nh ? "攻方暴击值(" + f(cv) + ")<=目标血量(" + f(nh) + ")" :
                                 "攻方血量(" + f(eh) + ")<=目标血量(" + f(nh) + ")");
            System.out.printf("  %-12s(cv=%s,hp=%s) -> %-12s(hp=%s) : %s %s%n",
                    e.getId(), f(cv), f(eh), n.getId(), f(nh), verdict, why);
        }
        System.out.println();
    }

    private static String f(double d) {
        if (Double.isInfinite(d)) return "Inf";
        return String.format("%.1f", d);
    }

    @Test
    void faithfulRound_autoFightAfterPlaceEffect() throws Exception {
        attachCapture();
        new Entities().summon();
        GameRoll gr = new GameRoll();

        Method cp = GameRoll.class.getDeclaredMethod("checkPlaceEffect");
        cp.setAccessible(true);
        cp.invoke(gr);                       // 真实每轮：先地形效果

        printConditionTable("checkPlaceEffect 之后");

        Map<String, Double> before = snapshot();
        Method af = GameRoll.class.getDeclaredMethod("doAutoFight");
        af.setAccessible(true);
        resetCapture();                      // 只看自动战斗自身的日志
        boolean crashed = false;
        try {
            af.invoke(gr);
        } catch (Exception ex) {
            crashed = true;
            System.out.println("  doAutoFight 抛出: " + ex.getCause());
        }
        Map<String, Double> after = snapshot();

        printDiff(before, after, "真实每轮：checkPlaceEffect -> doAutoFight");
        System.out.println("  jack(1/0) 是否让自动战斗循环崩溃 = " + crashed);
    }

    @Test
    void isolated_autoFightWithoutPlaceEffect() throws Exception {
        attachCapture();
        new Entities().summon();
        GameRoll gr = new GameRoll();

        printConditionTable("无任何地形效果(隔离)");

        Map<String, Double> before = snapshot();
        Method af = GameRoll.class.getDeclaredMethod("doAutoFight");
        af.setAccessible(true);
        resetCapture();
        boolean crashed = false;
        try {
            af.invoke(gr);
        } catch (Exception ex) {
            crashed = true;
            System.out.println("  doAutoFight 抛出: " + ex.getCause());
        }
        Map<String, Double> after = snapshot();

        printDiff(before, after, "隔离：仅 doAutoFight（无地形效果）");
        System.out.println("  jack(1/0) 是否让自动战斗循环崩溃 = " + crashed);
    }
}
