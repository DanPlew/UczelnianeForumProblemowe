package ufp.uczelnianeforumproblemowe.logic.uzytkownikService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.uczelnianeforumproblemowe.jpa.models.TokenWeryfikacyjny;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.repositories.UzytkownikRepository;
import ufp.uczelnianeforumproblemowe.logic.emailService.EmailService;
import ufp.uczelnianeforumproblemowe.logic.tokenService.TokenWeryfikacyjnyService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.UzytkownikView;
import ufp.uczelnianeforumproblemowe.security.exceptions.UzytkownikAlreadyExistExeption;

import java.time.LocalDateTime;

@Service
public class UzytkownikService implements RejestracjaUzytkownikaInterface, RejestracjaTokenaInterface, RejestracjaMailInterface {

    private final UzytkownikRepository uzytkownikRepository;
    private final TokenWeryfikacyjnyService tokenWeryfikacyjnyService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UzytkownikService(@Autowired UzytkownikRepository uzytkownikRepository,
                             @Autowired TokenWeryfikacyjnyService tokenWeryfikacyjnyService,
                             @Autowired PasswordEncoder passwordEncoder,
                             @Autowired EmailService emailService){
        this.uzytkownikRepository = uzytkownikRepository;
        this.tokenWeryfikacyjnyService = tokenWeryfikacyjnyService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public void rejestracja(UzytkownikView uzytkownikView) throws UzytkownikAlreadyExistExeption {
        if(sprawdzenieKontaNaPodstawieLoginu(uzytkownikView.getLogin())
        || sprawdzenieKontaNaPodstawieMailaUczelnianego(uzytkownikView.getEmailUczelniany())){
            throw new UzytkownikAlreadyExistExeption("Taki użytkownik już istnieje");
        }
        Uzytkownik uzytkownik = new Uzytkownik(uzytkownikView);
        uzytkownik.setHaslo(passwordEncoder.encode(uzytkownik.getHaslo()));
        uzytkownikRepository.save(uzytkownik);

        rejestracjaTokena(uzytkownik);
    }

    @Override
    public boolean sprawdzenieKontaNaPodstawieLoginu(String login) {
        return uzytkownikRepository.findByLogin(login) != null ? true : false;
    }

    @Override
    public boolean sprawdzenieKontaNaPodstawieMailaUczelnianego(String emailUczelniany) {
        return uzytkownikRepository.findByEmailUczelniany(emailUczelniany) != null ? true : false;
    }

    @Override
    public void rejestracjaTokena(Uzytkownik uzytkownik){
        TokenWeryfikacyjny tokenWeryfikacyjny = tokenWeryfikacyjnyService.utworzTokenWeryfikacyjny();
        tokenWeryfikacyjny.setUzytkownik(uzytkownik);
        tokenWeryfikacyjnyService.zapiszTokenWeryfikacyjny(tokenWeryfikacyjny);

        rejestracjaMaila(uzytkownik, tokenWeryfikacyjny.getToken());
    }

    @Override
    @Transactional
    public void sprawdzenieTokena(String token) {
        TokenWeryfikacyjny tokenWeryfikacyjny = tokenWeryfikacyjnyService.znajdzToken(token);
        if(tokenWeryfikacyjny == null) throw new IllegalStateException("Nie znaleziono takiego tokenu");

        if(tokenWeryfikacyjny.isCzyKontoAktywne()) throw new IllegalStateException("Konto zostało już aktywowane");

        LocalDateTime wygasniecieTokenu = tokenWeryfikacyjny.getDataWygasniecia();

        if (wygasniecieTokenu.isBefore(LocalDateTime.now())) throw new IllegalStateException("Token wygasł");

        uzytkownikRepository.aktywowanieKonta(tokenWeryfikacyjny.getUzytkownik().getLogin());
    }

    @Override
    public void rejestracjaMaila(Uzytkownik uzytkownik, String token) {
        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailService.wyslij(uzytkownik.getEmailPrywatny(), zbudujMaila(uzytkownik.getLogin(), link));
    }

    @Override
    public String zbudujMaila(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
