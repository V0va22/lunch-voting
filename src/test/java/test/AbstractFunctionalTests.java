package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:postgresql://localhost:5432/voting-test",
		"spring.datasource.username=postgres",
		"spring.datasource.password=root",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"spring.datasource.data=classpath:/initial_data.sql"
})
@IntegrationTest("server.port:8090")
public class AbstractFunctionalTests {

	public static final String HTTP_LOCALHOST = "http://localhost:8090/";

	protected RestTemplate restTemplate = new RestTemplate();

	protected <T> ResponseEntity<T> send(String url, HttpMethod method, Class<T> tClass, ROLE role) {
		return send(url,method, null, tClass, role);
	}
	protected <T> ResponseEntity<T> send(String url, HttpMethod method, Object body, Class<T> tClass, ROLE role){
		HttpEntity<?> requestEntity = new HttpEntity<>(body, role.getAuthHeader());
		return restTemplate.exchange(HTTP_LOCALHOST + url, method, requestEntity, tClass);
	}

	public enum ROLE {
		ADMIN,
		USER;

		public MultiValueMap<String, String> getAuthHeader(){
			MultiValueMap<String, String> headers = new HttpHeaders();

			headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			if (this == ADMIN){
				headers.add(HttpHeaders.AUTHORIZATION, "Basic YWRtaW46YWRtaW4=");
			} else if (this == USER) {
				headers.add(HttpHeaders.AUTHORIZATION, "Basic dXNlcjp1c2Vy");
			}
			return headers;
		}
	}
}
