package io.pivotal.noscan.cf.nozzle;

import io.pivotal.cf.nozzle.config.CfFirehoseProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(CfFirehoseUserPasswordTest.SpringConfig.class)
@ActiveProfiles("userpassword")
public class CfFirehoseUserPasswordTest {

    @Autowired
    private CfFirehoseProperties cfFirehoseProperties;

    @Test
    public void testThatPropsArePopulated() {
        assertThat(cfFirehoseProperties).isNotNull();
        assertThat(cfFirehoseProperties.getUser()).isEqualTo("auser");
        assertThat(cfFirehoseProperties.getPassword()).isEqualTo("apass");
        assertThat(cfFirehoseProperties.getAuthToken()).startsWith("bearer eyJhbGciOiJSUzI1");
    }

    @Configuration
    @SpringBootApplication
    static class SpringConfig {

        @Bean
        public CfFirehoseProperties cfFirehoseProperties() {
            return new CfFirehoseProperties();
        }

    }

}
