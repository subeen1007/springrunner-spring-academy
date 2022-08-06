package moviebuddy.domain;

import moviebuddy.MovieBuddyFactory;
import moviebuddy.MovieBuddyProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

/**
 * @author springrunner.kr@gmail.com
 */
@ActiveProfiles(MovieBuddyProfile.CSV_MODE)
@SpringJUnitConfig(MovieBuddyFactory.class)
//@ExtendWith(SpringExtension.class)//JUnit이 테스트 실행 전략을 확장할 때 사용하는 애노테이션
//@ContextConfiguration(classes = MovieBuddyFactory.class)
public class MovieFinderTest {

//    필드에서 주입 but 권장X(테스트시 어려움 있음)
    @Autowired MovieFinder movieFinder;

//    생성자주입
//    @Autowired
//    MovieFinderTest(MovieFinder movieFinder){
//        this.movieFinder = movieFinder;
//    }

//    setter에서 주입
//    void setMovieFinder(MovieFinder movieFinder){
//        this.movieFinder = movieFinder;
//    }

    @Test
    void NotEmpty_directedBy() {
        List<Movie> movies = movieFinder.directedBy("Michael Bay");
        Assertions.assertEquals(3, movies.size());
    }

    @Test
    void NotEmpty_ReleasedYearBy() {
        List<Movie> movies = movieFinder.releasedYearBy(2015);
        Assertions.assertEquals(225, movies.size());
    }

}
