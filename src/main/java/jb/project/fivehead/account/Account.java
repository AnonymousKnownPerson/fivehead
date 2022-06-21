package jb.project.fivehead.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table
public class Account implements UserDetails {
    @Id
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    private Long id;
    private String username;
    private String passwd;
    private String email;
    private String url;
    @Enumerated(EnumType.STRING)
    private AccountUserRole accountUserRole;
    private Boolean locked;
    private Boolean enabled;

    public Account(String username, String passwd, String email, String url, AccountUserRole accountUserRole, Boolean locked, Boolean enabled) {
        this.username = username;
        this.passwd = passwd;
        this.email = email;
        this.url = url;
        this.accountUserRole = accountUserRole;
        this.locked = locked;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", passwd='****" +  '\'' +
                ", email='" + email + '\'' +
                ", url='" + url + '\'' +
                ", accountUserRole=" + accountUserRole +
                ", locked=" + locked +
                ", enabled=" + enabled +
                '}';
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(accountUserRole.name()));
    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
