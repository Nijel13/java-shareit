package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    User add(User user);

    User getById(long id);

    User update(User user);

    void delete(Long id);

    List<User> getList();

}