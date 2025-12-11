package lk.ijse.ceylonteapay.dto;

import java.time.LocalDate;

public class OtherWorkDTO {
    private int workID;
    private int emp_ID;
    private String empName;
    private int lnd_Id;
    private String lndName;
    private LocalDate date;
    private String details;

    public OtherWorkDTO() {
    }

    public OtherWorkDTO(int emp_ID, int lnd_Id, LocalDate date, String details) {
        this.emp_ID = emp_ID;
        this.lnd_Id = lnd_Id;
        this.date = date;
        this.details = details;
    }

    public OtherWorkDTO(int workID, int emp_ID, int lnd_Id, LocalDate date, String details) {
        this.workID = workID;
        this.emp_ID = emp_ID;
        this.lnd_Id = lnd_Id;
        this.date = date;
        this.details = details;
    }

    public OtherWorkDTO(int workID, int emp_ID, String empName, int lnd_Id, String lndName, LocalDate date, String details) {
        this.workID = workID;
        this.emp_ID = emp_ID;
        this.empName = empName;
        this.lnd_Id = lnd_Id;
        this.lndName = lndName;
        this.date = date;
        this.details = details;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getLndName() {
        return lndName;
    }

    public void setLndName(String lndName) {
        this.lndName = lndName;
    }

    public int getWorkID() {
        return workID;
    }

    public void setWorkID(int workID) {
        this.workID = workID;
    }

    public int getEmp_ID() {
        return emp_ID;
    }

    public void setEmp_ID(int emp_ID) {
        this.emp_ID = emp_ID;
    }

    public int getLnd_Id() {
        return lnd_Id;
    }

    public void setLnd_Id(int lnd_Id) {
        this.lnd_Id = lnd_Id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "OtherWorkDTO{" +
                "workID=" + workID +
                ", emp_ID=" + emp_ID +
                ", lnd_id=" + lnd_Id +
                ", date=" + date +
                ", details='" + details + '\'' +
                '}';
    }
}
