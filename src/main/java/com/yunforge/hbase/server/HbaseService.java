package com.yunforge.hbase.server;

import java.io.IOException;
import java.util.List;


public interface HbaseService {

	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月29日
	 * @param tableName
	 * @param rowName
	 * @param familyName
	 * @param qualifier
	 * @return
	 * 返回:String
	 * 说明:根据表名，行名，列族，列名获取指定列的值
	 */
	public String get(String tableName, String rowName, String familyName, String qualifier);
	
	
	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月29日
	 * @param tableName
	 * @param rowName
	 * @return
	 * 返回:SocTagInfo
	 * 说明:根据行名称获取一条数据
	 */
	public <T> T get(String tableName, String rowName, Class<?> clz);
	
	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月29日
	 * @param tableName
	 * @param rowKey
	 * @param familyName
	 * @param column
	 * @param value
	 * 返回:void
	 * 说明:将值插入表中指定的行，列簇，列中  
	 */
	public void put(final String tableName, final String rowKey,  
            final String familyName, final String column, final String value);
	
	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月30日
	 * @param tablename
	 * @param arr
	 * @return
	 * @throws IOException
	 * 返回:List<SocTagInfo>
	 * 说明:查询出符合条件的数据
	 * 		arr的格式如下：
	 * 列族，列名，值
	 * 条件：查询 course列族中art列值为97 ，且 course列族中math列值为100的行  
	 */
	public <T> List<T> selectByFilter(String tablename, List<String> arr, Class<?> clz);
	
	/**
	 * 
	 * 作者:覃飞剑 日期:2018年5月30日
	 * 
	 * @param tableName
	 * @param rowKey
	 * @param columnFamily
	 * @param objectBean
	 *            返回:void 说明:根据表名，行名，列族，实体类插入或更新一条数据
	 */
	public <T> void insterDataByTableNameAndColumnFamilyAndBean(String tableName, String rowKey, String columnFamily,
			T objectBean);
	
	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月31日
	 * @param tableName
	 * @param startRow
	 * @param stopRow
	 * @param clz
	 * @return
	 * 返回:List<Class<?>>
	 * 说明:浏览表中某些行的信息，例子中指定的起始行和结束行
	 */
	public <T> List<T> find(String tableName, String startRow, String stopRow, Class<?> clz);
	
	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月31日
	 * @param tableName
	 * @param rowKey
	 * @return
	 * 返回:T
	 * 说明:根据表名，列名查询一条记录
	 */
	public <T> T findByTableNameAndRowKey(String tableName, String rowKey, Class<?> clz);
	
	
}
