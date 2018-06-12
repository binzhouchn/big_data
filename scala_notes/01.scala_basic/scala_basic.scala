1. scala定义变量 var a : String = "bin" 或者 var a = "bin"其中: String是指定这个变量的类型
2. scala定义常量 val a ：String = "bin" 或者 val a = "bin"
3.scala写循环
for (x <- 1 to 10) { }这个是指x从1到10 或者 for (x <- 1 until 10) { }指从1到10上界开区间
或者 for (x <- list) { } 循环list中的所有值
4. scala写函数
def addInt(x : Int, y : Int) : Int = {   //这里:Int是说明return的类型，也可以不写
    var sum = 0
    sum = x + y
    return sum
}
def printStr() : Unit = {   //这里:Unit说明return类型是void空
    println("hello bin!")
}
def main(args: Array[String]): Unit = { }  //这个是main函数
//注意scala写函数比如
def square(x : Int) : Int = {
    return x * x   //也可以写成x *ｘ不需要加return
}
//还可以这样
def square(x : Int) : Int = x * x   //不需要加{ } 和return
5. scala闭包举例
var factor = 10
val closure =  (i : Int) => i * factor
def main(args: Array[String]): Unit = {
    val a = addInt(2, 9)
    println("closure = " + closure(3))
}
6. scala字符串 string是immutable的
var sb = new StringBuilder()
sb += 'a'
sb ++= "hello"    // 这里要注意的是char可以 += 而string需要++=
7. scala数组
// 一维数组：
val arr = new Array[String](3)   // [String]这个是数组的类型，3是数组的大小
或者 val arr = Array("spark", "hadoop", "hive")   // 注意直接赋初始值的数组不需要new
// 多维数组：
var arr = Array.ofDim[Int](3, 3)  // 定义了一个3*3的二维数组
8. scala异常
catch字句是按次序捕捉的，因此越具体的异常越要靠前，如果抛出的异常不在catch字句中
该异常则无法处理, catch中是模式匹配的写法:
try {
    Integer.parseInt("scala")
} catch {
    case ex : NumberFormatException => println("parameter is not Integer")
    case ex : IllegalArgumentException => println("parameter is not illegal")
} finally {
    println("exiting finally...")
}
9. scala提取器
object Test {
  def main(args: Array[String]): Unit = {
    val a = Test(2, 5)
    println(a)
//    val x = 10
//    x match {
//      case 10 => println("is 10")
//      case _ => println("is not 10")
//    }
  }
  def apply(x : Int, y : Int) = x * y
  // def unapply(z : Int) : Option[Int] = Some(math.sqrt(z))
}
10. scala文件IO
//文件写操作： scala进行文件写操作，直接用的都是java中的I/O类(java.io.File):
import java.io.{File, PrintWriter}
def main(args: Array[String]): Unit = {
      val writer = new PrintWriter(new File("E:/Scala_demo/io/test123.txt"))
      writer.write("This is scala io test!")
      writer.close()
}
  //文件读操作
