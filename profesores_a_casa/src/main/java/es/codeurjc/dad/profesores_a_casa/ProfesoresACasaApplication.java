package es.codeurjc.dad.profesores_a_casa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.apache.commons.logging.*;

@EnableCaching
@SpringBootApplication
public class ProfesoresACasaApplication {

	private static final Log LOG = LogFactory.getLog(ProfesoresACasaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProfesoresACasaApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		LOG.info("Activating cache...");
		return new ConcurrentMapCacheManager("cache");
	}
}
