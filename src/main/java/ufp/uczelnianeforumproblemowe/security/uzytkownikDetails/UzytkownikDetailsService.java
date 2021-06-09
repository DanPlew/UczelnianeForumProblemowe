package ufp.uczelnianeforumproblemowe.security.uzytkownikDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.repositories.UzytkownikRepository;

@Service
public class UzytkownikDetailsService implements UserDetailsService {

    private UzytkownikRepository uzytkownikRepository;

    public UzytkownikDetailsService(@Autowired UzytkownikRepository uzytkownikRepository){
        this.uzytkownikRepository = uzytkownikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Uzytkownik uzytkownik = uzytkownikRepository.findByLogin(login);
        if(uzytkownik == null) throw new UsernameNotFoundException(login);
        return new UzytkownikDetails(uzytkownik);
    }
}
