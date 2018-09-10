''' Demonstrate TensorFlow experiments '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import tensorflow as tf

# Set parameters
batch_size = 80
image_dim = 28
hparams = tf.contrib.training.HParams(
    num_labels=10,
    batch_size=80,
    num_steps=8000,
    hidden_layers=[128, 32])

# Function to parse MNIST TFRecords
def parser(record):
    features = tf.parse_single_example(record,
        features={
          'images': tf.FixedLenFeature([], tf.string),
          'labels': tf.FixedLenFeature([], tf.int64),
        })
    image = tf.decode_raw(features['images'], tf.uint8)
    image.set_shape([image_dim * image_dim])
    image = tf.cast(image, tf.float32) * (1. / 255) - 0.5
    label = features['labels']
    return image, label

# Create the DNNClassifier
def create_estimator(hidden_layers, num_labels, conf):
    column = tf.feature_column.numeric_column('pixels',
        shape=[image_dim * image_dim])
    return tf.estimator.DNNClassifier(hidden_layers, [column],
        n_classes=num_labels, config=conf)

# Train the estimator
def train_func():
    dataset = tf.data.TFRecordDataset('mnist_train.tfrecords')
    dataset = dataset.map(parser).repeat().batch(batch_size)
    image, label = dataset.make_one_shot_iterator().get_next()
    return {'pixels': image}, label

# Test the estimator
def test_func():
    dataset = tf.data.TFRecordDataset('mnist_test.tfrecords')
    dataset = dataset.map(parser).batch(batch_size)
    image, label = dataset.make_one_shot_iterator().get_next()
    return {'pixels': image}, label

# Create experiment
def create_experiment(conf, params):
    return tf.contrib.learn.Experiment(
        estimator=create_estimator(params.hidden_layers,
            params.num_labels, conf),
        train_input_fn=train_func,
        eval_input_fn=test_func,
        train_steps=params.num_steps)

# Run experiment
run_config = tf.contrib.learn.RunConfig(model_dir='experiment_output')
tf.contrib.learn.learn_runner.run(
    experiment_fn=create_experiment,
    run_config=run_config,
    schedule='train_and_evaluate',
    hparams=hparams
)
