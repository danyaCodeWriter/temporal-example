package ru.danil.trysomething.activity;

import io.temporal.spring.boot.ActivityImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.danil.trysomething.dto.CheckDto;
import ru.danil.trysomething.entity.Check;
import ru.danil.trysomething.exception.FastFailException;
import ru.danil.trysomething.repository.CheckRepository;


@Component
@ActivityImpl(taskQueues = "PaymentTaskQueue")
@RequiredArgsConstructor
public class RepositoryActivityImpl implements RepositoryActivity {
    private final ModelMapper mapper;
    private final CheckRepository repository;

    @Override
    @Transactional
    public Boolean saveCheck(CheckDto check) {
        Check map;
        try {
            map = mapper.map(check, Check.class);
        } catch (Exception e) {
            throw new FastFailException("fail mapping");
        }
        Check save = repository.save(map);
        return true;
    }


}
