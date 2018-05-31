package com.yunforge.cmsarticle;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.yunforge.hbase.JavaConnectHbaseApplication;
import com.yunforge.hbase.model.Student;
import com.yunforge.hbase.server.HbaseService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { JavaConnectHbaseApplication.class })
public class JavaConnectHbaseApplicationTests {

	@Autowired
	HbaseService hbaseService;

	Gson gson = new Gson();
	Student stu = new Student();
	
	String tableName = "table1";
	String columnFamily = "cf1";
	
	@Test
	public void contextLoads() {
		
		
	}
	
	/**
	 * 根据表名，rowkey，列族，列名获取值
	 */
	public void getColumnValue() {
		 String string = hbaseService.get(tableName, "001", columnFamily, "name");
		 System.out.println(string);
	}
	/**
	 * 根据开始与结束行获取表数据
	 */
	public void listByStartAndEndRowNum() {
		List<Student> find = hbaseService.find(tableName, null, null, Student.class);
		find.forEach(data ->{
			System.out.println(gson.toJson(data));
		});
	}
	
	/**
	 * 插入一条数据
	 */
	public void insterOneDataByBean() {
		stu.setChinese(new BigDecimal(100));
		stu.setMath(new BigDecimal(30));
		stu.setGender("女");
		stu.setName("赵飞燕");
		stu.set数学("30分");
		stu.set语文("100分");
		
		String tableName = "table1";
		String columnFamily = "cf1";
		String rowKey = tableName + ":" + columnFamily + "(" + Date.from(Instant.now()) + ")";
		System.out.println(rowKey);
		
		hbaseService.insterDataByTableNameAndColumnFamilyAndBean(tableName, rowKey, columnFamily, stu);
	}
	
	/**
	 * 条件查询
	 */
	public void findDataByFilter() {
		List<String> arr=new ArrayList<String>();  
        arr.add("cf1,gender,女");  
        arr.add("cf1,math,30"); 
        List<Student> selectByFilter = hbaseService.selectByFilter(tableName, arr, Student.class);
        selectByFilter.forEach(data -> {
        	System.out.println(gson.toJson(data));
        });
	}
	
}
