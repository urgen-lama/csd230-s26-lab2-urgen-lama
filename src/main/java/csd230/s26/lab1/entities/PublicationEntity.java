package csd230.s26.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PUBLICATION")
public abstract class PublicationEntity extends ProductEntity {
    private String title;
    private int copies;

    public PublicationEntity() {
        super();
        setTitle("Publication");
        setCopies(0);
    }

    public PublicationEntity(String title, double price, int copies) {
        super();
        setPrice(price);  // price goes up to ProductEntity
        this.title = title;
        this.copies = copies;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }
}