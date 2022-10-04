package br.com.convergencia.testejava.login;

import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;

@SpringBootApplication
public class TesteJavaLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteJavaLoginApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(new CustomHttpComponentsClientHttpRequestFactory());
	}
	
	private static final class CustomHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

		@Override
		protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {

			if (HttpMethod.GET.equals(httpMethod)) {
				return new HttpEntityEnclosingGetRequestBase(uri);
			}
			return super.createHttpUriRequest(httpMethod, uri);
		}
	}
	
	private static final class HttpEntityEnclosingGetRequestBase extends HttpEntityEnclosingRequestBase {

		public HttpEntityEnclosingGetRequestBase(final URI uri) {
			super.setURI(uri);
		}

		@Override
		public String getMethod() {
			return HttpMethod.GET.name();
		}
	}

}
