Listing 5-3: refactored_calendar.py
import smtplib, sys
my_address = 'me@example.com'

# set up and send the email
def send_calendar(address, num_entries, subject):
    headers = [ 'Subject: ' + subject,
        'From: ' + address,
        'To: ' + address,
        ]
    entries = open('my_calendar').readlines()[:num_entries]
    msg = '\r\n'.join(headers) + '\r\n' + ''.join(entries)
    smtp = smtplib.SMTP('mail')
    smtp.sendmail(my_address, [my_address], msg)
    smtp.close()

# parse the command-line argument
if sys.argv[1] == 'weekly':
    send_calendar(my_address, 50, 'Weekly calendar')
else:
    send_calendar(my_address, 10, 'Daily calendar')
