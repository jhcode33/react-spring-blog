package jhcode.blog.entity;

import jakarta.persistence.*;
import jhcode.blog.common.BaseTimeEntity;
import jhcode.blog.common.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseTimeEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    // 이메일로 로그인함
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username; // 사람 이름이다.

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, name="region_id")
    private String regionId;

    @Column(nullable = false,columnDefinition = "TINYINT")
    private int status=0;// 탈퇴 여부; 0=현재 회원, 1=탈퇴 회원.


    @Enumerated(EnumType.STRING)
    private Role roles;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Comment> comments = new ArrayList<>();

    //== 생성자 Builder ==//
    @Builder
    public Member(String email, String password, String username, Role roles, String phone, String regionId) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.roles = roles;
        this.phone = phone;
        this.regionId = regionId;
    }

    //== update ==//
    public void update(String password, String username) {
        this.password = password;
        this.username = username;
    }

    //========== UserDetails implements ==========//
    /**
     * Token을 고유한 Email 값으로 생성합니다
     * @return email;
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority("ROLE_" + this.roles.name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
