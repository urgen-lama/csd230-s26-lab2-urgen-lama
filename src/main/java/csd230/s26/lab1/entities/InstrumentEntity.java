package csd230.s26.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public abstract class InstrumentEntity extends ProductEntity {

    @Column(name = "brand")
    private String brand;

    @Column(name = "instrument_condition")
    private String instrumentCondition;

    public InstrumentEntity() {
        super();
    }

    public InstrumentEntity(String brand, String instrumentCondition) {
        super();
        this.brand = brand;
        this.instrumentCondition = instrumentCondition;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getInstrumentCondition() {
        return instrumentCondition;
    }

    public void setInstrumentCondition(String instrumentCondition) {
        this.instrumentCondition = instrumentCondition;
    }

    @Override
    public String toString() {
        return super.toString() + " -> InstrumentEntity{" +
                "brand='" + brand + '\'' +
                ", instrumentCondition='" + instrumentCondition + '\'' +
                '}';
    }
}