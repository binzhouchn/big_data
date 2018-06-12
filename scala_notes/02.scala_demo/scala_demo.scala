// scala版的wordcount小示例:
import org.apache.spark.{SparkConf, SparkContext}
object ScalaWordCount {
  def main(args: Array[String]): Unit = {
    //参数检查
    if (args.length < 2) {
      System.err.println("usage: ScalaWordCount<input><output>")
      System.exit(1)
    }
    val input = args(0)   // 命令行中第一个传入的参数，读取数据文件的地址
    val output = args(1)  //命令行中第二个传入的参数，输出结果的地址
    // 创建scala版本的SparkContext
    val conf = new SparkConf().setAppName("scalaWordCount")
    val sc = new SparkContext(conf)
    // 读取数据
    val lines = sc.textFile(input)
    // 进行wordcount计算，在scala中就一行代码，非常方便
    val resultRdd=lines.flatMap(_.split("\\s+")).map((_,1)).reduceByKey(_+_)
    // 保存结果
    resultRdd.saveAsTextFile(output)
    // 手动关闭SparkContext驱动
    sc.stop()
  }
}
-------------------------------------------
//案例：Actor Hello World
import scala.actors.Actor    //已经deprecated了
class HelloActor extends Actor {
    def act() {
        while(true) {
            receive{   //消息接收
                case name:String => println("Hello, " + name)
            }
        }
    }
}
val helloactor = new HelloActor
helloactor.start() //启动线程
helloactor ! "bin"  // helloactor指的是拨过去那一方的电话号码
两个Actor之间要相互收发消息（案例：打电话）
import scala.actors.Actor
case class Message(content:String, sender:Actor)
class LeoTelephoneActor extends Actor {
    def act() {
        while(true) {
            receive {
                case Message(content, sender) => {println("leo telephone " + content);sender ! "I'm leo, please call me later"}
            }
        }
    }
}
class JackTelephoneActor(val leoTelephoneActor:Actor) extends Actor {
    def act() {
        leoTelephoneActor ! Message("Hello, Leo, I'm Jack", this)
        receive {
            case response:String =>println("Jack telephone " + response)
        }
    }
}
val leo = new LeoTelephoneActor
val jack = new JackTelephoneActor