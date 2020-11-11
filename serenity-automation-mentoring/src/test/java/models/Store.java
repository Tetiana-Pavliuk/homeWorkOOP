package models;

public class Store {
    private long id;
    private long petId;
    private long quantity;
    private String shipDate;
    private String status;
    private boolean complete;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public static Store createStore() {
        Store store = new Store();
        store.setId(1);
        store.setQuantity(1);
        store.setShipDate("2020-08-17T19:14:36.799Z");
        store.setStatus("placed");
        store.setComplete(false);
        return store;
    }

}