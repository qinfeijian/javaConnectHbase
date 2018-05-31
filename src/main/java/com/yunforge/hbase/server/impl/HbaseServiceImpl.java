package com.yunforge.hbase.server.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.yunforge.hbase.server.HbaseService;
import com.yunforge.hbase.util.MapConvertBeanUtil;
import com.yunforge.hbase.util.StringUtil;

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


	/**
	 * 
	 * 作者:覃飞剑 日期:2018年5月29日
	 * 
	 * @param tableName
	 * @param rowName
	 * @return 返回:T 说明:通过行名称获取一条数据
	 */
	public <T> T get(String tableName, String rowName, Class<?> clz) {
		return htemplate.get(tableName, rowName, new RowMapper<T>() {
			public T mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				return getBeanInfo(ceList, clz);
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
	 * 作者:覃飞剑 日期:2018年5月30日
	 * 
	 * @param tablename
	 * @param arr
	 * @return
	 * @throws IOException
	 *             返回:List<T> 说明:查询出符合条件的数据 arr的格式如下： 列族，列名，值 条件：查询
	 *             course列族中art列值为97 ，且 course列族中math列值为100的行
	 */
	public <T> List<T> selectByFilter(String tablename, List<String> arr, Class<?> clz) {
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
		return htemplate.find(tablename, s1, new RowMapper<T>() {

			public T mapRow(Result result, int rowNum) throws Exception {
				List<Cell> ceList = result.listCells();
				return getBeanInfo(ceList, clz);
			}
		});
	}

	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月31日
	 * @param ceList
	 * @param clz
	 * @return
	 * 返回:T
	 * 说明:把查询到的数据封装到实体类中
	 */
	public <T> T getBeanInfo(List<Cell> ceList, Class<?> clz) {
		System.out.println(ceList.toString());
		T beanObj;
		try {
			beanObj = (T)clz.newInstance();
			if (ceList != null && ceList.size() > 0) {
				for (Cell cell : ceList) {
					// 获取值
					String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
					// 获取列
					String quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
							cell.getQualifierLength());
					Field[] fields = beanObj.getClass().getDeclaredFields();// 获得属性
					for (int i = 0; i < fields.length; i++) {
						Field field = fields[i];
						// 此处应该判断beanObj,property不为null
						String name = field.getName();
						PropertyDescriptor pd = new PropertyDescriptor(name, beanObj.getClass());
						Method setMethod = pd.getWriteMethod();
						if (setMethod != null && name.equals(quali)) {
							String type = field.getType().toString();
							// 这里注意实体类中set方法中的参数类型，如果不是String类型则进行相对应的转换
							if (type.equals("class java.lang.String")) {
								setMethod.invoke(beanObj, value);// invoke是执行set方法
							}
							if (type.equals("class java.lang.Integer")) {
								setMethod.invoke(beanObj, Bytes.toInt(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));// invoke是执行set方法
							}
							if (type.equals("class java.lang.Short")) {
								setMethod.invoke(beanObj, Bytes.toShort(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));// invoke是执行set方法
							}
							if (type.equals("class java.lang.Double")) {
								setMethod.invoke(beanObj, Double.parseDouble(value));// invoke是执行set方法 }
							}
							if (type.equals("class java.lang.Boolean")) {
								setMethod.invoke(beanObj, Boolean.parseBoolean(value));// invoke是执行set方法 }
							}
							if (type.equals("class java.util.Date")) {
								setMethod.invoke(beanObj, StringUtil.getDate(value, "YYYY-MM-DD HH24:mm:ss"));// invoke是执行set方法 }
							}
							if (type.equals("class java.math.BigDecimal")) {
								setMethod.invoke(beanObj, new BigDecimal(value.toString()));// invoke是执行set方法 }
							}
						}
					}
				}
			}
			return beanObj;
		} catch (InstantiationException | IllegalAccessException | IntrospectionException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

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
			T objectBean) {
		Map<String, String> propertys = MapConvertBeanUtil.getProperty(objectBean);
		propertys.entrySet().forEach(entry -> {
			String property = entry.getKey();
			String value = entry.getValue();
			put(tableName, rowKey, columnFamily, property, value);
		});
	}


	@Override
	public <T> List<T> find(String tableName, String startRow, String stopRow, Class<?> clz) {
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
				return htemplate.find(tableName, scan, new RowMapper<T>() {

					@Override
					public T mapRow(Result result, int rowNum) throws Exception {
						List<Cell> ceList = result.listCells();
						return getBeanInfo(ceList, clz);
					}
				});
		
	}

	/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月31日
	 * @param tableName
	 * @param rowKey
	 * @param clz
	 * @return
	 * 说明:@see com.yunforge.hbase.server.HbaseService#findByTableNameAndRowKey(java.lang.String, java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T findByTableNameAndRowKey(String tableName, String rowKey, Class<?> clz) {
		return htemplate.get(tableName, rowKey, new RowMapper<T>() {

			@Override
			public T mapRow(Result result, int rowNum) throws Exception {
				// TODO Auto-generated method stub
				List<Cell> ceList = result.listCells();
				return getBeanInfo(ceList, clz);
			}
			
		});
	}


}