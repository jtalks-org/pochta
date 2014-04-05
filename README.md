JTalks Pochta
======

Fake Mail Server (Mail Stub) that is useful when testing your applications. 
It's like Russian Post Office (Pochta) - things get it, but they never get delivered.

####Info
Project has been just started. Justification: other public services (like Mailtrap) started to cost money which is bad for open source.

####Requirements
- Should be a working SMTP server that can receive mails from applications.
- Should produce a `war` or `jar` file
- Inboxes can be set in the configuration of the app (config depends on the binary type). Inbox has a name & password and a secret token.
- When mail is sent with username/password, Pochta should receive the message and save it (in memory or on disk).
- When an REST GET request is sent for the `pochta/inbox/mail?token=secret`, then a JSON list of messages returned
- Max number of messages in inbox should be configured and when max+1 is received, then 0th element is removed.
