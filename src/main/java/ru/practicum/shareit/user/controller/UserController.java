package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.UserService.UserService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static ru.practicum.shareit.user.mapper.UserMapper.toUser;
import static ru.practicum.shareit.user.mapper.UserMapper.toUserDto;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @PostMapping // Метод добавления пользователя
    public UserDto add(@Valid @RequestBody UserDto userDto) {
        log.info("Добавляем пользователя по имени: {}.", userDto.getName());
        return toUserDto(userService.add(toUser(userDto)));
    }

    @PatchMapping("/{userId}") // Метод обновления пользователя по его id
    public UserDto update(@Valid @Min(1) @PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        log.info("Обновляем пользователя по Id={}.", userId);
        return toUserDto(userService.update(toUser(userDto), userId));
    }

    @GetMapping("/{userId}") // Метод получения пользователя по его id
    public UserDto update(@Valid @PathVariable Long userId) {
        log.info("Получаем пользователя по Id={}.", userId);
        return toUserDto(userService.getById(userId));
    }

    @DeleteMapping("/{userId}") // Метод удаления пользователя по id
    public void delete(@Valid @Min(1) @PathVariable Long userId) {
        log.info("Удалаяем пользователя по Id={}.", userId);
        userService.delete(userId);
    }

    @GetMapping
    public List<User> getList() { // Метод получения списка всех пользователей
        log.info("Получаем список всех пользователей.");
        return userService.getList();
    }
}