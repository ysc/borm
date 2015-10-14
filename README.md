<h1>borm – 大数据的对象持久化</h1>
[捐赠致谢](https://github.com/ysc/QuestionAnsweringSystem/wiki/donation)
<p>
HBase必读：<br/>
1、<a href="http://yangshangchuan.iteye.com/blog/1953929" target="_blank">建立Hadoop环境</a><br/>
2、<a href="http://yangshangchuan.iteye.com/blog/1954018" target="_blank">建立HBase环境</a><br/>
3、<a href="http://yangshangchuan.iteye.com/blog/1953733" target="_blank">项目初始建立步骤</a><br/>
如何切换底层存储为HBase：<br/>
1、修改pom.xml，指定gora-core.version的值为0.3<br/>
2、修改gora.properties，指定gora.datastore.default的值为org.apache.gora.hbase.store.HBaseStore<br/>
3、修改hbase-site.xml，指定HBase集群依赖的zookeeper的主机及端口
</p>
<p>
MySQL必读：<br/>
MySQL只是方便开发阶段的调试工作，生产环境中不宜使用。<br/>
如何切换底层存储为MySQL：<br/>
1、修改pom.xml，指定gora-core.version的值为0.2.1<br/>
2、修改gora.properties，指定gora.datastore.default的值为org.apache.gora.sql.store.SqlStore
</p>
