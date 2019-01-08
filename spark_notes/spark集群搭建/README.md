# spark集群搭建

[jdk-8u171-linux-x64.tar.gz](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html)<br>
[scala-2.11.8.rpm](https://www.scala-lang.org/download/2.11.8.html)<br>
[hadoop-2.7.6.tar.gz](https://archive.apache.org/dist/hadoop/core/hadoop-2.7.6/)<br>
[spark-2.3.0-bin-hadoop2.7.tgz](https://archive.apache.org/dist/spark/spark-2.3.0/)


1. hostname不一定要master，slave这种取法，可以自己定<br>
2. 1-3步骤即**无密码访问**和**安装java和scala**可以参照[小狼咕咕](https://www.cnblogs.com/zengxiaoliang/p/6478859.html)<br>
3. 从hadoop和spark解压完后开始，参照[spark2.3.1集群模式搭建 从第7点开始](https://blog.csdn.net/p_q_hersen/article/details/81394095)<br>
4. 补充一点spark/conf/中的slave文件中也要加上slave用户名同hadoop slave<br>

**有两个坑注意下：**
 - 启动不了datanode，[解决链接](https://blog.csdn.net/ss762349239/article/details/52758064)
 - 启动了两个datanode，但hadoop管理界面都看到只有一个datanode，[解决链接](https://blog.csdn.net/baidu_19473529/article/details/52996380)
 

附录：

hadoop管理界面地址(一般): ip_addresss:50070<br>
spark Master界面(一般): ip_addresss:8080
