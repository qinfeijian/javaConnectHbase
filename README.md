# javaConnectHbase

## 配置HbaseTemplate
在HbaseTemplateConfig配置类中配置一个HbaseTemplate的bean给它起一个别名为htemplate

The HBase clients will discover the running HBase cluster using the following two properties:
1.hbase.zookeeper.quorum: is used to connect to the zookeeper cluster
2.zookeeper.znode.parent. tells which znode keeps the data (and address for HMaster) for the cluster

The value of zookeeper.znode.parent in HBASE_CONF/hbase-site.xml is specified as /hbase-unsecure (see below) which is correct
but for some reason (still trying to figure this out), the value being printed is /hbase. 
So currently I’ve overridden this programatically in the client program by adding the following line to the program

HBASE客户端将使用以下两个属性发现正在运行的HASBASE集群：
1.HbaseZooKeEp.QuRUM:用于连接到动物园管理员集群
2.zookeeper.znode.parent.告诉哪个ZNUB为集群保留数据（HMMAX地址）

在Hbasy-CONF/HbaseSIT.xml中，ZooKeEp.ZNoDe.Poad的值被指定为/HBASE不安全（见下文），这是正确的，
但出于某种原因（仍然试图解决这个问题），正在打印的值为/HbASE。
所以现在我通过在程序中添加下面的行来在客户端程序中重写这个程序。

所以基于以上原因 我们需要配置的东西为
conf.set("hbase.zookeeper.quorum", "dn1.yfbd.com");   //集群名
conf.set("hbase.zookeeper.property.clientPort", "2181");  //端口
conf.set("zookeeper.znode.parent", "/hbase-unsecure");

其中dn1.yfbd.com为一个集群的名称,这个在本地电脑的hosts上配置具体的ip地址，或者直接配置成ip地址。

## 在HbaseOperations接口中,它描述了HbaseTemplate有哪些接口可以供我们使用

## HbaseService有一些自定义接口,HbaseServiceImpl为其实现类
这里面它使用了HbaseTemplate实现了一些逻辑,具体逻辑请看接口注解

## MapConvertBeanUtil为工具类它里面定义了一些工具方法
如:
/**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月30日
	 * @param socTagInfo
	 * @param bean
	 * @return
	 * 返回:T
	 * 说明:将HBASE的一行数据转换为给定的实体类数据并返回
	 */
	public static <T> T converDataToBean(SocTagInfo socTagInfo, T bean);
  
  /**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月30日
	 * @param beanObj
	 * @param map
	 * 返回:void
	 * 说明:传入map数据给给定的实体类设置对应的值
	 */
	public static <T> T setProperty(T beanObj, Map<String, Object> map);
  
  /**
	 * 
	 * 作者:覃飞剑
	 * 日期:2018年5月30日
	 * @param beanObj
	 * @return
	 * 返回:Map<String,String>
	 * 说明:把实体类中所有属性映射成Map<String, String>
	 */
	public static Map<String, String> getProperty(Object beanObj);
  
  
  
  ## ObjectAndByte工具类
  两个方法为Object和二进制数据之间的转换提供实现
  
  
  
