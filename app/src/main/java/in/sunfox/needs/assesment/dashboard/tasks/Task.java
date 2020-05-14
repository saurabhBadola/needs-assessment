package in.sunfox.needs.assesment.dashboard.tasks;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Task implements Serializable {

    public static class Status{
        public static final int PENDING = 0;
        public static final int COMPLETED = 1;
    }

    public static class BeneficiaryTask{
        private String beneficiaryId;
        private boolean taskCompleted;
        private String beneficiaryName;
        private String beneficiaryAddress;
        private String beneficiaryPhone;


        public String getBeneficiaryId() {
            return beneficiaryId;
        }

        public void setBeneficiaryId(String beneficiaryId) {
            this.beneficiaryId = beneficiaryId;
        }

        public boolean isTaskCompleted() {
            return taskCompleted;
        }

        public void setTaskCompleted(boolean taskCompleted) {
            this.taskCompleted = taskCompleted;
        }

        public String getBeneficiaryName() {
            return beneficiaryName;
        }

        public void setBeneficiaryName(String beneficiaryName) {
            this.beneficiaryName = beneficiaryName;
        }

        public String getBeneficiaryAddress() {
            return beneficiaryAddress;
        }

        public void setBeneficiaryAddress(String beneficiaryAddress) {
            this.beneficiaryAddress = beneficiaryAddress;
        }

        public String getBeneficiaryPhone() {
            return beneficiaryPhone;
        }

        public void setBeneficiaryPhone(String beneficiaryPhone) {
            this.beneficiaryPhone = beneficiaryPhone;
        }

        public BeneficiaryTask() {
        }


    }

   private String title;
   private String token;
   private String description;
   private String assignmentDate;
   private int status;
   private List<BeneficiaryTask> beneficiaries;

    public Task() {
    }


    public Task(String title, String token, String description, String assignDate, int status, List<BeneficiaryTask> beneficiaries) {
        this.title = title;
        this.token = token;
        this.description = description;
        this.assignmentDate = assignDate;
        this.status = status;
        this.beneficiaries = beneficiaries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BeneficiaryTask> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<BeneficiaryTask> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", token='" + token + '\'' +
                ", description='" + description + '\'' +
                ", assignmentDate='" + assignmentDate + '\'' +
                ", status=" + status +
                ", beneficiaries=" + beneficiaries +
                '}';
    }
}
