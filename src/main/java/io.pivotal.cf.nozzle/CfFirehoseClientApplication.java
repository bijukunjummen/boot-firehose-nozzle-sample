package io.pivotal.cf.nozzle;

import org.cloudfoundry.doppler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@SpringBootApplication
public class CfFirehoseClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfFirehoseClientApplication.class, args);
	}

}


@Component
class SampleCommandLineRunner implements CommandLineRunner {

	private final DopplerClient dopplerClient;

	private static final Logger LOGGER = LoggerFactory.getLogger(SampleCommandLineRunner.class);

	@Autowired
	public SampleCommandLineRunner(DopplerClient dopplerClient) {
		this.dopplerClient = dopplerClient;
	}

	@Override
	public void run(String... args) throws Exception {

		Flux<Event> cfEvents = this.dopplerClient.firehose(
				FirehoseRequest
						.builder()
						.subscriptionId(UUID.randomUUID().toString()).build());

		cfEvents
//				.filter(e -> LogMessage.class.isInstance(e))
//				.map(e -> (LogMessage)e)
//				.map(LogMessage::getMessage)
				.subscribe(e -> LOGGER.info(e.toString()));

	}

}
