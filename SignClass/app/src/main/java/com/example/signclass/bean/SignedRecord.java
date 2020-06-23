package com.example.signclass.bean;

public class SignedRecord {
    private String SignedTime;
    private String SingedLocation;
    private Integer Signed;
    private Integer Notsigned;

    public String getSignedTime() {
        return SignedTime;
    }

    public String getSingedLocation() {
        return SingedLocation;
    }

    public Integer getSigned() {
        return Signed;
    }

    public Integer getNotsigned() {
        return Notsigned;
    }

    public SignedRecord(String signedTime, Integer signed,Integer notsigned) {
        SignedTime = signedTime;
        //SingedLocation = singedLocation;
        Signed = signed;
        Notsigned = notsigned;

    }
}
