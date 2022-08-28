package moviebuddy;

import com.github.benmanes.caffeine.cache.Caffeine;
import moviebuddy.cache.CachingAdvice;
import moviebuddy.cache.CachingAspect;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.cache.annotation.CacheResult;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = {"moviebuddy"})
@PropertySource("/application.properties")
@Import({MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})   //빈구성정보를 불러옴
@EnableAspectJAutoProxy
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

    /*
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public Advisor cachingAdvisor(CacheManager cacheManager){
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, CacheResult.class);
        Advice advice = new CachingAdvice(cacheManager);

        //Advisor = PointCut(대상 선정 알고리즘) + Advice(부가기능)
        return new DefaultPointcutAdvisor(pointcut, advice);

    }
    */

    @Bean
    public CachingAspect cachingAspect(CacheManager cacheManager){
        return new CachingAspect(cacheManager);
    }
    @Configuration
    static class DomainModuleConfig{

    }

    @Configuration
    static class DataSourceModuleConfig{

    }

}
