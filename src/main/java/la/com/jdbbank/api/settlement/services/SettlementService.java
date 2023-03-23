package la.com.jdbbank.api.settlement.services;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Arrays;
import la.com.jdbbank.api.settlement.entity.SettlementResponse;
import la.com.jdbbank.api.settlement.utility.SignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
public class SettlementService {
	private final Logger log = LoggerFactory.getLogger(SettlementService.class);
	@Value("${BASE_URL_LMPS}")
	private String BASE_URL;// = "http://175.28.1.3:8055/report/settlement";
	private static final String LMPS_SETTLEMENT = "/settlement";
	private static final String LMPS_TRANSACTION = "/transaction";

	private RestTemplate restTemplate;
	@Autowired
	private SignatureUtil signUtil;
	@Autowired
	ObjectMapper objectMapper;

	public ResponseEntity<SettlementResponse> getSetlement(String body) throws JsonMappingException, JsonProcessingException {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("X-SOURCE-REQUEST-SIGNATURE", this.signUtil.signedhash(body));
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));

		HttpEntity<?> requestEntity = new HttpEntity(body, requestHeaders);

		try {
			/***
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5*1000);
			factory.setReadTimeout(5*1000);
			 */
			/***
			this.restTemplate = new RestTemplateBuilder()
					.setConnectTimeout(Duration.ofMinutes(10)) // connection time-out
					.setReadTimeout(Duration.ofMinutes(10)) // read time-out
					.build();
			 */
			this.restTemplate = new RestTemplate();

			this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			this.log.info("JDB Settlement Request To LMPS Service " + requestEntity);
			String url = BASE_URL+LMPS_SETTLEMENT;
			ResponseEntity<SettlementResponse> response = this.restTemplate.postForEntity(url, requestEntity,
					SettlementResponse.class, new Object[0]);
			
			this.log.info("JDB Settlement Response To LMPS Service " + response.getStatusCodeValue() + " : "
					+  new ObjectMapper().setSerializationInclusion(Include.NON_NULL).writerWithDefaultPrettyPrinter()
					.writeValueAsString(response.getBody()));
			return response;
		} catch (RestClientResponseException e) {
			this.log.error("JDB Settlement Response To LMPS Service  Error " + e.getRawStatusCode() + " : "
					+ e.getResponseBodyAsString() + " - " + e.getMessage());

			return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(this.objectMapper.readValue(e.getResponseBodyAsString(), SettlementResponse.class));
		}
	}

	public ResponseEntity<?> getTransaction(String body) throws JsonMappingException, JsonProcessingException {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("X-SOURCE-REQUEST-SIGNATURE", this.signUtil.signedhash(body));
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));

		HttpEntity<?> requestEntity = new HttpEntity(body, requestHeaders);

		try {

			this.restTemplate = new RestTemplate();
			this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			this.log.info("JDB Log Transaction To Lapnet Service Request " + requestEntity);
			String url = BASE_URL+LMPS_TRANSACTION;
			ResponseEntity<?> response = this.restTemplate.postForEntity(url, requestEntity, String.class);

			this.log.info("JDB Log Transaction To Lapnet Service Response " + response.getStatusCodeValue() + " : "
					+  new ObjectMapper().writerWithDefaultPrettyPrinter()
					.writeValueAsString(response.getBody())+" : "+response.getHeaders()+" : "+body+" : "+requestEntity);
			return response;
		} catch (RestClientResponseException e) {
			this.log.error("JDB Inquiry To Lapnet Service Response Error Status " + e.getRawStatusCode() + " : "
					+ e.getResponseBodyAsString() + " - " + e.getMessage()+" : "+e.getResponseHeaders()+" : "+body);

			return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(this.objectMapper.readValue(e.getResponseBodyAsString(), String.class));
		}
	}
}
