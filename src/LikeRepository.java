import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeRepository {

    // 1. 좋아요 추가
    public int save(int userId, int postId) {
        String sql = """
                INSERT INTO likes (user_id, post_id)
                VALUES (?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, postId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[save] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // 2. 좋아요 취소
    public int delete(int userId, int postId) {
        String sql = """
                DELETE FROM likes
                WHERE user_id = ? AND post_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, postId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[delete] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // 3. 특정 게시글 좋아요 목록 조회
    public List<Like> findByPostId(int postId) {
        List<Like> likes = new ArrayList<>();

        String sql = """
                SELECT user_id, post_id, created_at
                FROM likes
                WHERE post_id = ?
                ORDER BY created_at ASC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Like like = new Like(
                            rs.getInt("user_id"),
                            rs.getInt("post_id"),
                            rs.getString("created_at")
                    );
                    likes.add(like);
                }
            }

        } catch (SQLException e) {
            System.out.println("[findByPostId] 오류 발생");
            e.printStackTrace();
        }

        return likes;
    }

    // 4. 좋아요 여부 확인
    public boolean exists(int userId, int postId) {
        String sql = """
                SELECT COUNT(*)
                FROM likes
                WHERE user_id = ? AND post_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, postId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("[exists] 오류 발생");
            e.printStackTrace();
        }

        return false;
    }
}