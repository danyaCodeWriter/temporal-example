package ru.danil.trysomething.activity;

import io.temporal.spring.boot.ActivityImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.danil.trysomething.client.CheckDto;
import ru.danil.trysomething.exception.FastFailException;


@Component
@ActivityImpl(taskQueues = "DormantTaskQueue")
@Data
@Slf4j
@RequiredArgsConstructor
public class PaymentActivityImpl implements PaymentActivity {

    private final RestTemplate template;

    @Override
    public Boolean deposit(CheckDto message) {
        ResponseEntity<Boolean> responseEntityResponseEntity = template.postForEntity("http://localhost:8084/payment/deposit", message, Boolean.class);
        return responseEntityResponseEntity.getBody();
    }

    @Override
    public Boolean withdraw(CheckDto message) {
        ResponseEntity<Boolean> booleanResponseEntity = template.postForEntity("http://localhost:8084/payment/withdraw", message, Boolean.class);
        if (booleanResponseEntity.getStatusCode().is2xxSuccessful()) {
            return booleanResponseEntity.getBody();
        } else if (booleanResponseEntity.getStatusCode().is4xxClientError()) {
            throw new FastFailException("400 ошибка, нет смысла ретраить!");
        } else {
            return false;
        }
    }
}
