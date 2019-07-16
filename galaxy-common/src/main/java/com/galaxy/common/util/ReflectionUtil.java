package com.galaxy.common.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.galaxy.common.exception.UnexpectedRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
public class ReflectionUtil {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	/**
	 * 保存方式枚举
	 * CREATE（新建）/UPDATE（更新）/DELETE（删除）
	 */
	public enum ENTITY_SAVE_METHOD_ENUM {
		/**
		 * 新建
		 */
		CREATE,
		/**
		 * 更新
		 */
		UPDATE
	}

	public static void fillCommonFields(Object object, ENTITY_SAVE_METHOD_ENUM entitySaveMethod, String operatorUserCode) {
		switch (entitySaveMethod) {
		case CREATE:
			setField(object, "createUserCode", operatorUserCode);
			setField(object, "createTime", LocalDateTime.now());
			setField(object, "updateUserCode", operatorUserCode);
			setField(object, "updateTime", LocalDateTime.now());
			break;
		case UPDATE:
			setField(object, "updateUserCode", operatorUserCode);
			setField(object, "updateTime", LocalDateTime.now());
			break;
		default:
			break;
		}
	}

	private static void setField(Object object, String fieldName, Object fieldValue) {
		Field field;
		try {
			field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(object, fieldValue);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new UnexpectedRuntimeException("Failed to set field:" + fieldName, e);
		}
	}
	
	
	public static Object getObjectFieldValue(Object obj, String fieldName) {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (!fieldName.equals(field.getName())) {
				continue;
			}
			field.setAccessible(true);
			try {
				return field.get(obj);
			} catch (IllegalAccessException e) {
				throw new UnexpectedRuntimeException("Field not accessible:" + fieldName, e);
			}
		}
		throw new UnexpectedRuntimeException("No such field:" + fieldName);
	}
	
	public static String[] getCommonFields(String pkFieldName) {
		List<String> fields = new ArrayList<>();
		fields.add(pkFieldName);
		fields.add("createUserCode");
		fields.add("createTime");
		fields.add("updateUserCode");
		fields.add("updateTime");
		return fields.toArray(new String[fields.size()]);
	}
}
