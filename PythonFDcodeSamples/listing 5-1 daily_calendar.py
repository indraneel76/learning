#Listing 5-1: daily_calendar.py
import smtplib              # get the module for sending email
my_address = 'me@example.com'
headers = [ 'Subject: Daily calendar',
    'From: ' + my_address,
    'To: ' + my_address,
    ]                       # this list spans four lines for readability
entries = open('my_calendar').readlines()[:10]
msg = '\r\n'.join(headers) + '\r\n' + ''.join(entries)

smtp = smtplib.SMTP('mail') # replace 'mail' with the name of your mailhost
smtp.sendmail(my_address, [my_address], msg)
smtp.close()