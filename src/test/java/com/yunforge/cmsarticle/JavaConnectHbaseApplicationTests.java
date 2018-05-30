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
//		 List<SocTagInfo> find = hbaseService.find("table1", null, null);
//		 find.forEach(data -> {
//        	Student result = MapConvertBeanUtil.converDataToBean(data,  new Student());
//        	System.out.println(gson.toJson(result));
//		 });
		
		
		
		/**
		 * 根据表名，rowkey查询一条数据并映射到给定实体类
		 */
//		getDataByTableNameAndRowKeyAndBean("table1", "table1:cf1(Wed May 30 09:36:36 CST 2018)", stu);
//		
//		System.out.println(gson.toJson(stu));
		
		
		
		
		
		
		/**
		 * 插入一条数据
		 */
//		stu.setChinese(new BigDecimal(90));
//		stu.setMath(new BigDecimal(80));
//		stu.setGender("女");
//		stu.setName("孙尚香");
//		stu.set数学("80分");
//		stu.set语文("90分");
//		
//		String tableName = "table1";
//		String columnFamily = "cf1";
//		String rowKey = tableName + ":" + columnFamily + "(" + Date.from(Instant.now()) + ")";
//		System.out.println(rowKey);
//		
//		insterDataByTableNameAndColumnFamilyAndBean(tableName, columnFamily, stu);
		
		
		/**
		 * 条件查询
		 */
		List<String> arr=new ArrayList<String>();  
        arr.add("cf1,gender,man");  
        arr.add("cf2,math,91"); 
        List<SocTagInfo> selectByFilter = hbaseService.selectByFilter("table1", arr);
        selectByFilter.forEach(data -> {
        	Student result = MapConvertBeanUtil.converDataToBean(data,  new Student());
        	System.out.println(gson.toJson(result));
        });
		
	}
	
	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月30日
	 * @param tableName
	 * @param rowKey
	 * @param columnFamily
	 * @param objectBean
	 * 返回:void
	 * 说明:根据表名，行名，列族，实体类插入或更新一条数据
	 */
	public void insterDataByTableNameAndColumnFamilyAndBean(String tableName, String rowKey, String columnFamily, Object objectBean) {
		Map<String, String> propertys = MapConvertBeanUtil.getProperty(objectBean);
		propertys.entrySet().forEach(entry -> {
			String property = entry.getKey();
			String value = entry.getValue();
			hbaseService.put(tableName, rowKey, columnFamily, property, value);
		});
	}
	
	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月30日
	 * @param <T>
	 * @param tableName
	 * @param rowKey
	 * @param objectBean
	 * @return
	 * 返回:T
	 * 说明:根据表名，行名，实体类，把查到的HBASE数据映射到实体类中并返回
	 */
	public <T> void getDataByTableNameAndRowKeyAndBean(String tableName, String rowKey, T objectBean) {
		final Map<String, Object> map = new HashMap<String, Object>();
		SocTagInfo socTagInfo = hbaseService.get(tableName, rowKey);
		List<DetailCellData> detail = socTagInfo.getDetail();

		detail.forEach(data -> {
			map.put(data.getQualifier(), data.getValue());
		});
		MapConvertBeanUtil.setProperty(objectBean, map);
	}
	
	
	
}
