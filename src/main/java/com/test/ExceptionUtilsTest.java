package com.test;

import com.game.entity.Entity;
import com.game.func.ExceptionUtils;
import com.game.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExceptionUtilsTest {
    @Test
    void test(){
        ExceptionUtils.nullPointerExceptionTrigger(Entity.EntityType.NULL);
    }
}
