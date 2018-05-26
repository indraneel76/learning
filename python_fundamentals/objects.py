a=496
print(id(a))
b=1729
print(id(b))
b=a
print(id(b))
print(a==b)
print(id(a)==id(b))
print ( a is b)
c=496
print(id(c))
print (a==c)
print(id(a)==id(c))

print( a is None)
print("===============================")
p= [4,7,11]
print(id(p))
q=[4,7,11]
print(id(q))
print(p==q)
print (p is q)
def modify(k):
    k.append(39)
    print("k = ",k)
    print("id k ",id(k))

modify(p)
print("p = ",p)
print(" id p = ",id(p))

print("==============================")
def f(d):
    return d

c=[6,10,16]
print("id(c) = ",id(c))
e =f(c)
print("id(e) = ",id(e))

print (c is e)