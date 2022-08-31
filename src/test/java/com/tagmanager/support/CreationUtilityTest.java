package com.tagmanager.support;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.services.tagmanager.model.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class for Creation.
 */
public class CreationUtilityTest {
   /**
    * CreationUtility instance holder.
    */
   public static CreationUtility creationUtility;
   /**
    * ReaderUtility instance holder.
    */
   public static ReaderUtility readerUtility;

   /**
    * The setup method.
    */
   @BeforeAll
   public static void setup() {
      creationUtility = new CreationUtility();
      readerUtility = new ReaderUtility();
   }

   /**
    *
    * Test method for create tag.
    */
   @Test
   public void testCreateTag() {
      System.out.println("Create a new Tag");
      // Create Tag
      Tag newTag = new Tag();
      newTag.setName("my new hello world" + Math.random());
      newTag.setType("ua");

      Parameter trackingId = new Parameter();
      trackingId.setKey("trackingId").setValue("UA-1234-5").setType("template");
      List<Parameter> uaParameters = new ArrayList<>();
      uaParameters.add(trackingId);

      newTag.setParameter(uaParameters);

      List<Account> accountList = readerUtility.fetchAllAccounts();

      if (!accountList.isEmpty()) {

         Account account = accountList.get(0);

         newTag = creationUtility.createTag(account, newTag);
      }

      System.out.println("New tag is create with tag id : " + newTag.getTagId() + " and Tag name : " + newTag.getName());
      assertNotNull(newTag.getTagId());

   }

   /**
    *
    * Test method for create trigger.
    */
   @Test
   public void testCreateTrigger() {
      System.out.println("Create a new Trigger");
      // Create the condition parameters objects.
      Parameter arg0 = new Parameter();
      arg0.setType("template");
      arg0.setKey("arg0");
      arg0.setValue("{{Page URL}}");
      Parameter arg1 = new Parameter();
      arg1.setType("template");
      arg1.setKey("arg1");
      arg1.setValue("timed.html");

      // Create the auto event condition object.
      Condition condition = new Condition();
      condition.setType("contains");
      condition.setParameter(Arrays.asList(arg0, arg1));

      // Create the event parameter.
      Parameter eventName = new Parameter();
      eventName.setType("template");
      eventName.setValue("gtm.timer");

      // Create the interval parameter.
      Parameter interval = new Parameter();
      interval.setType("template");
      interval.setValue("10000");

      // Create the limit parameter.
      Parameter limit = new Parameter();
      limit.setType("template");
      limit.setValue("10");

      // Create the trigger object.
      Trigger trigger = new Trigger();
      trigger.setName("Timer Trigger " + Math.random());
      trigger.setType("timer");
      trigger.setAutoEventFilter(Arrays.asList(condition));
      trigger.setEventName(eventName);
      trigger.setInterval(interval);
      trigger.setLimit(limit);

      List<Account> accountList = readerUtility.fetchAllAccounts();

      if (!accountList.isEmpty()) {

         Account account = accountList.get(0);

         trigger = creationUtility.createTrigger(account, trigger);
      }

      System.out.println("New trigger is create with id : " + trigger.getTriggerId() + " and name : " + trigger.getName());

      assertNotNull(trigger.getTriggerId());

   }

   /**
    *
    * Test method for create workspace.
    */
   @Test
   public void testCreateWorkspace() {

      List<Account> accountList = readerUtility.fetchAllAccounts();

      if (!accountList.isEmpty()) {

         Account account = accountList.get(0);

         List<Workspace> workspaceList = readerUtility.fetchAllWorkspaces(account);
         if (workspaceList.size() > 2) {
            System.out.println("test execution terminated , due to maximum workspace count reached.");
            return;
         }

         Workspace workspace = new Workspace();
         workspace.setDescription("Unit Test workspace");
         workspace.setName("Unit test workspace " + Math.random());
         workspace = creationUtility.createWorkspace(account, workspace);
         System.out.println("New workspace is create with id : " + workspace.getWorkspaceId() + " and name : " + workspace.getName());
      }

   }
}
