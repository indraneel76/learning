''' Demonstrates deep learning using fully connected layers and tuning '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import tensorflow as tf
import tensorflow.contrib.learn as learn
from tensorflow.contrib.layers import fully_connected

# Read MNIST data
dataset = learn.datasets.mnist.read_data_sets('MNIST-data', one_hot=True)

# Placeholders for MNIST images
img_holder = tf.placeholder(tf.float32, [None, 784])
lbl_holder = tf.placeholder(tf.float32, [None, 10])
train = tf.placeholder(tf.bool)

# Layer settings
hid_nodes = 200
out_nodes = 10
keep_prob = 0.5

# Create layers
with tf.contrib.framework.arg_scope(
    [fully_connected],
    normalizer_fn=tf.contrib.layers.batch_norm,
    normalizer_params={'is_training': train}):
        layer1 = fully_connected(img_holder, hid_nodes, scope='layer1')
        layer1_drop = tf.layers.dropout(layer1, keep_prob, training=train)
        layer2 = fully_connected(layer1_drop, hid_nodes, scope='layer2')
        layer2_drop = tf.layers.dropout(layer2, keep_prob, training=train)
        layer3 = fully_connected(layer2_drop, hid_nodes, scope='layer3')
        layer3_drop = tf.layers.dropout(layer3, keep_prob, training=train)
        out_layer = fully_connected(layer3_drop, out_nodes,
            activation_fn=None, scope='layer4')

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
                lbl_holder: lbl_batch, train: True})

    # Determine success rate
    prediction = tf.equal(tf.argmax(out_layer, 1), tf.argmax(lbl_holder, 1))
    success = tf.reduce_mean(tf.cast(prediction, tf.float32))
    print('Success rate: ', sess.run(success,
        feed_dict={img_holder: dataset.test.images,
            lbl_holder: dataset.test.labels, train: False}))
