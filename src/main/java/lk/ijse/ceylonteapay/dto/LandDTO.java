package lk.ijse.ceylonteapay.dto;

public class LandDTO {
    private int lndID;
    private String lndName;
    private String lndNo;

    public LandDTO() {
    }

    public LandDTO(int lndID, String lndName, String lndNo) {
        this.lndID = lndID;
        this.lndName = lndName;
        this.lndNo = lndNo;
    }

    public LandDTO(String lndName, String lndNo) {
        this.lndName = lndName;
        this.lndNo = lndNo;
    }

    public int getLndID() {
        return lndID;
    }

    public void setLndID(int lndID) {
        this.lndID = lndID;
    }

    public String getLndName() {
        return lndName;
    }

    public void setLndName(String lndName) {
        this.lndName = lndName;
    }

    public String getLndNo() {
        return lndNo;
    }

    public void setLndNo(String lndNo) {
        this.lndNo = lndNo;
    }

    @Override
    public String toString() {
        return "LandDTO{" +
                "lndID=" + lndID +
                ", lndName='" + lndName + '\'' +
                ", lndNo='" + lndNo + '\'' +
                '}';
    }
}
