import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.bukan.ssm.domain.model.lesson.LessonDao;

/**
 * @author by Ilin_ai on 26.09.2017.
 */
@ContextConfiguration(locations = {"classpath:domain-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Component
public class TTest {
    @Autowired
    LessonDao lessonDao;

    @Test
    public void t(){
        lessonDao.getStats();
    }
}
