package com.example.mat.blood.pojo;

/**
 * Created by allu on 6/21/17.
 */

public class BloodRequest {
    private String bloodGroup,userUid,userName,hospitalName,timeStamp;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public BloodRequest(String bloodGroup, String userUid, String userName, String hospitalName, String timeStamp) {
        this.bloodGroup = bloodGroup;
        this.userUid = userUid;
        this.userName = userName;
        this.hospitalName = hospitalName;
        this.timeStamp = timeStamp;
    }
}
