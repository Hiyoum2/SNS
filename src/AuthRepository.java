import java.sql.*;

public class AuthRepository {

    // 이메일로 사용자 조회
    public LoginUser findUserByEmail(String email) {
        String sql = "SELECT id, email, password_hash, username " +
                     "FROM users " +
                     "WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new LoginUser(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("username")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("[findUserByEmail] 오류 발생");
            e.printStackTrace();
        }

        return null;
    }

    // 세션 생성
    public int saveSession(int userId, String refreshTokenHash, String expiresAt) {
        String sql = "INSERT INTO sessions (id, user_id, refresh_token_hash, expires_at) " +
                     "VALUES (seq_sessions.NEXTVAL, ?, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'))";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, refreshTokenHash);
            pstmt.setString(3, expiresAt);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[saveSession] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // refresh token으로 세션 조회
    public Session findSessionByRefreshToken(String refreshTokenHash) {
        String sql = "SELECT id, user_id, refresh_token_hash, expires_at, created_at, revoked_at " +
                     "FROM sessions " +
                     "WHERE refresh_token_hash = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, refreshTokenHash);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Session(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("refresh_token_hash"),
                            rs.getString("expires_at"),
                            rs.getString("created_at"),
                            rs.getString("revoked_at")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("[findSessionByRefreshToken] 오류 발생");
            e.printStackTrace();
        }

        return null;
    }

    // 로그아웃 처리
    public int revokeSession(String refreshTokenHash) {
        String sql = "UPDATE sessions " +
                     "SET revoked_at = CURRENT_TIMESTAMP " +
                     "WHERE refresh_token_hash = ? " +
                     "AND revoked_at IS NULL";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, refreshTokenHash);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[revokeSession] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }

    // refresh token 재발급
    public int updateRefreshToken(int sessionId, String newRefreshTokenHash, String newExpiresAt) {
        String sql = "UPDATE sessions " +
                     "SET refresh_token_hash = ?, expires_at = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS') " +
                     "WHERE id = ? AND revoked_at IS NULL";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newRefreshTokenHash);
            pstmt.setString(2, newExpiresAt);
            pstmt.setInt(3, sessionId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("[updateRefreshToken] 오류 발생");
            e.printStackTrace();
        }

        return 0;
    }
}