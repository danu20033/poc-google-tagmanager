package com.tagmanager.support;

import java.util.List;

import com.google.api.services.tagmanager.model.Account;
import com.google.api.services.tagmanager.model.Tag;
import com.google.api.services.tagmanager.model.Trigger;
import com.google.api.services.tagmanager.model.Workspace;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class for Update.
 */
public class UpdationUtilityTest {

   /**
    * UpdationUtility instance holder.
    */
   private static UpdationUtility updationUtility;

   /**
    * ReaderUtility instance holder.
    */
   public static ReaderUtility readerUtility;

   /**
    * The setup method.
    */
   @BeforeAll
   public static void setUp() {
      updationUtility = new UpdationUtility();
      readerUtility = new ReaderUtility();
   }

   /**
    * Test method for update tag.
    */
   @Test
   public void testUpdateTag() {
      Account account = getDefaultAccount();
      List<Tag> tagList = readerUtility.fetchAllTags(account);

      if (tagList != null && !tagList.isEmpty()) {
         Tag tagTobeUpdated = tagList.get(0);

         String updatedName = tagTobeUpdated.getName() + DateTime.now().getMillis();

         tagTobeUpdated.setName(updatedName);

         Tag updatedTag = updationUtility.updateTag(tagTobeUpdated);

         Assertions.assertEquals(updatedName, updatedTag.getName(), "Tag aName is not updated.");
      }

   }

   /**
    * Test method for update trigger.
    */
   @Test
   public void testUpdateTrigger() {
      Account account = getDefaultAccount();
      List<Trigger> triggerList = readerUtility.fetchAllTriggers(account);

      if (triggerList != null && !triggerList.isEmpty()) {
         Trigger trigger = triggerList.get(0);

         String updatedName = trigger.getName() + DateTime.now().getMillis();

         trigger.setName(updatedName);

         Trigger updateTrigger = updationUtility.updateTrigger(trigger);

         Assertions.assertEquals(updatedName, updateTrigger.getName(), "Trigger name is not updated.");
      }

   }

   /**
    * Test method for update workspace.
    */
   @Test
   public void testUpdateWorkspace() {
      Account account = getDefaultAccount();
      List<Workspace> workspaceList = readerUtility.fetchAllWorkspaces(account);

      if (workspaceList != null && !workspaceList.isEmpty()) {
         Workspace workspace = workspaceList.get(0);

         String updatedDescription = workspace.getDescription() + DateTime.now().getMillis();

         workspace.setDescription(updatedDescription);
         Workspace updatedWorkspace = updationUtility.updateWorkspace(workspace);

         Assertions.assertEquals(updatedDescription, updatedWorkspace.getDescription(), "Workspace description is not updated.");
      }

   }

   /**
    * Get default account,
    *
    * @return account instance
    */
   private Account getDefaultAccount() {
      List<Account> accountList = readerUtility.fetchAllAccounts();

      if (!accountList.isEmpty()) {
         return accountList.get(0);
      }
      return null;
   }

}
