#Listing 10-3: Coin toss program
import random
headcount = tailcount = 0
userinput = ''
print "Now tossing a coin..."
while userinput.lower() != "q":
    flip = random.choice(['heads', 'tails'])
    if flip == 'heads':
        headcount += 1
        print "heads! the number of heads is now %d" % headcount
    else:
        tailcount += 1
        print "tails! the number of tails is now %d" % tailcount
    print "Press 'q' to quit",
    userinput = raw_input("or another key to toss again:")
print "the total number of heads:", headcount
print "the total number of tails:", tailcount
