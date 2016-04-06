package es.us.lsi.smartcitizen.entity;


public class UserDomainEntity {

    private int userId;
    private String email;

    public UserDomainEntity(int userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
