package ru.practicum.shareit.user.UserService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.validation.UserValidationService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    private final UserValidationService userValidationService;

    @Override
    public User add(User user) {
        userValidationService.checkUniqueEmailUserAdd(user); // Проверка объекта user на уникальность e-mail
        return repository.add(user);
    }

    @Override
    public User getById(long id) {
        return repository.getById(id);
    }

    @Override
    public User update(User user, Long userId) {
        getById(userId); // Проверка пользователя по его id на существование в памяти
        user.setId(userId);
        userValidationService.checkUniqueEmailUserUpdate(user); // Проверка объекта userDto на уникальность e-mail
        User updateUser = repository.getById(user.getId());
        if (user.getName() == null) {
            user.setName(updateUser.getName());
        }
        if (!user.getName().isBlank() && !updateUser.getName().equals(user.getName())) {
            updateUser.setName(user.getName());
        }
        if (user.getEmail() == null) {
            user.setEmail(updateUser.getEmail());
        }
        if (!user.getEmail().isBlank() && !updateUser.getEmail().equals(user.getEmail())) {
            updateUser.setEmail(user.getEmail());
        }
        return repository.update(updateUser);
    }


    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public List<User> getList() {
        return repository.getList();
    }

}