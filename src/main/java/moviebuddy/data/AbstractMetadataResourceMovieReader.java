package moviebuddy.data;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import moviebuddy.ApplicationException;
import moviebuddy.domain.MovieReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public abstract class AbstractMetadataResourceMovieReader implements MovieReader, ResourceLoaderAware {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private String metadata;
    private ResourceLoader resourceLoader;

    public String getMetadata() {
        return metadata;
    }

    @Value("${movie.metadata}")
    public void setMetadata(String metadata) {
        this.metadata = Objects.requireNonNull(metadata, "metadata is a required value.");
    }

    public URL getMetadataUrl() {
        String location = getMetadata();
        if(location.startsWith("file:")){
            //file URl
        }else if(location.startsWith("http:")){
            //http URL
        }
        return ClassLoader.getSystemResource(location);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    public Resource getMetadataResource(){
        return resourceLoader.getResource(getMetadata());
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
//        ClassLoader.getSystemResource(getMetadata()); 클래스패스 상의 자원만 처리할 수 있다
//        file: ,htpt:, ftp:

        Resource resource = getMetadataResource();
        if(!resource.exists()){
            throw new FileNotFoundException(metadata);
        }
        if(!resource.isReadable()){
            throw new ApplicationException(String.format("cannot read to metadata. [%s]", metadata));
        }

        log.info(resource + " is ready");
    }

    @PreDestroy
    public void destroy() throws Exception {
        log.info("Destroyed bean");
    }

}