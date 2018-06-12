#-*- coding: utf-8 -*-
import sys
import os
import re
import time
from operator import add, sub, abs # 运算函数
import random
import itertools
import numpy as np
import pandas as pd
from scipy import stats
import collections
from collectios import Counter
import socket
from math import sqrt, log, pow, ceil
# 这里不需要sklearn, tensorflow, keras这样在单机中使用的的包，因为是在pyspark中，机器学习分布式算法在pyspark.mllib中调用
# pyspark.sql模块
from pyspark.sql import *
from pyspark.sql import SparkSession
from pyspark.sql import Row
from pyspark.sql import DataFrameWriter
from pyspark.sql.types import *
from pyspark.sql.functions import * #常见的包含mean, std, explode, split, window
# pyspark.ml模块
from pyspark.ml import Pipeline
from pyspark.ml.feature import * # OneHotEncoder, StringIndexer, VectorIndexer, HashingTF, Tokenizer
from pyspark.ml.classification import * # LR, NB, RF, MPC, OneVsRest
from pyspark.ml.evaluation import *
from pyspark.ml.tuning import * # CrossValidator, ParamGridBuilder
from pyspark.ml.linalg import Vectors,VectorUDT
# pyspark.mllib模块
from pyspark.mllib.recommendation import *
from pyspark.mllib.stat import Statistics
from pyspark.mllib.util import MLUtils
# from pyspark import SparkContext
# sc = SparkContext(appName="Binzhou_Test2")
spark = SparkSession.builder \
    .appName("BinZhou_Test") \
    .config('log4j.rootCategory',"WARN") \
    .enableHiveSupport() \
    .getOrCreate()
sc = spark.sparkContext