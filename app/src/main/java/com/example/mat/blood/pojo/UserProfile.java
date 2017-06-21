package com.example.mat.blood.pojo;

/**
 * Created by allu on 6/18/17.
 */

public class UserProfile {
    private String Uid,UserName,EmailId,PhotoUrl; // basic

    private String FullName,BloodGroup,AddressLine1,AddressLine2,City,State;

    public UserProfile(String uid, String userName, String emailId, String photoUrl) {
        Uid = uid;
        UserName = userName;
        EmailId = emailId;
        PhotoUrl = photoUrl;
    }

    public UserProfile(String uid, String userName, String emailId, String photoUrl, String fullName, String bloodGroup, String addressLine1, String addressLine2, String city, String state) {
        Uid = uid;
        UserName = userName;
        EmailId = emailId;
        PhotoUrl = photoUrl;
        FullName = fullName;
        BloodGroup = bloodGroup;
        AddressLine1 = addressLine1;
        AddressLine2 = addressLine2;
        City = city;
        State = state;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        AddressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        AddressLine2 = addressLine2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
