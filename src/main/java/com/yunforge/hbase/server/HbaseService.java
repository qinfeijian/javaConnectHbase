package com.yunforge.hbase.server;

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
	
}
