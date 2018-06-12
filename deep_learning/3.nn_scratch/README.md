## We will now build a simple Neural Network from scratch. 

[**1. 简单的神经网络代码**](1.nn_scratch.ipynb)

The only external library we will be using is Autograd. Autograd is an automatic differentiation library that will allow us to compute
gradients for arbitrary functions written with Numpy.

[**1.5 吴恩达测验作业及答案**](http://m.blog.csdn.net/justry24)

 - http://m.blog.csdn.net/justry24/article/details/77915062
 - http://m.blog.csdn.net/justry24/article/details/78128646
 - http://m.blog.csdn.net/justry24/article/details/78148991
 - http://m.blog.csdn.net/justry24/article/details/78152213
 - http://m.blog.csdn.net/justry24/article/details/78189307
 - http://m.blog.csdn.net/justry24/article/details/78283031

[**2. 吴恩达shallow神经网络from scratch**](2.shallow_neural_network)

```
# 无论浅层还是深层神经网络通用方法

1. Initialize parameters / Define hyperparameters
2. Loop for num_iterations:
    a. Forward propagation
    b. Compute cost function
    c. Backward propagation
    d. Update parameters (using parameters, and grads from backprop) 
4. Use trained parameters to predict labels
```

其中bp的求导证明在bp文件夹gd_proof文件夹中

[**3. 吴恩达deep神经网络from scratch**](3.deep_neural_network)

[**4. 吴恩达改善深度神经网络**](4.improve_DNN)

[**5. 吴恩达结构化机器学习项目**](http://m.blog.csdn.net/justry24/article/details/78189307)

 - An **end-to-end approach**: Input an image (x) to a neural network and have it directly learn a mapping to make a prediction as to whether there's a red light and/or green light(y). [*it has distinct steps for the input end and the output end*]
