package ru.danil.trysomething.activity;


import io.temporal.activity.ActivityInterface;
import ru.danil.trysomething.dto.CheckDto;
import ru.danil.trysomething.entity.Check;

@ActivityInterface
public interface RepositoryActivity {
    Boolean saveCheck(CheckDto check);

}
