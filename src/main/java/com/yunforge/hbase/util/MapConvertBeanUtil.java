package com.yunforge.hbase.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MapConvertBeanUtil {
	
	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月30日
	 * @param beanObj
	 * @param map
	 * 返回:void
	 * 说明:传入map数据给给定的实体类设置对应的值
	 */
	public static <T> T setProperty(T beanObj, Map<String, Object> map) {
		for (String key : map.keySet()) {
			Object value = map.get(key);
			try {
				Field[] fields = beanObj.getClass().getDeclaredFields();// 获得属性
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					// 此处应该判断beanObj,property不为null
					String name = field.getName();
					PropertyDescriptor pd = new PropertyDescriptor(name, beanObj.getClass());
					Method setMethod = pd.getWriteMethod();
					if (setMethod != null && name.equals(key)) {
						String type = field.getType().toString();
						// 这里注意实体类中set方法中的参数类型，如果不是String类型则进行相对应的转换
						if (type.equals("class java.lang.String")) {
							setMethod.invoke(beanObj, value);// invoke是执行set方法
						}
						if (type.equals("class java.lang.Integer")) {
							setMethod.invoke(beanObj, (Integer) value);// invoke是执行set方法
						}
						if (type.equals("class java.lang.Short")) {
							setMethod.invoke(beanObj, (Short) value);// invoke是执行set方法
						}
						if (type.equals("class java.lang.Double")) {
							setMethod.invoke(beanObj, (Double) value);// invoke是执行set方法 }
						}
						if (type.equals("class java.lang.Boolean")) {
							setMethod.invoke(beanObj, (Boolean) value);// invoke是执行set方法 }
						}
						if (type.equals("class java.util.Date")) {
							setMethod.invoke(beanObj, (Date) value);// invoke是执行set方法 }
						}
						if (type.equals("class java.math.BigDecimal")) {
							setMethod.invoke(beanObj, new BigDecimal(value.toString()));// invoke是执行set方法 }
						}
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
		}
		return beanObj;
	}

	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月30日
	 * @param beanObj
	 * @return
	 * 返回:Map<String,String>
	 * 说明:把实体类中所有属性映射成Map<String, String>
	 */
	public static Map<String, String> getProperty(Object beanObj) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			Field[] fields = beanObj.getClass().getDeclaredFields();// 获得属性
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				// 此处应该判断beanObj,property不为null
				String name = field.getName();
				PropertyDescriptor pd = new PropertyDescriptor(name, beanObj.getClass());
				Method setMethod = pd.getReadMethod();
				if (setMethod != null) {
					Object value = setMethod.invoke(beanObj);
					result.put(name, value.toString());
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return result;
	}
}
