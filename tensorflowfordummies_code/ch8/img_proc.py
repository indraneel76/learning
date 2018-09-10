''' Demonstrates image processing with TensorFlow '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import numpy as np
import tensorflow as tf

# Load and pre-process PNG data
queue = tf.train.string_input_producer(['input_aircraft.png'])
reader = tf.WholeFileReader()
_, png_data = reader.read(queue)
orig_tensor = tf.image.decode_png(png_data)
img_tensor = tf.reshape(orig_tensor, [-1, 1, 232, 706])
img_tensor = tf.transpose(img_tensor, [0, 2, 3, 1])
img_tensor = tf.image.convert_image_dtype(img_tensor, tf.float32)

# Remove noise using a box filter
conv_filter = np.zeros([3, 3, 1, 1])
conv_filter[0, 0, :, :] = 0.1111
conv_filter[0, 1, :, :] = 0.1111
conv_filter[0, 2, :, :] = 0.1111
conv_filter[1, 0, :, :] = 0.1111
conv_filter[1, 1, :, :] = 0.1111
conv_filter[1, 2, :, :] = 0.1111
conv_filter[2, 0, :, :] = 0.1111
conv_filter[2, 1, :, :] = 0.1111
conv_filter[2, 2, :, :] = 0.1111
img_tensor = tf.nn.conv2d(img_tensor, conv_filter, [1, 1, 1, 1], 'SAME')

# Increase contrast
img_tensor = tf.reshape(img_tensor, [232, 706, 1])
img_tensor = tf.image.adjust_contrast(img_tensor, 0.8)

# Flip horizontal
img_tensor = tf.image.flip_left_right(img_tensor)

# Create summary data and FileWriter
img_tensor = tf.reshape(img_tensor, [1, 232, 706, 1])
img_tensor = tf.image.convert_image_dtype(img_tensor, tf.uint8)
summary_op = tf.summary.image('Output', img_tensor)
file_writer = tf.summary.FileWriter('log')

# Store result to PNG
img_tensor = tf.reshape(img_tensor, [232, 706, 1])
img_tensor = tf.image.encode_png(img_tensor)

with tf.Session() as sess:
    coord = tf.train.Coordinator()
    threads = tf.train.start_queue_runners(coord=coord)

    # Execute session
    output_data, summary = sess.run([img_tensor, summary_op])

    # Write output PNG data to file
    output_file = open('output_aircraft.png', 'wb+')
    output_file.write(output_data)
    output_file.close()

    # Print summary data
    file_writer.add_summary(summary)
    file_writer.flush()

    # Wait for threads to terminate
    coord.request_stop()
    coord.join(threads)
