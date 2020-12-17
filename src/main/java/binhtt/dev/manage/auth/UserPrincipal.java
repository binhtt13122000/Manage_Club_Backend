package binhtt.dev.manage.auth;

import binhtt.dev.manage.entities.Account;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
// đối tượng của mình là userEntity
// đối tượng của Security là class implement UserDetails
//username, password, role, có bị block không, rồi có hết phiên đăng nhập chưa
public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Account account;
    //constructor??

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //grantedAuthority -> collection của role nhưng ADMIN -> ROLE_ADMIN , USER -> ROLE_USER
        return Collections.singleton(new SimpleGrantedAuthority(account.getRole().getRoleName()));
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getStudentID();
    }

    @Override
    public boolean isAccountNonExpired() {
        return account.isStatus();
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.isStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return account.isStatus();
    }

    @Override
    public boolean isEnabled() {
        return account.isStatus();
    }
}
