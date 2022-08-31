package com.tagmanager.support;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import com.google.api.services.tagmanager.model.Account;
import com.google.api.services.tagmanager.model.CustomTemplate;
import com.google.api.services.tagmanager.model.Folder;
import com.google.api.services.tagmanager.model.Tag;
import com.google.api.services.tagmanager.model.Trigger;
import com.google.api.services.tagmanager.model.Variable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Test class for Reader.
 */
@TestMethodOrder(OrderAnnotation.class)
public class ReaderUtilityTest {
   /**
    * ReaderUtility instance holder.
    */
   private static ReaderUtility readerUtility;

   /**
    * The setup method.
    */
   @BeforeAll
   public static void setup() {
      readerUtility = new ReaderUtility();
   }

   /**
    *
    * Test method fetch all accounts.
    */
   @Test
   @Order(10)
   public void testFetchAllAccounts() {
      System.out.println("All Accounts");

      for (Account account : readerUtility.fetchAllAccounts()) {
         System.out.println(account);
         assertNotNull(account);
      }

   }

   /**
    *
    * Test method fetch all tags.
    */
   @Test
   @Order(50)
   public void testFetchAllTags() {
      System.out.println("All Tags");
      List<Account> accountList = readerUtility.fetchAllAccounts();

      if (!accountList.isEmpty()) {

         Account account = accountList.get(0);

         List<Tag> tagList = readerUtility.fetchAllTags(account);

         tagList.forEach(System.out::println);

         assertNotNull(tagList);
      }

   }

   /**
    *
    * Test method fetch all triggers.
    */
   @Test
   @Order(40)
   public void testFetchAllTriggers() {
      System.out.println("All Triggers");
      List<Account> accountList = readerUtility.fetchAllAccounts();

      if (!accountList.isEmpty()) {

         Account account = accountList.get(0);

         List<Trigger> triggerListList = readerUtility.fetchAllTriggers(account);

         triggerListList.forEach(System.out::println);
         assertNotNull(triggerListList);
      }
   }

   /**
    *
    * Test method fetch all variables.
    */
   @Test
   @Order(60)
   public void testFetchAllVariables() {
      System.out.println("All Variables");
      List<Account> accountList = readerUtility.fetchAllAccounts();

      if (!accountList.isEmpty()) {

         Account account = accountList.get(0);

         List<Variable> variableList = readerUtility.fetchAllVariables(account);

         variableList.forEach(System.out::println);
         assertNotNull(variableList);
      }

   }

   /**
    *
    * Test method fetch all folders.
    */
   @Test
   @Order(30)
   public void testFetchAllFolders() {
      System.out.println("All Folders");
      List<Account> accountList = readerUtility.fetchAllAccounts();

      if (!accountList.isEmpty()) {

         Account account = accountList.get(0);

         List<Folder> folderList = readerUtility.fetchAllFolders(account);

         folderList.forEach(System.out::println);

         assertNotNull(folderList);
      }
   }

   /**
    *
    * Test method fetch all templates.
    */
   @Test
   @Order(20)
   public void testFetchAllTemplates() {
      System.out.println("All Templates");
      List<Account> accountList = readerUtility.fetchAllAccounts();

      if (!accountList.isEmpty()) {

         Account account = accountList.get(0);

         List<CustomTemplate> customTemplateList = readerUtility.fetchAllTemplates(account);

         customTemplateList.forEach(System.out::println);

         assertNotNull(customTemplateList);
      }
   }
}
