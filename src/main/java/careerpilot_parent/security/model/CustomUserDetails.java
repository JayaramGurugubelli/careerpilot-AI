package careerpilot_parent.security.model;
import careerpilot_parent.shared.enums.AccountStatus;
import careerpilot_parent.user.entity.Role;
import careerpilot_parent.user.entity.User;
import careerpilot_parent.user.entity.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<UserRole> userRoles = user.getRoles();


        return userRoles.stream()
                .map(UserRole::getRole)
                .map(Role::getName)
                .map(Enum::name)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Login using email.
     * If you prefer username login,
     * return user.getUsername();
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Account not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Account not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountStatus() != AccountStatus.LOCKED;
    }

    public Long getUserId(){

        return user.getId();

    }

    /**
     * Credentials not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Account enabled
     */
    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

}