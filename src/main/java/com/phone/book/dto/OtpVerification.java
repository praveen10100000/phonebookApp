package com.phone.book.dto;

public class OtpVerification {
    
     private String phoneNumber ;
     private String countryCode ;
     private int otp ;
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public int getOtp() {
        return otp;
    }
    public void setOtp(int otp) {
        this.otp = otp;
    }
    @Override
    public String toString() {
        return "OtpVerification [countryCode=" + countryCode + ", otp=" + otp + ", phoneNumber=" + phoneNumber + "]";
    }
    public OtpVerification(String phoneNumber, String countryCode, int otp) {
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.otp = otp;
    } 
     
    public OtpVerification()
    {
        
    }
     
}
