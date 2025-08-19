package fr.eni.eni_store.service;

import fr.eni.eni_store.bo.Article;

import java.util.List;

public class ServiceHelper {

    /**
     * Utilitaire pour centraliser la construction d'un service response
     * Et logger la réponse métier
     * @param code
     * @param data
     * @return
     * @param <T>
     */
    static <T> ServiceResponse<T> buildResponse(String code, T data){
        ServiceResponse<T> serviceResponse = new ServiceResponse<T>();
        serviceResponse.code = code;
        serviceResponse.data = data;

        // TODO : Logger la reponse (HOOK / AOP)
        System.out.println(String.format("Service response : code=%s", code));

        return serviceResponse;
    }

    static <T> ServiceResponse<T> buildResponse(String code){
        return buildResponse(code, null);
    }
}
