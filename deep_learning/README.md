# 深度学习

## 目录

[**0. 深度学习（蚂蚁笔记）**](https://leanote.com/note/5a2741edf40b55511b000000)

[**1. 在不同的划分上进行训练并测试**](1.train_test)

[**2. 反向传播算法入门资料链接**](2.backpropagation)

[**3. 手动构建浅、深神经网络**](3.nn_scratch)

[**4. CNN入门**](4.cnn)

[**5. RNN入门**](5.rnn)

[**6. 参数选择**](#参数选择)

## tensorflow gpu配置
[windows深度学习gpu配置(tensorflow)](tensorflow_gpu_windows.md)

---

### 参数选择

 - batch size：根据gpu大小定，一般越小最后效果会越好；但也不能太小，容易过拟合；32，64，128可以试试
 - kernel size：根据任务调，3，5，7等

