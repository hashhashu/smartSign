package com.example.signclass.bean;

public class SignedRecord {
    private String SignedTime;
    private String SingedLocation;

    public String getSignedTime() {
        return SignedTime;
    }

    public String getSingedLocation() {
        return SingedLocation;
    }

    public SignedRecord(String signedTime, String singedLocation) {
        SignedTime = signedTime;
        SingedLocation = singedLocation;
    }
}
