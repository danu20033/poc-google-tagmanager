package com.tagmanager.support;

import java.util.Objects;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.SneakyThrows;
import lombok.Synchronized;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.tagmanager.TagManager;

/**
 * GTMHelper handles all GTM specific functions.
 */
public class GTMHelper {

   /**
    * Application name to be used in each request to GTM.
    */
   private static final String APPLICATION_NAME = "GTMHelper";

   /**
    * Instance holder of json factory.
    */
   private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
   /**
    * Instance holder of TagManger.
    */
   private TagManager manager;

   /**
    * Instance holder of httpTransport.
    */
   private final NetHttpTransport httpTransport;
   /**
    * Instance holder of GTMHelper.
    */
   private static GTMHelper instance;

   /**
    * GTMHelper singleton accessor method.
    *
    * @return GTMHelper instance.
    */
   @Synchronized
   public static GTMHelper getInstance() {
      if (Objects.isNull(instance)) {
         // lazy init
         instance = new GTMHelper();
      }
      return instance;
   }

   /**
    * Private constructor to avoid instance creation.
    */
   @SneakyThrows
   private GTMHelper() {
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();
   }

   /**
    * Create tagMangerInstance.
    *
    * @return TagManager instance
    *
    */
   @SneakyThrows
   public TagManager createTagManager() {
      if (manager == null) {
         ServiceAccountCredentials credential = GoogleOAuthUtil.getInstance().getServiceAccountCredentials();
         HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credential);
         manager = new TagManager.Builder(httpTransport, JSON_FACTORY, requestInitializer).setApplicationName(APPLICATION_NAME).build();
      }
      return manager;
   }

   /**
    * Create tagMangerInstance.
    *
    * @return TagManager instance
    *
    */
   @SneakyThrows
   public TagManager createTagManagerForAuthClient() {
      if (manager == null) {
         Credential credential = GoogleOAuthUtil.getInstance().authorizeAuthClient();
         manager = new TagManager.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
      }
      return manager;
   }

}
