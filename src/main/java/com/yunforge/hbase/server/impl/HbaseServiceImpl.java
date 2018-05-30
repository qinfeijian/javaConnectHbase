package com.yunforge.hbase.server.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Service;

import com.yunforge.hbase.model.DetailCellData;
import com.yunforge.hbase.model.SocTagInfo;
import com.yunforge.hbase.server.HbaseService;

@Service("hbaseService")
public class HbaseServiceImpl implements HbaseService {

	@Resource(name = "htemplate")
	private HbaseTemplate htemplate;

	// 获取表中指定行，列簇，列的值
	/**
	 * 
	 * 作者:覃飞剑 日期:2018年5月29日
	 * 
	 * @param tableName
	 *            表名
	 * @param rowName
	 *            行名
	 * @param familyName
	 *            列族
	 * @param qualifier
	 *            列
	 * @return 说明:@see com.yunforge.hbase.server.HbaseService#get(java.lang.String,
	 *         java.lang.String, java.lang.String, java.lang.String)
	 */
	public String get(String tableName, String rowName, String familyName, String qualifier) {
		return htemplate.get(tableName, rowName, familyName, qualifier, new RowMapper<String>() {

			@Override
			public String mapRow(Result result, int rowNum) throws Exception {
				// TODO Auto-generated method stub
				List<Cell> ceList = result.listCells();
				String res = "";
				if (ceList != null && ceList.size() > 0) {
					for (Cell cell : ceList) {
						res = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
					}
				}
				return res;
			}
		});
	}

	// 浏览表中某些行的信息，例子中指定的起始行和结束行
	public List<SocTagInfo> find(String tableName, String startRow, String stopRow) {
		// 使用Scan可以实现过滤的功能
		Scan scan = new Scan();
		if (startRow == null) {
			startRow = "";
		}
		if (stopRow == null) {
			stopRow = "";
		}
		scan.setStartRow(Bytes.toBytes(startRow));
		scan.setStopRow(Bytes.toBytes(stopRow));
		return htemplate.find(tableName, scan, new RowMapper<SocTagInfo>() {

			public SocTagInfo mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				return getInfo(ceList);
			}
		});

	}

	/**
	 * 
	 * 作者:覃飞剑 日期:2018年5月29日
	 * 
	 * @param tableName
	 * @param rowName
	 * @return 返回:SocTagInfo 说明:通过行名称获取一条数据
	 */
	public SocTagInfo get(String tableName, String rowName) {
		return htemplate.get(tableName, rowName, new RowMapper<SocTagInfo>() {

			public SocTagInfo mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				return getInfo(ceList);
			}
		});
	}

	/**
	 * 
	 * 作者:覃飞剑 日期:2018年5月29日
	 * 
	 * @param tableName
	 * @param rowKey
	 * @param familyName
	 * @param column
	 * @param value
	 *            返回:void 说明:将值插入表中指定的行，列簇，列中
	 */
	public void put(final String tableName, final String rowKey, final String familyName, final String column,
			final String value) {
		htemplate.put(tableName, rowKey, familyName, column, value.getBytes());
	}

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
	public List<SocTagInfo> selectByFilter(String tablename, List<String> arr) {
		FilterList filterList = new FilterList();
		Scan s1 = new Scan();
		for (String v : arr) { // 各个条件之间是“与”的关系
			String[] s = v.split(",");
			filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(s[0]), Bytes.toBytes(s[1]), CompareOp.EQUAL,
					Bytes.toBytes(s[2])));
			// 添加下面这一行后，则只返回指定的cell，同一行中的其他cell不返回
			// s1.addColumn(Bytes.toBytes(s[0]), Bytes.toBytes(s[1]));
		}
		s1.setStartRow(Bytes.toBytes(""));
		s1.setStopRow(Bytes.toBytes(""));
		s1.setFilter(filterList);
		return htemplate.find(tablename, s1, new RowMapper<SocTagInfo>() {

			public SocTagInfo mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				return getInfo(ceList);
			}
		});
	}

	/**
	 * 
	 * 作者:覃飞剑 日期:2018年5月29日
	 * 
	 * @param ceList
	 * @return 返回:SocTagInfo 说明:把查到的数据封装到实体类中
	 */
	public SocTagInfo getInfo(List<Cell> ceList) {
		System.out.println(ceList.toString());
		final SocTagInfo info = new SocTagInfo();
		if (ceList != null && ceList.size() > 0) {
			for (Cell cell : ceList) {
				DetailCellData cellData = new DetailCellData();
				String row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
				// 获取值
				String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
				// 获取列簇
				String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
				// 获取列
				String quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
						cell.getQualifierLength());
				if (null == info.getRow() || info.getRow().isEmpty()) {
					info.setRow(row);
				}
				cellData.setRow(row);
				cellData.setFamily(family);
				cellData.setQualifier(quali);
				cellData.setValue(value);
				if (info.getDetail() == null) {
					info.setDetail(new ArrayList<DetailCellData>());
				}
				info.getDetail().add(cellData);
			}
		}
		return info;
	}

}