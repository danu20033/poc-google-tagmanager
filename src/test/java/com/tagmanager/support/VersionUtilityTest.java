package com.tagmanager.support;

import java.util.List;

import com.google.api.services.tagmanager.model.Account;
import com.google.api.services.tagmanager.model.ContainerVersion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class for version utility.
 */
public class VersionUtilityTest {
   /**
    * VersionUtility instance holder.
    */
   public static VersionUtility versionUtility;

   /**
    * ReaderUtility instance holder.
    */
   public static ReaderUtility readerUtility;

   /**
    * The setup method.
    */
   @BeforeAll
   public static void setUp() {
      versionUtility = new VersionUtility();
      readerUtility = new ReaderUtility();
   }

   /**
    * Test method for get live version.
    */
   @Test
   public void testGetLiveVersion() {
      Account account = getDefaultAccount();
      ContainerVersion containerVersion = versionUtility.getLiveVersion(account);
      Assertions.assertNotNull(containerVersion);
   }

   /**
    * Test method for publish a version.
    */
   @Test
   public void testPublishVersion() {
      Account account = getDefaultAccount();
      ContainerVersion containerVersion = versionUtility.getLiveVersion(account);
      ContainerVersion publishedContainerVersion = versionUtility.publishVersion(containerVersion);
      Assertions.assertNotNull(publishedContainerVersion);
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
