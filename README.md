1. Create private key file:

   openssl genrsa -out xero_privatekey.pem 1024

2. Create public key file:

   openssl req -newkey rsa:1024 -x509 -key xero_privatekey.pem -out xero_publickey.cer -days 365
   
3. Register your consumer key and secret in XERO Development site:
      https://app.xero.com/Application

      OAuth Credentials
      Consumer Key
         RE••••••••••••••••••••••••••FR
       
      Consumer Secret
         SP••••••••••••••••••••••••••NM

      Regenerate Key and SecretNote, For Private applications, the consumer 
      token and secret are also used as the access token and secret.  
      API Endpoint URL: https://api.xero.com/api.xro/2.0/

4. Upload the xero_publickey.cer to  https://app.xero.com/Application
      Upload a new Public Key Certificate
 
5. Put your private key into: privateKey.pem

6. Put your consumer key and secret into: xeroApi.properties
