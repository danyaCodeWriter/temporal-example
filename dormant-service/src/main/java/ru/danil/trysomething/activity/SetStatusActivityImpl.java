package ru.danil.trysomething.activity;

import io.temporal.spring.boot.ActivityImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.danil.trysomething.exception.FastFailException;


@Component
@ActivityImpl(taskQueues = "DormantTaskQueue")
@Data
@Slf4j
@RequiredArgsConstructor
public class SetStatusActivityImpl implements SetStatusActivity {

    private final RestTemplate template;

    @Override
    public void setStatusToBlocked(Long id) {
        String url = String.format("http://localhost:8081/users/%d/status?status=BLOCKED", id);
        ResponseEntity<String> booleanResponseEntity = template.postForEntity(url, id, String.class);
        if (booleanResponseEntity.getStatusCode().is4xxClientError()) {
            throw new FastFailException("400 ошибка, нет смысла ретраить!");
        }
    }
}
