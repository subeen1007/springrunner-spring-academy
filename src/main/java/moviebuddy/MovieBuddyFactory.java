package moviebuddy;

import moviebuddy.domain.CsvMovieReader;
import moviebuddy.domain.MovieFinder;
import moviebuddy.domain.MovieReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieBuddyFactory {

    @Bean
    public MovieReader movieReader(){
        return new CsvMovieReader();//메소드 콜 방식
    }
    @Bean
    public MovieFinder movieFinder(MovieReader movieReader){
        return new MovieFinder(movieReader);//메소드 파라미터 방식
    }
}
