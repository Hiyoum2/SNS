public class Like {
    private int userId;
    private int postId;
    private String createdAt;

    public Like() {}

    public Like(int userId, int postId, String createdAt) {
        this.userId = userId;
        this.postId = postId;
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Like{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}