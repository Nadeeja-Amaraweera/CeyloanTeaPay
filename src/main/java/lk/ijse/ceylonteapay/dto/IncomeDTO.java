package lk.ijse.ceylonteapay.dto;

public class IncomeDTO {
    private int incomeId;
    private int paymentId;
    private String month;
    private int year;
    private double teaSalary;
    private double otherWorkSalary;
    private double thisMonthIncome;
    private double finalIncome;

    public IncomeDTO() {
    }

    public IncomeDTO(int paymentId, String month, int year, double teaSalary, double otherWorkSalary, double thisMonthIncome, double finalIncome) {
        this.paymentId = paymentId;
        this.month = month;
        this.year = year;
        this.teaSalary = teaSalary;
        this.otherWorkSalary = otherWorkSalary;
        this.thisMonthIncome = thisMonthIncome;
        this.finalIncome = finalIncome;
    }

    public IncomeDTO(int incomeId, int paymentId, String month, int year, double teaSalary, double otherWorkSalary, double thisMonthIncome, double finalIncome) {
        this.incomeId = incomeId;
        this.paymentId = paymentId;
        this.month = month;
        this.year = year;
        this.teaSalary = teaSalary;
        this.otherWorkSalary = otherWorkSalary;
        this.thisMonthIncome = thisMonthIncome;
        this.finalIncome = finalIncome;
    }

    public IncomeDTO(double teaSalary, double otherWorkSalary) {
        this.teaSalary = teaSalary;
        this.otherWorkSalary = otherWorkSalary;
    }

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getTeaSalary() {
        return teaSalary;
    }

    public void setTeaSalary(double teaSalary) {
        this.teaSalary = teaSalary;
    }

    public double getOtherWorkSalary() {
        return otherWorkSalary;
    }

    public void setOtherWorkSalary(double otherWorkSalary) {
        this.otherWorkSalary = otherWorkSalary;
    }

    public double getThisMonthIncome() {
        return thisMonthIncome;
    }

    public void setThisMonthIncome(double thisMonthIncome) {
        this.thisMonthIncome = thisMonthIncome;
    }

    public double getFinalIncome() {
        return finalIncome;
    }

    public void setFinalIncome(double finalIncome) {
        this.finalIncome = finalIncome;
    }

    @Override
    public String toString() {
        return "IncomeDTO{" +
                "incomeId=" + incomeId +
                ", paymentId=" + paymentId +
                ", month='" + month + '\'' +
                ", year=" + year +
                ", teaSalary=" + teaSalary +
                ", otherWorkSalary=" + otherWorkSalary +
                ", thisMonthIncome=" + thisMonthIncome +
                ", finalIncome=" + finalIncome +
                '}';
    }
}
