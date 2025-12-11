package lk.ijse.ceylonteapay.dto;

public class PaymentDTO {
    private int paymentId;
    private int empId;
    private String empName;
    private double teaSalary;
    private double expenseSalary;
    private double finalSalary;

    public PaymentDTO() {
    }

    public PaymentDTO(int empId, String empName, double teaSalary, double expenseSalary, double finalSalary) {
        this.empId = empId;
        this.empName = empName;
        this.teaSalary = teaSalary;
        this.expenseSalary = expenseSalary;
        this.finalSalary = finalSalary;
    }

    public PaymentDTO(int paymentId, int empId, String empName, double teaSalary, double expenseSalary, double finalSalary) {
        this.paymentId = paymentId;
        this.empId = empId;
        this.empName = empName;
        this.teaSalary = teaSalary;
        this.expenseSalary = expenseSalary;
        this.finalSalary = finalSalary;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "paymentId=" + paymentId +
                ", empId=" + empId +
                ", empName='" + empName + '\'' +
                ", teaSalary=" + teaSalary +
                ", expenseSalary=" + expenseSalary +
                ", finalSalary=" + finalSalary +
                '}';
    }
}
