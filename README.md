<h1>Gora – 大数据持久化</h1>
<p>
HBase必读：<br/>
1、<a href="http://yangshangchuan.iteye.com/blog/1953929" target="_blank">建立Hadoop环境</a><br/>
2、<a href="http://yangshangchuan.iteye.com/blog/1954018" target="_blank">建立HBase环境</a><br/>
3、<a href="http://yangshangchuan.iteye.com/blog/1953733" target="_blank">项目初始建立步骤</a><br/>
如何切换底层存储为HBase：<br/>
1、修改pom.xml，指定gora-core.version的值为0.3<br/>
2、修改gora.properties，指定gora.datastore.default的值为org.apache.gora.hbase.store.HBaseStore
</p>
<p>
MySQL必读：<br/>
MySQL只是做一个演示，最新的Gora版本不支持MySQL，所以MySQL仅仅是一个演示。<br/>
如何切换底层存储为MySQL：<br/>
1、修改pom.xml，指定gora-core.version的值为0.2.1<br/>
2、修改gora.properties，指定gora.datastore.default的值为org.apache.gora.sql.store.SqlStore
</p>
