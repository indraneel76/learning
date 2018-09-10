from tensorflow.examples.tutorials.mnist import input_data
mnist=input_data.read_data_sets("/tmp/data/",one_hot=True)
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt

plt.imshow(np.reshape(mnist.train.images[8],[28,28]),cmap='gray')
#plt.show()

learning_rate=0.1
num_steps=500
batch_size=128
display_step=10

n_hidden_1=10
n_hidden_2=10
num_input=784
num_classes=10

X=tf.placeholder("float",[None,num_input])
Y=tf.placeholder("float",[None,num_classes])

weights={
    'h1':tf.Variable(tf.random_normal([num_input,n_hidden_1])),
    'h2':tf.Variable(tf.random_normal([n_hidden_1,n_hidden_2])),
    'out':tf.Variable(tf.random_normal([n_hidden_2,num_classes]))

        }
biases={

    'b1':tf.Variable(tf.random_normal([n_hidden_1])),
    'b2':tf.Variable(tf.random_normal([n_hidden_2])),
    'out':tf.Variable(tf.random_normal([num_classes]))

}


#create Model

def neural_net(x):
    #Hidden fully connected layer with 10 neurons
    layer_1=tf.add(tf.matmul(x,weights['h1']),biases['b1'])

    #Hidden fully connected layer with 10 neurons
    layer_2= tf.add(tf.matmul(x,weights['h2']),biases['b2'])

    #Output fully connected layer with a neurons for each class
    out_layer=tf.matmul(layer_2,weights['out'])+biases['out']
    return out_layer

#construct model
logits = neural_net(X)

#define loss and optimizer

loss_op =tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(logits=logits,labels=Y))

