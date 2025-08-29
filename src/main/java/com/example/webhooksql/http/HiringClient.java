package main.java.com.example.webhooksql.http;


import com.example.webhooksql.dto.FinalQueryRequest;
import com.example.webhooksql.dto.GenerateWebhookRequest;
import com.example.webhooksql.dto.GenerateWebhookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class HiringClient {


private final WebClient webClient; // baseUrl points to generateWebhook


public GenerateWebhookResponse generate(GenerateWebhookRequest request) {
return webClient.post()
.uri("")
.contentType(MediaType.APPLICATION_JSON)
.bodyValue(request)
.retrieve()
.bodyToMono(GenerateWebhookResponse.class)
.block();
}


public void submitFinalQuery(String webhookUrl, String accessToken, FinalQueryRequest body) {
WebClient.create()
.post()
.uri(webhookUrl)
.headers(h -> {
h.set(HttpHeaders.AUTHORIZATION, accessToken); // raw JWT per spec
h.setContentType(MediaType.APPLICATION_JSON);
})
.body(BodyInserters.fromValue(body))
.retrieve()
.toBodilessEntity()
.doOnError(err -> log.error("Submission failed", err))
.block();
}
}