package lk.ijse.ceylonteapay.dto;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class EmployeeDTO {

    private int id;
    private SimpleStringProperty name;
    private LocalDate dob;
    private SimpleStringProperty nic;
    private SimpleStringProperty address;
    private SimpleStringProperty gender;
    private SimpleStringProperty telNo;

    public EmployeeDTO(int id, String name, LocalDate dob, String nic, String address, String gender, String telNo) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.dob = dob;
        this.nic = new SimpleStringProperty(nic);
        this.address = new SimpleStringProperty(address);
        this.gender = new SimpleStringProperty(gender);
        this.telNo = new SimpleStringProperty(telNo);
    }

    public EmployeeDTO(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
    }

    public EmployeeDTO(String name, LocalDate dob, String nic, String address, String gender, String telNo) {
        this.name =new SimpleStringProperty(name);
        this.dob = dob;
        this.nic = new SimpleStringProperty(nic);
        this.address = new SimpleStringProperty(address);
        this.gender = new SimpleStringProperty(gender);
        this.telNo = new SimpleStringProperty(telNo);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic.get();
    }

    public SimpleStringProperty nicProperty() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic.set(nic);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getGender() {
        return gender.get();
    }

    public SimpleStringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getTelNo() {
        return telNo.get();
    }

    public SimpleStringProperty telNoProperty() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo.set(telNo);
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name=" + name +
                ", dob=" + dob +
                ", nic=" + nic +
                ", address=" + address +
                ", gender=" + gender +
                ", telNo=" + telNo +
                '}';
    }

}
