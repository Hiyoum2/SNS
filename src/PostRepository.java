import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    // 1. 게시글 목록 조회
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();

        String sql = """
                SELECT id, user_id, content, created_at, updated_at
                FROM posts
                ORDER BY created_at DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

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

        } catch (SQLException e) {
            System.out.println("[findAll] 오류 발생");
            e.printStackTrace();
        }

        return posts;
    }

    // 2. 게시글 상세 조회
    public Post findById(int postId) {
        String sql = """
                SELECT id, user_id, content, created_at, updated_at
                FROM posts
                WHERE id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Post(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("content"),
                            rs.getString("created_at"),
                            rs.getString("updated_at")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("[findById] 오류 발생");
            e.printStackTrace();
        }

        return null;
    }

    // 3. 게시글 작성
    public int save(int userId, String content) {
        String sql = """
                INSERT INTO posts (id, user_id, content)
                VALUES (seq_posts.NEXTVAL, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, content);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[save] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // 4. 게시글 수정
    public int update(int postId, String newContent) {
        String sql = """
                UPDATE posts
                SET content = ?, updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newContent);
            pstmt.setInt(2, postId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[update] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // 5. 게시글 삭제
    public int delete(int postId) {
        String sql = "DELETE FROM posts WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[delete] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }
}