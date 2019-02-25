package com.hfr.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionEngine {

	/**
	 * Will search object "instance" for fields of the type "type" and return a list.
	 * @param type
	 * @param instance
	 * @return
	 */
	
	public static List<Field> crackOpenAColdOne(Class<?> type, Object instance) {

		List<Field> fields = new ArrayList();
		
		for (Field field : instance.getClass().getFields()) {
			if (field.getType().isAssignableFrom(type)) {
				fields.add(field);
			}
		}
		
		return fields;
	}
	
	public static List<Object> pryObjectsFromFieldList(List<Field> fields, Object o) {
		
		List<Object> objects = new ArrayList();

		for (Field field : fields) {
			
			try {
				
				objects.add(field.get(o));
				
			} catch(Exception ex) {
				
			}
		}
		
		return objects;
	}
}
