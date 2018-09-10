''' Provides a simple example of TensorFlow training '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import os
import tensorflow as tf

# Define a trainable variable
x_var = tf.Variable(0., name='x_result')

# Define an untrainable variable to hold the global step
step_var = tf.Variable(0, trainable=False)

# Express loss in terms of the variable
loss = x_var * x_var - 4.0 * x_var + 5.0

# Find variable value that minimizes loss
learn_rate = 0.1
num_epochs = 40
optimizer = tf.train.GradientDescentOptimizer(learn_rate).minimize(loss, global_step=step_var)

# Initialize variables
init = tf.global_variables_initializer()

# Create the saver
saver = tf.train.Saver()

# Create summary data and FileWriter
summary_op = tf.summary.scalar('x', x_var)
file_writer = tf.summary.FileWriter('log', graph=tf.get_default_graph())

# Launch session
with tf.Session() as sess:
    sess.run(init)

    for epoch in range(num_epochs):
        _, step, result, summary = sess.run([optimizer, step_var, x_var, summary_op])
        print('Step %d: Computed result = %f' % (step, result))

        # Print summary data
        file_writer.add_summary(summary, global_step=step)
        file_writer.flush()

    # Store variable data
    saver.save(sess, os.getcwd() + '/output')
    print('Final x_var: %f' % sess.run(x_var))
