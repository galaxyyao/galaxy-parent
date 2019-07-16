package com.galaxy.common.util;

import com.galaxy.common.annotation.SensitiveField;
import com.galaxy.common.enums.SensitiveFieldEnum;
import com.galaxy.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author: 何民明
 * @date: 2019/7/16 11:38
 * @description:
 * 处理需要使用掩码的敏感字段
 */
public class SensitiveFieldUtil {

    private static Logger logger = LoggerFactory.getLogger(SensitiveFieldUtil.class);

    public static void encrypt(Object entity) throws BusinessException {
        if (Objects.isNull(entity)) {
            logger.error("The parameter shouldn't be null");
            return;
        }
        Class<?> clz = entity.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (int i = 0, j = fields.length; i < j; i++) {
            Field field = fields[i];
            if (!field.isAnnotationPresent(SensitiveField.class)) {
                continue;
            }
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                logger.error(e.getCause().getMessage());
                throw new BusinessException("CRM1001");
            }
            if (Objects.isNull(value)) {
                continue;
            }
            if (!(value instanceof String)) {
                logger.error("This types of the field must be String");
                throw new BusinessException("This types of the field must be String");
            }
            String valueStr = value.toString();
            SensitiveField sensitiveFieldAnnotation = field.getAnnotation(SensitiveField.class);
            SensitiveFieldEnum sensitiveFieldEnum = sensitiveFieldAnnotation.type();

            switch (sensitiveFieldEnum) {
                case MOBILE_NO:
                    valueStr = valueStr.substring(0, 3) + valueStr.substring(3, 7).replaceAll("\\S", "*") + valueStr.substring(7);
                    break;
                case IDENTITY_NO:
                    valueStr = valueStr.substring(0, 6) + valueStr.substring(6).replaceAll("\\S", "*");
                    break;
                default:
                    valueStr = valueStr.replaceAll("\\S", "*");
            }

            try {
                field.set(entity, valueStr);
            } catch (IllegalAccessException e) {
                logger.error(e.getCause().getMessage());
                throw new BusinessException("CRM1001");
            }
        }
    }


}
