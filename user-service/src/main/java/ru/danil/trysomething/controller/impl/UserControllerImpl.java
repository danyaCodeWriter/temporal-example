package ru.danil.trysomething.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.danil.trysomething.controller.UserController;
import ru.danil.trysomething.dto.UserDto;
import ru.danil.trysomething.entity.User;
import ru.danil.trysomething.service.UserService;
import ru.danil.trysomething.statuses.EventUserStatus;

import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createFullUser(@RequestBody @Validated UserDto userDto) {
        log.info("Creating user: {}", userDto);
        User user = userService.createUser(userDto);
        log.debug("Created user: {}", user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/address")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addAddressToUser(@RequestParam Set<Long> addressId,
                                                 @PathVariable long id) {
        if (addressId != null && !addressId.isEmpty()) {
            User user = userService.addAddressToUser(id, addressId);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<UserDto> get(@PathVariable long id) {
        UserDto user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/status")
    @Override
    public ResponseEntity<String> setStatus(@PathVariable long id, @RequestParam EventUserStatus status) {
        userService.setStatus(id, status);
        return ResponseEntity.ok(status.toString());
    }
}
