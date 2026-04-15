import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    // 1. 회원가입
    public int save(String email, String passwordHash, String username) {
        String sql = """
                INSERT INTO users (id, email, password_hash, username)
                VALUES (seq_users.NEXTVAL, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, passwordHash);
            pstmt.setString(3, username);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[save] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // 2. 사용자 정보 조회
    public User findById(int userId) {
        String sql = """
                SELECT id, email, password_hash, username, created_at
                FROM users
                WHERE id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("username"),
                            rs.getString("created_at")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("[findById] 오류 발생");
            e.printStackTrace();
        }

        return null;
    }

    // 3. 사용자 정보 수정
    public int update(int userId, String newEmail, String newPasswordHash, String newUsername) {
        String sql = """
                UPDATE users
                SET email = ?, password_hash = ?, username = ?
                WHERE id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newEmail);
            pstmt.setString(2, newPasswordHash);
            pstmt.setString(3, newUsername);
            pstmt.setInt(4, userId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[update] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // 4. 내 게시글 조회
    public List<Post> findPostsByUserId(int userId) {
        List<Post> posts = new ArrayList<>();

        String sql = """
                SELECT id, user_id, content, created_at, updated_at
                FROM posts
                WHERE user_id = ?
                ORDER BY created_at DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("content"),
                            rs.getString("created_at"),
                            rs.getString("updated_at")
                    );
                    posts.add(post);
                }
            }

        } catch (SQLException e) {
            System.out.println("[findPostsByUserId] 오류 발생");
            e.printStackTrace();
        }

        return posts;
    }
}