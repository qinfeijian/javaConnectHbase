package com.yunforge.cmsarticle;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
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
import java.time.Instant;
import java.util.ArrayList;
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
		Student stu = new Student();
//

		/**
		 * 根据表名，rowkey，列族，列名获取值
		 */
		// String string = hbaseService.get("table1", "001", "cf1", "name");
		// System.out.println(string);

		
		
		
		/**
		 * 根据开始与结束行获取表数据
		 */
		 List<SocTagInfo> find = hbaseService.find("table1", null, null);
		 find.forEach(data -> {
        	Student result = MapConvertBeanUtil.converDataToBean(data,  new Student());
        	System.out.println(gson.toJson(result));
		 });
		
		
		
		/**
		 * 根据表名，rowkey查询一条数据并映射到给定实体类
		 */
//		getDataByTableNameAndRowKeyAndBean("table1", "table1:cf1(Wed May 30 09:36:36 CST 2018)", stu);
//		
//		System.out.println(gson.toJson(stu));
		
		
		
		
		
		
		/**
		 * 插入一条数据
		 */
//		stu.setChinese(new BigDecimal(100));
//		stu.setMath(new BigDecimal(30));
//		stu.setGender("女");
//		stu.setName("赵飞燕");
//		stu.set数学("30分");
//		stu.set语文("100分");
//		
//		String tableName = "table1";
//		String columnFamily = "cf1";
//		String rowKey = tableName + ":" + columnFamily + "(" + Date.from(Instant.now()) + ")";
//		System.out.println(rowKey);
//		
//		hbaseService.insterDataByTableNameAndColumnFamilyAndBean(tableName, rowKey, columnFamily, stu);
		
		
		/**
		 * 条件查询
		 */
//		List<String> arr=new ArrayList<String>();  
//        arr.add("cf1,gender,女");  
//        arr.add("cf1,math,30"); 
//        List<SocTagInfo> selectByFilter = hbaseService.selectByFilter("table1", arr);
//        selectByFilter.forEach(data -> {
//        	Student result = MapConvertBeanUtil.converDataToBean(data,  new Student());
//        	System.out.println(gson.toJson(result));
//        });
		
	}
	
	
	
	
}
