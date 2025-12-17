package lk.ijse.ceylonteapay.dto;

import java.time.LocalDate;

public class DeliveryCartTM {
    private int stockId;
    private int factoryId;
    private String factoryName;
    private int qty;
    private LocalDate date;

    public DeliveryCartTM() {
    }

    public DeliveryCartTM(int stockId, int factoryId, String factoryName, int qty, LocalDate date) {
        this.stockId = stockId;
        this.factoryId = factoryId;
        this.factoryName = factoryName;
        this.qty = qty;
        this.date = date;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DeliveryCartTM{" +
                "stockId=" + stockId +
                ", factoryId=" + factoryId +
                ", factoryName='" + factoryName + '\'' +
                ", qty=" + qty +
                ", date=" + date +
                '}';
    }
}
