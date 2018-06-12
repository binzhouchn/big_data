```python
cd /bin
pyspark   #启动spark's python shell
读取文件：如果在服务器上则data = sc.textFile("file:///home/xxxxx/lpsa.data")
如果是在hdfs上则data = sc.textFile("/user/xxxxx/testData/lpsa.data")
```

```python
服务器上[ip地址]运行py文件的时候
遇到的问题一：
from pyspark.sql import SparkSession
SparkSession not found
解决方法是进入sit环境以后默认的是spark1.5所以需要改成spark2.0环境
change_spark_version然后source change_spark_version spark-2.1.0.1
```

```
cd /home/bigdata/software/spark-2.0.2.1-bin-2.4.0.8/conf
#config for Python
export PYSPARK_PYTHON=/usr/local/anaconda/bin/python
export PYSPARK_DRIVER_PYTHON=/home/bigdata/software/anaconda/bin/python
# 如果要搭建jupyter+spark开发环境应该, ~/.bash_profile中配置环境变量
export PATH
export R_HOME=PATH=$PATH:/home/bigdata/software/R-3.1.2
export SPARK_HOME=/home/bigdata/software/spark-2.1.0.2-bin-2.4.0.10
export PYTHONPATH=/home/bigdata/software/spark-2.1.0.2-bin-2.4.0.10/python/lib/py4j-0.10.4-src.zip:/home/bigdata/software/spark-2.1.0.2-bin-2.4.0.10/python
#export PYSPARK_DRIVER_PYTHON=ipython
#export PYSPARK_DRIVER_PYTHON_OPTS="notebook"
```
