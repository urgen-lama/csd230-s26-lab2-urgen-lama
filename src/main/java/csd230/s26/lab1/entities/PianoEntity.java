package csd230.s26.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PIANO")
public class PianoEntity extends InstrumentEntity {

    @Column(name = "key_count", nullable = true)
    private int keyCount;

    @Column(name = "is_digital", nullable = true)
    private boolean isDigital;

    @Column(name = "price")
    private Double price;

    public PianoEntity() {
        super();
    }

    public PianoEntity(String brand, String instrumentCondition, int keyCount, boolean isDigital, Double price) {
        super(brand, instrumentCondition);
        this.keyCount = keyCount;
        this.isDigital = isDigital;
        this.price = price;
    }

    public int getKeyCount() {
        return keyCount;
    }

    public void setKeyCount(int keyCount) {
        this.keyCount = keyCount;
    }

    public boolean getIsDigital() {
        return isDigital;
    }

    public void setIsDigital(boolean isDigital) {
        this.isDigital = isDigital;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void sellItem() {
        System.out.println("Selling " + this.getBrand() + " piano for $" + this.price);
    }

    @Override
    public String toString() {
        return super.toString() + " -> PianoEntity{" +
                "keyCount=" + keyCount +
                ", isDigital=" + isDigital +
                ", price=" + price +
                '}';
    }
}