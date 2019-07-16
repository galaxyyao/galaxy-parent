package com.galaxy.cypher;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.galaxy.common.profile.DefaultProfileUtil;
import com.google.common.base.Strings;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@SpringBootApplication
@ComponentScan({"com.galaxy"})
public class CypherApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(CypherApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(CypherApplication.class);
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
	public void run(String... arg0) {

	}
}
