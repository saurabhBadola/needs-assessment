package in.sunfox.needs.assesment.authentication.model;

import androidx.annotation.NonNull;

public class Volunteer {

    private String name;
    private String gender;
    private int age;
    private String residenceAddress;
    private String email;
    private String designation;
    private String organisation;
    private String pinCode;
    private String workFieldArea;
    private AttachableField permitData;
    private AttachableField faceShieldData;
    private boolean hasVehicle;
    private boolean hasCovid19Symptoms;
    private boolean hasFoodShortage;
    private String identityProofResourcePath;


    public Volunteer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getWorkFieldArea() {
        return workFieldArea;
    }

    public void setWorkFieldArea(String workFieldArea) {
        this.workFieldArea = workFieldArea;
    }

    public AttachableField getPermitData() {
        return permitData;
    }

    public void setPermitData(AttachableField permitData) {
        this.permitData = permitData;
    }

    public AttachableField getFaceShieldData() {
        return faceShieldData;
    }

    public void setFaceShieldData(AttachableField faceShieldData) {
        this.faceShieldData = faceShieldData;
    }

    public boolean isHasVehicle() {
        return hasVehicle;
    }

    public void setHasVehicle(boolean hasVehicle) {
        this.hasVehicle = hasVehicle;
    }

    public boolean isHasCovid19Symptoms() {
        return hasCovid19Symptoms;
    }

    public void setHasCovid19Symptoms(boolean hasCovid19Symptoms) {
        this.hasCovid19Symptoms = hasCovid19Symptoms;
    }

    public boolean isHasFoodShortage() {
        return hasFoodShortage;
    }

    public void setHasFoodShortage(boolean hasFoodShortage) {
        this.hasFoodShortage = hasFoodShortage;
    }

    public String getIdentityProofResourcePath() {
        return identityProofResourcePath;
    }

    public void setIdentityProofResourcePath(String identityProofResourcePath) {
        this.identityProofResourcePath = identityProofResourcePath;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", residenceAddress='" + residenceAddress + '\'' +
                ", email='" + email + '\'' +
                ", designation='" + designation + '\'' +
                ", organisation='" + organisation + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", workFieldArea='" + workFieldArea + '\'' +
                ", permitData=" + permitData +
                ", faceShieldData=" + faceShieldData +
                ", hasVehicle=" + hasVehicle +
                ", hasCovid19Symptoms=" + hasCovid19Symptoms +
                ", hasFoodShortage=" + hasFoodShortage +
                ", identityProofResourcePath='" + identityProofResourcePath + '\'' +
                '}';
    }

    public static class AttachableField {


        private boolean hasData;
        private String dataResourcePath;

        public AttachableField() {
        }

        public boolean isHasData() {
            return hasData;
        }

        public void setHasData(boolean hasData) {
            this.hasData = hasData;
        }

        public String getDataResourcePath() {
            return dataResourcePath;
        }

        public void setDataResourcePath(String dataResourcePath) {
            this.dataResourcePath = dataResourcePath;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }
    }

}
