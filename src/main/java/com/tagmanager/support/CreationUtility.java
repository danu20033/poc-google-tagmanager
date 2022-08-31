package com.tagmanager.support;

import java.io.IOException;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import com.google.api.services.tagmanager.TagManager;
import com.google.api.services.tagmanager.model.*;

/**
 * Utility to create GTM data.
 */
@NoArgsConstructor
public class CreationUtility {

   /**
    * Creates a new tag for the given account.
    *
    * @param pAccount
    *           the account.
    * @param pTag
    *           the new tag to be created.
    * @return new tag.
    *
    */
   public Tag createTag(final Account pAccount, final Tag pTag) {

      TagManager manager = GTMHelper.getInstance().createTagManager();

      Workspace workspace = getDefaultWorkspace(pAccount, manager);

      try {
         return manager.accounts().containers().workspaces().tags().create(workspace.getPath(), pTag).execute();
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while creating a tag.", e);
      }

   }

   /**
    * Creates a new trigger for the given account.
    *
    * @param pAccount
    *           the account.
    * @param pTrigger
    *           the new trigger to be created.
    * @return new trigger.
    *
    */
   public Trigger createTrigger(final Account pAccount, final Trigger pTrigger) {

      TagManager manager = GTMHelper.getInstance().createTagManager();

      Workspace workspace = getDefaultWorkspace(pAccount, manager);

      try {
         return manager.accounts().containers().workspaces().triggers().create(workspace.getPath(), pTrigger).execute();
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while creating a trigger.", e);
      }

   }

   /**
    * Creates a new zone for the given account.
    *
    * @param pAccount
    *           the account.
    * @param pZone
    *           the new zone to be created.
    * @return new zone.
    */
   public Zone createZone(final Account pAccount, final Zone pZone) {
      TagManager manager = GTMHelper.getInstance().createTagManager();
      Workspace workspace = getDefaultWorkspace(pAccount, manager);
      try {
         return manager.accounts().containers().workspaces().zones().create(workspace.getPath(), pZone).execute();
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while creating a zone.", e);
      }
   }

   /**
    * Creates a workspace on the given account.
    *
    * @param pAccount
    *           the account.
    * @param pWorkspace
    *           the workspace to be created.
    * @return new workspace.
    */
   public Workspace createWorkspace(final Account pAccount, final Workspace pWorkspace) {
      TagManager manager = GTMHelper.getInstance().createTagManager();
      // get containers for the account.
      Container defaultContainer = getDefaultContainer(pAccount, manager);
      try {
         return manager.accounts().containers().workspaces().create(defaultContainer.getPath(), pWorkspace).execute();
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while creating a workspace.", e);
      }

   }

   /**
    * This will return the default workspace for given account, first get the container for the given account and then retrieve the
    * workspace of that container.
    *
    * @param pAccount
    *           the account.
    * @param manager
    *           tag manager instance.
    * @return the default workspace.
    *
    */
   @SneakyThrows
   private Workspace getDefaultWorkspace(final Account pAccount, final TagManager manager) {
      Workspace defaultWorkspace = null;
      // get containers for the account.
      Container defaultContainer = getDefaultContainer(pAccount, manager);
      List<Workspace> workspaceList = null;
      workspaceList = manager.accounts().containers().workspaces().list(defaultContainer.getPath()).execute().getWorkspace();
      if (workspaceList != null && !workspaceList.isEmpty()) {
         // first item will be taken as the default workspace.
         defaultWorkspace = workspaceList.get(0);
      } else {
         throw new GTMUtilityException("No Workspace available");
      }

      return defaultWorkspace;
   }

   /**
    *
    * This will return the default container for the given account.
    *
    * @param pAccount
    *           the account.
    * @param manager
    *           tag manager instance.
    * @return default Container instance.
    */
   private Container getDefaultContainer(final Account pAccount, final TagManager manager) {

      try {
         ListContainersResponse listContainersResponse = manager.accounts().containers().list(pAccount.getPath()).execute();
         List<Container> containerList = listContainersResponse.getContainer();
         if (containerList != null && !containerList.isEmpty()) {
            return containerList.get(0);
         } else {
            throw new GTMUtilityException("No Containers available");
         }
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while accessing containers");
      }

   }

}
