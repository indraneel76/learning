import numpy as np
x = np.empty([3,2], dtype = int)
print (x)

# array of five zeros. Default dtype is float
import numpy as np
x = np.zeros(5)
print (x)

# custom type
import numpy as np
x = np.zeros((2,2), dtype = [('x', 'i4'), ('y', 'i4')])
print (x)