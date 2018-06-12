## 反向传播算法入门资源索引  [引用自](http://www.52nlp.cn/%E5%8F%8D%E5%90%91%E4%BC%A0%E6%92%AD%E7%AE%97%E6%B3%95%E5%85%A5%E9%97%A8%E8%B5%84%E6%BA%90%E7%B4%A2%E5%BC%95)

1、一切从维基百科开始，大致了解一个全貌：
[反向传播算法](https://zh.wikipedia.org/wiki/%E5%8F%8D%E5%90%91%E4%BC%A0%E6%92%AD%E7%AE%97%E6%B3%95) [Backpropagation](https://en.wikipedia.org/wiki/Backpropagation)

2、拿起纸和笔，再加上ipython or 计算器，通过一个例子直观感受反向传播算法：
[A Step by Step Backpropagation Example](https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/)

3、再玩一下上篇例子对应的200多行Python代码: [neural-network.py](neural-network.py)

4、有了上述直观的反向传播算法体验，可以从1986年这篇经典的论文入手了：[Learning representations by back-propagating errors](https://www.iro.umontreal.ca/~vincentp/ift3395/lectures/backprop_old.pdf)

5、如果还是觉得晦涩，推荐读一下["Neural Networks and Deep Learning"](http://neuralnetworksanddeeplearning.com/index.html)这本深度学习在线书籍的第二章：[How the backpropagation algorithm works](http://neuralnetworksanddeeplearning.com/chap2.html)

6、或者可以通过油管看一下这个神经网络教程的前几节关于反向传播算法的视频: [Neural Network Tutorial](https://www.youtube.com/playlist?list=PL29C61214F2146796)

7、[hankcs](https://weibo.com/hankcs?ssl_rnd=1511493475.2063&is_hot=1) 同学对于上述视频和相关材料有一个解读: [反向传播神经网络极简入门](http://www.hankcs.com/ml/back-propagation-neural-network.html)

8、这里还有一个比较简洁的数学推导：[Derivation of Backpropagation](https://www.cs.swarthmore.edu/~meeden/cs81/s10/BackPropDeriv.pdf)

9、[神牛gogo](https://weibo.com/xitike) 同学对反向传播算法原理及代码解读：[神经网络反向传播的数学原理](https://zhuanlan.zhihu.com/p/22473137)

10、关于反向传播算法，更本质一个解释：自动微分反向模式（Reverse-mode differentiation ）[Calculus on Computational Graphs: Backpropagation](http://colah.github.io/posts/2015-08-Backprop/)


## 梯度下降三种方式

 - Batch gradient descent: Use all examples in each iteration
 - Stochastic gradient descent: Use 1 example in each iteration
 - Mini-batch gradient descent: Use b example in each iteration
