JTalks Pochta
======

Fake Mail Server (Mail Stub) that is useful when testing your applications. 
It's like Russian Post Office (Pochta) - things get in, but they never get delivered. Cases when you may need it:
- You write System tests against the app that sends mails to its users. You'd like to intercept those message to test 
them or to go through the workflow in automated manner.
- You have a PROD database with all its users, you're eager to test your new version of the app on this DB but you're 
afraid mails will be delivered to real users.

####Installation
- Download the [latest version](http://repo.jtalks.org/content/repositories/releases/org/jtalks/jtalks-pochta/1.1/jtalks-pochta-1.1.jar)
- Run `java -jar pochta.jar`
- Open [localhost:9000](http://localhost:9000)

Note, that during the first startup a folder `~/.pochta` is created with the default configuration. You can change the configuration to match your needs: http/smtp ports, mailboxes and passwords.

####Usage
Now, imagine your app sends mail to its users using Pochta SMTP Server: `SUT --> Pochta --> realuser@mail.com`. After the mail was sent 
to Pochta it doesn't actually deliver the message to the end user, but rather Pochta stores it. Now in tests you can get the list of mails with the content e.g. by following: [localhost:9000/inboxes/user?token=secret](http://localhost:9000/inboxes/user?token=secret) and the returned content may look like this:
```json
[
  {
    "server_id": 1,
    "sender_ip": "\/127.0.0.1:54642",
    "mail_body": "From: web@gmail.com\r\nTo: realuser@mail.com\r\nMessage-ID: <1722681408.0.1398714670448.JavaMail.sbashkyrtsev@sbashkyrtsev-nb.local>\r\nSubject: This is the long  long long long long long long longSubject Line!\r\nMIME-Version: 1.0\r\nContent-Type: text\/plain; charset=us-ascii\r\nContent-Transfer-Encoding: 7bit\r\n\r\nThis is actual message\r\n",
    "envelope_from": "web@gmail.com",
    "delivery_date": "Mon Apr 28 23:51:10 MSK 2014",
    "envelope_recipients": ["realuser@mail.com"]
  }
]
```

You can create new mailboxes in config file, while configuring them you should specify both mailbox and the password. When trying to use Pochta as SMTP Server,
specify these credentials. Afterwards when you try to retreive stored data from Pochta, pass the password as a `?secret=password`. Well, you should see self 
explanatory errors if you're doing something wrong. 
