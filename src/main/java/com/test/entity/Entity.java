package com.test.entity;

import com.test.func.Attack;
import com.test.func.Crit;
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
    private ArrayList<Entity> entities = new ArrayList<>();
    private Crit crit = new Crit();
    private double critValue;
    private static final Logger logger = LoggerFactory.getLogger(Entity.class);
    private static String ownerId;
    public Entity(EntityType type,double health,String id){
        this.type = type;
        setId(id);
        setHealth(health);
        if(type != EntityType.LOOT) {
            entitySummoned(health, id);
        } else if (type == EntityType.LOOT) {
            entities.add(new Cow(20,ownerId + " of loot"));
            lootEntitySummoned(health,id,entities);
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

    public enum EntityType{
        NULL,
        DEFAULT,
        COW,
        MAN,
        LOOT,
        KFC
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
        if (originHealth != 0) {
            logger.info("{}的血量由{}变为{}", id, originHealth, Math.round(health - (Math.round((critValue) * 10 / 10.0))));
        }
        else if(originHealth == 0){
            logger.info("{}刚被生成，血量已由{}变为{}", id, originHealth, Math.round(health * 10) / 10.0);
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void entitySummoned(double health,String id){
        logger.info("生成了{}:[\n    hp:{}\n]", id, health);
    }

    public static void lootEntitySummoned(double health, String id, ArrayList entities){
        logger.info("生成了{}:[\n    hp:{}\n    loots:{}\n]", id, health, entities);
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
                LootEntity entityLoot = new LootEntity(EntityType.LOOT, 5.0, id + "'s loot");
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
            return new Cow(health, id);
        }
        else if (Objects.equals(type, "man")){
            return new Man(health, id);
        } else if (Objects.equals(type, "kfc entity")) {
            return new KFCEntity(health,id);
        } else {
            return new NullEntity(health, id);
        }
    }

    @Override
    public String toString() {
        return id;
    }
}
