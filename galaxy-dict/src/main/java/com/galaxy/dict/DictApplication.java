package com.galaxy.dict;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.galaxy.common.profile.DefaultProfileUtil;
import com.google.common.base.Strings;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackageClasses = { DictApplication.class, Jsr310JpaConverters.class })
@SpringBootApplication
@ComponentScan({ "com.galaxy" })
@EnableScheduling
public class DictApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(DictApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(DictApplication.class);
		app.addListeners();
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		String protocol = env.getProperty("server.ssl.key-store") == null ? "http" : "https";
		logger.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}{}\n\t"
						+ "External: \t{}://{}:{}{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"),
				Strings.nullToEmpty(env.getProperty("server.contextPath")), protocol,
				InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
				Strings.nullToEmpty(env.getProperty("server.contextPath")), env.getActiveProfiles());
	}

	@Override
	public void run(String... arg0) throws Exception {

	}
}
