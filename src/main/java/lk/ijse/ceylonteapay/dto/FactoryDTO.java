package lk.ijse.ceylonteapay.dto;

public class FactoryDTO {
    private int factoryId;
    private String factoryName;
    private String factoryAddress;

    public FactoryDTO() {
    }

    public FactoryDTO(String factoryName, String factoryAddress) {
        this.factoryName = factoryName;
        this.factoryAddress = factoryAddress;
    }

    public FactoryDTO(int factoryId, String factoryName, String factoryAddress) {
        this.factoryId = factoryId;
        this.factoryName = factoryName;
        this.factoryAddress = factoryAddress;
    }

    public int getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryAddress() {
        return factoryAddress;
    }

    public void setFactoryAddress(String factoryAddress) {
        this.factoryAddress = factoryAddress;
    }

    @Override
    public String toString() {
        return "FactoryDTO{" +
                "factoryId=" + factoryId +
                ", factoryName='" + factoryName + '\'' +
                ", factoryAddress='" + factoryAddress + '\'' +
                '}';
    }
}
