import pandas as pd
import numpy as np 
from pandas import ExcelWriter
from pandas import ExcelFile
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import accuracy_score


df1 = pd.read_excel('newdata.xlsx')

features = df1.values[1:,1:10]
labels = df1.values[1:,10]
features_train, features_test, labels_train, labels_test = train_test_split(features,labels,test_size=0.20,random_state=10)
clf = GaussianNB()
clf.fit(features_train,labels_train)
pred_labels = clf.predict(features_test)

print(accuracy_score(labels_test,pred_labels,normalize=True)*100)