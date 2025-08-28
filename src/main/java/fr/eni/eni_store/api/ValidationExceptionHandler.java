package fr.eni.eni_store.api;

import fr.eni.eni_store.service.ServiceHelper;
import fr.eni.eni_store.service.ServiceResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/*
class CustomError {
    public String key;
    public String message;

    public CustomError() {
    }

    public CustomError(String key, String message) {
        this.key = key;
        this.message = message;
    }
}
*/

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ServiceResponse<List<String>> handleConstraintViolation(ConstraintViolationException exception){

        // Recupérer les messages d'erreurs de valiation
        // .map = mapping (transformer de la data)
        List<String> errors = exception.getConstraintViolations().stream().
                map(cv -> cv.getMessage()).toList();

        /*
        List<CustomError> errors = exception.getConstraintViolations().stream().
                map(cv -> {
                    String path = cv.getPropertyPath().toString();
                    String[] split = path.split("\\.");

                    return new CustomError(split[split.length-1], cv.getMessage());
                }).toList();
         */

        // Les placer dans data
        return ServiceHelper.buildResponse("798", "Erreur de validation", errors);
    }
}
