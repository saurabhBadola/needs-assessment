package in.sunfox.needs.assesment.beneficiary;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity.TAG;

public class Beneficiary {

    private enum Employment {
        SELF_EMPLOYED,
        UNEMPLOYED,
        GOVERNMENT_SECTOR,
        PRIVATE_SECTOR
    }

    private enum DataType {
        AUDIO,
        FILE,
        TEXT
    }


    private String name;
    private List<FamilyMember> familyMembers;
    private String phone;
    private String pinCode;
    private String ward;
    private String gramPanchayat;
    private String Mandal;
    private String address;
    private String recipientOfAnyScheme;
    private String needsDescription;
    private String TasksDone;
    private String IdentityProofUrl;
    private String referrerVolunteerPhoneNumber;

    public Beneficiary() {
        familyMembers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getGramPanchayat() {
        return gramPanchayat;
    }

    public void setGramPanchayat(String gramPanchayat) {
        this.gramPanchayat = gramPanchayat;
    }

    public String getMandal() {
        return Mandal;
    }

    public void setMandal(String mandal) {
        Mandal = mandal;
        Log.d(TAG, "setMandal: "+mandal);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRecipientOfAnyScheme() {
        return recipientOfAnyScheme;
    }

    public void setRecipientOfAnyScheme(String recipientOfAnyScheme) {
        this.recipientOfAnyScheme = recipientOfAnyScheme;
    }

    public String getNeedsDescription() {
        return needsDescription;
    }

    public void setNeedsDescription(String needsDescription) {
        this.needsDescription = needsDescription;
    }

    public String getTasksDone() {
        return TasksDone;
    }

    public void setTasksDone(String tasksDone) {
        TasksDone = tasksDone;
    }

    public String getIdentityProofUrl() {
        return IdentityProofUrl;
    }

    public void setIdentityProofUrl(String identityProofUrl) {
        IdentityProofUrl = identityProofUrl;
    }

    public String getReferrerVolunteerPhoneNumber() {
        return referrerVolunteerPhoneNumber;
    }

    public void setReferrerVolunteerPhoneNumber(String referrerVolunteerPhoneNumber) {
        this.referrerVolunteerPhoneNumber = referrerVolunteerPhoneNumber;
    }


    //    class MultiTypeData {
//        private DataType dataType;
//        private String resource;
//
//        public MultiTypeData() {
//        }
//
//        public MultiTypeData(DataType dataType, String resource) {
//            this.dataType = dataType;
//            this.resource = resource;
//        }
//
//        public DataType getDataType() {
//            return dataType;
//        }
//
//        public void setDataType(DataType dataType) {
//            this.dataType = dataType;
//        }
//
//        public String getResource() {
//            return resource;
//        }
//
//        public void setResource(String resource) {
//            this.resource = resource;
//        }
//    }


    public static class FamilyMember {

        private String name;
        private String employment;

       public FamilyMember() {
        }

        public String getEmployment() {
            return employment;
        }

        public void setEmployment(String employment) {
            this.employment = employment;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "FamilyMember{" +
                    "name='" + name + '\'' +
                    ", employment='" + employment + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "Beneficiary{" +
                "name='" + name + '\'' +
                ", familyMembers=" + familyMembers +
                ", phone='" + phone + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", ward='" + ward + '\'' +
                ", gramPanchayat='" + gramPanchayat + '\'' +
                ", Mandal='" + Mandal + '\'' +
                ", address='" + address + '\'' +
                ", recipientOfAnyScheme='" + recipientOfAnyScheme + '\'' +
                ", needsDescription='" + needsDescription + '\'' +
                ", TasksDone='" + TasksDone + '\'' +
                ", IdentityProofUrl='" + IdentityProofUrl + '\'' +
                ", referrerVolunteerPhoneNumber='" + referrerVolunteerPhoneNumber + '\'' +
                '}';
    }
}
