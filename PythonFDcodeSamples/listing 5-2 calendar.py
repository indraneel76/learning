#Listing 5-2: calendar.py
import smtplib, sys
my_address = 'me@example.com'
if sys.argv[1] == 'weekly':
    headers = [ 'Subject: Weekly calendar',
        'From: ' + my_address,
        'To: ' + my_address,
        ]
    entries = open('my_calendar').readlines()[:50]
    msg = '\r\n'.join(headers) + '\r\n' + ''.join(entries)
    smtp = smtplib.SMTP('mail')
    smtp.sendmail(my_address, [my_address], msg)
    smtp.close()
else:
    headers = [ 'Subject: Daily calendar',
        'From: ' + my_address,
        'To: ' + my_address,
        ]
    entries = open('my_calendar').readlines()[:10]
    msg = '\r\n'.join(headers) + '\r\n' + ''.join(entries)
    smtp = smtplib.SMTP('mail')
    smtp.sendmail(my_address, [my_address], msg)
    smtp.close()
