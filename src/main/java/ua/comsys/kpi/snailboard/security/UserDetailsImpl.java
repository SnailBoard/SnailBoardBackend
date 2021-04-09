package ua.comsys.kpi.snailboard.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ua.comsys.kpi.snailboard.user.model.User;

public class UserDetailsImpl implements UserDetails {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;


    public static UserDetailsImpl fromUserEntityToUserDetails(User userEntity) {
        UserDetailsImpl c = new UserDetailsImpl();
        c.email = userEntity.getEmail();
        c.password = userEntity.getPassword();
        c.grantedAuthorities = userEntity.getRoles().stream().
                map((var role) -> new SimpleGrantedAuthority(role.getCode().name())).
                collect(Collectors.toList());
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
