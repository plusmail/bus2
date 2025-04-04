package kroryi.bus2.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")  // DB 테이블 이름
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 고유 아이디 (PK)

    @Column(nullable = false, unique = true)
    private String userId;  // 유저 아이디

    @Column(nullable = false)
    private String username;  // 이름

    @Column(nullable = false)
    private String password;  // 비밀번호

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // 권한 (예: USER, ADMIN 등)

    @Enumerated(EnumType.STRING)
    @Column(name = "signup_type", nullable = false)
    private SignupType signupType = SignupType.GENERAL; // 가입유형 (ex. 일반, 소셜 등)

    @Column(name = "signup_date")
    private LocalDate signupDate;
}
