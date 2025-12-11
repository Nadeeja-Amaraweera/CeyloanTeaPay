package lk.ijse.ceylonteapay.dto;

public class IncomeDTO {
    private int incomeId;
    private int paymentId;
    private double pastMonthIncome;
    private double amount;

    public IncomeDTO() {
    }

    public IncomeDTO(int incomeId, int paymentId, double pastMonthIncome, double amount) {
        this.incomeId = incomeId;
        this.paymentId = paymentId;
        this.pastMonthIncome = pastMonthIncome;
        this.amount = amount;
    }

    public IncomeDTO(int paymentId, double pastMonthIncome, double amount) {
        this.paymentId = paymentId;
        this.pastMonthIncome = pastMonthIncome;
        this.amount = amount;
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

    public double getPastMonthIncome() {
        return pastMonthIncome;
    }

    public void setPastMonthIncome(double pastMonthIncome) {
        this.pastMonthIncome = pastMonthIncome;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "IncomeDTO{" +
                "incomeId=" + incomeId +
                ", paymentId=" + paymentId +
                ", pastMonthIncome=" + pastMonthIncome +
                ", amount=" + amount +
                '}';
    }
}
