''' Demonstrate cluster computing '''

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import subprocess
import tensorflow as tf

# Session hook to print output
class OutputHook(tf.train.SessionRunHook):

    def before_run(self, run_context):
        return tf.train.SessionRunArgs(pi_var)

    def after_run(self, run_context, run_values):
        print('Pi approximation:', run_values.results)

# Define a cluster with two jobs and five tasks
spec = tf.train.ClusterSpec({'worker':
    ['localhost:31415', 'localhost:31416', 'localhost:31417', 'localhost:31418']})

# Perform task-dependent operations
flags = tf.flags
flags.DEFINE_string('task', '', '')
if not flags.FLAGS.task:

    # Launch the worker processes
    subprocess.Popen('python cluster.py --task=0', stderr=subprocess.STDOUT)
    subprocess.Popen('python cluster.py --task=1', stderr=subprocess.STDOUT)
    subprocess.Popen('python cluster.py --task=2', stderr=subprocess.STDOUT)
    subprocess.Popen('python cluster.py --task=3', stderr=subprocess.STDOUT)

else:
    N = 10
    num_workers = float(spec.num_tasks('worker') - 1)
    delta_x = float(1)/float(N * num_workers)
    task_index = int(flags.FLAGS.task)

    # Create server
    server = tf.train.Server(spec, job_name='worker', task_index=task_index)

    with tf.device('/job:worker/task:0'):
        pi_var = tf.Variable(0., dtype=tf.float32)

    with tf.device('/job:worker/task:1'):
        for i in range(N):
            x_i = delta_x * (i * num_workers + 0.5)
            pi_var += 4 * delta_x/(1 + x_i * x_i)

    with tf.device('/job:worker/task:2'):
        for i in range(N):
            x_i = delta_x * (i * num_workers + 1.5)
            pi_var += 4 * delta_x/(1 + x_i * x_i)

    with tf.device('/job:worker/task:3'):
        for i in range(N):
            x_i = delta_x * (i * num_workers + 2.5)
            pi_var += 4 * delta_x/(1 + x_i * x_i)

    # Launch session
    output_hook = OutputHook()
    with tf.train.MonitoredTrainingSession(master='grpc://localhost:31415',
            is_chief=(task_index == 0), chief_only_hooks=[output_hook]) as sess:
        sess.run(pi_var)
