package com.galaxy.common.id;

import java.util.UUID;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class IdWorker {
    private IdWorker() {

    }

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public static String generateUuidWithoutDash() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
