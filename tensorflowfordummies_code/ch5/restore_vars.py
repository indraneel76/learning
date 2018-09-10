''' Demonstrates retrieval of variable values from a file '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import os
import tensorflow as tf

# Create session
with tf.Session() as sess:

    # Load stored graph into current graph
    saver = tf.train.import_meta_graph('output.meta')

    # Restore variables into graph
    saver.restore(sess, os.getcwd() + '/output')

    # Display value of variable
    print('Variable value: ', sess.run('x_result:0'))
