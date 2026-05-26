package csd230.s26.lab1.pojos;

public abstract class Instrument implements SaleableItem {

    private String brand;
    private String instrumentCondition;

    public Instrument() {
    }

    public Instrument(String brand, String instrumentCondition) {
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
        return "Instrument{" +
                "brand='" + brand + '\'' +
                ", instrumentCondition='" + instrumentCondition + '\'' +
                '}';
    }
}