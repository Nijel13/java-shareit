package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Map;

public interface ItemRepository {
    Item add(Item item);

    Item getById(long id);

    Item update(Item item);

    Map<Long, Item> getItemMap();

    List<ItemDto> getListById(Long ownerId);

    List<ItemDto> getSearch(String text);

}