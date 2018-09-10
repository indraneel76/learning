''' Demonstrates polynomial regression with TensorFlow '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import tensorflow as tf

# Random input values
N = 40
x = tf.random_normal([N])
a_real = tf.truncated_normal([N], mean=3.)
b_real = tf.truncated_normal([N], mean=-2.)
c_real = tf.truncated_normal([N], mean=-1.)
d_real = tf.truncated_normal([N], mean=1.)
y = a_real * tf.pow(x, 3) + b_real * tf.pow(x, 2) + c_real * x + d_real

# Variables
a = tf.Variable(tf.random_normal([]))
b = tf.Variable(tf.random_normal([]))
c = tf.Variable(tf.random_normal([]))
d = tf.Variable(tf.random_normal([]))

# Compute model and loss
model = a * tf.pow(x, 3) + b * tf.pow(x, 2) + c * x + d
loss = tf.reduce_mean(tf.pow(model - y, 2))

# Create optimizer
learn_rate = 0.01
num_epochs = 400
num_batches = N
optimizer = tf.train.GradientDescentOptimizer(learn_rate).minimize(loss)

# Initialize variables
init = tf.global_variables_initializer()

# Launch session
with tf.Session() as sess:
    sess.run(init)

    # Perform training
    for epoch in range(num_epochs):
        for batch in range(num_batches):
            sess.run(optimizer)

    # Display results
    print('a = ', sess.run(a))
    print('b = ', sess.run(b))
    print('c = ', sess.run(c))
    print('d = ', sess.run(d))
