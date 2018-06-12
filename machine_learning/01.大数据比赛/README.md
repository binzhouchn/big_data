## 一级目录

[**1.一折blending**](#一折blending)

[**2. 二折blending**](#二折blending)

[**3. 模型融合**](#模型融合)

[**4. 规则**](#规则)

[**5. xgb调参**](#xgb调参)

[**6. WOE, IV(information value)来选取离散特征重要性**](#woe_iv选取离散特征重要性)

[**7. xxx用法**](#xxx用法)

---

### 一折blending 

一折blending相当于简单的模型融合stacking
将X的预测概率值（或0,1值）作为特征加入X，然后跑个最终模型（一般用LR跑会比较快）

### 二折blending

将X等分成两份，第一份训练出来的模型用于X2预测，得到的预测概率加入到X1
第二份训练出来的模型用于X1预测，得到的预测概率加入到X2
然后和一折blending一样跑最终模型

### 模型融合

模型融合一般有两种方法，stacking和blending，其实这两种差不多。

1.最简单的就是几个模型跑完（RF模型取不同的seed就会出不同的结果有小幅波动）
预测出的0,1值用majority vote

2.其次用几个模型得到的预测概率进行聚合：
比如取max，取平均等等（需要最后选个阈值）

3.对于用AUC准则的，将几个模型得到的结果，每个新特征的列方向进行rank排序然后算个列方向的值，再放入LR中跑个最终模型。

### 规则

（1）有时候用规则效果反而更好，都不需要有设置验证集
（2）找强特征，看分布

### xgb调参
```python
import xgboost as xgb

params = {
        'objective': 'binary:logistic',
        'eta': 0.01,
        'colsample_bytree': 0.887,
        'min_child_weight': 2,
        'max_depth': 10,
        'subsample': 0.886,
        'alpha': 10,
        'gamma': 30,
        'lambda': 50,
        'verbose_eval': True,
        'nthread': 8,
        'eval_metric': 'auc',
        'scale_pos_weight': 10,
        'seed': 201703,
        'missing': -1
        }

 params = {
'colsample_bytree': 0.5041920450812235,
'gamma': 0.690363148214239,
'learning_rate': 0.01,
'max_depth': 8,
'min_child_weight': 9,
'nthread': 1,
'objective': 'binary:logistic',
'reg_alpha': 4.620727573976632,
'reg_lambda': 1.9231173132006631,
'scale_pos_weight': 5,
'seed': 2017,
'subsample': 0.5463188675095159
}

model = xgb.BoostClassifier(加参数即params=)       (这个是sklearn框架)

xgb_m1 = model.fit()

xgb_m1.predict  / xgb_m1.predict_proba

----------------

xgb.train() 这个是xgb的原生态框架

需要将数据 data_t = xgb.DMatrix(X, label=y)

xgb_m2 = xgb.train(params, data_t)

xgb_m2.predict(xgb.DMatrix(test))  这个得到的就是概率【一列 n*1】，而sklean中predict得到的是0和1,predict_proba得到的是概率【两列 n*2，看后面一列>0.5预测为1】

还有个参数evals可以加验证集，early_stopping_rounds=1000 最高迭代1000次，如果验证集误差上升就停止
```

### WOE_IV选取离散特征重要性

注：只针对类别或离散变量，连续性变量可以先进行分箱操作（用R或者python跑DT然后看节点来确定分割点）[IV详解 CSDN](http://blog.csdn.net/kevin7658/article/details/50780391)

### xxx用法
