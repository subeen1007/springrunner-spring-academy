package moviebuddy;

import moviebuddy.domain.CsvMovieReader;
import moviebuddy.domain.MovieFinder;
import moviebuddy.domain.MovieReader;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Repository;

@Configuration
//@ComponentScan(basePackages = {"moviebuddy"})
@Repository
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})   //빈구성정보를 불러옴
public class MovieBuddyFactory {

    @Configuration
    static class DomainModuleConfig{

    }

    @Configuration
    static class DataSourceModuleConfig{
        @Bean
        public MovieReader movieReader(){
            return new CsvMovieReader();
        }
    }

}
