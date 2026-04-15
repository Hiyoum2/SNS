public class Session {
    private int id;
    private int userId;
    private String refreshTokenHash;
    private String expiresAt;
    private String createdAt;
    private String revokedAt;

    public Session() {
    }

    public Session(int id, int userId, String refreshTokenHash, String expiresAt, String createdAt, String revokedAt) {
        this.id = id;
        this.userId = userId;
        this.refreshTokenHash = refreshTokenHash;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getRefreshTokenHash() {
        return refreshTokenHash;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRevokedAt() {
        return revokedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRefreshTokenHash(String refreshTokenHash) {
        this.refreshTokenHash = refreshTokenHash;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setRevokedAt(String revokedAt) {
        this.revokedAt = revokedAt;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", userId=" + userId +
                ", refreshTokenHash='" + refreshTokenHash + '\'' +
                ", expiresAt='" + expiresAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", revokedAt='" + revokedAt + '\'' +
                '}';
    }
}