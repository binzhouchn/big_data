# I. 完全分布式spark集群搭建

### 预装软件下载

[jdk-8u171-linux-x64.tar.gz](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html)<br>
[scala-2.11.8.rpm](https://www.scala-lang.org/download/2.11.8.html)<br>
[hadoop-2.7.6.tar.gz](https://archive.apache.org/dist/hadoop/core/hadoop-2.7.6/)<br>
[spark-2.3.0-bin-hadoop2.7.tgz](https://archive.apache.org/dist/spark/spark-2.3.0/)

### 主要步骤
1. hostname不一定要master，slave这种取法，可以自己定<br>
2. 1-3步骤即**无密码访问**和**安装java和scala**可以参照[小狼咕咕](https://www.cnblogs.com/zengxiaoliang/p/6478859.html)<br>
3. 从hadoop和spark解压完后开始，参照[spark2.3.1集群模式搭建 从第7点开始](https://blog.csdn.net/p_q_hersen/article/details/81394095)<br>
4. 补充一点spark/conf/中的slave文件中也要加上slave用户名同hadoop slave<br>

### 有几个坑注意下

 - Unable to load native-hadoop library, [解决链接](https://www.jianshu.com/p/f25a0caafcc6)
 - 启动不了datanode，[解决链接](https://blog.csdn.net/ss762349239/article/details/52758064)
 - 启动了两个datanode，但hadoop管理界面都看到只有一个datanode，[解决链接](https://blog.csdn.net/baidu_19473529/article/details/52996380)
 - jupyter中实例化spark报错，Java gateway process exited before sending the driver its port number,[解决链接](https://blog.csdn.net/a2099948768/article/details/79580634)
 
 


附录：

hadoop管理界面地址(一般): ip_addresss:50070<br>
spark Master界面(一般): ip_addresss:8080




# II. 单台机器上spark搭建 linux user

1. 首先在.bashrc下加入java和scala路径<br>
2. 在hadoop/etc/hadoop文件中配置core-site.xml, slave和hdfs-site.xml文件<br>
3. 在spark/conf文件中配置core-site.xml, hive-site.xml, hdfs-site.xml, slaves, spark-env.sh<br>

先启动hadoop/sbin中的start-all.sh<br>
再启动spark/sbin中的start-all.sh

spark提交：./spark-submit --master spark://10.xx.4.xx:7077 /opt/algor/zhoubin/software/w2v_test.py

### 有几个注意点

1. jupyter中实例化spark报错，Java gateway process exited before sending the driver its port number,[解决链接](https://blog.csdn.net/a2099948768/article/details/79580634)
```python
import os
os.environ["JAVA_HOME"] = "/opt/algor/zhoubin/software/jdk1.8.0_171"
```
2. 读取hdfs
```
# 查看根目录
hdfs dfs -ls /
# 创建文件
hdfs dfs -mkdir /test
# 把本地文件放上去
hdfs dfs -put sample_libsvm_data.txt /test
```
```python
# 读取hdfs中的文件
training=spark.read.format('libsvm').load('hdfs://10.xx.4.xx:9000/test/sample_libsvm_data.txt')
```

3. java.io.IOException: No space left on device<br>
spark/conf/spark-env.sh中加入
```
export SPARK_LOCAL_DIRS=spark.local.dir /opt/algor/zhoubin/software/spark-2.3.0-bin-hadoop2.7/sparktmp
```