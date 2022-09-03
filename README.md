#POC Google Tag manager:

This is a POC project which was done to try out google tag manger api.
To run this application you need to have an google tag manager account.
Follow the instruction below to create google tag manager account and run this poc project.

# How to configure Google tag manager

## Step 1 : Create tag manager account.

- Log in to https://tagmanager.google.com/ using your gmail account.
- click on create account and add a new account by filling the form


## Step 2 : Configure Google cloud API

- log in to the https://console.cloud.google.com/ using same gmail account.
- Create a project if already not exists.
- Go to dashboard of the project.
- Navigate to “APIs & Services” -> Enabled APIs and Services
> If tag manager api ia not listed in the api list you have to add it. To add tag manager api , click on the Enable API and Services button and search for tag manager and select it.

### Configure for service Account.
- Navigate to Credentials tab
- click on “+Create credentials” link and select "Service account"
> Fill the form with account name and account id
- click on Continue button
> Select the role as the owner
> Click the done button.
>
- for the first time you will get the pop up to download service account json file.
> you can find the service account json in the KEYS tab as well,
> Go to keys tab press the "ADD KEY" button the select Create new key and select as json and press CREATE button


- Once key is created you need to add this key user to grant permission in the TAG manager.

- Open the Tag manager via https://tagmanager.google.com/
- Select "Admin" tab -> Click "User Management"
- Here you can add new user to the tag manager Click the + button
- provide the email address generated in the service key account.
- select administrator access here.
> When you create the service account, it will generate the email for that account, it should be used here.   
> eg: xxxx-zzzz@xxxxxx.iam.gserviceaccount.com

### Configure with Auth Client ID

- Navigate to Credintials tab
- click on “+Create credentials” link and select "OAuth Client ID"
> you will get the configure consent screen if you are doing this for the first time.
- Create consent configuration just selecting User Type "External" option
- Fill the OAuth consent screen details
- Now again select the “Credentials” tab and fill the information and press create button
> there will be a pop up dialog you can download the client_secret.json file.

- Again go to “OAuth consent” tab and add a test user as your gmail account.
> This should be the user account you will be getting when you run the application.

Now Google tag manager is configured to use as an api with configured user.


## Step 3 : Configure project.

Here we are going to use downloaded service_account.json or client_secret.json file in our project.

- Navigate to the  {project directory}/src/main/resources/ folder and copy downloaded service account/ client secret json file to it.
> please make sure name of the json file, It should be service_account.json for service and client_secrets.json for Auth Client

>Once you place the service_account.json file in the above folder project is ready to run.


## Step 4: Run the Project

There are two Junit classes which you can run in the project.

```
CreationUtilityTest.java

ReaderUtilityTest.java
```

To run ReaderUtilityTest you need to have created tags/triggers/variables/folders in your tag manager account.


- Execute CreationUtilityTest.java as a Junit test in your IDE.

> you will see that your test case is passed.
 


