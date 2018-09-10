''' Demonstrates deep learning using TensorFlow and fully connected layers '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import tensorflow as tf
import tensorflow.contrib.learn as learn

# Read MNIST data
dataset = learn.datasets.mnist.read_data_sets('MNIST-data', one_hot=True)

# Placeholders for MNIST images
img_holder = tf.placeholder(tf.float32, [None, 784])
lbl_holder = tf.placeholder(tf.float32, [None, 10])

# Layer settings
hid_nodes = 200
out_nodes = 10

# Define weights
w0 = tf.Variable(tf.random_normal([784, hid_nodes]))
w1 = tf.Variable(tf.random_normal([hid_nodes, hid_nodes]))
w2 = tf.Variable(tf.random_normal([hid_nodes, hid_nodes]))
w3 = tf.Variable(tf.random_normal([hid_nodes, out_nodes]))

# Define biases
b0 = tf.Variable(tf.random_normal([hid_nodes]))
b1 = tf.Variable(tf.random_normal([hid_nodes]))
b2 = tf.Variable(tf.random_normal([hid_nodes]))
b3 = tf.Variable(tf.random_normal([out_nodes]))

# Create layers
layer_1 = tf.add(tf.matmul(img_holder, w0), b0)
layer_1 = tf.nn.relu(layer_1)
layer_2 = tf.add(tf.matmul(layer_1, w1), b1)
layer_2 = tf.nn.relu(layer_2)
layer_3 = tf.add(tf.matmul(layer_2, w2), b2)
layer_3 = tf.nn.relu(layer_3)
out_layer = tf.matmul(layer_3, w3) + b3

# Compute loss
loss = tf.reduce_mean(
    tf.nn.softmax_cross_entropy_with_logits(
        logits=out_layer, labels=lbl_holder))

# Create optimizer
learning_rate = 0.01
num_epochs = 15
batch_size = 100
num_batches = int(dataset.train.num_examples/batch_size)
optimizer = tf.train.AdamOptimizer(learning_rate).minimize(loss)

# Initialize variables
init = tf.global_variables_initializer()

# Launch session
with tf.Session() as sess:
    sess.run(init)

    # Loop over epochs
    for epoch in range(num_epochs):

        # Loop over batches
        for batch in range(num_batches):
            img_batch, lbl_batch = dataset.train.next_batch(batch_size)
            sess.run(optimizer, feed_dict={img_holder: img_batch,
                lbl_holder: lbl_batch})

    # Determine success rate
    prediction = tf.equal(tf.argmax(out_layer, 1), tf.argmax(lbl_holder, 1))
    success = tf.reduce_mean(tf.cast(prediction, tf.float32))
    print('Success rate: ', sess.run(success,
        feed_dict={img_holder: dataset.test.images,
            lbl_holder: dataset.test.labels}))
