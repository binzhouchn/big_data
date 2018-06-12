## 二级目录

[**1. pyspark前期准备**](#pyspark前期准备)

[**2. pyspark比较常用的transform操作**](#pyspark比较常用的transform操作)
    
   - [2.1 map(func)](#map)
   - [2.2 filter(func)](#filter)
   - [2.3 flatMap(func)](#flatmap)
   - [2.4 mapPartitions(func)](#mappartitions)
   - [2.5 sample(withReplacement, fraction, seed)](#sample)
   - [2.6 union(otherDataset)](#union)
   - [2.7 intersection(otherDataset)](#intersection)
   - [2.8 distinct([numTasks])](#distinct)
   - [2.9 groupByKey([numTasks])](#groupbykey)
   - [2.10 reduceByKey(func, [numTasks])](#reducebykey)
   - [2.11 sortByKey([ascending=True], [numTasks])](#sortbykey)
   - [2.12 join(otherDataset, [numTasks])](#join)
   - [2.13 cogroup(otherDataset, [numTasks])](#cogroup)
   - [2.14 cartesian(otherDataset)](#cartesian)
   - [2.15 coalesce(numPartitions)](#coalesce)
   - [2.16 repartition(numPartitions)](#repartition)
   - [2.17 repartitionAndSortWithinPartitions(partitioner)](#repartitionandsortwithinpartitions)

[**3. pyspark比较常用的action操作**](#pyspark比较常用的action操作)
   
   - [3.1 reduce(func)](#reduce)
   - [3.2 collect(func)](#collect)
   - [3.3 count(func)](#count)
   - [3.4 first(func)](#first)
   - [3.5 take(func)](#take)
   - [3.6 takeSample(withReplacement, num, [seed])](#takesample)
   - [3.7 takeOrdered(n, [ordering])](#takeordered)
   - [3.8 saveAsTextFile(path)](#saveastextfile)
   - [3.9 saveAsSequenceFile(path)](#saveassequencefile)
   - [3.10 saveAsObjectFile(path)](#saveasobjectfile)
   - [3.11 countByKey(func)](#countbykey)
   - [3.12 foreach(func)](#foreach)
   
[**4. pyspark排序问题**](#pyspark排序问题)

---

## pyspark前期准备
```python
# lambda: 快速定义单行的最小函数，inline的匿名函数
(lambda x : x ** 2)(3) 9
# 或者
f = lambda x : x ** 2
f(3)  9

# python比较常用的高阶函数 higher order function: map, filter, reduce
# map
arr_str = ["hello", "this"]
arr_num = [3,1,6,10,12]
def f(x):
    return x ** 2
map(lambda x : x ** 2, arr_num) [9,1,36,100,144]
map(f, arr_num) [9,1,36,100,144]
map(len, arr_str) [5,4]
map(lambda x : (x, 1), arr_str)  [('hello', 1), ('this', 1)]
# filter
filter(lambda x : len(x) >= 5, arr_str) ['hello']
filter(lambda x : x > 5, arr_num) [6,10,12]
# reduce
reduce(lambda x, y : x + y, arr_num) 32
```

## pyspark比较常用的transform操作

### map

```python
# 将func函数作用到数据集的每个元素，生成一个新的分布式的数据集并返回
>>> a = sc.parallelize(('a', 'b', 'c'))
>>> a.map(lambda x: x + '1').collect()
['a1', 'b1', 'c1']
```
### filter

```python
# 选出所有func返回值为true的元素，作为一个新的数据集返回
>>> a = sc.parallelize(range(10))
>>> a.filter(lambda x: x % 2 == 0).collect()  # 选出0-9的偶数
[0, 2, 4, 6, 8]
```
### flatMap
```python
# 与map相似，但是每个输入的item能够被map到0个或者更多的items输出，
# 也就是说func的返回值应当是一个Sequence，而不是一个单独的item
>>> l = ['I am Tom', 'She is Jenny', 'He is Ben']
>>> a = sc.parallelize(l,3)
>>> a.flatMap(lambda line: line.split()).collect()  # 将每个字符串中的单词划分出来
['I', 'am', 'Tom', 'She', 'is', 'Jenny', 'He', 'is', 'Ben']
```
### mapPartitions

```python
# 与map相似，但是mapPartitions的输入函数单独作用于RDD的每个分区(block)上，因此func的输入和返回值都必须是迭代器iterator。 
# 例如：假设RDD有十个元素0~9，分成三个区，使用mapPartitions返回每个元素的平方。如果使用map方法，map中的输入函数会被调用10次，
# 而使用mapPartitions方法，输入函数只会被调用3次，每个分区被调用1次。
>>> def squareFunc(a):
. . .     for i in a:
. . .         yield i * i
. . .
>>> a = sc.parallelize(range(10), 3)
PythonRDD[1] at RDD at PythonRDD.scala:48
>>> a.mapPartitions(squareFunc).collect()
[0, 1, 4, 9, 16, 25, 36, 49, 64, 81]
```
### sample
```python
# 从数据中抽样，withReplacement表示是否有放回，withReplacement=true表示有放回抽样，fraction为抽样的概率（0<=fraction<=1），seed为随机种子。 
# 例如：从1-100之间抽取样本，被抽取为样本的概率为0.2
>>> data = sc.parallelize(range(1,101),2)
>>> sample = data.sample(True, 0.2)
>>> sampleData.count()
19
>>> sampleData.collect()
[16, 19, 24, 29, 32, 33, 44, 45, 55, 56, 56, 57, 65, 65, 73, 83, 84, 92, 96]
！！！注意，Spark中的sample抽样，当withReplacement=True时，相当于采用的是泊松抽样；当withReplacement=False时，相当于采用伯努利抽样，fraction并不是表示抽样得到的样本占原来数据总量的百分比，
而是一个元素被抽取为样本的概率。fraction=0.2并不是说明要抽出100个数字中20%的数据作为样本，而是每个数字被抽取为样本的概率为0.2，这些数字被认为来自同一总体，样本的大小并不是固定的，而是服从二项分布。
```

### union
```python
# 并集操作，将源数据集与union中的输入数据集取并集，默认保留重复元素（如果不保留重复元素，可以利用distinct操作去除，下边介绍distinct时会介绍）。
>>> data1 = sc.parallelize(range(10))
>>> data2 = sc.parallelize(range(6,15))
>>> data1.union(data2).collect()
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 7, 8, 9, 10, 11, 12, 13, 14]
```
### intersection
```python
# 交集操作，将源数据集与union中的输入数据集取交集，并返回新的数据集。
>>> data1 = sc.parallelize(range(10))
>>> data2 = sc.parallelize(range(6,15))
>>> data1.intersection(data2).collect()
[8, 9, 6, 7]
```
### distinct
```python
# 去除数据集中的重复元素。
>>> data1 = sc.parallelize(range(10))
>>> data2 = sc.parallelize(range(6,15))
>>> data1.union(data2).distinct().collect()
[0, 8, 1, 9, 2, 10, 11, 3, 12, 4, 5, 13, 14, 6, 7]
```
### groupByKey
```python
# 作用于由键值对(K, V)组成的数据集上，将Key相同的数据放在一起，返回一个由键值对(K, Iterable)组成的数据集。 
# 注意：1. 如果这一操作是为了后续在每个键上进行聚集（aggregation），比如sum或者average，此时使用reduceByKey或者aggregateByKey的效率更高。2. 默认情况下，输出的并行程度取决于RDD分区的数量，但也可以通过给可选参数numTasks赋值来调整并发任务的数量。
>>>a = [("hello", 1), ("bin", 10), ("zz", 3), ("bin", 100), ("bin", 1000)]
>>>b = sc.parallelize(a)
>>>c = b.groupByKey()
>>>c.map(lambda x: {x[0]: list(x[1])}).collect()
[{'hello': [1]}, {'zz': [3]}, {'bin': [10, 100, 1000]}]
```
### reduceByKey
```python
# 作用于键值对(K, V)上，按Key分组，然后将Key相同的键值对的Value都执行func操作，得到一个值，注意func的类型必须满足
>>> pupils = newRows.map(lambda r: (r[1], int(r[6])))  # r[1]为ward的名字，r[6]为每个学校的学生数
>>> ward_pupils = pupils.reduceByKey(lambda x, y: x + y)   # 计算各个ward中的学生数
>>> ward_pupils.collect()  # 输出各个ward中的学生数
[('Stifford Clays', 1566), ('Shenley', 1625), ('Southbury', 3526), ('Rainham and Wennington', 769), ('Bromley Town', 574), ('Waltham Abbey Honey Lane', 835), ('Telegraph Hill', 1238), ('Chigwell Village', 1506), ('Gooshays', 2097), ('Edgware', 2585), ('Camberwell Green', 1374), ('Glyndon', 4633),...]
```
### sortByKey
```python
# 按照Key进行排序，ascending的值默认为True，True/False表示升序还是降序 
```
### join
```python
# 类似于SQL中的连接操作，即作用于键值对(K, V)和(K, W)上，返回元组 (K, (V, W))，spark也支持外连接，包括leftOuterJoin，rightOuterJoin和fullOuterJoin。例子：
>>> class1 = sc.parallelize(('Tom', 'Jenny', 'Bob')).map(lambda a: (a, 'attended'))
>>> class2 = sc.parallelize(('Tom', 'Amy', 'Alice', 'John')).map(lambda a: (a, 'attended'))
>>> class1.join(class2).collect()
[('Tom', ('attended', 'attended'))]
>>> class1.leftOuterJoin(class2).collect()
[('Tom', ('attended', 'attended')), ('Jenny', ('attended', None)), ('Bob', ('attended', None))]
>>> class1.rightOuterJoin(class2).collect()
[('John', (None, 'attended')), ('Tom', ('attended', 'attended')), ('Amy', (None, 'attended')), ('Alice', (None, 'attended'))]
>>> class1.fullOuterJoin(class2).collect()
[('John', (None, 'attended')), ('Tom', ('attended', 'attended')), ('Jenny', ('attended', None)), ('Bob', ('attended', None)), ('Amy', (None, 'attended')), ('Alice', (None, 'attended'))]
```
### cogroup
```python
# 作用于键值对(K, V)和(K, W)上，返回元组 (K, (Iterable, Iterable))。这一操作可叫做groupWith
>>> class1 = sc.parallelize(('Tom', 'Jenny', 'Bob')).map(lambda a: (a, 'attended'))
>>> class2 = sc.parallelize(('Tom', 'Amy', 'Alice', 'John')).map(lambda a: (a, 'attended'))
>>> group = class1.cogroup(class2)
>>> group.collect()
[('John', (<pyspark.resultiterable.ResultIterable object at 0x7fb7e808afd0>, <pyspark.resultiterable.ResultIterable object at 0x7fb7e808a1d0>)), ('Tom', (<pyspark.resultiterable.ResultIterable object at 0x7fb7e808a7f0>, <pyspark.resultiterable.ResultIterable object at 0x7fb7e808a048>)), 
('Jenny', (<pyspark.resultiterable.ResultIterable object at 0x7fb7e808a9b0>, <pyspark.resultiterable.ResultIterable object at 0x7fb7e808a208>)), ('Bob', (<pyspark.resultiterable.ResultIterable object at 0x7fb7e808ae80>, <pyspark.resultiterable.ResultIterable object at 0x7fb7e8b448d0>)), 
('Amy', (<pyspark.resultiterable.ResultIterable object at 0x7fb7e8b44c88>, <pyspark.resultiterable.ResultIterable object at 0x7fb7e8b44588>)), ('Alice', (<pyspark.resultiterable.ResultIterable object at 0x7fb7e8b44748>, <pyspark.resultiterable.ResultIterable object at 0x7fb7e8b44f98>))]
>>> group.map(lambda x: {x[0]: [list(x[1][0]), list(x[1][1])]}).collect()
[{'John': [[], ['attended']]}, {'Tom': [['attended'], ['attended']]}, {'Jenny': [['attended'], []]}, {'Bob': [['attended'], []]}, {'Amy': [[], ['attended']]}, {'Alice': [[], ['attended']]}]
```
### cartesian
```python
# 笛卡尔乘积，作用于数据集T和U上，返回(T, U)，即数据集中每个元素的两两组合
>>> a = sc.parallelize(('a', 'b', 'c'))
>>> b = sc.parallelize(('d', 'e', 'f'))
>>> a.cartesian(b).collect()
[('a', 'd'), ('a', 'e'), ('a', 'f'), ('b', 'd'), ('b', 'e'), ('b', 'f'), ('c', 'd'), ('c', 'e'), ('c', 'f')]
```
### coalesce
```python
# 将RDD的分区数减小到numPartitions个。当数据集通过过滤规模减小时，使用这个操作可以提升性能。
```
### repartition
```python
# 重组数据，数据被重新随机分区为numPartitions个，numPartitions可以比原来大，也可以比原来小，平衡各个分区。这一操作会将整个数据集在网络中重新洗牌。
```
### repartitionAndSortWithinPartitions
```python
# 根据给定的partitioner函数重新将RDD分区，并在分区内排序。这比先repartition然后在分区内sort高效，原因是这样迫使排序操作被移到了shuffle阶段。
```



## pyspark比较常用的action操作

### reduce
```python
# 使用函数func（两个输入参数，返回一个值）对数据集中的元素做聚集操作。函数func必须是可交换的（我理解的就是两个参数互换位置对结果不影响），并且是相关联的，从而能够正确的进行并行计算。
>>> data = sc.parallelize(range(1,101))
>>> data.reduce(lambda x, y: x+y)
5050
```
### collect
```python
# 在driver程序中以数组形式返回数据集中所有的元素。这以action通常在执行过filter或者其他操作后返回一个较小的子数据集时非常有用
>>> data = sc.parallelize(range(1,101))
>>> data.filter(lambda x: x %10==0).collect()
[10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
```
### count
```python
# 返回数据集中元素的个数。
>>> data.count()
100
```
### first
```python
# 返回数据集中的第一个元素，相当于take(1)。
>>> data.first()
1
```
### take
```python
# 以数组形式返回数据集中前n个元素。需要注意的是，这一action并不是在多个node上并行执行，而是在driver程序所在的机器上单机执行，会增大内存的压力，使用需谨慎。
>>> data.take(10)
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
```
### takeSample
```python
# 以数组形式返回从数据集中抽取的样本数量为num的随机样本，有替换或者无替换的进行采样。可选参数[seed]可以允许用户自己预定义随机数生成器的种子。
>>> data.takeSample(False, 20)
[60, 97, 91, 62, 48, 7, 49, 89, 40, 44, 15, 2, 33, 8, 30, 82, 87, 96, 32, 31]   
>>> data.takeSample(True, 20)
[96, 71, 20, 71, 80, 42, 70, 93, 77, 26, 14, 82, 50, 30, 30, 56, 93, 46, 70, 70]
```
### takeOrdered
```python
# 返回RDD的前n个元素，可以利用自然顺序或者由用户执行排序的comparator。
>>> score = [('Amy',98),('Bob',87),('David',95),('Cindy',76),('Alice',84),('Alice',33)]
>>> scoreRDD = sc.parallelize(score)
>>> scoreRDD.takeOrdered(3)
[('Alice', 33), ('Alice', 84), ('Amy', 98)]  #可以根据两个Alice的例子看到，当元祖中第一个元素相同时，会继续比较第二个元素，仍然按升序排列
>>> scoreRDD.takeOrdered(3, key=lambda x: x[1])  #按照分数升序排序
[('Alice', 33), ('Cindy', 76), ('Alice', 84)]
>>> scoreRDD.takeOrdered(3, key=lambda x: -x[1])  #按照分数降序排序
[('Amy', 98), ('David', 95), ('Bob', 87)]
注意，第2个参数这里是一个匿名函数，这个匿名函数并不会改变scoreRDD中的值，也就是第3个例子中，并不是将每个人的分数变为负数，而是提供一个排序的依据，说明此时为降序排序。
```
###  saveAsTextFile
```python
# 将数据集中的元素以文本文件（或者文本文件的一个集合）的形式写入本地文件系统，或者HDFS，或者其他Hadoop支持的文件系统的指定路径path下。Spark会调用每个元素的toString方法，将其转换为文本文件中的一行。
```
### saveAsSequenceFile
```python
# 将数据集中的元素以Hadoop SequenceFile的形式写入本地文件系统，或者HDFS，或者其他Hadoop支持的文件系统的指定路径path下。RDD的元素必须由实现了Hadoop的Writable接口的key-value键值对组成。在Scala中，也可以是隐式可以转换为Writable的键值对（Spark包括了基本类型的转换，例如Int，Double，String等等）
```
### saveAsObjectFile
```python
# 利用Java序列化，将数据集中的元素以一种简单的形式进行写操作，并能够利用SparkContext.objectFile()加载数据。（适用于Java和Scala）
```
### countByKey
```python
# 只能作用于键值对(K, V)形式的RDDs上。按照Key进行计数，返回键值对(K, int)的哈希表。
```
### foreach
```python
# 在数据集的每个元素上调用函数func。这一操作通常是为了实现一些副作用，比如更新累加器或者与外部存储系统进行交互。注意：在foreach()之外修改除了累加器以外的变量可能造成一些未定义的行为。更多内容请参阅闭包进行理解。
from __future__ import print_function
或者
def f(x):
    print x
然后 rdd.foreach(print) 或者rdd.foreach(f)
```

## pyspark排序问题
spark排序的时候，比如取top5返回的时候并不是从高到底排序好的，是最高的5个，但是这5个内部是无序的


