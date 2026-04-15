public class User {
    private int id;
    private String email;
    private String passwordHash;
    private String username;
    private String createdAt;

    public User() {}

    public User(int id, String email, String passwordHash, String username, String createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.username = username;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}