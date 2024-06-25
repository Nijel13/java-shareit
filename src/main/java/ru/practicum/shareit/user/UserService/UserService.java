package ru.practicum.shareit.user.UserService;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    User add(User user);

    User getById(long id);

    User update(User user, Long userId);

    void delete(Long id);

    List<User> getList();

}