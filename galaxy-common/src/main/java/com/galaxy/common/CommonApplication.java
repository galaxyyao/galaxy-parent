package com.galaxy.common;

import com.galaxy.common.profile.DefaultProfileUtil;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@EntityScan(basePackageClasses = { CommonApplication.class, Jsr310JpaConverters.class })
@SpringBootApplication
@ComponentScan({ "com.galaxy" })
public class CommonApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CommonApplication.class);

	protected static String getApplicationInfo(Environment env) {
		String protocol = env.getProperty("server.ssl.key-store") == null ? "http" : "https";
		String hostAddress;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			hostAddress = "Unknown host address";
		}
		return "\n----------------------------------------------------------\n\t" + "Application '"
				+ env.getProperty("spring.application.name") + "' is running! Access URLs:\n\t" + "Local: \t\t"
				+ protocol + "://localhost:" + env.getProperty("server.port")
				+ Strings.nullToEmpty(env.getProperty("server.contextPath")) + "\n\t" + "External: \t" + protocol
				+ "://" + hostAddress + ":" + env.getProperty("server.port")
				+ Strings.nullToEmpty(env.getProperty("server.contextPath")) + "\n\t" + "Profile(s): \t["
				+ String.join(",", env.getActiveProfiles())
				+ "]\n----------------------------------------------------------";
	}

	protected static ConfigurableApplicationContext startApplication(String[] args,
			Class<? extends CommonApplication> clazz) {
		SpringApplication app = new SpringApplication(clazz);
		app.addListeners(new ApplicationPidFileWriter());
		DefaultProfileUtil.addDefaultProfile(app);
		return app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Application started.");
	}
}
