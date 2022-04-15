## 二级目录

[**1. pyspark两种初始化方式**](#pyspark两种初始化方式)

  - [1.1 spark](#spark_init)
  - [1.2 sc](#spark_sc_init)
  - [1.3 spark和sc的区别](#spark和sc的区别)

[**2. pyspark创建rdd和spark dataframe**](#rdd和dataframe创建)
  - [2.1 分布式rdd创建方法](#分布式rdd创建方法)
  - [2.2 spark dataframe创建方法](#spark_dataframe创建方法)

[**3. pyspark rdd读取和加载成dataframe**](#spark_rdd读取和加载成dataframe)

[**4. pyspark sql基本使用**](#pyspark基本使用)
  - [4.1 df显示](#df显示)
  - [4.2 df增加一列](#df增加一列)
  - [4.3 df打印Schema](#df打印schema)
  - [4.4 df选择一列](#df选择一列)
  - [4.5 df选择一列并age增加1](#df选择一列并age增加1)
  - [4.6 df filter](#df_filter)
  - [4.7 df groupBy](#df_groupby)
  - [4.8 Register the DataFrame as a SQL temporary view](#df_register)
  - [4.9 df explode用法](#df_explode用法)
  - [4.10 udf用法](#udf用法)

[**5. pyspark数字类型以及空表df创建**](#pyspark数字类型以及空表df创建)

---

## pyspark两种初始化方式

### spark_init
```python
# $example on:init_session$ 
from pyspark.sql import SparkSession
spark = SparkSession.builder \
    .appName("BinZhou_Test") \
    .config('log4j.rootCategory',"WARN") \
    .enableHiveSupport() \
    .getOrCreate()
```
### spark_sc_init
```python
sc = spark.sparkContext
```
### spark和sc的区别

> spark和sc的方法有点不同<br>
> spark相当于sc的包装，更高层<br>
> sc:<pyspark.context.SparkContext at 0x7fd5ad90fe50><br>
> spark:<pyspark.sql.session.SparkSession at 0x7fd59ee55d50><br>


## rdd和dataframe创建

### 分布式rdd创建方法
```python
sc.parallelize()
sc.textFile()
sc.range()
```
### spark_dataframe创建方法
```python
spark.createDataFrame(pandas_df or np_array.tolist())
spark.table(fbichk.bz_tmp)
spark.sql('select * from fdb.bz_tmp')
spark.read.csv(people.csv)
spark.read.json(people.json)
spark.read.text(people.txt)

# 附:
spark如果想自己创建dataframe instead of直接读取可以：
先生成一个pandas dataframe,然后spark.createDataFrame(pandas_df)
或者先生成一个np array然后再spark.createDataFrame(np_array.tolist(),[col name])
```
## spark_rdd读取和加载成dataframe
```python
sc = spark.sparkContext

# Load a text file and convert each line to a Row.
lines = sc.textFile("examples/src/main/resources/people.txt")
parts = lines.map(lambda l: l.split(","))
people = parts.map(lambda p: Row(name=p[0], age=int(p[1])))

# Infer the schema, and register the DataFrame as a table.
schemaPeople = spark.createDataFrame(people)
schemaPeople.createOrReplaceTempView("people")

# SQL can be run over DataFrames that have been registered as a table.
teenagers = spark.sql("SELECT name FROM people WHERE age >= 13 AND age <= 19")
# The results of SQL queries are Dataframe objects.

# rdd returns the content as an :class:`pyspark.RDD` of :class:`Row`.
teenNames = teenagers.rdd.map(lambda p: "Name: " + p.name).collect()
for name in teenNames:
    print(name)
# Name: Justin
```

## pyspark基本使用

### df显示
```python
df.show()
# +----+-------+
# | age|   name|
# +----+-------+
# |null|Michael|
# |  30|   Andy|
# |  19| Justin|
# +----+-------+
```
### df增加一列
```python
df.withColumn('double_age',df['age']*2)
df['age']是df其中一列

# df增加一列，比如这一列都是[上海] 
import pyspark.sql.functions as F
F.lit('上海')
# 两种方式都可以添加到原来的spark df中
df.withColumn('city',F.lit('南京'))
df.select('member_id',(F.lit('南京')).alias('city')).show()
# +---------+----+
# |member_id|city|
# +---------+----+
# |     6001|  南京|
# |     6002|  南京|
# |     6003|  南京|
# |     6004|  南京|
# |     6005|  南京|
# +---------+----+
```
### df打印Schema
```python
# Print the schema in a tree format
df.printSchema()
# root
# |-- age: long (nullable = true)
# |-- name: string (nullable = true)
```
### df选择一列
```python
df.select("name").show()
# +-------+
# |   name|
# +-------+
# |Michael|
# |   Andy|
# | Justin|
# +-------+
```
### df选择一列并age增加1
```python
df.select(df['name'], df['age'] + 1).show()
# +-------+---------+
# |   name|(age + 1)|
# +-------+---------+
# |Michael|     null|
# |   Andy|       31|
# | Justin|       20|
# +-------+---------+

df.select(df['name'], (df['age'] + 1).alias('age')).show()
# +-------+---------+
# |   name|      age|
# +-------+---------+
# |Michael|     null|
# |   Andy|       31|
# | Justin|       20|
# +-------+---------+
```
### df_filter
```python
df.filter(df['age'] > 21).show()
# +---+----+
# |age|name|
# +---+----+
# | 30|Andy|
# +---+----+
```
### df_groupby
```python
df.groupBy("age").count().show()
# +----+-----+
# | age|count|
# +----+-----+
# |  19|    1|
# |null|    1|
# |  30|    1|
# +----+-----+
```
### df_register
```python
# Register the DataFrame as a SQL temporary view
df.createOrReplaceTempView("bz_all_tmp")
sqlDF = spark.sql("SELECT * FROM bz_all_tmp")
sqlDF.show()
# +----+-------+
# | age|   name|
# +----+-------+
# |null|Michael|
# |  30|   Andy|
# |  19| Justin|
# +----+-------+

#也可以直接读取hive table
spark.sql('use fbd')
df_raw = spark.table('fbd.bz_all_tmp')
```
### df_explode用法
```python
# spark中的explode用法
spark_df = spark_df.select(spark_df['city'],spark_df['community_org'],spark_df['community'],\
spark_df['longitude'],spark_df['latitude'],(explode(split('address',','))).alias('address'),spark_df['villagekey'])
```
```python
#PySpark将datafram中“map”类型的列转换为多个列:https://www.cnpython.com/qa/69046

from pyspark.sql.functions import explode, split
from pyspark.sql.functions import col
from pyspark.sql.types import IntegerType, StringType, FloatType, DoubleType, MapType
import json
'''
databox='[{"productId":92,"productName":"xx医疗保险","description":"xxx设计的医疗保险","personalized":true,"soldCount":2000,"databox":"uuid=oDxxR0LLtI&applicant=1#564xx27#柳红#1974-12-21 00:00:00#F&applicantMd5=95d79131f538dbbf6c4&applicantProductId=24&sendCode=0041248305&agentCode=000258&businessId=wxxc&sceneId=accxpt&abtestId=v2020_01_01&abgroupId=2&productId=992&algoId=top_k"}]'
'''
@udf(returnType=MapType(StringType(), StringType()))
def dbx_analy1(x):
    res = {}
    dx = json.loads(x)[0]
    _d = dx.pop('databox')
    dx.update(**{i:j for i,j in [x.split('=') for x in _d.split('&')]})
    return dx
df3 = df2.select(*cols, dbx_analy1('databox').alias('databox_map'))
keys = (df3.select(explode("databox_map")).select("key").distinct().rdd.flatMap(lambda x : x).collect())
exprs = [col('databox_map').getItem(k).alias(k) for k in keys]
df4 = df3.select(*cols, *exprs)
```

### udf用法

**用法一**   
数据情况：

|member_id|linajia_community_org|lianjia_address|col4_disc|level|
|:--:|:--:|:--:|:--:|:--:|
|6001|明湖雅居|莫愁湖东路12号|address_demp|1|
|6002|新旺花苑|江宁区秣陵街道|address_demp|0|
|6003|华庭北园|华庭北园|address_demp|2|
|6004|童卫路|童卫路|address_demp|2|
|6005|新安里|新安里|address_demp|3|
|6006|和睦北园|天目山路9号|address_demp|2|

```python
def score_addr(row):
    coname = row[0]
    ljaddr = row[1]
    referr = row[2]
    if referr == '2':   # 注意这里是'2'不是2，hive里存的大部分都是string类型
        if len(re.findall(u'.*?\d+',ljaddr)) > 0:
            return 9
        elif len(re.findall(u'(?:园|府|所|湾|城|汇|苑|碑|阁|庭|居|堡|界|埠|曲|洞|廷|轩|台|间|谷|门|庄|区)\s*$',coname)) > 0:
            return 8
        elif len(re.findall(u'(?:路|里|街|弄|巷|道|胡同)\s*$', coname)) > 0:
            return 3
        else:
            return 5
    # elif referr == '1'
    # elif referr == '0'
    else:
        if len(re.findall(u'.*?\d+',ljaddr)) > 0:
            return 8
        elif len(re.findall(u'(?:园|府|所|湾|城|汇|苑|碑|阁|庭|居|堡|界|埠|曲|洞|廷|轩|台|间|谷|门|庄|区)\s*$',ljaddr)) > 0:
            return 7
        elif len(re.findall(u'(?:路|里|街|弄|巷|道|胡同)\s*$', ljaddr)) > 0:
            return 2
        else:
            return 3
            
f = udf(score_addr, IntegerType()) # 这里IntegerType指的是返回类型是int
df.select(df['member_id'],(f(array(df.lianjia_community_org, df.lianjia_address, df['level']))).alias('new_score')).show()
# +---------+-----+
# |member_id|level|
# +---------+-----+
# |     6001|    8|
# |     6002|    7|
# |     6003|    8|
# |     6004|    3|
# |     6005|    2|
# |     6006|    9|
# +---------+-----+
```
**用法二**   
```python
def fun(s):
    dict_digit2 = (u'十五期',u'十四期',u'十三期',u'十二期',u'十一期',u'十期',u'九期',u'八期') 
    pattern = [r'\(+.*\)+']
    a = re.findall(u'\(+.*\)+',s)
    if len(a) == 0:
        return s
    a = a[0][1:len(a[0])-1]
    if a in dict_digit2:
        pattern = [r'\(',r'\)']
    for pat in pattern:
        s = re.sub(pat,'',s)
    return s
f = udf(fun,StringType()) # 指返回的是string type
df = df.select(df['member_id'], df['city_code'],f(df['address']).alias('address'))
```
## pyspark数字类型以及空表df创建
数字类型（ByteType、ShortType、IntegerType、LongType、FloatType、DoubleType、DecimalType）
```python
save_table='fbxxx.bz_result1'
st = StructType([StructField("word_postag", StringType(), True),StructField("word_count", LongType(),True)])
spark.createDataFrame([("1",1)], schema=st).filter(col('word_count')==-1).write.saveAsTable(save_table,mode='overwrite')
```

