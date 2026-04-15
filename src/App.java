import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PostRepository postRepository = new PostRepository();
        CommentRepository commentRepository = new CommentRepository();
        LikeRepository likeRepository = new LikeRepository();
        UserRepository userRepository = new UserRepository();
        FollowRepository followRepository = new FollowRepository();
        AuthService authService = new AuthService();

        while (true) {
            System.out.println("\n===== 메인 메뉴 =====");
            System.out.println("1. 게시글");
            System.out.println("2. 댓글");
            System.out.println("3. 좋아요");
            System.out.println("4. 사용자");
            System.out.println("5. 팔로우");
            System.out.println("6. 세션/인증");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int mainMenu = Integer.parseInt(scanner.nextLine());

            switch (mainMenu) {
                case 1:
                    postMenu(scanner, postRepository);
                    break;
                case 2:
                    commentMenu(scanner, commentRepository);
                    break;
                case 3:
                    likeMenu(scanner, likeRepository);
                    break;
                case 4:
                    userMenu(scanner, userRepository);
                    break;
                case 5:
                    followMenu(scanner, followRepository);
                    break;
                case 6:
                    authMenu(scanner, authService);
                    break;
                case 0:
                    System.out.println("프로그램 종료");
                    scanner.close();
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }
    }

    private static void postMenu(Scanner scanner, PostRepository postRepository) {
        while (true) {
            System.out.println("\n===== 게시글 메뉴 =====");
            System.out.println("1. 게시글 목록 조회");
            System.out.println("2. 게시글 상세 조회");
            System.out.println("3. 게시글 작성");
            System.out.println("4. 게시글 수정");
            System.out.println("5. 게시글 삭제");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int menu = Integer.parseInt(scanner.nextLine());

            switch (menu) {
                case 1:
                    List<Post> posts = postRepository.findAll();
                    if (posts.isEmpty()) {
                        System.out.println("게시글이 없습니다.");
                    } else {
                        for (Post post : posts) {
                            System.out.println(post);
                        }
                    }
                    break;

                case 2:
                    System.out.print("조회할 게시글 ID: ");
                    int postId = Integer.parseInt(scanner.nextLine());

                    Post post = postRepository.findById(postId);
                    if (post == null) {
                        System.out.println("해당 게시글이 없습니다.");
                    } else {
                        System.out.println(post);
                    }
                    break;

                case 3:
                    System.out.print("작성자 user_id: ");
                    int userId = Integer.parseInt(scanner.nextLine());

                    System.out.print("내용: ");
                    String content = scanner.nextLine();

                    int saveResult = postRepository.save(userId, content);
                    if (saveResult > 0) {
                        System.out.println("게시글 작성 성공");
                    } else {
                        System.out.println("게시글 작성 실패");
                    }
                    break;

                case 4:
                    System.out.print("수정할 게시글 ID: ");
                    int updatePostId = Integer.parseInt(scanner.nextLine());

                    System.out.print("새 내용: ");
                    String newContent = scanner.nextLine();

                    int updateResult = postRepository.update(updatePostId, newContent);
                    if (updateResult > 0) {
                        System.out.println("게시글 수정 성공");
                    } else {
                        System.out.println("게시글 수정 실패 또는 대상 없음");
                    }
                    break;

                case 5:
                    System.out.print("삭제할 게시글 ID: ");
                    int deletePostId = Integer.parseInt(scanner.nextLine());

                    int deleteResult = postRepository.delete(deletePostId);
                    if (deleteResult > 0) {
                        System.out.println("게시글 삭제 성공");
                    } else {
                        System.out.println("게시글 삭제 실패 또는 대상 없음");
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }
    }

    private static void commentMenu(Scanner scanner, CommentRepository commentRepository) {
        while (true) {
            System.out.println("\n===== 댓글 메뉴 =====");
            System.out.println("1. 댓글 목록 조회");
            System.out.println("2. 댓글 작성");
            System.out.println("3. 댓글 수정");
            System.out.println("4. 댓글 삭제");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int menu = Integer.parseInt(scanner.nextLine());

            switch (menu) {
                case 1:
                    System.out.print("조회할 게시글 ID: ");
                    int postId = Integer.parseInt(scanner.nextLine());

                    List<Comment> comments = commentRepository.findByPostId(postId);
                    if (comments.isEmpty()) {
                        System.out.println("댓글이 없습니다.");
                    } else {
                        for (Comment comment : comments) {
                            System.out.println(comment);
                        }
                    }
                    break;

                case 2:
                    System.out.print("게시글 ID: ");
                    int savePostId = Integer.parseInt(scanner.nextLine());

                    System.out.print("작성자 user_id: ");
                    int saveUserId = Integer.parseInt(scanner.nextLine());

                    System.out.print("댓글 내용: ");
                    String saveContent = scanner.nextLine();

                    int saveResult = commentRepository.save(savePostId, saveUserId, saveContent);
                    if (saveResult > 0) {
                        System.out.println("댓글 작성 성공");
                    } else {
                        System.out.println("댓글 작성 실패");
                    }
                    break;

                case 3:
                    System.out.print("수정할 댓글 ID: ");
                    int commentId = Integer.parseInt(scanner.nextLine());

                    System.out.print("새 댓글 내용: ");
                    String newContent = scanner.nextLine();

                    int updateResult = commentRepository.update(commentId, newContent);
                    if (updateResult > 0) {
                        System.out.println("댓글 수정 성공");
                    } else {
                        System.out.println("댓글 수정 실패 또는 대상 없음");
                    }
                    break;

                case 4:
                    System.out.print("삭제할 댓글 ID: ");
                    int deleteCommentId = Integer.parseInt(scanner.nextLine());

                    int deleteResult = commentRepository.delete(deleteCommentId);
                    if (deleteResult > 0) {
                        System.out.println("댓글 삭제 성공");
                    } else {
                        System.out.println("댓글 삭제 실패 또는 대상 없음");
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }
    }

    private static void likeMenu(Scanner scanner, LikeRepository likeRepository) {
        while (true) {
            System.out.println("\n===== 좋아요 메뉴 =====");
            System.out.println("1. 게시글 좋아요");
            System.out.println("2. 게시글 좋아요 취소");
            System.out.println("3. 게시글 좋아요 목록 조회");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int menu = Integer.parseInt(scanner.nextLine());

            switch (menu) {
                case 1:
                    System.out.print("게시글 ID: ");
                    int savePostId = Integer.parseInt(scanner.nextLine());

                    System.out.print("user_id: ");
                    int saveUserId = Integer.parseInt(scanner.nextLine());

                    if (likeRepository.exists(saveUserId, savePostId)) {
                        System.out.println("이미 좋아요를 누른 게시글입니다.");
                    } else {
                        int saveResult = likeRepository.save(saveUserId, savePostId);
                        if (saveResult > 0) {
                            System.out.println("좋아요 성공");
                        } else {
                            System.out.println("좋아요 실패");
                        }
                    }
                    break;

                case 2:
                    System.out.print("게시글 ID: ");
                    int deletePostId = Integer.parseInt(scanner.nextLine());

                    System.out.print("user_id: ");
                    int deleteUserId = Integer.parseInt(scanner.nextLine());

                    int deleteResult = likeRepository.delete(deleteUserId, deletePostId);
                    if (deleteResult > 0) {
                        System.out.println("좋아요 취소 성공");
                    } else {
                        System.out.println("좋아요 취소 실패 또는 대상 없음");
                    }
                    break;

                case 3:
                    System.out.print("조회할 게시글 ID: ");
                    int findPostId = Integer.parseInt(scanner.nextLine());

                    List<Like> likes = likeRepository.findByPostId(findPostId);
                    if (likes.isEmpty()) {
                        System.out.println("좋아요가 없습니다.");
                    } else {
                        for (Like like : likes) {
                            System.out.println(like);
                        }
                        System.out.println("총 좋아요 수: " + likes.size());
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }
    }

    private static void userMenu(Scanner scanner, UserRepository userRepository) {
        while (true) {
            System.out.println("\n===== 사용자 메뉴 =====");
            System.out.println("1. 회원가입");
            System.out.println("2. 사용자 정보 조회");
            System.out.println("3. 사용자 정보 수정");
            System.out.println("4. 내 게시글 조회");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int menu = Integer.parseInt(scanner.nextLine());

            switch (menu) {
                case 1:
                    System.out.print("이메일: ");
                    String email = scanner.nextLine();

                    System.out.print("비밀번호(hash로 가정): ");
                    String passwordHash = scanner.nextLine();

                    System.out.print("사용자 이름(username): ");
                    String username = scanner.nextLine();

                    int saveResult = userRepository.save(email, passwordHash, username);
                    if (saveResult > 0) {
                        System.out.println("회원가입 성공");
                    } else {
                        System.out.println("회원가입 실패");
                    }
                    break;

                case 2:
                    System.out.print("조회할 사용자 ID: ");
                    int userId = Integer.parseInt(scanner.nextLine());

                    User user = userRepository.findById(userId);
                    if (user == null) {
                        System.out.println("해당 사용자가 없습니다.");
                    } else {
                        System.out.println(user);
                    }
                    break;

                case 3:
                    System.out.print("수정할 사용자 ID: ");
                    int updateUserId = Integer.parseInt(scanner.nextLine());

                    System.out.print("새 이메일: ");
                    String newEmail = scanner.nextLine();

                    System.out.print("새 비밀번호(hash): ");
                    String newPasswordHash = scanner.nextLine();

                    System.out.print("새 사용자 이름(username): ");
                    String newUsername = scanner.nextLine();

                    int updateResult = userRepository.update(updateUserId, newEmail, newPasswordHash, newUsername);
                    if (updateResult > 0) {
                        System.out.println("사용자 정보 수정 성공");
                    } else {
                        System.out.println("사용자 정보 수정 실패 또는 대상 없음");
                    }
                    break;

                case 4:
                    System.out.print("조회할 사용자 ID: ");
                    int findUserId = Integer.parseInt(scanner.nextLine());

                    List<Post> posts = userRepository.findPostsByUserId(findUserId);
                    if (posts.isEmpty()) {
                        System.out.println("작성한 게시글이 없습니다.");
                    } else {
                        for (Post post : posts) {
                            System.out.println(post);
                        }
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }
    }

    private static void followMenu(Scanner scanner, FollowRepository followRepository) {
        while (true) {
            System.out.println("\n===== 팔로우 메뉴 =====");
            System.out.println("1. 사용자 팔로우");
            System.out.println("2. 사용자 언팔로우");
            System.out.println("3. 팔로잉 목록 조회");
            System.out.println("4. 팔로워 목록 조회");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            int menu = Integer.parseInt(scanner.nextLine());

            switch (menu) {
                case 1:
                    System.out.print("내 user_id (follower_id): ");
                    int followerId = Integer.parseInt(scanner.nextLine());

                    System.out.print("팔로우할 user_id (following_id): ");
                    int followingId = Integer.parseInt(scanner.nextLine());

                    if (followerId == followingId) {
                        System.out.println("자기 자신은 팔로우할 수 없습니다.");
                        break;
                    }

                    if (followRepository.exists(followerId, followingId)) {
                        System.out.println("이미 팔로우한 사용자입니다.");
                    } else {
                        int saveResult = followRepository.save(followerId, followingId);
                        if (saveResult > 0) {
                            System.out.println("팔로우 성공");
                        } else {
                            System.out.println("팔로우 실패");
                        }
                    }
                    break;

                case 2:
                    System.out.print("내 user_id (follower_id): ");
                    int deleteFollowerId = Integer.parseInt(scanner.nextLine());

                    System.out.print("언팔로우할 user_id (following_id): ");
                    int deleteFollowingId = Integer.parseInt(scanner.nextLine());

                    int deleteResult = followRepository.delete(deleteFollowerId, deleteFollowingId);
                    if (deleteResult > 0) {
                        System.out.println("언팔로우 성공");
                    } else {
                        System.out.println("언팔로우 실패 또는 대상 없음");
                    }
                    break;

                case 3:
                    System.out.print("조회할 사용자 ID(follower_id): ");
                    int findFollowerId = Integer.parseInt(scanner.nextLine());

                    List<Follow> followingList = followRepository.findFollowingByUserId(findFollowerId);
                    if (followingList.isEmpty()) {
                        System.out.println("팔로잉 목록이 없습니다.");
                    } else {
                        for (Follow follow : followingList) {
                            System.out.println(follow);
                        }
                        System.out.println("총 팔로잉 수: " + followingList.size());
                    }
                    break;

                case 4:
                    System.out.print("조회할 사용자 ID(following_id): ");
                    int findFollowingId = Integer.parseInt(scanner.nextLine());

                    List<Follow> followerList = followRepository.findFollowersByUserId(findFollowingId);
                    if (followerList.isEmpty()) {
                        System.out.println("팔로워 목록이 없습니다.");
                    } else {
                        for (Follow follow : followerList) {
                            System.out.println(follow);
                        }
                        System.out.println("총 팔로워 수: " + followerList.size());
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }
    }

    private static void authMenu(Scanner scanner, AuthService authService) {
    while (true) {
        System.out.println("\n===== 세션/인증 메뉴 =====");
        System.out.println("1. 로그인");
        System.out.println("2. 로그아웃");
        System.out.println("3. 토큰 재발급");
        System.out.println("0. 뒤로가기");
        System.out.print("선택: ");

        int menu = Integer.parseInt(scanner.nextLine());

        switch (menu) {
            case 1:
                System.out.print("이메일: ");
                String email = scanner.nextLine();

                System.out.print("비밀번호(hash): ");
                String passwordHash = scanner.nextLine();

                String loginResult = authService.login(email, passwordHash);
                System.out.println(loginResult);
                break;

            case 2:
                System.out.print("refresh token: ");
                String logoutRefreshToken = scanner.nextLine();

                String logoutResult = authService.logout(logoutRefreshToken);
                System.out.println(logoutResult);
                break;

            case 3:
                System.out.print("기존 refresh token: ");
                String oldRefreshToken = scanner.nextLine();

                String refreshResult = authService.refresh(oldRefreshToken);
                System.out.println(refreshResult);
                break;

            case 0:
                return;

            default:
                System.out.println("잘못된 입력입니다.");
                break;
        }
    }
}
}