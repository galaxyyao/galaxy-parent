package com.galaxy.common.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtil {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	public enum ENTITY_SAVE_METHOD_ENUM {
		CREATE, UPDATE, DELETE
	}

	public static void fillCommonFields(Object object, ENTITY_SAVE_METHOD_ENUM entitySaveMethod, String operatorUserCode) {
		switch (entitySaveMethod) {
		case CREATE:
			setField(object, "isDeleted", "0");
			setField(object, "createdById", operatorUserCode);
			setField(object, "createdTime", LocalDateTime.now());
			setField(object, "lastModifiedById", operatorUserCode);
			setField(object, "lastModifiedTime", LocalDateTime.now());
			break;
		case UPDATE:
			setField(object, "lastModifiedById", operatorUserCode);
			setField(object, "lastModifiedTime", LocalDateTime.now());
			break;
		case DELETE:
			setField(object, "isDeleted", "1");
			setField(object, "lastModifiedById", operatorUserCode);
			setField(object, "lastModifiedTime", LocalDateTime.now());
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
			logger.error("Failed to set field:" + fieldName, e);
		}
	}
	
	
	public static Object getObjectVal(Object obj, String name) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (!name.equals(fieldName)) {
				continue;
			}
			field.setAccessible(true);
			return field.get(obj);
		}
		return "";
	}
	
	public static String[] getCommonFields(String pkFieldName) {
		List<String> fields = new ArrayList<String>();
		fields.add(pkFieldName);
		fields.add("isDeleted");
		fields.add("createdById");
		fields.add("createdTime");
		fields.add("lastModifiedById");
		fields.add("lastModifiedTime");
		return fields.toArray(new String[fields.size()]);
	}
}
