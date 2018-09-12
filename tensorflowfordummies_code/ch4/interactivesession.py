import tensorflow as tf

from tensorflow import logging
logging.set_verbosity(tf.logging.INFO)
t1=tf.constant(1.2)
t2=tf.constant(3.5)
prod =tf.multiply(t1,t2)
sess =tf.InteractiveSession()
logging.info("product: %f ",prod.eval())