package nl.miwnn.cohort19.mylinh.ChoreManager.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author My Linh Lu
 * Handles exceptions
 */
@ControllerAdvice
public class ChoreManagerExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public String handleNotFound(
            ResponseStatusException exception,
            Model model) {

        model.addAttribute("statusCode", exception.getStatusCode().value());
        model.addAttribute("bericht", exception.getReason());

        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleAlgemeneUitzondering(
            Exception exception,
            Model model) {

        model.addAttribute("bericht", exception.getMessage());
        return "error/500";
    }
}
