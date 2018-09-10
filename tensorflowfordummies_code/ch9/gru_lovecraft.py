''' Demonstrates text prediction with GRUs '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import collections
import string
import sys
import time

import tensorflow as tf
import numpy as np

# Split text into words
python3 = sys.version_info[0] == 3
with open('lovecraft.txt', 'r') as f:
    input_str = f.read().lower()
    if python3:
        trans = input_str.maketrans('', '', string.punctuation)
        input_str = input_str.translate(trans)
    else:
        input_str = input_str.decode("utf-8").translate(None, string.punctuation)
    words = input_str.split()
    num_words = len(words)

# Convert words to values
word_freq = collections.Counter(words).most_common()
vocab_size = len(word_freq)
lookup = dict()
for word, _ in word_freq:
    lookup[word] = len(lookup)
input_vals = np.asarray([[lookup[str(word)]] for word in words])
input_vals = input_vals.reshape(-1,)

# Set values
input_size = 6
batch_size = 10
num_hidden = 600

# Placeholders
input_holder = tf.placeholder(tf.float32, [batch_size, input_size])
label_holder = tf.placeholder(tf.float32, [batch_size, vocab_size])

# Reshape input and feed to RNN
cell = tf.nn.rnn_cell.GRUCell(num_hidden)
outputs, _ = tf.nn.static_rnn(cell, [input_holder], dtype=tf.float32)

# Compute loss
weights = tf.Variable(tf.random_normal([num_hidden, vocab_size]))
biases = tf.Variable(tf.random_normal([vocab_size]))
model = tf.matmul(outputs[-1], weights) + biases
loss = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(logits=model, labels=label_holder))

# Create optimizer and check result
optimizer = tf.train.AdagradOptimizer(0.1).minimize(loss)
check = tf.equal(tf.argmax(model, 1), tf.argmax(label_holder, 1))
correct = tf.reduce_sum(tf.cast(check, tf.float32))

# Execute the graph
start_time = time.time()
with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())
    input_block = np.empty([batch_size, input_size])
    label_block = np.empty([batch_size, vocab_size])

    step = 0
    num_correct = 0.
    accuracy = 0.
    while accuracy < 95.:
        for i in range(batch_size):
            offset = np.random.randint(num_words-(input_size+1))
            input_block[i, :] = input_vals[offset:offset+input_size]
            label_block[i, :] = np.eye(vocab_size)[input_vals[offset+input_size]]
        _, corr = sess.run([optimizer, correct],
            feed_dict={input_holder: input_block, label_holder: label_block})
        num_correct += corr
        accuracy = 100*num_correct/(1000*batch_size)
        if step % 1000 == 0:
            print('Step', step, '- Accuracy =', accuracy)
            num_correct = 0
        step += 1

# Display timing result
duration = time.time() - start_time
print('Time to reach 95% accuracy: {:.2f} seconds'.format(duration))
