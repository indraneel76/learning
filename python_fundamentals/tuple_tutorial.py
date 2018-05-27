'''tuples are immutable sequence. objects later can't be added or deleted'''
t= ("norway",4.93,3)
print(type(t))
print(t)
print(t[2])
print(len(t))
for item in t:
    print(item)

'''concatenate tuples'''
t=t+(33816.0,256)
print(t)
t=t*3
print(t)

'''nested tuples'''
a=((220,34),5,(444,556),'f','test',445.33)
print(a)
print(a[2][1])
'''
single element tuple
'''
t =(3434,)
print(type(t))
'''empty tuple'''
e=()

p=1,2,33,454,4,'a',56,4534
print(p)
print(type(p))

def minmax(items):
    return min(items),max(items)
lower,upper = minmax([34,23,45,67,34,23,6,56565,23])
print("lower ",lower)
print("upper ",upper)

a='jelly'
b='bean'
a,b=b,a
print(a,b)

t=tuple([561,1105,1729,2465])
print("t",t)

t =tuple("carmichael")
print(t)
print('m' in t)
print ('m' not in t)