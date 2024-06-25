package ru.practicum.shareit.item.ItemService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.UserService.UserService;
import ru.practicum.shareit.validation.ItemValidationService;
import ru.practicum.shareit.validation.UserValidationService;

import java.util.Collections;
import java.util.List;

import static ru.practicum.shareit.item.mapper.ItemMapper.toItemDto;

@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserService userService;
    private final UserValidationService userValidationService;
    private final ItemValidationService itemValidationService;

    @Override
    public Item add(Item item, Long ownerId) {
        userService.getById(ownerId); // Проверяем владельца вещи по id на существование в памяти
        item.setOwner(ownerId);
        itemValidationService.checkItemDtoWhenAdd(toItemDto(item)); // Проверяем поля объекта itemDto перед добавлением
        return repository.add(item);
    }

    @Override
    public Item getById(Long id, Long ownerId) {
        if (!repository.getItemMap().containsKey(id)) { // Проверяем вещь по id на существование в памяти
            log.error("Вещь по id={} отсутствует в памяти.", id);
            throw new NotFoundException("Вещь по id=" + id + " не существует");
        }
        userService.getById(ownerId); // Проверяем владельца вещи по id на существование в памяти
        return repository.getById(id);
    }

    @Override
    public Item update(Item item, Long itemId, Long ownerId) {
        getById(itemId, ownerId); // Проверяем вещь по id на существование в памяти
        item.setId(itemId);
        userService.getById(ownerId); // Проверяем владельца вещи по id на существование в памяти
        item.setOwner(ownerId);
        itemValidationService.checkOwnerItem(itemId, ownerId); // Проверяем соответствие владельца вещи

        Item updateItem = getById(itemId, ownerId);
        if (item.getName() == null) {
            item.setName(updateItem.getName());
        }
        if (!item.getName().isBlank() && !updateItem.getName().equals(item.getName())) {
            updateItem.setName(item.getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(updateItem.getDescription());
        }
        if (!item.getDescription().isBlank() && !updateItem.getDescription().equals(item.getDescription())) {
            updateItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(updateItem.getAvailable());
        }
        if (updateItem.getAvailable() != item.getAvailable()) {
            updateItem.setAvailable(item.getAvailable());
        }
        if (!updateItem.getOwner().equals(item.getOwner())) {
            updateItem.setOwner(item.getOwner());
        }
        return repository.update(updateItem);
    }

    @Override
    public List<ItemDto> getListUserById(Long ownerId) {
        userService.getById(ownerId); // Проверяем владельца вещи по id на существование в памяти
        return repository.getListById(ownerId);
    }

    @Override
    public List<ItemDto> getSearch(String text) {
        if (text == null || text.isBlank()) { // Проверка на пустою строку и на null
            log.error("В поиск передано пустое значение {}!", text);
            return Collections.emptyList();
        }
        log.info("Осуществляем поиск вещи содержащее текст: {}.", text);
        return repository.getSearch(text);
    }

}