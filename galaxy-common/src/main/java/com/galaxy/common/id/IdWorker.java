package com.galaxy.common.id;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxy.common.exception.UnexpectedRuntimeException;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class IdWorker {
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public static String generateUuidWithoutDash() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
