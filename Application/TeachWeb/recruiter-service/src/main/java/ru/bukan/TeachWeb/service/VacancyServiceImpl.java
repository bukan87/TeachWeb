package ru.bukan.TeachWeb.service;

import ru.bukan.TeachWeb.domain.model.VacancyEntity;

import javax.jws.WebService;


/**
 * @author by Ilin_ai on 31.05.2017.
 */
@WebService(
        targetNamespace = "http://teachWeb.bukan.ru/v1/vacancyService/",
        endpointInterface = "ru.bukan.TeachWeb.service.VacancyService",
        serviceName = "VacancyService")
public class VacancyServiceImpl implements VacancyService {

    @Override
    public Long changeStatus(long id, String status) {
        VacancyEntity vacancy = new VacancyEntity(id);
        vacancy.changeStatus(status);
        return vacancy.getId();
    }
}
