package ru.bukan.TeachWeb.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author by Ilin_ai on 31.05.2017.
 */

@WebService(targetNamespace = "http://teachWeb.bukan.ru/v1/vacancyService/")
public interface VacancyService {

    @WebMethod
    Long changeStatus(long id, String status);
}
