package com.hfr.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class ReflectionEngine {

	/**
	 * Will search object "instance" for fields of the type "type" and return a list.
	 * @param type
	 * @param instance
	 * @return
	 */
	
	public static List<Field> crackOpenAColdOne(Class<?> type, Class<?> clazz) {

		List<Field> fields = new ArrayList();
		
		//System.out.println(clazz.toString());
		
		for (Field field : clazz.getFields()) {
			if (field.getType().isAssignableFrom(type)) {
				fields.add(field);
			}
		}
		
		if(clazz.getSuperclass() != null) {
			fields.addAll(crackOpenAColdOne(type, clazz.getSuperclass()));
		}
		
		return fields;
	}
	
	public static void setDoubleToZero(Object o, String name) {
		
		Class<?> clazz = o.getClass();

		while(clazz.getSuperclass() != null) {
			
			try {
				
				Field type = ReflectionHelper.findField(o.getClass(), name);
		
				Object val = type.get(o);
				
				if(val != null && val instanceof Double) {
	
					type.setDouble(o, 0);
				}
			
			} catch(Exception x) { }
			
			clazz = clazz.getSuperclass();
		}
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
