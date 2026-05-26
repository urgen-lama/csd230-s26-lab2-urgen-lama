package csd230.s26.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GUITAR")
public class GuitarEntity extends InstrumentEntity {

    @Column(name = "string_count", nullable = true)
    private int stringCount;

    @Column(name = "is_electric", nullable = true)
    private boolean isElectric;

    // --- DELETED: private Double price; (To stop variable shadowing) ---

    public GuitarEntity() {
        super();
    }

    public GuitarEntity(String brand, String instrumentCondition, int stringCount, boolean isElectric, Double price) {
        super(brand, instrumentCondition);
        this.stringCount = stringCount;
        this.isElectric = isElectric;
        // Assign price directly to the inherited parent field
        this.setPrice(price);
    }

    public int getStringCount() {
        return stringCount;
    }

    public void setStringCount(int stringCount) {
        this.stringCount = stringCount;
    }

    public boolean getIsElectric() {
        return isElectric;
    }

    public void setIsElectric(boolean isElectric) {
        this.isElectric = isElectric;
    }

    @Override
    public double getPrice() {
        // Fetch from the master inherited method instead of a local variable
        return super.getPrice();
    }

    @Override
    public void sellItem() {
        System.out.println("Selling " + this.getBrand() + " guitar for $" + this.getPrice());
    }

    @Override
    public String toString() {
        return super.toString() + " -> GuitarEntity{" +
                "stringCount=" + stringCount +
                ", isElectric=" + isElectric +
                ", price=" + this.getPrice() +
                '}';
    }
}