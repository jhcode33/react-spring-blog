package jhcode.blog.dto.request.member;

import jhcode.blog.common.Role;
import jhcode.blog.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request-
 * 회원 가입 요청 dto
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberRegisterDto {

    private String email;
    private String password;
    private String passwordCheck;
    private String username;
    private String phone;
    private String regionId;

    @Builder
    public MemberRegisterDto(String email, String password, String passwordCheck, String username, String phone, String regionId) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.username = username;
        this.phone = phone;
        this.regionId = regionId;
    }

    // DTO -> Entity
    public static Member ofEntity(MemberRegisterDto dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .phone(dto.getPhone())
                .regionId(dto.getRegionId())
                .roles(Role.USER)
                .build();
    }
}