val line = Console.readLine()
// 文件上读取
import scala.io.Source
def main(args: Array[String]): Unit = {
    println("following is the content read: ")
    Source.fromFile("E:/Scala_demo/io/djt.txt").foreach {
        print
    }
}
--读取文件I/O
方法一：
import scala.io.Source
1. val source = Source.fromFile("path", "UTF-8"); source.getLines
2. val source = Source.fromFile("path", "UTF-8"); source.getLines.toArray
3. val source = Source.fromFile("path", "UTF-8"); source.mkString
从URL以及字符串中读取字符
val source = Source.fromURL("web address", "UTF-8")
使用完Source对象之后，调用Source.close方法，关闭IO流资源
方法二：
结合Java IO流，读取任意文件
import java.io._
val file = new File("path")
val bytes = new Array[Byte](file.length.toInt)
val fis = new FileInputStream(file)
fis.read(bytes)
fis.close()
结合Java IO流，写文件
val pw = new PrintWriter("path")
pw.println("text")
pw.close()
11. scala柯里化(Currying)
定义：将原来接受两个参数的函数变成新的接受一个参数的函数的过程。
新的函数返回一个以原来第二个参数为参数的函数
没有柯里化举例：
object Test {
  def main(args: Array[String]): Unit = {
    val x = multiplyBy(9)   //这里返回的赋值给x的是函数
//    println(x)
    val n = x(17)
    println(n)
    //或者直接 val n = multiplyBy(9)(17)
  }
  def multiplyBy(factor : Double) = (x : Double) => factor * x
}
//柯里化举例：
val x = multiplyBy(9) _
def multiplyBy(factor : Double)(x : Double) = factor * x
12. scala高阶函数
// map的使用举例
val list = List("spark" -> 1, "storm" -> 2, "hadoop" -> 3)
list.map(x => x._1).foreach(y => println(y))
map.contains, map.getOrElse("key", 0)
// flatMap扁平化的使用举例
List(List(1,2,3,4), List(5,6,7,8)).flatMap(x => x).foreach(x => println(x))
// filter函数
Array(1,2,3,4,5).fileter(_ > 3).foreach(x => println(x))
--两个or的判断条件(1 until 1000).filter( n => n % 3 = 0|| n % 5 == 0).reduce(_+_)或者.sum
// reduce函数
Array(1,2,3,4,5,6).reduce(_+_)  //所有的数进行累加
// fold函数
=============================================================================
【快学scala】笔记
1. for循环写法
for (i <- 1 to 10) {
    println(i)
}
val a = Array(1,2,3,4,5)
val a = List(1,2,3,4,5)
for (i <- a) {
    println(i)
}
循环中的变量不用定义，如
for(i <- 1 to 10; j = i *ｉ) println(j)
2. 不变数组：
Array
比如val arr = Array(1,2,3,4,5)可以变成string, arr.mkString得到12345
还可以在变的过程中指定分隔符:arr.mkString(",")得到1,2,3,4,5
arr.toString得到的是Array(1,2,3,4,5)
可变数组：
import scala.collection.mutable.ArrayBuffer
val a = new ArrayBuffer[Int]()
a += 1
a ++= Array(6,7,8)
3. 创建hashmap
val a = new mutable.HashMap[String, String]()
a.put("hadoop", "hive")
a("hadoop")得到"hive" 如果没有则会报错
a.get("hadoop") 得到 Some("hive")如果没有则None
a += ("spark", "test12")
如果b是另一个hashmap则要用 ++=
移除 a-= "hadoop"
4. 元组：
val a = ("hello", 1) 也可以通过val a = "hello" -> 1来构建元组
读取元组第一个元素a._1,第二个元素 a._2注意不是从0开始的
val a = Array("@", "$")
    val b = Array(3,5)
    a.zip(b) 可以得到Array(("@", 3), ("$", 5))
