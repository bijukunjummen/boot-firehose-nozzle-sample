package io.pivotal.cf.nozzle.config;

import cf.dropsonde.firehose.Firehose;
import cf.dropsonde.firehose.FirehoseBuilder;
import org.cloudfoundry.client.CloudFoundryClient;
import org.cloudfoundry.spring.client.SpringCloudFoundryClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * Responsible for all bean definitions to set up a Firehose client
 */
@Configuration
@EnableConfigurationProperties(CfFirehoseProperties.class)
public class FirehoseClientConfiguration {
    @Bean
    public Firehose firehose(CfFirehoseProperties firehoseClientProperties) {
        String oAuthToken = getOAuthAccessToken(firehoseClientProperties);
        return FirehoseBuilder.create(
                firehoseClientProperties.getDopplerEndpoint(),
                oAuthToken
        ).skipTlsValidation(firehoseClientProperties.isSkipTlsValidation())
                .build();
    }


    private String getOAuthAccessToken(CfFirehoseProperties firehoseClientProperties) {
        if (firehoseClientProperties.getUser() != null
                && firehoseClientProperties.getPassword() != null
                && firehoseClientProperties.getApiEndpoint() != null ) {
            String oauthAccessToken = loginAndGetAccessToken(firehoseClientProperties);
            return oauthAccessToken;
        }
        Assert.notNull(firehoseClientProperties.getAuthToken());
        return firehoseClientProperties.getAuthToken();
    }

    private String loginAndGetAccessToken(CfFirehoseProperties props) {
        CloudFoundryClient cloudFoundryClient= SpringCloudFoundryClient.builder()
                .host(props.getApiEndpoint())
                .username(props.getUser())
                .password(props.getPassword())
                .skipSslValidation(true)
                .build();
        return cloudFoundryClient.getAccessToken().get();
    }
}
