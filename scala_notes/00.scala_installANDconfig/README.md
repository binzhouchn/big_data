## scala安装及开发环境搭建Windows

**【Scala环境安装及配置】**

windows上用scala先安装java(我装了jdk1.7) 
如果cmd中输入java -version显示不了则手动添加java路径到环境变量中： 
右击【我的电脑】--【属性】--【高级】--【环境变量】 
新建系统变量--变量名 JAVA_HOME 变量值输入路径C:\Program Files\Java\jdk1.7.0_17 
Path变量中添加;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin; 
CLASSPATH变量中输入(如果没有则新建一个) .;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar; 
maven环境变量配置， 
系统变量中新建MVN，添加E:\apache-maven-3.3.9 
Path中添加E:\apache-maven-3.3.9\bin

安装完java以后再安装scala 
如果cmd中输入scala -version显示不了则手动添加scala路径到环境变量中： 
新建系统变量--变量名 SCALA_HOME 变量值输入路径 C:\Program Files (x86)\scala 
Path变量中添加 ;C:\Program Files (x86)\scala\bin

**【Scala开发环境搭建】**

下载IntelliJ idea和scala plugin 
安装完IntelliJ后在setting--plugin中导入下载的zip file (scala-intellij-bin-2016.3.5.zip) 
重启后选择scala项目有然后分别选上Java和scala jdk安装的位置地址 
创建Maven项目，具体操作及编译我放在本地电脑上[Maven配置_编译打包Spark应用_以及测试环境提交Application.doc]

---

## scala安装及开发环境搭建Linux