package com.agilemonkey.crm.security;

import com.agilemonkey.crm.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
@ToString
@RequiredArgsConstructor
public class AppUserPrincipal implements UserDetails, OAuth2User {
    private final Set<GrantedAuthority> authorities;
    private final User user;
    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getAttribute("email");
    }

    @Override
    public String getName() {
        return getAttribute("name");
    }
}
