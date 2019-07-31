# SlackSMSMessager
An Android app to send SMS messages to Slack and Slack messages as SMS.

This app is written to be able to communicate with my lovely girlfriend since I was going to be in a place without a smartphone but with an ancient cell phone from 90's. My only communication channel was SMS so I wrote this app which sends the message received from an SMS to Slack using Slack RTM API and vice verse. This way, we were able to communicate with my girlfriend and we missed each other less :)

The Android code of the app sends received SMS to a REST API of the server side. The application receives the messages sent from Slack via push notifications then which it is converted a SMS message within the app.

The server side code is reachable here: https://github.com/ovuncsezer/SlackSMSMessagerServer

**For Installation**
1. Application server should be deployed. 
2. Update the fields inside strings.xml file accordingly.
2. Build the application.
3. Install the application to an Android device.
4. Keep the app working at background.
