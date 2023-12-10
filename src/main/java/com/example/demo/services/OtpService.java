package com.example.demo.services;

public interface OtpService {
    public String generateOtp();

    public void storeOtp(String phoneNumber);

    public String getOtp(String phoneNumber);

    public boolean isValid(String phoneNumber);
}
