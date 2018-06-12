## 在不同的划分上进行训练并测试

以cat app example为例：

data from webpages 200,000
data from mobile app 10,000

我们想要对mobile的样本预测的比较好，则需要划分测试集训练集分别为：
> train: web 200,000 mobile 5,000<br>
> dev: mobile 2,500<br>
> test: mobile 2,500<br>


