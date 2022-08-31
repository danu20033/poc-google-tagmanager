package com.tagmanager.support;

/**
 * Custom Exception type used in the application.
 */
public class GTMUtilityException extends RuntimeException {

   /**
    * Constructor for message.
    *
    * @param message
    *           the exception message
    */
   public GTMUtilityException(final String message) {
      super(message);
   }

   /**
    * Constructor for specific message with stack trace.
    *
    * @param message
    *           the exception message
    * @param cause
    *           throwable stack trace.
    */
   public GTMUtilityException(final String message, final Throwable cause) {
      super(message, cause);
   }
}
