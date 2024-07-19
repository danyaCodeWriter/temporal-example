package ru.danil.trysomething.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface Activity {

    @ActivityMethod
    void startActivity();
    @ActivityMethod
    void firstActivity();
    @ActivityMethod
    void secondActivity();
    @ActivityMethod
    void thirdActivity();
}
