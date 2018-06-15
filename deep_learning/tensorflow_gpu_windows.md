环境：<br>
OS：Windows 7，64 bit；<br>
显卡：NVIDIA GeForce GT 610，显卡查看方法：计算机【设备管理器】⇒ 【显示适配器】<br>
Python 的版本，注意只能为 3.5，并非越高越好；<br>
CUDA，9.0；<br>
cudnn，7.0.5；<br>
tensorflow-gpu，1.7.0；<br>

### 1. 首先在windows中查看显卡类型
可以右击我的电脑->设备管理器->显示适配器查看自己的显卡

必须要独立显卡，而且是NVIDIA卡

### 2. 去NVIDIA驱动官网下载显卡对应的最新驱动

[下载地址](https://www.nvidia.com/Download/index.aspx?lang=en-us)

### 3. 下载完驱动进行安装， 安装完驱动后去控制面板->NVIDIA控制面板中查看最新驱动比如是cuda 9.2

### 4. 接着去NVIDIA CUDA官网下载对应的cuda版本，这里我下载了cuda9.0（一般驱动和cuda版本要对应，最新驱动的话对应最新的cuda就行了）。下载完后进行安装，安装是注意不要勾选Visual Studio Integration，Driver components也可以不用选

[下载地址](https://developer.nvidia.com/cuda-downloads)

(optional)安装完后需要把一些路径加入path，CUDA_PATH和CUDA_PATH_V9_0都会在安装的时候生成，自己添加<br>
1) 新建CUDA_SDK_PATH : C:\ProgramData\NVIDIA Corporation\CUDA Samples\v9.0<br>
2) 在path中添加%CUDA_PATH%\lib\Win32;%CUDA_SDK_PATH%\bin\Win64;%CUDA_SDK_PATH%\common\lib\x64;;C:\Program Files (x86)\NVIDIA Corporation\PhysX\Common

### 5. 然后去nvidia cudnn下载cudnn，cuda9.0对应的是cudnn7.0及以上，下载下来的zip包解压后把文件放入C:\Program Files\NVIDIA GPU Computing Toolkit\CUDA\v9.0相应的文件夹中bin,include,lib即可

[下载地址](https://developer.nvidia.com/rdp/cudnn-archive)

### 6. (optional，可能有用)安装DXSDK_Jun10.exe

[下载地址](https://www.microsoft.com/en-us/download/details.aspx?displaylang=en&id=6812)

### 7. 然后安装tensorflow gpu版

可以pip install tensorflow-gpu或者去
pypi搜索tensorflow gpu [官网下载](https://pypi.org/project/tensorflow-gpu/#history) <br>
我这里下载了tensorflow-gpu==1.7.0

### 8. 查看GPU使用情况

```bash
"C:\Program Files\NVIDIA Corporation\NVSMI\nvidia-smi.exe" -l 10
```

### refer
https://blog.csdn.net/XCCCCZ/article/details/80385448
https://blog.csdn.net/sb19931201/article/details/53648615
