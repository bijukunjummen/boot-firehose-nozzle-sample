package io.pivotal.cf.nozzle.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cf")
@Data
public class CfProperties {
	private String user;
	private String password;
	private String host;
	private boolean skipSslValidation = true;
}
