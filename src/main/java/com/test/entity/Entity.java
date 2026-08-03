package com.test.entity;

import com.test.func.Attack;
import com.test.func.Crit;
import com.test.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Entity implements Attack {
    private double health;
    private String id;
    private double damage;
    private EntityType type;
    private boolean isDead;

    private Crit crit = new Crit();
    private double critValue;
    private static final Logger logger = LoggerFactory.getLogger(Entity.class);
    private static String ownerId;
    private static boolean isFirstSummon;
    private static Entity lootInLoot;
    public Entity(EntityType type,double health,String id){
        this.type = type;
        setId(id);
        setHealth(health);
        if(type != EntityType.LOOT) {
            entitySummoned(health, id);
        } else if (type == EntityType.LOOT) {
            lootInLoot = new Cow(20,ownerId + " of loot");
            Server.ClientHandler.getEntities().add(lootInLoot);
            lootEntitySummoned(health,id,lootInLoot);
        }
        if (type.equals(EntityType.NULL)){
            try {
                throw new NullPointerException("实体类型为" + EntityType.NULL);
            }
            catch (Exception e) {
                logger.error("实体类型为NULL导致了以下异常:",e);
                //System.exit(1);
            }
        }
        if (health <= 0 || isDead){
            die();
        }
    }

    public  double getDamage() {
        return this.damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
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
        MOUSE
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
            logger.info("{}的血量由{}变为{}", id, originHealth, Math.round(health - (Math.round((critValue) * 10 / 10.0))));
        }
        else if(originHealth == 0 && isFirstSummon){
            logger.info("{}刚被生成，血量已由{}变为{}", id, originHealth, Math.round(health * 10) / 10.0);
            setFirstSummon(false);
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void entitySummoned(double health,String id){
        logger.info("生成了{}:[\n    hp:{}\n]", id, health);
    }

    public void lootEntitySummoned(double health, String id, Entity entity){
        logger.info("生成了{}:[\n    hp:{}\n    loots:{}\n]", id, health, Server.ClientHandler.getEntities().get(getEntityIndex(entity)));
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
                Server.ClientHandler.getEntities().remove(this);
                LootEntity entityLoot = new LootEntity(EntityType.LOOT, 5.0, id + "'s loot");
                logger.debug("有实体死亡，当前实体列表为{}",Server.ClientHandler.getEntities());

            };
            Thread lootSpawnThread = new Thread(lootSpawnRunnable);
            lootSpawnThread.setName("Loot Spawn Thread");
            lootSpawnThread.start();
        };
        Thread entityDieThread = new Thread(entityDieRunnable);
        entityDieThread.setName("Entity Die Thread");
        entityDieThread.start();
    }

    public void attack(Entity target, double health,double damage,double critValue){
        logger.info("{}攻击了{}造成了{}点血量", getId(), target.getId(), Math.round((getDamage() / critValue) * 10) / 10.0);
        target.setHealth(target.getHealth() - (getDamage() / critValue));
    }

    public static Entity summon(String type,double health,String id){
        if(Objects.equals(type, "cow")){
            setFirstSummon(true);
            return new Cow(health, id);
        }
        else if (Objects.equals(type, "man")){
            setFirstSummon(true);
            return new Man(health, id);
        } else if (Objects.equals(type, "kfc entity")) {
            setFirstSummon(true);
            return new KFCEntity(health,id);
        } else if (Objects.equals(type,"mouse")) {
            setFirstSummon(true);
            return new Mouse(health, id);
        } else {
            setFirstSummon(true);
            return new NullEntity(health, id);
        }
    }

    @Override
    public String toString() {
        return id;
    }

    public static Integer getEntityIndex(Entity entity){
        if(Server.ClientHandler.getEntities().indexOf(entity) < 0) {
            logger.warn("由于战利品在实体索引中为-1，所以已将其处理为最大索引");
            return Server.ClientHandler.getEntities().size() - 1;
        }
        return Server.ClientHandler.getEntities().indexOf(lootInLoot);
    }
    public void checkEntityHealth(){
        if (health <= 0){
            die();
        }
    }
}
