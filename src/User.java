public class User {
    private int id;
    private String username;
    private String phone;
    private String location;
    private String registeringDate;
    private int purchasedAmount;

    public User(int id, String username, String phone, String location, String registeringDate, int purchasedAmount) {
        setId(id);
        setUsername(username);
        setPhone(phone);
        setLocation(location);
        setRegisteringDate(registeringDate);
        setPurchasedAmount(purchasedAmount);
    }

    public String displayInfo() {
        return  "-----------------------[USER CARD]-----------------------| ID: " + getId() + "\n" +
                "Name: " + getUsername() + "\n" +
                "Phone number: " + getPhone() + "\n" +
                "Address: " + getLocation() + "\n" +
                "Registered science: " + getRegisteringDate() + "\n" +
                "Purchased transport amount:" + getPurchasedAmount() + "\n" +
                "-----------------------------------------------------------------------";
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegisteringDate() {
        return this.registeringDate;
    }

    public void setRegisteringDate(String registeringDate) {
        this.registeringDate = registeringDate;
    }

    public int getPurchasedAmount() {
        return this.purchasedAmount;
    }

    public void setPurchasedAmount(int purchasedAmount) {
        this.purchasedAmount = purchasedAmount;
    }
}
