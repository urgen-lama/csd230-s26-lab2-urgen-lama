package csd230.s26.lab1.pojos;

/**
 * DTO for {@link csd230.s26.lab1.pojos.PianoEntity}
 */
public class Piano extends Instrument {

    private int keyCount;
    private boolean isDigital;
    private Double price;

    public Piano() {
        super();
    }

    public Piano(String brand, String instrumentCondition, int keyCount, boolean isDigital, Double price) {
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

    public boolean isDigital() {
        return isDigital;
    }

    public void setDigital(boolean digital) {
        this.isDigital = digital;
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
        return super.toString() + " -> Piano{" +
                "keyCount=" + keyCount +
                ", isDigital=" + isDigital +
                ", price=" + price +
                '}';
    }
}