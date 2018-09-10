''' Shows how graphs can be created, executed, and written to files '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import os
import tensorflow as tf

# Enable logging
tf.logging.set_verbosity(tf.logging.INFO)

# Create tensors
t1 = tf.constant([1.2, 2.3, 3.4, 4.5])
t2 = tf.constant([5.6, 6.7, 7.8, 8.9])
t3 = tf.concat([t1, t2], 0)
t4 = tf.random_normal([8])
t5 = tf.tensordot(t3, t4, 1)

# Create operations to generate summary data
tf.summary.histogram('t1', t1)
tf.summary.histogram('t2', t2)
tf.summary.histogram('t3', t3)
tf.summary.histogram('t4', t4)
tf.summary.scalar('t5', t5)
merged_op = tf.summary.merge_all()

# Create FileWriter
file_writer = tf.summary.FileWriter('log', graph=tf.get_default_graph())

# Execute first graph
with tf.Session() as sess:

    # Execute the session
    dot_result, summary = sess.run([t5, merged_op])

    # Write the result to the log
    tf.logging.info('Result of dot product: %f', dot_result)

    # Print the summary data
    file_writer.add_summary(summary)
    file_writer.flush()

    # Obtain the GraphDef and write it to a file
    tf.train.write_graph(sess.graph, os.getcwd(), 'graph1.dat')

# Create second graph and make it default
graph = tf.Graph()
with graph.as_default():

    # Compute the average
    t6 = tf.random_uniform([8], 4.0, 8.0)
    t7 = tf.fill([8], 6.0)
    t8 = tf.reduce_mean(t6 + t7)

    # Execute first graph
    with tf.Session() as sess:

        # Execute the session
        sess.run(t8)

        # Obtain the GraphDef and write it to a file
        tf.train.write_graph(sess.graph, os.getcwd(), 'graph2.dat')
