package com.smoredeep.google;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.smoredeep.entity.TbUser;

import lombok.Getter;

@Getter
public class PrincipalDetails implements OAuth2User {
    private TbUser user;
    private Map<String, Object> attributes;

    public PrincipalDetails(TbUser user) {
        this.user=user;
    }

    public PrincipalDetails(TbUser user, Map<String, Object> attributes) {
        this.user=user;
        this.attributes=attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getAdminIs();
            }
        });
        return collect;
    }

    @Override
    public String getName() {
        return "name";
    }
}
