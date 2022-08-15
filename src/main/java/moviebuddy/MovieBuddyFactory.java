package moviebuddy;

import com.github.benmanes.caffeine.cache.Caffeine;
import moviebuddy.cache.CachingAdvice;
import moviebuddy.data.CachingMovieReader;
import moviebuddy.domain.MovieReader;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = {"moviebuddy"})
@PropertySource("/application.properties")
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})   //빈구성정보를 불러옴
public class MovieBuddyFactory {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("moviebuddy");//탐지

        return marshaller;
    }

    @Bean
    public CacheManager caffeineCashManager(){
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS));
        return cacheManager;
    }

    @Configuration
    static class DomainModuleConfig{

    }

    @Configuration
    static class DataSourceModuleConfig{

        @Primary
        @Bean
        public ProxyFactoryBean chchingMovieReaderFactory(ApplicationContext applicationContext) {
            MovieReader target = applicationContext.getBean(MovieReader.class);
            CacheManager cacheManager = applicationContext.getBean(CacheManager.class);

            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setTarget(target);

            //클래스 프락시 활성화(true)/ 비활성화(false, 기본값)
//            proxyFactoryBean.setProxyTargetClass(true);
            proxyFactoryBean.addAdvice(new CachingAdvice(cacheManager));
            return proxyFactoryBean;
        }
    }

}