5. 类及构造器(与java不同)
class Person(val name : String,private val age : Int = 10) {  //主构造方法
  println("just constructed another person")
  def description = name + " is " + age + " years old."
}
构造了一个类，构造方法init两个参数public name和privte age，和一个description的方法查看name和age，外部类不能直接查看私有age
使用和java一样在main或其他类中val a = new Person("bin",26)
或者val a = new Person("bin")即可
6. 伴生对象（scala没有静态方法或静态字段，你可以用object这个语法结构来达到同样的目的。对象本质上可以拥有类的所有特性--它甚至可以扩展其他类或特质）：
比如：
object Person {
  private val age = 10
  def description = "age is " + age
}
伴生对象没有主构造方法，main或外部类可以直接Person.description调用，class类因为没有静态方法所以只能实例化后才能调用
7. 正则表达式：
val pattern = "[0-9]+\s+".r
pattern.findAllIn("abcdefg....string")
8. 特质（相当于java中的接口）用extends，而不是implements
trait Logger {
    def log(msg:String) //这是个抽象方法，不需要声明abstract
}
重写特质的抽象方法时不需要给出override关键字
如果你需要的特质不止一个，可以用with关键字来添加额外的特质:
class ConsoleLogger extends Logger with Cloneable with Serializable
9. 匿名函数：
x => x * 3或者(x : Int) => x * 3，
如果类型已知并且参数在=>右侧只出现一次，则可以用_替换: 3 * _
10. Array集合
不可变：Array()
可变：ArrayBuffer() scala.collection.mutable.ArrayBuffer
都可以互相调用方法进行转换
ArrayBuffer里面加一个元素 比如：+= 1 加多个元素：++= Array(2,3,4)
11. List集合：
List(1,2,3)我们可以 9 :: List(1,2,3)使其变成List(9,1,2,3)
注意::是右结合的。通过::操作符，列表将从末端开始构建
还有注意一点List(1,2,3).head是1，List(1,2,3).tail是List(2,3)，
List(3).head是3然后List(3).tail是List()即Nil
可变的list：scala.collection.mutable.LinkedList()
用法和java一样
12. Set集合
Set()
排序的set有scala.collection.mutable.LinkedHashSet()或者
scala.collection.immutable.SortedSet()
已排序的集使用红黑树实现的！
--集合元素操作
col :+ ele 将元素添加到集合尾部  Seq
ele +: col 将元素添加到集合头部  Seq
col + ele 在集合尾部添加元素  Set、Map
col + （ele1，ele2） 将其他集合添加到集合的尾部  Set、Map
col - ele 将元素从集合删除  Set、Map、ArrayBuffer
col - （ele1，ele2） 将子集合从集合删除  Set、Map、ArrayBuffer
col1 ++ col2 将其他集合添加到集合尾部  Iterable
col2 ++：col1 将其他集合添加到集合头部  Iterable
ele :: list 将元素添加到list的头部  List
list2 ::: list1 将其他list添加到list的头部  List
list1 ::: list2 将其他list添加到list的尾部  List
set1 | set2 取两个set的并集  Set
set1 & set2 取两个set的交集  Set
set1 &~ set2 取两个set的diff  Set
col += ele 给集合添加一个元素  可变集合
col += （ele1，ele2） 给集合添加一个集合  可变集合
col ++= col2 给集合添加一个集合  可变集合
col -= ele 从集合中删除一个元素  可变集合
col -= （ele1，ele2） 从集合中删除一个子集合  可变集合
col -= col2 向集合中删除一个子集合  可变集合
ele +=: col 向集合头部添加一个元素  ArrayBuffer
col2 ++=：col 向集合头部添加一个集合  ArrayBuffer
--集合的常用方法：
head、last、tail
length、isEmpty
sum、max、min
count、exists、filter、filterNot
takeWhile、dropWhile
take、drop、splitAt
takeRight、dropRight
sclice
contains、startsWith、endsWith
indexOf
intersect、diff
13. 迭代器
while (iter.hasNext)
    对iter.next()执行某种操作
或者用for循环：
for (elem <- iter)
    对elem执行某种操作
