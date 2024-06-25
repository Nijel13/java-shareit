package ru.practicum.shareit.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class UserValidationService {
    private final UserRepository userRepository;

    public void checkUniqueEmailUserAdd(User user) { // Метод проверки уникальности e-mail при добавлении
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            log.error("Ошибка! Пользователь с пустым e-mail не может быть добавлен!");
            throw new ValidateException("Ошибка! Пользователь с пустым e-mail не может быть добавлен!");
        }
        for (User listUser : userRepository.getList()) {
            if (listUser.getEmail().equals(user.getEmail()) && !listUser.getId().equals(user.getId())) {
                log.error("Ошибка! Пользователь с e-mail: {} уже существует!", user.getEmail());
                throw new ConflictException("Ошибка! Пользователь с e-mail " + user.getEmail() + " уже существует!");
            }
        }
    }

    public void checkUniqueEmailUserUpdate(User user) { // Метод проверки уникальности e-mail при обновлении
        for (User u : userRepository.getList()) {
            if (u.getEmail().equals(user.getEmail()) && !u.getId().equals(user.getId())) {
                log.error("Ошибка! Пользователь с e-mail: {} уже существует!", user.getEmail());
                throw new ConflictException("Ошибка! Пользователь с e-mail " + user.getEmail() + " уже существует!");
            }
        }
    }
}