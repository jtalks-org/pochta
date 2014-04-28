JTalks Pochta
======

Fake Mail Server (Mail Stub) that is useful when testing your applications. 
It's like Russian Post Office (Pochta) - things get it, but they never get delivered.

####Installation
- Download the [latest version](http://repo.jtalks.org/content/repositories/releases/org/jtalks/jtalks-pochta/1.0/jtalks-pochta-1.0.jar)
- Run `java -jar pochta.jar`
- Open http://localhost:9000

Note, that during the first startup a folder `~/.pochta` is created with the default configuration. You can change the configuration to match your needs: http/smtp ports, mailboxes and passwords.

####Usage
You can create new mailboxes in config file, while configuring them you should specify both mailbox and the password. When trying to use Pochta as SMTP Server,
specify these credentials. Afterwards when you try to retreive stored data from Pochta, pass the password as a `?secret=password`. Well, you should see self 
explanatory errors if you're doing something wrong. 
