public class Seller {
    private int id;
    private String name;
    private String contactPhone;
    private String address;

    public Seller(int id, String name, String contactPhone, String address) {
        setId(id);
        setName(name);
        setContactPhone(contactPhone);
        setAddress(address);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
