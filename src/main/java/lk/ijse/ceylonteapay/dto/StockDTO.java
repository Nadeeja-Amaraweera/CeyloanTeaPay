package lk.ijse.ceylonteapay.dto;

import java.time.LocalDate;

public class StockDTO {
    private int id;
    private LocalDate date;
    private String quality;
    private int quantity;
    private int availableQuantity;

    public StockDTO() {
    }


    public StockDTO(int id, LocalDate date, String quality) {
        this.id = id;
        this.date = date;
        this.quality = quality;
    }

    public StockDTO(LocalDate date, String quality, int quantity, int availableQuantity) {
        this.date = date;
        this.quality = quality;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
    }

    public StockDTO(int id, LocalDate date, String quality, int quantity, int availableQuantity) {
        this.id = id;
        this.date = date;
        this.quality = quality;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public String toString() {
        return "StockDTO{" +
                "id=" + id +
                ", date=" + date +
                ", quality='" + quality + '\'' +
                ", quantity=" + quantity +
                ", availableQuantity=" + availableQuantity +
                '}';
    }
}
