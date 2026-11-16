package com.game.archive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Archive {
    private static final Logger LOGGER = LoggerFactory.getLogger(Archive.class);
    public void showEntityArchive(String name,double health){
        StringBuilder entityArchiveContent = buildEntityArchive(name, health);
        LOGGER.info(entityArchiveContent.toString());
    }
    public StringBuilder buildEntityArchive(String name,double health){
        StringBuilder sb = new StringBuilder();
        sb.append("\n")
                .repeat("-", 20)
                .append("\n")
                .append("|")
                .append("实体名称：")
                .append("\t")
                .append(name)
                .append("|")
                .append("\n")
                .append("|")
                .append("实体血量：")
                .append("\t")
                .append(health)
                .append("|")
                .append("\n")
                .repeat("-",20);
        return sb;
    }
}
