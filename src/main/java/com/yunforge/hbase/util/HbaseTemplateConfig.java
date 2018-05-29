package com.yunforge.hbase.util;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

@Configuration
public class HbaseTemplateConfig {
	@Bean(name="htemplate")
	public HbaseTemplate getHbaseTemplate() {
		 HbaseTemplate hbaseTemplate = new HbaseTemplate();  
	       org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();  
	       conf.set("hbase.zookeeper.quorum", "dn1.yfbd.com");  
	       conf.set("hbase.zookeeper.port", "2181");  
	       conf.set("hbase.zookeeper.property.clientPort", "2181");  
	       conf.set("zookeeper.znode.parent", "/hbase-unsecure");
	       hbaseTemplate.setConfiguration(conf);  
	       hbaseTemplate.setAutoFlush(true);  
		return hbaseTemplate;
	}

}
