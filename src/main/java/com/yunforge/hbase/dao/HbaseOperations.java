package com.yunforge.hbase.dao;

import java.util.List;

import org.apache.hadoop.hbase.protobuf.generated.ClientProtos.Scan;
import org.springframework.data.hadoop.hbase.ResultsExtractor;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;

public interface HbaseOperations {
	
	/** 
	 * 回调方法 
	 */  
	<T> T execute(String tableName, TableCallback<T> action);  
	  
	/** 
	 * 根据表名，查找Hbase表中的列簇信息 
	 */  
	<T> T find(String tableName, String family, final ResultsExtractor<T> action);  
	  
	/** 
	 * 浏览表的各列信息 
	 */  
	<T> T find(String tableName, String family, String qualifier, final ResultsExtractor<T> action);  
	  
	 /** 
	 * 根据Scan查找表信息，并将信息封装到ResultsExtractor中 
	 */  
	<T> T find(String tableName, final Scan scan, final ResultsExtractor<T> action);  
	  
	 /** 
	 * 查找表中的列簇信息 
	 */  
	<T> List<T> find(String tableName, String family, final RowMapper<T> action);  
	  
	/** 
	 * 查找表的各列信息 
	 */  
	<T> List<T> find(String tableName, String family, String qualifier, final RowMapper<T> action);  
	  
	/** 
	 * 将查找到的信息以回调的方式显示 
	 */  
	<T> List<T> find(String tableName, final Scan scan, final RowMapper<T> action);  
	  
	/** 
	 * 获取表中指定行的信息 
	 */  
	<T> T get(String tableName, String rowName, final RowMapper<T> mapper);  
	  
	/** 
	 * 获取行对应的列簇信息 
	 */  
	<T> T get(String tableName, String rowName, String familyName, final RowMapper<T> mapper);  
	  
	/** 
	 * 获取指定行，指定列簇的指定列信息 
	 */  
	<T> T get(String tableName, final String rowName, final String familyName, final String qualifier, final RowMapper<T> mapper);  
	  
	/** 
	 * 将数据插入表中指定行，指定列簇，指定列中 
	 */  
	void put(String tableName, final String rowName, final String familyName, final String qualifier, final byte[] data);  
	  
	/** 
	 * 删除指定行的列簇信息 
	 */  
	void delete(String tableName, final String rowName, final String familyName);  
	  
	/** 
	 * 删除指定行，指定列簇，指定列的信息 
	 */  
	void delete(String tableName, final String rowName, final String familyName, final String qualifier);  

}
