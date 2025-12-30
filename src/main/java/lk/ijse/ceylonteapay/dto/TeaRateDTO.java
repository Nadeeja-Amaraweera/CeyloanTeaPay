package lk.ijse.ceylonteapay.dto;

public class TeaRateDTO {
    private int rateId;
    private String month;
    private int year;
    private double rate;

    public TeaRateDTO() {
    }

    public TeaRateDTO(String month, int year, double rate) {
        this.month = month;
        this.year = year;
        this.rate = rate;
    }



    public TeaRateDTO(int rateId, String month, int year, double rate) {
        this.rateId = rateId;
        this.month = month;
        this.year = year;
        this.rate = rate;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "TeaRateDTO{" +
                "rateId=" + rateId +
                ", month='" + month + '\'' +
                ", year=" + year +
                ", rate=" + rate +
                '}';
    }
}
