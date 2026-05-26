package csd230.s26.lab1.pojos;

public class Guitar extends Instrument {

    private int stringCount;
    private boolean isElectric;
    private Double price;

    public Guitar() {
        super();
    }

    public Guitar(String brand, String instrumentCondition, int stringCount, boolean isElectric, Double price) {
        super(brand, instrumentCondition);
        this.stringCount = stringCount;
        this.isElectric = isElectric;
        this.price = price;
    }

    public int getStringCount() {
        return stringCount;
    }

    public void setStringCount(int stringCount) {
        this.stringCount = stringCount;
    }

    public boolean isElectric() {
        return isElectric;
    }

    public void setElectric(boolean electric) {
        this.isElectric = electric;
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
        System.out.println("Selling " + this.getBrand() + " guitar for $" + this.price);
    }

    @Override
    public String toString() {
        return super.toString() + " -> Guitar{" +
                "stringCount=" + stringCount +
                ", isElectric=" + isElectric +
                ", price=" + price +
                '}';
    }
}