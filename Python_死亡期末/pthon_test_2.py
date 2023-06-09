import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

count = 0
A1 = pd.read_csv('111年度A1交通事故資料.csv', encoding='UTF8')
# A1_g = A1.groupby('事故類型及型態子類別名稱')
# A1_gk = list(A1_g.groups.keys())

# for ckey in A1_gk:
#     count += 1

# type_S = np.zeros([count])

# for i in range(count):
#     type_S[i] = len(A1_g.get_group(A1_gk[i]))

# count = 0
# p1 = plt.pie(type_S,
#              radius=1.5,
#              labels=A1_gk,
#              autopct='%.lf%%')

# ############

A1_g = A1.groupby('肇因研判子類別名稱-主要')
A1_gk = list(A1_g.groups.keys())

for ckey in A1_gk:
    count += 1

reason_S = np.zeros([count])

for i in range(count):
    reason_S[i] = len(A1_g.get_group(A1_gk[i]))

count = 0
p2 = plt.pie(reason_S,
             radius=1.5,
             labels=A1_gk,
             autopct='%.lf%%')

############

# 設定字型
plt.rcParams['font.family'] = 'Arial'
# 設定中文字型
plt.rcParams['font.sans-serif'] = ['Microsoft JhengHei']  # 使用微軟正黑體字型

plt.show()
