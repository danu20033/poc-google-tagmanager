package com.tagmanager.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import com.google.api.services.tagmanager.TagManager;
import com.google.api.services.tagmanager.model.*;

/**
 * Utility to read on GTM data.
 */
@NoArgsConstructor
public class ReaderUtility {

   /**
    * This will return all Accounts in the GTM.
    *
    * @return list of Account instances.
    *
    */
   @SneakyThrows
   public List<Account> fetchAllAccounts() {

      TagManager manager = GTMHelper.getInstance().createTagManager();
      ListAccountsResponse listAccountsResponses = manager.accounts().list().execute();
      return listAccountsResponses.getAccount();

   }

   /**
    * This will return all Tags for the given account.
    *
    * @param pAccount
    *           the account.
    * @return list of tags for the given account.
    */
   @SneakyThrows
   public List<Tag> fetchAllTags(final Account pAccount) {
      List<Tag> tagList = new ArrayList<>();
      TagManager manager = GTMHelper.getInstance().createTagManager();

      final List<Workspace> workspaceList = fetchAllWorkspaces(pAccount);

      // get All tags from each workspace.
      workspaceList.forEach(workspace -> {
         try {
            List<Tag> tags = manager.accounts().containers().workspaces().tags().list(workspace.getPath()).execute().getTag();
            if (tags != null && !tags.isEmpty()) {
               tagList.addAll(tags);
            }
         } catch (IOException e) {
            throw new GTMUtilityException("Exception occurred while accessing tags.", e);
         }
      });

      return tagList;

   }

   /**
    * This will return all Triggers for the given account.
    *
    * @param pAccount
    *           the account.
    * @return list of triggers for the given account.
    */
   @SneakyThrows
   public List<Trigger> fetchAllTriggers(final Account pAccount) {
      List<Trigger> triggerList = new ArrayList<>();
      TagManager manager = GTMHelper.getInstance().createTagManager();

      final List<Workspace> workspaceList = fetchAllWorkspaces(pAccount);
      // get All triggers from each workspace.
      workspaceList.forEach(workspace -> {
         try {
            List<Trigger> triggers = manager.accounts().containers().workspaces().triggers().list(workspace.getPath()).execute().getTrigger();
            if (triggers != null && !triggers.isEmpty()) {
               triggerList.addAll(triggers);
            }
         } catch (IOException e) {
            throw new GTMUtilityException("Exception occurred while accessing triggers.", e);
         }
      });

      return triggerList;
   }

   /**
    * This will return all Variables for the given account.
    *
    * @param pAccount
    *           the account.
    * @return list of Variables for the given account.
    */
   @SneakyThrows
   public List<Variable> fetchAllVariables(final Account pAccount) {
      List<Variable> variableList = new ArrayList<>();
      TagManager manager = GTMHelper.getInstance().createTagManager();

      final List<Workspace> workspaceList = fetchAllWorkspaces(pAccount);
      // get All variables from each workspace.
      workspaceList.forEach(workspace -> {
         try {
            List<Variable> variables = manager.accounts().containers().workspaces().variables().list(workspace.getPath()).execute().getVariable();
            if (variables != null && !variables.isEmpty()) {
               variableList.addAll(variables);
            }
         } catch (IOException e) {
            throw new GTMUtilityException("Exception occurred while accessing variables.", e);
         }
      });

      return variableList;
   }

   /**
    * This will return all Folders for the given account.
    *
    * @param pAccount
    *           the account.
    * @return list of Folders for the given account.
    */
   @SneakyThrows
   public List<Folder> fetchAllFolders(final Account pAccount) {
      List<Folder> folderList = new ArrayList<>();
      TagManager manager = GTMHelper.getInstance().createTagManager();

      final List<Workspace> workspaceList = fetchAllWorkspaces(pAccount);
      // get All variables from each workspace.
      workspaceList.forEach(workspace -> {
         try {
            List<Folder> folders = manager.accounts().containers().workspaces().folders().list(workspace.getPath()).execute().getFolder();
            if (folders != null && !folders.isEmpty()) {
               folderList.addAll(folders);
            }
         } catch (IOException e) {
            throw new GTMUtilityException("Exception occurred while accessing folders.", e);
         }
      });

      return folderList;
   }

   /**
    * This will return all Templates for the given account.
    *
    * @param pAccount
    *           the account.
    * @return list of Templates for the given account.
    */
   @SneakyThrows
   public List<CustomTemplate> fetchAllTemplates(final Account pAccount) {
      List<CustomTemplate> customTemplateList = new ArrayList<>();
      TagManager manager = GTMHelper.getInstance().createTagManager();

      final List<Workspace> workspaceList = fetchAllWorkspaces(pAccount);
      // get All variables from each workspace.
      workspaceList.forEach(workspace -> {
         try {
            List<CustomTemplate> templates = manager.accounts().containers().workspaces().templates().list(workspace.getPath()).execute().getTemplate();
            if (templates != null && !templates.isEmpty()) {
               customTemplateList.addAll(templates);
            }
         } catch (IOException e) {
            throw new GTMUtilityException("Exception occurred while accessing templates.", e);
         }
      });

      return customTemplateList;
   }

   /**
    * This will return all zones for the given account.
    *
    * @param pAccount
    *           the account.
    * @return list of zones for the given account.
    */
   public List<Zone> fetAllZones(final Account pAccount) {
      List<Zone> zoneList = new ArrayList<>();
      TagManager manager = GTMHelper.getInstance().createTagManager();

      final List<Workspace> workspaceList = fetchAllWorkspaces(pAccount);

      // get All zones from each workspace.
      workspaceList.forEach(workspace -> {
         try {
            List<Zone> zones = manager.accounts().containers().workspaces().zones().list(workspace.getPath()).execute().getZone();
            if (zones != null && !zones.isEmpty()) {
               zoneList.addAll(zones);
            }
         } catch (IOException e) {
            throw new GTMUtilityException("Exception occurred while accessing zones.", e);
         }
      });

      return zoneList;
   }

   /**
    * This will return all workspaces for given account, first get the containers for given account and then retrieve the workspaces for
    * each container.
    *
    * @param pAccount
    *           the account
    * @return list of workspaces
    *
    */
   @SneakyThrows
   public List<Workspace> fetchAllWorkspaces(final Account pAccount) {
      final TagManager manager = GTMHelper.getInstance().createTagManager();
      final List<Workspace> workspaceList = new ArrayList<>();
      // get containers for the account.
      ListContainersResponse listContainersResponse = manager.accounts().containers().list(pAccount.getPath()).execute();
      List<Container> containerList = listContainersResponse.getContainer();

      // get all workspaces for the all containers
      containerList.forEach(container -> {
         try {
            workspaceList.addAll(manager.accounts().containers().workspaces().list(container.getPath()).execute().getWorkspace());
         } catch (IOException e) {
            throw new GTMUtilityException("Exception occurred while accessing workspaces.", e);
         }
      });

      return workspaceList;
   }

}
