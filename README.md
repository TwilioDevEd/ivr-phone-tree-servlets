<a href="https://www.twilio.com">
  <img src="https://static0.twilio.com/marketing/bundles/marketing/img/logos/wordmark-red.svg" alt="Twilio" width="250" />
</a>

# IVR Phone Tree: IVR for beginners. Powered by Twilio - Java/Servlets

[![Build Status](https://travis-ci.org/TwilioDevEd/ivr-phone-tree-servlets.svg?branch=master)](https://travis-ci.org/TwilioDevEd/ivr-phone-tree-servlets)

This is an application example implementing an automated phone line using Twilio
and Java Servlets.

[Read the full tutorial here](https://www.twilio.com/docs/tutorials/walkthrough/ivr-phone-tree/java/servlets)!

## Local Development

1. First clone this repository and `cd` into it.

   ```bash
   $ git clone git@github.com:TwilioDevEd/ivr-phone-tree-servlets.git
   $ cd ivr-phone-tree-servlets
   ```

2. Run the application.

   ```bash
   $ ./gradlew appRun
   ```

3. Check it out at [http://localhost:8080](http://localhost:8080).

4. Expose the application to the wider Internet using [ngrok](https://ngrok.com/).

   ```bash
   $ ngrok http 8080
   ```

1. Configure Twilio to call your webhooks

  You will also need to configure Twilio to call your application when calls are
  received in your [*Twilio Number*](https://www.twilio.com/user/account/messaging/phone-numbers).
  The voice URL should look something like this:

  ```
  http://<your-ngrok-subdomain>.ngrok.io/ivr/welcome
  ```

  ![Configure Voice](https://s3.amazonaws.com/com.twilio.prod.twilio-docs/images/ivr-webhook.width-800.png)

6. Grab your phone and call your newly-provisioned number!


## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
