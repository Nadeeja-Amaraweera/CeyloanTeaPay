package lk.ijse.ceylonteapay.dto;

import java.time.LocalDate;

public class DeliveryTeaDTO {
    private int deliveryId;
    private int deliveryFactoryId;
    private String deliveryFactoryName;
    private LocalDate deliveryDate;

    public DeliveryTeaDTO() {
    }

    public DeliveryTeaDTO(int deliveryFactoryId, String deliveryFactoryName, LocalDate deliveryDate) {
        this.deliveryFactoryId = deliveryFactoryId;
        this.deliveryFactoryName = deliveryFactoryName;
        this.deliveryDate = deliveryDate;
    }

    public DeliveryTeaDTO(int deliveryId, int deliveryFactoryId, String deliveryFactoryName, LocalDate deliveryDate) {
        this.deliveryId = deliveryId;
        this.deliveryFactoryId = deliveryFactoryId;
        this.deliveryFactoryName = deliveryFactoryName;
        this.deliveryDate = deliveryDate;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getDeliveryFactoryId() {
        return deliveryFactoryId;
    }

    public void setDeliveryFactoryId(int deliveryFactoryId) {
        this.deliveryFactoryId = deliveryFactoryId;
    }

    public String getDeliveryFactoryName() {
        return deliveryFactoryName;
    }

    public void setDeliveryFactoryName(String deliveryFactoryName) {
        this.deliveryFactoryName = deliveryFactoryName;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "DeliveryTeaDTO{" +
                "deliveryId=" + deliveryId +
                ", deliveryFactoryId=" + deliveryFactoryId +
                ", deliveryFactoryName='" + deliveryFactoryName + '\'' +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}
