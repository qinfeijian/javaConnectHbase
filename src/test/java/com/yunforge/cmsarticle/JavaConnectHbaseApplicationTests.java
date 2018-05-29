package com.yunforge.cmsarticle;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.yunforge.hbase.JavaConnectHbaseApplication;
import com.yunforge.hbase.model.DetailCellData;
import com.yunforge.hbase.model.SocTagInfo;
import com.yunforge.hbase.model.Student;
import com.yunforge.hbase.server.HbaseService;
import com.yunforge.hbase.util.MapConvertBeanUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { JavaConnectHbaseApplication.class })
public class JavaConnectHbaseApplicationTests {

	@Autowired
	HbaseService hbaseService;

	@Test

	public void contextLoads() {
		Gson gson = new Gson();
		
//		hbaseService.put("table1", "003", "cf1", "name", "张三");
//		hbaseService.put("table1", "003", "cf1", "gender", "张三");
//		hbaseService.put("table1", "003", "cf2", "chinese", "100");
//		hbaseService.put("table1", "003", "cf2", "math", "99");
		
		// String string = hbaseService.get("table1", "001", "cf1", "name");
		// System.out.println(string);
		
		
//		List<SocTagInfo> find = hbaseService.find("table1", "0", "1");
		
		
		Student stu = new Student();
		final Map<String, Object> map = new HashMap<String, Object>();
		SocTagInfo socTagInfo = hbaseService.get("table1", "003");
		List<DetailCellData> detail = socTagInfo.getDetail();
		
		detail.forEach(data -> {
			map.put(data.getQualifier(), data.getValue());
		});
		
		MapConvertBeanUtil.setProperty(stu, map);
		
		System.out.println(gson.toJson(stu));
		
		
		
		
	}

	 

}
