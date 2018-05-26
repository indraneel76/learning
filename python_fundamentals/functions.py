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