# 深度学习

## 目录

[**0. 深度学习（蚂蚁笔记）**](https://leanote.com/note/5a2741edf40b55511b000000)

[**1. 深度学习国内外优秀课程**](#深度学习国内外优秀课程)

[**2. 在不同的划分上进行训练并测试**](1.train_test)

[**3. 反向传播算法入门资料链接**](2.backpropagation)

[**4. 手动构建浅、深神经网络**](3.nn_scratch)

[**5. CNN入门**](4.cnn)

[**6. RNN入门**](5.rnn)

[**7. 参数选择**](#参数选择)

## tensorflow gpu配置
[windows深度学习gpu配置(tensorflow)](tensorflow_gpu_windows.md)

---

### 深度学习国内外优秀课程

[CS2311n Convolutional Neural Networks for Visual Recognition](https://cs231n.github.io/)

[deeplearning.ai coursera](https://www.coursera.org/specializations/deep-learning)

[paddlepaddle](http://ai.baidu.com/paddlepaddle)

### 参数选择

#### batch size：根据gpu大小定，一般越小最后效果会越好；但也不能太小，容易过拟合；32，64，128可以试试<br>
 
 [如果数据集比较小，完全可以采用全数据集]<br>
   - 由全数据集确定的方向能够更好地代表样本总体，从而更准确地朝向极值所在的方向<br>
   - 针对性单独更新各权值(不太清楚啥意思) <br>
   
 [对于更大的数据集]<br>
   - 一次性载入内存会爆掉
   - 适当的batch size内存利用率提高了，大矩阵乘法的并行化效率提高<br>
   - 适当的batch size跑完一次 epoch（全数据集）所需的迭代次数减少，对于相同数据量的处理速度进一步加快<br>
   - 适当的batch size在一定范围内，一般来说 Batch_Size 越大，其确定的下降方向越准，引起训练震荡越小<br>
 
 [传送链接1](https://blog.csdn.net/s_sunnyy/article/details/65445197)<br>
 [传送链接2](https://blog.csdn.net/qq_20259459/article/details/53943413)
 
#### kernel size：根据任务调，3，5，7等

