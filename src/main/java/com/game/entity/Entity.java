package com.game.entity;

import com.game.entity.func.Direction;
import com.game.entity.func.Move;
import com.game.func.Attack;
import com.game.func.ExceptionUtils;
import com.game.item.Item;
import com.game.server.GameRoll;
import com.game.util.RandomPool;
import com.game.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Entity implements Attack {
    private double health;
    private String id;
    private final EntityType type;
    private boolean isDead;
    private double critValue;
    private static final Logger LOGGER = LoggerFactory.getLogger(Entity.class);
    private boolean isFirstSummon;
    private Move move;

    public Entity(int index,EntityType type,double health,String id){
        this.type = type;
        setId(id);
        setHealth(health);
        if(type != EntityType.LOOT) {
            entitySummoned(health, id);
        } else {
            Item lootInLoot = GameRoll.getItems().get(RandomUtil.getRandInt(0, GameRoll.getItems().size() - 1));
            GameRoll.getItems().add(lootInLoot);
            lootEntitySummoned(health,id, lootInLoot);
        }
        ExceptionUtils.nullPointerExceptionTrigger(type);
        GameRoll.addEntity(index,this);
        setFirstSummon(true);
    }

    public double getCritValue() {
        return critValue;
    }

    public void setCritValue(double critValue) {
        this.critValue = critValue;
    }

    public boolean isFirstSummon() {
        return isFirstSummon;
    }

    public void setFirstSummon(boolean firstSummon) {
        isFirstSummon = firstSummon;
    }

    public enum EntityType{
        NULL,
        DEFAULT,
        COW,
        MAN,
        LOOT,
        KFC,
        MOUSE,
        RANDOM_ENTITY,
        DIVISION_ZERO,
        CHINESE
    }

    public double getHealth() {
        return Math.max(0.0,health);
    }

    public  String getId() {
        return id;
    }

    public void setHealth(double health) {
        double originHealth = this.health;
        this.health = Math.max(0,health);
        if (!isFirstSummon() && move != null) {
            LOGGER.info("{}的血量由{}变为{}，位置是{}", id, originHealth, health,this.move);
        }
        else if(originHealth == 0 && isFirstSummon){
            LOGGER.info("{}刚被生成，血量已由{}变为{}", id, originHealth, health);
            setFirstSummon(false);
        } else if (!isFirstSummon()) {
            LOGGER.info("{}的血量由{}变为{}", id, originHealth, health);
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void entitySummoned(double health,String id){
        LOGGER.info("生成了{}:[\n    hp:{}\n]", id, health);
    }

    public void lootEntitySummoned(double health, String id, Item item){
        LOGGER.info("生成了{}:[\n    hp:{}\n    loots:{}\n]", id, health, item);
    }

    public void die(){
        Runnable entityDieRunnable = () -> {
            LOGGER.info("{}死了", id);
            this.health = 0;
            isDead = true;
            Runnable lootSpawnRunnable = () -> {
                GameRoll.getEntities().remove(this);
                summon(EntityType.LOOT, 5.0, id + "'s loot");
                LOGGER.debug("有实体死亡，当前实体列表为{}",GameRoll.getEntities());
            };
            Thread lootSpawnThread = new Thread(lootSpawnRunnable);
            lootSpawnThread.setName("Loot Spawn Thread");
            lootSpawnThread.start();
        };
        Thread entityDieThread = new Thread(entityDieRunnable);
        entityDieThread.setName("Entity Die Thread");
        entityDieThread.start();
    }

    public void attack(Entity target, double critValue){
        if(target.getHealth() > 0) {
            LOGGER.info("{}攻击了{}造成了{}点血量", this.getId(), target.getId(),this.critValue);
            target.setHealth(target.getHealth() - this.critValue);
        }
        else {
            LOGGER.warn("目标{}已死亡",target);
        }
    }

    public static Entity summon(EntityType type, double health, String id) {
        try {
            return switch (type) {
                case NULL -> new NullEntity(health, id);
                case COW -> new Cow(health, id);
                case MAN -> new Man(health, id);
                case LOOT -> new LootEntity(health, id);
                case KFC -> new KFCEntity(health, id);
                case MOUSE -> new Mouse(health, id);
                case RANDOM_ENTITY -> new RandomPool.RandomEntity(health, id);
                case DIVISION_ZERO -> new DivisionZeroEntity(health, id);
                case CHINESE -> new Chinese(health,id);
                default -> new Cow(health, "实体生成出错");
            };
        } catch (Exception e) {
            LOGGER.error("{}生成失败",id,e);
            return null;
        }
    }

    public void move(Direction.Directions directions,String id){
        move = new Move(directions,id);
    }

    @Override
    public String toString() {
        return id;
    }

    public EntityType getType() {
        return type;
    }

    public void tick(){
        boolean needTick = true;
        while (!this.isDead && needTick){
            try {
                attack(GameRoll.getEntities().get(RandomUtil.getRandInt(0, GameRoll.getEntities().size() - 1)), this.getCritValue());
                LOGGER.info("{}完成了一次初始行动", id);
                needTick = false;
            } catch (Exception e) {
                LOGGER.error("实体tick异常");
                break;
            }
        }
    }
}
