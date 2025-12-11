package lk.ijse.ceylonteapay.dto;

import java.time.LocalDate;

public class DailyTeaDTO {
    private int teaID;
    private int empID;
    private String empName;
    private int lndID;
    private String lndName;
    private LocalDate dateCollected;
    private double fullWeight;
    private double bagWeight;
    private double waterWeight;
    private double totalWeight;
    private String Quality;

    public DailyTeaDTO() {
    }

    public DailyTeaDTO(int teaID, int empID, String empName, int lndID, String lndName, LocalDate dateCollected, double fullWeight, double bagWeight, double waterWeight, double totalWeight, String quality) {
        this.teaID = teaID;
        this.empID = empID;
        this.empName = empName;
        this.lndID = lndID;
        this.lndName = lndName;
        this.dateCollected = dateCollected;
        this.fullWeight = fullWeight;
        this.bagWeight = bagWeight;
        this.waterWeight = waterWeight;
        this.totalWeight = totalWeight;
        Quality = quality;
    }

    public DailyTeaDTO(int teaID, int empID, int lndID, LocalDate dateCollected, double fullWeight, double bagWeight, double waterWeight, double totalWeight, String quality) {
        this.teaID = teaID;
        this.empID = empID;
        this.lndID = lndID;
        this.dateCollected = dateCollected;
        this.fullWeight = fullWeight;
        this.bagWeight = bagWeight;
        this.waterWeight = waterWeight;
        this.totalWeight = totalWeight;
        Quality = quality;
    }

    public DailyTeaDTO(int empID, int lndID, LocalDate dateCollected, double fullWeight, double bagWeight, double waterWeight, double totalWeight, String quality) {
        this.empID = empID;
        this.lndID = lndID;
        this.dateCollected = dateCollected;
        this.fullWeight = fullWeight;
        this.bagWeight = bagWeight;
        this.waterWeight = waterWeight;
        this.totalWeight = totalWeight;
        Quality = quality;
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

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getTeaID() {
        return teaID;
    }

    public void setTeaID(int teaID) {
        this.teaID = teaID;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getLndID() {
        return lndID;
    }

    public void setLndID(int lndID) {
        this.lndID = lndID;
    }

    public LocalDate getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(LocalDate dateCollected) {
        this.dateCollected = dateCollected;
    }

    public double getFullWeight() {
        return fullWeight;
    }

    public void setFullWeight(double fullWeight) {
        this.fullWeight = fullWeight;
    }

    public double getBagWeight() {
        return bagWeight;
    }

    public void setBagWeight(double bagWeight) {
        this.bagWeight = bagWeight;
    }

    public double getWaterWeight() {
        return waterWeight;
    }

    public void setWaterWeight(double waterWeight) {
        this.waterWeight = waterWeight;
    }

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String quality) {
        Quality = quality;
    }

    @Override
    public String toString() {
        return "TeaDTO{" +
                "teaID=" + teaID +
                ", empID=" + empID +
                ", lndID=" + lndID +
                ", dateCollected=" + dateCollected +
                ", fullWeight=" + fullWeight +
                ", bagWeight=" + bagWeight +
                ", waterWeight=" + waterWeight +
                ", totalWeight=" + totalWeight +
                ", Quality='" + Quality + '\'' +
                '}';
    }
}
