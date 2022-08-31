package com.tagmanager.support;

import java.io.IOException;

import com.google.api.services.tagmanager.TagManager;
import com.google.api.services.tagmanager.model.Tag;
import com.google.api.services.tagmanager.model.Trigger;
import com.google.api.services.tagmanager.model.Workspace;
import com.google.api.services.tagmanager.model.Zone;

/**
 * Utility to update GTM data.
 */
public class UpdationUtility {

   /**
    * The tag will be updated with given tag details.
    *
    * @param pTag
    *           the tag to be updated.
    * @return updated tag.
    */
   public Tag updateTag(final Tag pTag) {
      TagManager manager = GTMHelper.getInstance().createTagManager();
      try {
         return manager.accounts().containers().workspaces().tags().update(pTag.getPath(), pTag).execute();
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while updating tag.", e);
      }

   }

   /**
    * The trigger will be updated with given trigger details.
    *
    * @param pTrigger
    *           the trigger to be updated.
    * @return updated trigger.
    */
   public Trigger updateTrigger(final Trigger pTrigger) {
      TagManager manager = GTMHelper.getInstance().createTagManager();
      try {
         return manager.accounts().containers().workspaces().triggers().update(pTrigger.getPath(), pTrigger).execute();
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while updating trigger.", e);
      }

   }

   /**
    * The zone will be updated with given zone details.
    *
    * @param pZone
    *           the zone to be updated.
    * @return updated zone.
    */
   public Zone updateZone(final Zone pZone) {
      TagManager manager = GTMHelper.getInstance().createTagManager();
      try {
         return manager.accounts().containers().workspaces().zones().update(pZone.getPath(), pZone).execute();
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while updating zone.", e);
      }

   }

   /**
    * The Workspace will be updated with given workspace details.
    *
    * @param pWorkspace
    *           the workspace to be updated.
    * @return updated workspace.
    */
   public Workspace updateWorkspace(final Workspace pWorkspace) {
      TagManager manager = GTMHelper.getInstance().createTagManager();
      try {
         return manager.accounts().containers().workspaces().update(pWorkspace.getPath(), pWorkspace).execute();
      } catch (IOException e) {
         throw new GTMUtilityException("Exception occurred while updating workspace.", e);
      }

   }

}
