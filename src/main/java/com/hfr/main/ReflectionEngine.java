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
			} catch(Exception ex) { }
		}
		
		return objects;
	}
	
	//advanced reflection tool for all you reflection needs!
	//reflects fields not only out of your class but also out of every superclass!
	//type-safe, comes with handy default return value in case operation was unsuccessful!
	//minimize crashes with our null-checks and try/catch-blocks!
	public static <T> T hasValue(Object e, Class<T> ret, String name, T def) {
		
		if(e == null) return def;

		Class<?> clazz = e.getClass();

		while(clazz.getSuperclass() != null) {
			
			/*System.out.println("============" + clazz.getName() + "============");
			
			for(int i = 0; i < clazz.getFields().length; i++)
				System.out.println(clazz.getFields()[i].getName());*/
			
			try {
				
				Field type = ReflectionHelper.findField(clazz, name);
		
				//System.out.println("Found field of name" + name + " with type " + type.getName() + " when looking for " + ret.getClass().getName());
				
				Object val = type.get(e);
				
				/*if(val != null) {
					System.out.println(val.getClass().getName());
					System.out.println(ret.getName());
				}*/
				
				if(val != null/* && ret.isAssignableFrom(val.getClass())*/) {
	
					val = (T)val;
					return (T)val;
				}
				
			} catch(Exception x) { }
			
			clazz = clazz.getSuperclass();
		}
		
		return def;
	}
	
	public static Object getVehicleFromSeat(Object e) {
		
		if(e == null || !MainRegistry.enableRadar)
			return null;
		
		Object driveable = hasValue(e, Object.class, "driveable", null);
		
		if(driveable == null) {
			//System.out.println("player is riding, but not an EntityDrivable :(");
			return null;
		}
		
		return driveable;
		
	}
}
