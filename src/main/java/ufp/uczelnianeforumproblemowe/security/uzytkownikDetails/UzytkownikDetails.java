package ufp.uczelnianeforumproblemowe.security.uzytkownikDetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UzytkownikDetails implements UserDetails {

    private Uzytkownik uzytkownik;

    public UzytkownikDetails(Uzytkownik uzytkownik){
        this.uzytkownik = uzytkownik;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(uzytkownik.getRanga().name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.uzytkownik.getHaslo();
    }

    @Override
    public String getUsername() {
        return this.uzytkownik.getLogin();
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
        return uzytkownik.isAktywnoscKonta();
    }
}