14. 模式匹配（可以用match来分别处理不同类型的值）
def bigger(o: Any): Any = {
  o match {
    case i: Int if i < 0 => i - 1
    case i: Int => i + 1
    case d: Double if d < 0.0 => d - 0.1
    case d: Double => d + 0.1
    case text: String => text + "s"
  }
}
def calcType(calc: Calculator) = calc match {
  case _ if calc.brand == "HP" && calc.model == "20B" => "financial"
  case _ if calc.brand == "HP" && calc.model == "48G" => "scientific"
  case _ if calc.brand == "HP" && calc.model == "30B" => "business"
  case _ => "unknown"
}
15. 偏函数
用下划线代替1+个参数的函数叫偏函数(partially applied function)
def sum(a:Int, b:Int, c:Int) a +ｂ + c
val p = sum(10,_:Int,20)
得到的p还是一个function这时通过p(100)可得到结果为130
偏函数第二种类型：
val pf: PartialFunction[Int, String] = {
    case i if i%2 == 0 => "even"
}
或者也可以写成:
val pf: (Int => String) = {
    case i if i % 2 == 0 => "even"
}
它也可能和orElse组合使用比如:
val tf: (Int => String) = pf orElse { case _ => "odd"}
16. 区分 <-, =>, ->
<-   for (i <- 0 until 100)  用于for循环, 符号∈的象形
=>   List(1,2,3).map(x => x*x) ((i:Int)=>i*i)(5) // 25
用于匿名函数，相当于Ruby的 |x|,Groovy的{x-> x*x} 也可用在import中定义别名：import javax.swing. {JFrame=>jf}
->  Map(1->"a",2->"b") 用于Map初始化, 也可以不用->而写成 Map((1,"a"),(2,"b"))
17. require
def fun(n : Int) = {
    require(n != 0)
    1.0 / n
}
18. if..else
没有java的 b = (x > y) ? 100 : -1
只有 val b = if (x > y) 100 else -1
19. take drop splitAt
1 to 10 by 2 take 3 // Range(1,3,5)
1 to 10 by 2 drop 3 // Range(7,9)
1 to 10 by 2 splitAt 2 // (Range(1, 3),Range(5, 7, 9))
20. 打印'a' to 'z'的前10个（高级写法）
('a' to 'z').slice(0, 10).foreach(println)
21. 函数中的泛型
def foo[T](x : T) = println("value is " + x)
类中的泛型
class c1[T] {
    private var v :Ｔ = _ //初始值为T类的缺省值
    def set(v1 : T) = {v = v1}
    def get = v
}
22. 函数作为参数: 映射式函数
例子1：
def f(x:Int, y:Int, m:(Int, Int)=>Int) = m(x,y)
f(3,4, (x,y)=>x+y) // 7
例子2：
def f(f2:Int=>Int) = f2(5)
f(100+) // 105, 100+是匿名函数 ((x:Int)=>100+x)的简写
23. 柯里化 curry化
def sum(a:Int)(b:Int) = { a + b } // 调用方式一:sum(1)(2) = 3
或者：def sum(a:Int) = { (b:Int)=> a + b } // sum(1)(2)
// 调用方式二：val t1 = sum(10);  val t2 = t1(20)
24. Iterator (注意iterator用一次就消失了,可用toList方法)
Iterator不属于集合类型，只是逐个存取集合中元素的方法：
三种常用的使用模式：
1. 使用while
val it = Iterator(1,3,5,7)
while(it.hasNext) println(it.next)
2. 使用for
for(e <- Iterator(1,3,5,7)) println(e)
3.使用foreach
Iterator(1,3,5,7) foreach println或者Iterator(1,3,5,7).foreach(println)
25. Stream求fib数列
fib数列：1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
def fib(a:Int, b:Int) : Stream[Int] = a #:: fib(b, a+b)   //这里a是结果存到stream的数据结构中，return的是本身的fib函数
val fibs = fib(1,2).take(7).toList // List(1, 2, 3, 5, 8, 13, 21)
val fibs = fib(1,2).takeWhile(_ < 400000).filter(_%2 == 0).sum
26. 变长参数：
def sum(nums : Int*) = {    //只要int类型的每一个参数都会执行
    var result = 0
    for(num <- nums) result += num
    result
}
传入sum(1,2,3,4,5)  // 15
27. 伴生对象
如果有一个class，还是有一个与class同名的object，那么这个object是class的伴生对象，class是object的伴生类。伴生类和伴生对象必须存放在一个.scala文件中。伴生类和伴生对象，最大的特点就在于，互相可以访问private field
28. 闭包
def getGreetingFunc(msg:String)=(name:String)=>println(msg +","+ name)
val a = getGreetingFunc("Hello") //得到function1
a("bin")得到 "hello,bin"
29. 隐式转换举例
object ScalaWordCount {
  def main(args: Array[String]): Unit = {
    class Man(val name: String)
    class Superman(val name: String) {
      def emitLaser = println("emit a laser!!")
    }
    implicit def man2supername(man: Man): Superman = new Superman(man.name)
    val bin = new Man("bin")
    bin.emitLaser  //Man没有emitLaser方法，就尝试去找接受Man作为参数的隐式转换函数的
    //在作用域内有没有这种把Man进行隐式转换
  }
}
30. Actor相当于java中的多线程编程（AKKA是多线程分布框架）
import scala.actors.Actor
actor不同于java的是避免了锁和共享
//Scala提供了Actor trait来让我们更方便地进行actor多线程编程，Actor trait就类似于java中的Thread和Runnable，是基础的多线程基类和接口。我们只要重写Actor trait的act方法，即可实现自己的线程执行体，育java中重写run方法类似。
//此外，使用start()方法启动actor；使用!符号想actor发送消息；actor内部使用receive和模式匹配接收消息
31. 跳出循环的三种方式：
boolean变量，return，import scala.util.control.Breaks._
32. scala继承层级
class Studnet {
    val classNumber:Int = 10
    val classScores:Array[Int] = new Array[Int](classNumber)
}
class PEStudent extends Student{
    override val classNumber:Int = 3    //普通学生课程有10门，体育特长生3门
}
但这种方法重写以后val pestudent = new PEStudent
pestudent.classNumber = 3,但是pestudent.classScores = Array()
此时只能提前使用scala对象继承的一个高级特性，提前定义，在父类构造函数执行之前，先执行子类的构造函数中的某些代码
class PEStudent extends {
    override val classNumber:Int = 3
} with Student