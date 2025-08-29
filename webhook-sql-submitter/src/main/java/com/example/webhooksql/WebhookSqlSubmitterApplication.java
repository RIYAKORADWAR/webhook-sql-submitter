package main.java.com.example.webhooksql;


import com.example.webhooksql.dto.FinalQueryRequest;
import com.example.webhooksql.dto.GenerateWebhookRequest;
import com.example.webhooksql.dto.GenerateWebhookResponse;
import com.example.webhooksql.http.HiringClient;
import com.example.webhooksql.logic.SqlBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class WebhookSqlSubmitterApplication implements ApplicationRunner {


private final HiringClient client;
private final SqlBuilder sqlBuilder;


@Value("${app.name:${APP_NAME:John Doe}}")
private String name;
@Value("${app.regNo:${APP_REG_NO:REG12347}}")
private String regNo;
@Value("${app.email:${APP_EMAIL:john@example.com}}")
private String email;


public static void main(String[] args) {
SpringApplication.run(WebhookSqlSubmitterApplication.class, args);
}


@Override
public void run(ApplicationArguments args) {
log.info("Starting submission flow for regNo={}", regNo);


// 1) generate webhook
GenerateWebhookRequest req = new GenerateWebhookRequest(name, regNo, email);
GenerateWebhookResponse resp = client.generate(req);
log.info("Received webhook={}, token=<redacted>", resp.webhook());


// 2) build SQL based on regNo parity
String finalSql = sqlBuilder.buildForRegNo(regNo);
log.info("Final SQL built ({} chars)", finalSql.length());


// 3) POST to returned webhook URL with JWT in Authorization
client.submitFinalQuery(resp.webhook(), resp.accessToken(), new FinalQueryRequest(finalSql));
log.info("Submission sent successfully ✅");
}
}