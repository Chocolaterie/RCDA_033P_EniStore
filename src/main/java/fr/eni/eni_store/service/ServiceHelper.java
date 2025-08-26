package fr.eni.eni_store.service;

import fr.eni.eni_store.bo.Article;
import fr.eni.eni_store.locale.LocaleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceHelper {

    private static final Logger logger = LoggerFactory.getLogger(ServiceHelper.class);

    /**
     * Utilitaire pour centraliser la construction d'un service response
     * Et logger la réponse métier
     * @param code
     * @param data
     * @return
     * @param <T>
     */
    static <T> ServiceResponse<T> buildResponse(String code, String message, T data){
        ServiceResponse<T> serviceResponse = new ServiceResponse<T>();
        serviceResponse.code = code;
        serviceResponse.message = message;
        serviceResponse.data = data;

        logger.info(String.format("Service response : code=%s | message=%s", code, message));
        // Avant
        //System.out.println(String.format("Service response : code=%s", code));

        return serviceResponse;
    }

    static <T> ServiceResponse<T> buildResponse(String code, String message){
        return buildResponse(code, message,null);
    }
}
