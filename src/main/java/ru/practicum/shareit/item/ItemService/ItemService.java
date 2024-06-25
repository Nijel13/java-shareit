package ru.practicum.shareit.item.ItemService;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item add(Item item, Long ownerId);

    Item getById(Long id, Long ownerId);

    Item update(Item item, Long id, Long ownerId);

    List<ItemDto> getListUserById(Long ownerId);

    List<ItemDto> getSearch(String text);
}