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
    private double damage;
    private EntityType type;
    private boolean isDead;
    private double critValue;
    private static final Logger logger = LoggerFactory.getLogger(Entity.class);
    private static String ownerId;
    private static boolean isFirstSummon;
    private Move move;

    public Entity(int index,EntityType type,double health,String id){
        this.type = type;
        setId(id);
        setHealth(health);
        if(type != EntityType.LOOT) {
            entitySummoned(health, id);
        } else if (type == EntityType.LOOT) {
            Item lootInLoot = GameRoll.getItems().get(RandomUtil.getRandInt(0, GameRoll.getItems().size() - 1));
            GameRoll.getItems().add(lootInLoot);
            lootEntitySummoned(health,id, lootInLoot);
        }
        ExceptionUtils.nullPointerExceptionTrigger(type);
        if (health <= 0 || isDead){
            die();
        }
        GameRoll.addEntity(index,this);
    }

    public  double getDamage() {
        return this.damage;
    }

    public double getCritValue() {
        return critValue;
    }

    public void setCritValue(double critValue) {
        this.critValue = critValue;
    }

    public static boolean isFirstSummon() {
        return isFirstSummon;
    }

    public static void setFirstSummon(boolean firstSummon) {
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
        if (!isFirstSummon()) {
            logger.info("{}的血量由{}变为{}，位置是{}", id, originHealth, Math.round(health - (Math.round((critValue) * 10 / 10.0))),this.move);
        }
        else if(originHealth == 0 && isFirstSummon){
            logger.info("{}刚被生成，血量已由{}变为{}，位置是{}", id, originHealth, Math.round(health * 10) / 10.0,this.move);
            setFirstSummon(false);
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void entitySummoned(double health,String id){
        logger.info("生成了{}:[\n    hp:{}\n]", id, health);
    }

    public void lootEntitySummoned(double health, String id, Item item){
        logger.info("生成了{}:[\n    hp:{}\n    loots:{}\n]", id, health, item);
    }

    public void damage(double health){
        this.health -= this.damage;
    }

    public void die(){
        Runnable entityDieRunnable = () -> {
            logger.info("{}死了", id);
            this.health = 0;
            isDead = true;
            ownerId = id;
            Runnable lootSpawnRunnable = () -> {
                GameRoll.getEntities().remove(this);
                LootEntity entityLoot = new LootEntity(EntityType.LOOT, 5.0, id + "'s loot");
                logger.debug("有实体死亡，当前实体列表为{}",GameRoll.getEntities());
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
            logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round((getDamage() / critValue) * 10) / 10.0);
            target.setHealth(target.getHealth() - (getDamage() / critValue));
        }
        else {
            target.die();
            logger.warn("目标{}已死亡",target);
        }
    }

    public static Entity summon(EntityType type,double health,String id) {
        try {
            setFirstSummon(true);
            return switch (type) {
                case NULL -> new NullEntity(health, id);
                case COW -> new Cow(health, id);
                case MAN -> new Man(health, id);
                case LOOT -> new LootEntity(type, health, id);
                case KFC -> new KFCEntity(health, id);
                case MOUSE -> new Mouse(health, id);
                case RANDOM_ENTITY -> new RandomPool.RandomEntity(health, id);
                case DIVISION_ZERO -> new DivisionZeroEntity(health, id);
                case CHINESE -> new Chinese(health,id);
                default -> new Cow(health, "实体生成出错");
            };
        } catch (Exception e) {
            logger.error("{}生成失败",id,e);
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
}
