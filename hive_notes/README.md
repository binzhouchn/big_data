# 主目录

[**1. pyspark kill hive命令操作**](#kill命令)

[**2. 数据文件放hive**](#数据文件放hive)

[**3. hive创建表（数据量不大）**](#hive创建表的另一种方式)

[**4. hive生成临时表**](#hive生成临时表)

[**5. hive增加一列index**](#hive增加一列index)

[**6. hive显示一列ArrayType**](#hive显示一列arraytype)

---

## kill命令

```python
# -*- coding: utf-8 -*-
import os

app = 'application_152154389x962_9769x33'
cmd = 'yarn application -kill {}'.format(app)
os.system(cmd)
```

## 数据文件放hive

将本地文件放入hive中，因为我们部门没有xshell运维权限，所以无法直接上传文件到hdfs或者hive中。可以用如下方法： 写一个.py的脚本<br>
```python
# -*- coding: utf-8 -*-
import os
cmd = "hadoop fs -mkdir /user/xxxxx/bz_addr"
os.system(cmd)
cmd = "hadoop fs -put aa.py /user/xxxxxx/bz_addr"
os.system(cmd)
```

## hive创建表的另一种方式
```sql
create table bz.new as 
select 'a', 'b' from bz.dual(这是一张伪表，有些数据)
union all 
select 'c', 'd' from bz.dual
```

这样就把a, b, c, d数据全部插入bz.new表了，
插入的结果是 

|col1|col2
|--|--
|a|b 
|c|d

## hive生成临时表

需要先产生一张table的这种做法，可以用更加高级的做法，需要生成表，而是生成一个类似表的临时模块 

旧的操作：
```sql
drop table if exists bz_tmp;
create table bz_tmp as 
select * from bz_shanghai_tmp
where addr_matched  = 1;
drop table if exists bz_tmp1;
create table bz_tmp1 as 
select * from bz_tmp
union all
select b.* from bz_shanghai_tmp b
left join bz_tmp a
on a.member_id =  b.member_id
where a.member_id is null
;
```
新的操作： 
(不需要生产表bz_tmp1的用法，生成模块bz_tmp1)
```sql
drop table if exists bz_tmp_shanghai;
create table bz_tmp_shanghai as
with bz_tmp as (
        select * from bz_shanghai_tmp
        where addr_matched  = 1
       )
select * from bz_tmp
union all
select b.* from bz_shanghai_tmp b
left join bz_tmp a
on a.member_id =  b.member_id
where a.member_id is null
;
```

## hive增加一列index

```sql
select a, row_number() over(order by a) as idx from fbxxx.bz_tmp
or
select a, row_number() over(partition by b order by c) from fbxxx.bz_tmp
```

## hive显示一列arraytype

```sql
select concat_ws("|", word_cut_array) from fbxxx.bz_tmp
```
