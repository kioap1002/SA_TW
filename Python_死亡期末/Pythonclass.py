import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
count = 0
A1 = pd.read_csv('111年度A1交通事故資料.csv', encoding='UTF8')
A1_g = A1.groupby('事故類型及型態子類別名稱')
A1_gk = A1_g.groups.keys()
for ckey in A1_gk:
    count+=1
type_S = np.zeros([count])

for i in range(count):
    for tS in A1_g.get_group(A1_gk[i]) :
        type_S[i] += 1
count = 0
p1 = plt.pie(type_S,
             radius=1.5,
             labels=A1_gk, 
             autopct='%.lf%%')
############
A1_g = A1.groupby('肇因研判子類別名稱-主要')
A1_gk = A1_g.groups.keys()
for ckey in A1_gk:
    count+=1
reason_S = np.zeros([count])
for i in range(count):
    for rS in A1_g.get_group(A1_gk[i]) :
        reason_S[i] += 1
count = 0
p2 = plt.pie(reason_S,
             radius=1.5,
             labels=A1_gk, 
             autopct='%.lf%%')
############
A1_g = A1.groupby('當事者區分-類別-大類別名稱-車種')
A1_gk = A1_g.groups.keys()
for ckey in A1_gk:
    count+=1
carType = np.zeros([count])
for i in range(count):
    for rS in A1_g.get_group(A1_gk[i]) :
        carType[i] += 1
count = 0
p3 = plt.pie(carType,
             radius=1.5,
             labels=A1_gk, 
             autopct='%.lf%%')
p1.show()
p2.show()
p3.show()