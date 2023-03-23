package la.com.jdbbank.api.settlement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class JdblapnetsattlementApplication {
	public static void main(String[] args) {
		SpringApplication.run(la.com.jdbbank.api.settlement.JdblapnetsattlementApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
		/***
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(5*1000);
		factory.setReadTimeout(5*1000);
		 */
		return restTemplateBuilder.setConnectTimeout(Duration.ofMinutes(10)).build();

		/***
		return new RestTemplateBuilder()
				.setConnectTimeout(Duration.ofMinutes(5))
				.setReadTimeout(Duration.ofMinutes(5))
				.build();
		 */

	}

}
