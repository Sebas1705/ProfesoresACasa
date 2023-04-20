package es.codeurjc.dad.profesores_a_casa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

import java.util.Collections;

import org.apache.commons.logging.*;

@EnableCaching
@EnableHazelcastHttpSession
@SpringBootApplication
public class ProfesoresACasaApplication {

	@Value("${hazel.ip.other-web}")
	public String hazelIp;


	private static final Log LOG = LogFactory.getLog(ProfesoresACasaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ProfesoresACasaApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		LOG.info("Activating cache...");
		return new ConcurrentMapCacheManager("cache");
	}

	@Bean
    public Config config() {

        Config config = new Config();

        JoinConfig joinConfig = config.getNetworkConfig().getJoin();

        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true).setMembers(Collections.singletonList(hazelIp));

        return config;
    }
}
