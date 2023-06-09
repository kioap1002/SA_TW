import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib as mpl
import matplotlib.pyplot as plt

data = pd.read_csv("111年度A1交通事故資料.csv")
# print(data['死亡受傷人數'])
# data2 = data
death = data['死亡受傷人數']

data2 = pd.DataFrame({'發生年度':[],'發生月份':[],'死亡人數':[],'受傷人數':[]})

data2['發生年度'] = data['發生年度']
data2['發生月份'] = data['發生月份']
# data['死亡受傷人數'] = data['死亡受傷人數'].apply(lambda x: int(x.split(';')[0].strip('死亡')))
data2['死亡人數'] = data['死亡受傷人數'].apply(lambda x: int(x.split(';')[0].strip('死亡')) if isinstance(x, str) else x)
data2['受傷人數'] = data['死亡受傷人數'].apply(lambda x: int(x.split(';')[1].strip('受傷')) if isinstance(x, str) else x)
# print(data['死亡受傷人數'])
print(data2)

data_group = data2.groupby('發生月份')
print(data_group.get_group(2))
