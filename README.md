# IVR Phone Tree: IVR for beginners. Powered by Twilio - Servlets

[![Build Status](https://travis-ci.org/TwilioDevEd/ivr-phone-tree-servlets.svg?branch=master)](https://travis-ci.org/TwilioDevEd/ivr-phone-tree-servlets)

This is an application example implementing an automated phone line using Twilio.

[Read the full tutorial here](https://www.twilio.com/docs/tutorials/walkthrough/ivr-phone-tree/java/servlets)!

## Local Development

1. Clone this repository and `cd` into it.

   ```bash
   $ git clone git@github.com:TwilioDevEd/ivr-phone-tree-servlets.git
   $ cd ivr-phone-tree-servlets
   ```

2. Run the application.

   ```bash
   $ ./gradlew jettyRun
   ```

3. Check it out at [http://localhost:8080](http://localhost:8080)

4. Expose the application to the wider Internet using [ngrok](https://ngrok.com/).

   ```bash
   $ ngrok 8000 http
   ```
   
5. Provision a number under the
   [Manage Numbers page](https://www.twilio.com/user/account/phone-numbers/incoming)
   on your account. Set the voice URL for the number to
   `http://<your-ngrok-subdomain>.ngrok.io/ivr/welcome`.

6. Grab your phone and call your newly-provisioned number!

That's it!

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
