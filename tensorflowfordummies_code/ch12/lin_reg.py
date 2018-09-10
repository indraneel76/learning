''' Demonstrate usage of TensorFlow's LinearRegressor '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import numpy as np
import tensorflow as tf

# Read dataset from CSV file
dataset = tf.contrib.learn.datasets.base.load_csv_with_header(
    filename='lin_reg.csv', target_dtype=np.float32,
    features_dtype=np.float32, target_column=1)

# Create feature column containing x-coordinates
column = tf.feature_column.numeric_column('x', shape=[1])

# Create the LinearRegressor
lin_reg = tf.estimator.LinearRegressor([column])

# Train the estimator
train_input = tf.estimator.inputs.numpy_input_fn(
    x={'x': np.array(dataset.data)},
    y=np.array(dataset.target), shuffle=True, num_epochs=50000)
lin_reg.train(train_input)

# Make two predictions
predict_input = tf.estimator.inputs.numpy_input_fn(
    x={'x': np.array([1.9, 1.4], dtype=np.float32)},
    num_epochs=1, shuffle=False)
results = lin_reg.predict(predict_input)

# Display the results
for value in results:
    print(value['predictions'])
