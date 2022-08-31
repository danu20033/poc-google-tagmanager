package com.tagmanager.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Objects;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.Synchronized;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.tagmanager.TagManagerScopes;
import com.google.auth.oauth2.ServiceAccountCredentials;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

/**
 * Authenticator util will handle all authentication to GTM.
 */
public class GoogleOAuthUtil {

   /**
    * Name of the client secrete file
    */
   private static final String CLIENT_SECRET_JSON_RESOURCE = "client_secrets.json";

   /**
    * Location of the temporary data store.
    */
   private static final String DATA_STORE_DIR =
         FileUtils.getTempDirectoryPath() + File.separator + GoogleOAuthUtil.class.getSimpleName() + "-" + SystemUtils.USER_NAME;

   /**
    * Name of the service account json file.
    */
   private static final String SERVICE_ACCOUNT_JSON_FILE = "service_account.json";

   /**
    * Access type of the GTM.
    */
   public static final String OFFLINE = "offline";
   /**
    * Authorized user type
    */
   public static final String USER = "user";
   /**
    * Instance holder of httpTransport
    */
   private final NetHttpTransport httpTransport;
   /**
    * Instance holder of dataStoreFactory
    */
   private final FileDataStoreFactory dataStoreFactory;
   /**
    * Instance holder of json factory.
    */
   private final JsonFactory jsonFactory;

   /**
    * Instance holder of GoogleOAuthUtil
    */
   private static GoogleOAuthUtil instance;

   /**
    * GoogleOAuthUtil singleton accessor method.
    *
    * @return GoogleOAuthUtil instance.
    */
   @Synchronized
   public static GoogleOAuthUtil getInstance() {
      if (Objects.isNull(instance)) {
         instance = new GoogleOAuthUtil();
      }
      return instance;
   }

   /**
    * Private constructor to avoid instance creation.
    */
   private GoogleOAuthUtil() {
      try {
         httpTransport = GoogleNetHttpTransport.newTrustedTransport();
         File lAuthDir = new File(DATA_STORE_DIR);
         dataStoreFactory = new FileDataStoreFactory(lAuthDir);
         jsonFactory = GsonFactory.getDefaultInstance();
      } catch (GeneralSecurityException | IOException e) {
         throw new GTMUtilityException("Exception occurred while initializing", e);
      }

   }

   /**
    * Authenticate using OAuth2 Client
    *
    * @return Credential instance
    *
    */
   @SneakyThrows
   public Credential authorizeAuthClient() {

      // Load client secrets.
      GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
            new InputStreamReader(GoogleOAuthUtil.class.getClassLoader().getResourceAsStream(CLIENT_SECRET_JSON_RESOURCE), StandardCharsets.UTF_8));

      // Set up authorization code flow for all auth scopes.
      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, TagManagerScopes.all())
            .setDataStoreFactory(dataStoreFactory).setAccessType(OFFLINE).build();

      return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(USER);
   }

   /**
    * Get service account credentials.
    *
    * @return the service account credentials instance
    */
   @SneakyThrows
   public ServiceAccountCredentials getServiceAccountCredentials() {

      @Cleanup
      InputStream stream = GoogleOAuthUtil.class.getClassLoader().getResourceAsStream(SERVICE_ACCOUNT_JSON_FILE);
      return ServiceAccountCredentials.fromStream(stream);

   }

}
