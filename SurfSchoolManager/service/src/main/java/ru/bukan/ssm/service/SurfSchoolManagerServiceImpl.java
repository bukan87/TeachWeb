package ru.bukan.ssm.service;

import ru.bukan.ssm.service.types.lesson.Lesson;

import javax.jws.WebService;

/**
 * @author by Ilin_ai on 19.07.2017.
 */
@WebService(endpointInterface = "ru.bukan.ssm.service.SurfSchoolManagerService",
        name = "SurfSchoolManagerService"
)
public class SurfSchoolManagerServiceImpl implements SurfSchoolManagerService {
/*
    @Autowired
    private Dao dao;*/

    @Override
    public Lesson getLessonInfo(long lessonId) {
        /*LessonEntity lessonEntity = dao.getByKey(LessonEntity.class, lessonId);
        LessonTransform transform = new LessonTransform();*/
        return null;
    }
}
