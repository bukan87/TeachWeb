package ru.bukan.TeachWeb.service;

import javax.xml.ws.Endpoint;

/**
 * @author by Ilin_ai on 31.05.2017.
 */
public class Publisher {
    public static void main(String[] args){
        Endpoint.publish("http://localhost:9999/VacancyService", new VacancyServiceImpl());
    }
}
