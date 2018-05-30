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
//		 hbaseService.put("table1", "003", "cf1", "name", "张三");
//		 hbaseService.put("table1", "003", "cf1", "gender", "男");
//		 hbaseService.put("table1", "003", "cf2", "chinese", "100");
//		 hbaseService.put("table1", "003", "cf2", "math", "99");

		// String string = hbaseService.get("table1", "001", "cf1", "name");
		// System.out.println(string);

		// List<SocTagInfo> find = hbaseService.find("table1", "0", "1");

		getDataByTableNameAndRowKeyAndBean("table1", "table1:cf1(Wed May 30 09:36:36 CST 2018)", stu);
		
		System.out.println(gson.toJson(stu));
		
		
		
		
		
		
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
