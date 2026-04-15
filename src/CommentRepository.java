import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {

    // 1. 댓글 목록 조회
    public List<Comment> findByPostId(int postId) {
        List<Comment> comments = new ArrayList<>();

        String sql = """
                SELECT id, post_id, user_id, content, created_at
                FROM comments
                WHERE post_id = ?
                ORDER BY created_at ASC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment(
                            rs.getInt("id"),
                            rs.getInt("post_id"),
                            rs.getInt("user_id"),
                            rs.getString("content"),
                            rs.getString("created_at")
                    );
                    comments.add(comment);
                }
            }

        } catch (SQLException e) {
            System.out.println("[findByPostId] 오류 발생");
            e.printStackTrace();
        }

        return comments;
    }

    // 2. 댓글 작성
    public int save(int postId, int userId, String content) {
        String sql = """
                INSERT INTO comments (id, post_id, user_id, content)
                VALUES (seq_comments.NEXTVAL, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, content);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[save] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // 3. 댓글 수정
    public int update(int commentId, String newContent) {
        String sql = """
                UPDATE comments
                SET content = ?
                WHERE id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newContent);
            pstmt.setInt(2, commentId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[update] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // 4. 댓글 삭제
    public int delete(int commentId) {
        String sql = "DELETE FROM comments WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, commentId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[delete] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }
}