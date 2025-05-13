package be.Aristote.config.security;

import java.io.IOException;

import be.Aristote.domain.model.UserEntity;
import be.Aristote.domain.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        String email = (String) oauthUser.getAttribute("email");

        UserEntity user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            request.getSession().setAttribute("oauthUserEmail", email);
            request.getSession().setAttribute("oauthUserName", oauthUser.getAttribute("name"));
            response.sendRedirect("/oauth2/register");
        } else {
            response.sendRedirect("/articles");
        }
    }
}