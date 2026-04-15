public class AuthService {

    private AuthRepository authRepository = new AuthRepository();

    // 로그인
    public String login(String email, String passwordHash) {
        LoginUser user = authRepository.findUserByEmail(email);

        if (user == null) {
            return "로그인 실패: 해당 이메일 사용자가 없습니다.";
        }

        if (!user.getPasswordHash().equals(passwordHash)) {
            return "로그인 실패: 비밀번호가 일치하지 않습니다.";
        }

        String refreshToken = TokenUtil.createRefreshToken();
        String expiresAt = TokenUtil.createExpiresAtAfterDays(7);

        int result = authRepository.saveSession(user.getId(), refreshToken, expiresAt);

        if (result > 0) {
            return "로그인 성공\nuserId: " + user.getId() +
                   "\nusername: " + user.getUsername() +
                   "\nrefreshToken: " + refreshToken +
                   "\nexpiresAt: " + expiresAt;
        }

        return "로그인 실패: 세션 저장 중 오류";
    }

    // 로그아웃
    public String logout(String refreshToken) {
        int result = authRepository.revokeSession(refreshToken);

        if (result > 0) {
            return "로그아웃 성공";
        }

        return "로그아웃 실패: 유효한 세션이 없거나 이미 로그아웃됨";
    }

    // 토큰 재발급
    public String refresh(String oldRefreshToken) {
        Session session = authRepository.findSessionByRefreshToken(oldRefreshToken);

        if (session == null) {
            return "토큰 재발급 실패: 해당 세션이 없습니다.";
        }

        if (session.getRevokedAt() != null) {
            return "토큰 재발급 실패: 이미 로그아웃된 세션입니다.";
        }

        String newRefreshToken = TokenUtil.createRefreshToken();
        String newExpiresAt = TokenUtil.createExpiresAtAfterDays(7);

        int result = authRepository.updateRefreshToken(session.getId(), newRefreshToken, newExpiresAt);

        if (result > 0) {
            return "토큰 재발급 성공\n새 refreshToken: " + newRefreshToken +
                   "\n새 expiresAt: " + newExpiresAt;
        }

        return "토큰 재발급 실패";
    }
}