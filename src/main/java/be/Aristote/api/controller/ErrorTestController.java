package be.Aristote.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/test")
public class ErrorTestController {

    @GetMapping("/403")
    public void error403() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tu n'as pas accès à cette ressource.");
    }

    @GetMapping("/404")
    public void error404() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cette ressource n'existe pas.");
    }

    @GetMapping("/500")
    public void error500() {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue.");
    }

    @GetMapping("/401")
    public void error401() {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Tu dois être connecté pour accéder.");
    }
}