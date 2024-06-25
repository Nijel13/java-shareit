package ru.practicum.shareit.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;

@Service
@AllArgsConstructor
@Slf4j
public class ItemValidationService {
    private final ItemRepository itemRepository;

    public void checkOwnerItem(Long itemId, Long ownerId) { // Метод проверки соответствия владельца вещи
        if (!itemRepository.getItemMap().get(itemId).getOwner().equals(ownerId)) {
            log.error("Ошибка! Пользователь по Id: {} не является владельцем вещи! " +
                    "Изменение вещи ЗАПРЕЩЕНО!", ownerId);
            throw new ForbiddenException("Вносить изменения в параметры вещи может только владелец!");
        }
    }

    public void checkItemDtoWhenAdd(ItemDto itemDto) { // Метод проверки полей объекта itemDto перед добавлением
        if (itemDto.getAvailable() == null
                || itemDto.getName() == null || itemDto.getName().isBlank()
                || itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            log.error("Ошибка! Вещь с пустыми полями не может быть добавлена!");
            throw new ValidateException("Ошибка! Вещь с пустыми полями не может быть добавлена!");
        }
    }

}