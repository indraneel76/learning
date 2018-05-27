import time
def banner(message,border="-"):
    line=border * len(message)
    print(line)
    print(message)
    print(line)

banner("Hello how are you ")
banner("I am fine","*")


print(time.ctime())

def show_time(message,time=time.ctime()):
    print(message)
    print(time)

show_time("how are you ")
show_time("how are you ")
show_time("how are you ")
show_time("how are you ")
show_time("how are you ")

'''dynamic type'''

def add (a,b):
    print (a+b)

add("hello","world")
add(1,2)
add([1,2],[3,4])

'''python name scope
Local
Enclosing
Global
Built-in
'''
'''demonstrating scoping'''
count=0
def show_count():
    print("count = ",count)

def set_count(c):
    global count
    count=c

    show_count()
    set_count(10)
    show_count()

print(dir(show_count))