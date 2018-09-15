import numpy as np
a = np.array([[1,2,3],[4,5,6]])
print (a.shape)
# this resizes the ndarray
import numpy as np

a = np.array([[1,2,3],[4,5,6]])
a.shape = (3,2)
print (a)

a = np.array([[1,2,3],[4,5,6]])
b = a.reshape(3,2)
print (b)

# an array of evenly spaced numbers
import numpy as np
a = np.arange(24)
print (a)

# this is one dimensional array
import numpy as np
a = np.arange(24)
print(a.ndim)

# now reshape it
b = a.reshape(2,4,3)
print (b)
# b is having three dimensions


# dtype of array is now float32 (4 bytes)
import numpy as np
x = np.array([1,2,3,4,5], dtype = np.float32)
print (x.itemsize)

x = np.array([1,2,3,4,5])
print (x.flags)