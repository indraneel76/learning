'''Execute a TensorFlow experiment in the cloud'''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import argparse
import os
import tensorflow as tf

# Set parameters
image_dim = 28
hidden_layers = [128, 32]
num_labels = 10

# Function to parse TFRecords
def tf_parser(record):
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

# Train the estimator
def read_data(data_file, batch_size):
    def input_func(params):
        dataset = tf.data.TFRecordDataset(data_file)
        dataset = dataset.map(tf_parser).repeat().batch(batch_size)
        image, label = dataset.make_one_shot_iterator().get_next()
        return {'pixels': image}, label
    return input_func

# Create the estimator
def create_estimator(hidden_layers, num_labels, conf):
    column = tf.feature_column.numeric_column('pixels',
        shape=[image_dim * image_dim])
    return tf.estimator.DNNClassifier(hidden_layers, [column],
        n_classes=num_labels, config=conf)

# Create experiment
def create_experiment(conf, params):
    train_file = os.path.join(params.data_dir, 'mnist_train.tfrecords')
    test_file = os.path.join(params.data_dir, 'mnist_test.tfrecords')
    return tf.contrib.learn.Experiment(
        estimator=create_estimator(params.hidden_layers,
            params.num_labels, conf),
        train_input_fn=read_data(train_file, params.train_batch_size),
        eval_input_fn=read_data(test_file, params.eval_batch_size),
        train_steps=params.num_steps)

# Process arguments
if __name__ == '__main__':
    parser = argparse.ArgumentParser()

    # Training arguments
    parser.add_argument(
        '--data_dir',
        help='Directory/bucket containing training/evaluation data',
        required=True
    )
    parser.add_argument(
        '--train_batch_size',
        help='Batch size for training steps',
        type=int,
        default=80
    )
    parser.add_argument(
        '--num_epochs',
        help='Maximum number of training epochs',
        type=int,
        default=1
    )
    parser.add_argument(
        '--train_steps',
        help='Number of training steps per epoch',
        type=int,
        default=8000
    )

    # Evaluation arguments
    parser.add_argument(
        '--eval_batch_size',
        help='Batch size for evaluation',
        type=int,
        default=80
    )
    parser.add_argument(
        '--eval-steps',
        help='Number of evalution steps',
        default=100,
        type=int
    )
    parser.add_argument(
        '--min-eval-frequency',
        help='Minimum number of training steps between evaluations',
        default=None,
        type=int
    )

    # Output arguments
    parser.add_argument(
        '--job-dir',
        help='Directory/bucket for storing output data',
        required=True
    )
    args = parser.parse_args()
    arguments = args.__dict__
    
    # Configure the running experiment
    run_config = tf.contrib.learn.RunConfig(model_dir=arguments['job_dir'])
    hparams = tf.contrib.training.HParams(
        num_labels=num_labels,
        data_dir=arguments['data_dir'],
        num_steps=arguments['train_steps'],
        train_batch_size=arguments['train_batch_size'],
        eval_batch_size=arguments['eval_batch_size'],
        hidden_layers=hidden_layers)
    tf.contrib.learn.learn_runner.run(
        experiment_fn=create_experiment,
        run_config=run_config,
        schedule="train_and_evaluate",
        hparams=hparams
    )