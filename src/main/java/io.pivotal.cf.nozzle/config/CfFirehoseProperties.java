package io.pivotal.cf.nozzle.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cf.firehose")
@Data
public class CfFirehoseProperties {
    private String dopplerEndpoint;
    private String authToken;
    private boolean skipTlsValidation = false;
    //Expected to have either user/password OR authtoken. Will prioritize user/password over oauthtoken
    private String user;
    private String password;
    private String apiEndpoint;
}
