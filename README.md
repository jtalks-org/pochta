JTalks Pochta
======

Fake Mail Server (Mail Stub) that is useful when testing your applications. 
It's like Russian Post Office (Pochta) - things get it, but they never get delivered. When you may need it:
- When you write System tests against the system that sends mails to its users. You'd like to intercept those message to test them or to go through the workflow.
- When you have a PROD database with all its users, you're eager to test your new version of the app on this DB but you're afraid mails will be delivered o real users.

####Installation
- Download the [latest version](http://repo.jtalks.org/content/repositories/releases/org/jtalks/jtalks-pochta/1.0/jtalks-pochta-1.0.jar)
- Run `java -jar pochta.jar`
- Open http://localhost:9000

Note, that during the first startup a folder `~/.pochta` is created with the default configuration. You can change the configuration to match your needs: http/smtp ports, mailboxes and passwords.

####Usage
Now, imaging your app sends mail to its users using Pochta SMTP Server: `SUT ----> Pochta ----> realuser@mail.com`. After the mail was delivered 
to Pochta it doesn't actually deliver, but rather it stores them. Now in tests you can get the list of mails with the content e.g. by following: http://localhost:9000/inboxes/user?token=secret and the returned content may look like this:
```json
[{
  server_id: 1,
  sender_ip: "/127.0.0.1:54642",
  mail_body: "From: web@gmail.com To: abcd@gmail.com Message-ID: <1722681408.0.1398714670448.JavaMail.sbashkyrtsev@sbashkyrtsev-nb.local> Subject: This is the long long long long long long long longSubject Line! MIME-Version: 1.0 Content-Type: text/plain; charset=us-ascii Content-Transfer-Encoding: 7bit This is actual message ",
  envelope_from: "web@gmail.com",
  delivery_date: "Mon Apr 28 23:51:10 MSK 2014",
  envelope_recipients: ["abcd@gmail.com"]
}]

```

You can create new mailboxes in config file, while configuring them you should specify both mailbox and the password. When trying to use Pochta as SMTP Server,
specify these credentials. Afterwards when you try to retreive stored data from Pochta, pass the password as a `?secret=password`. Well, you should see self 
explanatory errors if you're doing something wrong. 
