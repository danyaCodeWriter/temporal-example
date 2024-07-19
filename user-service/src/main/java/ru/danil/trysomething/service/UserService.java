package ru.danil.trysomething.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danil.trysomething.dto.UserDto;
import ru.danil.trysomething.entity.Address;
import ru.danil.trysomething.entity.Event;
import ru.danil.trysomething.entity.User;
import ru.danil.trysomething.repository.AddressRepository;
import ru.danil.trysomething.repository.UserRepository;
import ru.danil.trysomething.statuses.EventUserStatus;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper mapper;
    private final KafkaTemplate<String, Event> template;

    public User createUser(UserDto userDto) {
        User map = mapper.map(userDto, User.class);
        User save = userRepository.save(map);
        proceedEvent(EventUserStatus.valueOf(save.getStatus()), save.getId());
        return save;
    }

    public UserDto getUser(long id) {
        return userRepository.findById(id)
                .map(user -> mapper.map(user, UserDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void setStatus(long id, EventUserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        user.setStatus(status.toString());
        proceedEvent(status, user.getId());
        userRepository.save(user);
    }

    private void proceedEvent(EventUserStatus status, long userId) {
        Event event = new Event();
        event.setStatus(status);
        event.setUuid(UUID.randomUUID());
        RandomGenerator rnd = RandomGenerator.getDefault();
        event.setUserId(userId);
        template.send("events", event.getUuid().toString(), event);
    }

    @Transactional
    public User addAddressToUser(long userId, Set<Long> addressId) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        List<Address> allById = addressRepository.findAllById(addressId);
        user.getAddress().addAll(allById);
        return userRepository.save(user);
    }
}
