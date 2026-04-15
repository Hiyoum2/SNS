public class LoginUser {
    private int id;
    private String email;
    private String passwordHash;
    private String username;

    public LoginUser(int id, String email, String passwordHash, String username) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getUsername() {
        return username;
    }
}