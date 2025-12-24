package lk.ijse.ceylonteapay.dto;

import java.time.LocalDate;

public class PaymentDTO {
    private int paymentId;
    private int rateId;
    private int EmployeeId;
    private String EmployeeName;
    private double teaSalary;
    private double expenseSalary;
    private double finalSalary;
    private LocalDate date;

    public PaymentDTO() {
    }

    public PaymentDTO(int paymentId, int rateId, int employeeId, String employeeName, double teaSalary, double expenseSalary, double finalSalary, LocalDate date) {
        this.paymentId = paymentId;
        this.rateId = rateId;
        EmployeeId = employeeId;
        EmployeeName = employeeName;
        this.teaSalary = teaSalary;
        this.expenseSalary = expenseSalary;
        this.finalSalary = finalSalary;
        this.date = date;
    }

    public PaymentDTO(int rateId, int employeeId, String employeeName, double teaSalary, double expenseSalary, double finalSalary, LocalDate date) {
        this.rateId = rateId;
        EmployeeId = employeeId;
        EmployeeName = employeeName;
        this.teaSalary = teaSalary;
        this.expenseSalary = expenseSalary;
        this.finalSalary = finalSalary;
        this.date = date;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int employeeId) {
        EmployeeId = employeeId;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public double getTeaSalary() {
        return teaSalary;
    }

    public void setTeaSalary(double teaSalary) {
        this.teaSalary = teaSalary;
    }

    public double getExpenseSalary() {
        return expenseSalary;
    }

    public void setExpenseSalary(double expenseSalary) {
        this.expenseSalary = expenseSalary;
    }

    public double getFinalSalary() {
        return finalSalary;
    }

    public void setFinalSalary(double finalSalary) {
        this.finalSalary = finalSalary;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "paymentId=" + paymentId +
                ", rateId=" + rateId +
                ", EmployeeId=" + EmployeeId +
                ", EmployeeName='" + EmployeeName + '\'' +
                ", teaSalary=" + teaSalary +
                ", expenseSalary=" + expenseSalary +
                ", finalSalary=" + finalSalary +
                ", date=" + date +
                '}';
    }
}
