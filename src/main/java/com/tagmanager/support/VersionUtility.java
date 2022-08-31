package com.tagmanager.support;

import java.io.IOException;
import java.util.List;

import com.google.api.services.tagmanager.TagManager;
import com.google.api.services.tagmanager.model.Account;
import com.google.api.services.tagmanager.model.Container;
import com.google.api.services.tagmanager.model.ContainerVersion;

/**
 * Utility to process and retrieve gtm version details.
 */
public class VersionUtility {

   /**
    * Get the live container version of the given account.
    *
    * @param pAccount
    *           the account.
    * @return live ContainerVersion instance.
    */
   public ContainerVersion getLiveVersion(final Account pAccount) {
      TagManager manager = GTMHelper.getInstance().createTagManager();
      Container defaultContainer = getDefaultContainer(pAccount);

      try {
         return manager.accounts().containers().versions().live(defaultContainer.getPath()).execute();

      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while accessing container versions.", e);
      }
   }

   /**
    * Publish a given container version.
    *
    * @param pContainerVersion
    *           the container version to be published.
    * @return published container version.
    */
   public ContainerVersion publishVersion(final ContainerVersion pContainerVersion) {
      TagManager manager = GTMHelper.getInstance().createTagManager();

      try {
         return manager.accounts().containers().versions().publish(pContainerVersion.getPath()).execute().getContainerVersion();
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while publishing container versions.", e);
      }
   }

   private Container getDefaultContainer(final Account pAccount) {
      TagManager manager = GTMHelper.getInstance().createTagManager();
      try {
         List<Container> containerList = manager.accounts().containers().list(pAccount.getPath()).execute().getContainer();
         if (containerList != null && !containerList.isEmpty()) {
            return containerList.get(0);
         } else {
            throw new GTMUtilityException("No container available.");
         }
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while accessing containers.", e);
      }
   }

}
