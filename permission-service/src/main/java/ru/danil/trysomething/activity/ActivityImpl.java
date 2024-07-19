package ru.danil.trysomething.activity;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@io.temporal.spring.boot.ActivityImpl(taskQueues = "PermissionTaskQueue")
@RequiredArgsConstructor
public class ActivityImpl implements Activity {

    @Override
    public void startActivity() {
        System.out.println("StartActivity processed");
    }

    @Override
    public void firstActivity() {
        System.out.println("firstActivity processed");
    }

    @Override
    public void secondActivity() {
        System.out.println("secondActivity processed");
    }

    @Override
    public void thirdActivity() {
        System.out.println("thirdActivity processed");
    }
}
