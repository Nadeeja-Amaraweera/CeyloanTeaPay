package lk.ijse.ceylonteapay.dto;

import java.time.LocalDate;

public class DeliveryTeaDTO {
    private int deliveryId;
    private int deliveryFactoryId;
    private String deliveryFactoryName;
    private int stockId;
    private LocalDate deliveryDate;
    private int quantity;

    public DeliveryTeaDTO() {
    }

    public DeliveryTeaDTO(int deliveryFactoryId, int stockId, String deliveryFactoryName, LocalDate deliveryDate, int quantity) {
        this.deliveryFactoryId = deliveryFactoryId;
        this.stockId = stockId;
        this.deliveryFactoryName = deliveryFactoryName;
        this.deliveryDate = deliveryDate;
        this.quantity = quantity;
    }

    public DeliveryTeaDTO(int deliveryId, int deliveryFactoryId, int stockId, String deliveryFactoryName, LocalDate deliveryDate, int quantity) {
        this.deliveryId = deliveryId;
        this.deliveryFactoryId = deliveryFactoryId;
        this.stockId = stockId;
        this.deliveryFactoryName = deliveryFactoryName;
        this.deliveryDate = deliveryDate;
        this.quantity = quantity;
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

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "DeliveryTeaDTO{" +
                "deliveryId=" + deliveryId +
                ", deliveryFactoryId=" + deliveryFactoryId +
                ", stockId=" + stockId +
                ", deliveryFactoryName='" + deliveryFactoryName + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", quantity=" + quantity +
                '}';
    }
}
