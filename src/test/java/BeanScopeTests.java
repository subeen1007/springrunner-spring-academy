import moviebuddy.MovieBuddyFactory;
import moviebuddy.domain.MovieFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopeTests {
    @Test
    void Equals_MovieFinerBean(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MovieBuddyFactory.class);
        MovieFinder movieFinder = applicationContext.getBean(MovieFinder.class);

        applicationContext.getBean(MovieFinder.class);
        Assertions.assertEquals(movieFinder, applicationContext.getBean(MovieFinder.class));
    }
}
