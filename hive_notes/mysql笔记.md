## mysql笔记


windows安装直接用installer 5.5

```bash
#如果要让别人访问可以： 
use mysql 
mysql>update user set host='%' where user='root'; 
use fbi 
mysql>update user set host='%' where user='root'; 
mysql>quit 
#退出mysql 
c:/mysql/mysql server 5.5>net stop mysql 
c:/mysql/mysql server 5.5>net start mysql 
#如果加入环境变量的话直接就 
net stop mysql / net start mysql
```