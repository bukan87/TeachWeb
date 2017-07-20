package ru.bukan.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.bukan.ssm.domain.Dao;
import ru.bukan.ssm.domain.model.lesson.LessonEntity;
import ru.bukan.ssm.service.types.lesson.Lesson;
import ru.bukan.ssm.transform.LessonTransform;

import javax.jws.WebService;

/**
 * @author by Ilin_ai on 19.07.2017.
 */
@WebService(endpointInterface = "ru.bukan.ssm.service.SurfSchoolManagerService",
        name = "SurfSchoolManagerService"
)
public class SurfSchoolManagerServiceImpl implements SurfSchoolManagerService {

    @Autowired
    private Dao dao;

    @Override
    @Transactional
    public Lesson getLessonInfo(long lessonId) {
        LessonEntity lessonEntity = dao.getByKey(LessonEntity.class, lessonId);
        LessonTransform transform = new LessonTransform();

        return transform.fromEntity(lessonEntity);
    }
}
