package com.yunforge.hbase.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class MapConvertBeanUtil {

	public static void setProperty(Object beanObj, Map<String, Object> map) {
		for (String key : map.keySet()) {
			Object value = map.get(key);
			try {
				Field[] fields = beanObj.getClass().getDeclaredFields();// 获得属性
				Class clazz = beanObj.getClass();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					// 此处应该判断beanObj,property不为null
					String name = field.getName();
					PropertyDescriptor pd = new PropertyDescriptor(name, beanObj.getClass());
					Method setMethod = pd.getWriteMethod();
					if (setMethod != null && name.equals(key)) {
						String type = field.getType().toString();
						System.out.println(beanObj + "的字段是:" + name + "，参数类型是：" + type + "，set的值是： " + value);
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
	}

}
