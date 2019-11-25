import pandas as pd
import numpy as np 
from pandas import ExcelWriter
from pandas import ExcelFile
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import accuracy_score
df = pd.read_excel('merged1.xlsx')
df.columns =['time','mx','my','mz','gx','gy','gz','ax','ay','az','target']

#Duplicating the data frame
df1 = df
#print(df1.shape)
#print(df1.describe(include = 'all'))
#preprocessing

le = preprocessing.LabelEncoder()
mxc = le.fit_transform(df.mx)
myc = le.fit_transform(df.my)
mzc = le.fit_transform(df.mz)
gxc = le.fit_transform(df.gx)
gyc = le.fit_transform(df.gy)
gzc = le.fit_transform(df.gz)

axc = le.fit_transform(df.ax)
ayc = le.fit_transform(df.ay)
azc = le.fit_transform(df.az)

df1['mxc'] = mxc
df1['myc'] = myc
df1['mzc'] = mzc
df1['gxc'] = gxc
df1['gyc'] = gyc
df1['gzc'] = gzc
df1['axc'] = axc
df1['ayc'] = ayc
df1['azc'] = azc
df1['targets'] = df.target
dummy = ['mx','my','mz','gx','gy','gz','ax','ay','az','target']
df1 = df1.drop(dummy,axis=1)
print(df1.head())
print(df1.dtypes)


#Normalization
numf = ['mxc','myc','mzc','gxc','gyc','gzc','axc','ayc','azc']
scaledf={}
for each in numf:
    mean,std = df1[each].mean(),df1[each].std()
    scaledf[each]=[mean,std]
    df1.loc[:,each]=(df1[each]-mean)/std


writer = ExcelWriter('newdata.xlsx')
df1.to_excel(writer,'Sheet1',index=False)
writer.save()



