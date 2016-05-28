package io.pivotal.cf.nozzle.config;

import org.cloudfoundry.client.CloudFoundryClient;
import org.cloudfoundry.doppler.DopplerClient;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.CloudFoundryOperationsBuilder;
import org.cloudfoundry.reactor.doppler.ReactorDopplerClient;
import org.cloudfoundry.spring.client.SpringCloudFoundryClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Responsible for all bean definitions to set up a Firehose client
 */
@Configuration
@EnableConfigurationProperties(CfProperties.class)
public class FirehoseClientConfiguration {
	@Bean
	public DopplerClient dopplerClient(SpringCloudFoundryClient cloudFoundryClient) {
		return
				ReactorDopplerClient.builder()
						.cloudFoundryClient(cloudFoundryClient)
						.build();
	}

	@Bean
	public SpringCloudFoundryClient cloudFoundryClient(CfProperties cfProps) {
		return SpringCloudFoundryClient.builder()
				.host(cfProps.getHost())
				.username(cfProps.getUser())
				.password(cfProps.getPassword())
				.skipSslValidation(cfProps.isSkipSslValidation())
				.build();
	}

	@Bean
	public CloudFoundryOperations cloudFoundryOperations(CloudFoundryClient cloudFoundryClient) {
		return new CloudFoundryOperationsBuilder().cloudFoundryClient(cloudFoundryClient).build();
	}


}

