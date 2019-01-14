
import pickle


with open('cifar-10-batches-py/data_batch_2','rb') as imagefile:
    dict = pickle.load(imagefile,encoding='latin1')
    print(dict)
    imagefile.close()