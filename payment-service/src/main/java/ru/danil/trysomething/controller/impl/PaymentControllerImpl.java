package ru.danil.trysomething.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.danil.trysomething.controller.PaymentController;
import ru.danil.trysomething.dto.CheckDto;
import ru.danil.trysomething.service.PaymentService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService service;

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<Boolean> deposit(@RequestBody @Validated CheckDto check) {
        log.error("execute controller one more!");
        Boolean deposit = service.deposit(check);
        ResponseEntity<Boolean> booleanResponseEntity = deposit ? ResponseEntity.ok(deposit) : ResponseEntity.badRequest().build();
        return booleanResponseEntity;
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<Boolean> withdraw(@RequestBody @Validated CheckDto check) {
        log.error("execute controller one more!");
        Boolean withdraw = service.withdraw(check);
        ResponseEntity<Boolean> booleanResponseEntity = withdraw ? ResponseEntity.ok(withdraw) : ResponseEntity.badRequest().build();
        return booleanResponseEntity;
    }
}
