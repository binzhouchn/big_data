# -*- coding: utf-8 -*-
import os
cmd="source change_spark_version spark-2.1.0 && /home/bigdata/software/spark-2.1.0.7-bin-2.4.0.10/bin/spark-submit  --master yarn-cluster  --num-executors 50 --executor-memory 21g --driver-memory 21g  demo.py"
os.system(cmd)