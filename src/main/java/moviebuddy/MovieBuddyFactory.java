package moviebuddy;

import moviebuddy.data.CsvMovieReader;
import moviebuddy.data.XmlMovieReader;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

@Configuration
@ComponentScan(basePackages = {"moviebuddy"})
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})   //빈구성정보를 불러옴
public class MovieBuddyFactory {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("moviebuddy");//탐지

        return marshaller;
    }

    @Configuration
    static class DomainModuleConfig{

    }

    @Configuration
    static class DataSourceModuleConfig{

        private final Environment environment;

        public DataSourceModuleConfig(Environment environment){
            this.environment = environment;
        }

        @Profile(MovieBuddyProfile.CSV_MODE)
        @Bean
        public CsvMovieReader csvMovieReader(){
            CsvMovieReader movieReader = new CsvMovieReader();

            //애플리케이션 외부에서 작성된 설정정보를 읽어, 메타데이터 위치 설정하기
            movieReader.setMetadata(environment.getProperty("movie.metadata"));

            return movieReader;
        }

        @Profile(MovieBuddyProfile.XML_MODE)
        @Bean
        public XmlMovieReader xmlMovieReader(Unmarshaller unmarshaller){
            XmlMovieReader movieReader = new XmlMovieReader(unmarshaller);
            movieReader.setMetadata(environment.getProperty("movie.metadata"));

            return movieReader;
        }
    }

}
