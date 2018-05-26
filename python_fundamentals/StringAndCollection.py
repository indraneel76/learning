sample= "first" "second"
print(sample)
multline =""" a multiline string
 testing"""
print(multline)

multline ='''a multiline string for 
for testing it again'''
print(multline)
multline='a mulitline string for \n for testing it again and again'
print(multline)

number =str(6.02e23)
print(number)
s='parrot'
print(s[4])
print(type(s[4]))

c = 'oslo'
print(c.capitalize())

testbyte=b'some byte data'
print(testbyte.split())

sampledata ="Hi this is great i am some string "
data = sampledata.encode("utf-8")
print(data)
'''decodding '''
print (data.decode("utf-8"))