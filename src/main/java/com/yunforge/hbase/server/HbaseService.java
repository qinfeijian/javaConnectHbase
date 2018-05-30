package com.yunforge.hbase.server;

import java.io.IOException;
import java.util.List;

import com.yunforge.hbase.model.SocTagInfo;

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
	 * @param startRow
	 * @param stopRow
	 * @return
	 * 返回:List<SocTagInfo>
	 * 说明:根据开始与结束行获取指定表的数据
	 */
	public List<SocTagInfo> find(String tableName, String startRow, String stopRow);
	
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
	public SocTagInfo get(String tableName, String rowName);
	
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
	public List<SocTagInfo> selectByFilter(String tablename, List<String> arr);
	
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
	 * 作者:覃飞剑 日期:2018年5月30日
	 * 
	 * @param <T>
	 * @param tableName
	 * @param rowKey
	 * @param objectBean
	 * @return 返回:T 说明:根据表名，行名，实体类，把查到的HBASE数据映射到实体类中并返回
	 */
	public <T> void getDataByTableNameAndRowKeyAndBean(String tableName, String rowKey, T objectBean);
	
}
