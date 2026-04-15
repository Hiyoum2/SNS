import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FollowRepository {

    // 1. 팔로우
    public int save(int followerId, int followingId) {
        String sql = """
                INSERT INTO follows (follower_id, following_id)
                VALUES (?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, followerId);
            pstmt.setInt(2, followingId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[save] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // 2. 언팔로우
    public int delete(int followerId, int followingId) {
        String sql = """
                DELETE FROM follows
                WHERE follower_id = ? AND following_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, followerId);
            pstmt.setInt(2, followingId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[delete] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // 3. 내가 팔로우한 사람 목록 조회
    public List<Follow> findFollowingByUserId(int followerId) {
        List<Follow> follows = new ArrayList<>();

        String sql = """
                SELECT follower_id, following_id, created_at
                FROM follows
                WHERE follower_id = ?
                ORDER BY created_at ASC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, followerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Follow follow = new Follow(
                            rs.getInt("follower_id"),
                            rs.getInt("following_id"),
                            rs.getString("created_at")
                    );
                    follows.add(follow);
                }
            }

        } catch (SQLException e) {
            System.out.println("[findFollowingByUserId] 오류 발생");
            e.printStackTrace();
        }

        return follows;
    }

    // 4. 나를 팔로우한 사람 목록 조회
    public List<Follow> findFollowersByUserId(int followingId) {
        List<Follow> follows = new ArrayList<>();

        String sql = """
                SELECT follower_id, following_id, created_at
                FROM follows
                WHERE following_id = ?
                ORDER BY created_at ASC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, followingId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Follow follow = new Follow(
                            rs.getInt("follower_id"),
                            rs.getInt("following_id"),
                            rs.getString("created_at")
                    );
                    follows.add(follow);
                }
            }

        } catch (SQLException e) {
            System.out.println("[findFollowersByUserId] 오류 발생");
            e.printStackTrace();
        }

        return follows;
    }

    // 5. 이미 팔로우했는지 확인
    public boolean exists(int followerId, int followingId) {
        String sql = """
                SELECT COUNT(*)
                FROM follows
                WHERE follower_id = ? AND following_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, followerId);
            pstmt.setInt(2, followingId);

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