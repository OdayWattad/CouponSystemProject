/**
 * This class provides exceptions for the ones created in the service package
 */
package com.CouponSystemStage2.CouponSystem.Services;

public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
}
