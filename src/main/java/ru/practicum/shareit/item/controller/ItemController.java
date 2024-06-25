package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemService.ItemService;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static ru.practicum.shareit.item.mapper.ItemMapper.toItem;
import static ru.practicum.shareit.item.mapper.ItemMapper.toItemDto;

@RestController
@Slf4j
@RequestMapping("/items")
@AllArgsConstructor
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping // Метод добавления вещи
    public ItemDto add(@Min(1) @NotNull @RequestHeader(value = "X-Sharer-User-Id") Long ownerId,
                           @RequestBody ItemDto itemDto) {
        log.info("Добавляем вещь: {}", itemDto.getName());
        return toItemDto(itemService.add(toItem(itemDto), ownerId));
    }

    @PatchMapping("/{itemId}") // Метод обновления вещи по id
    public ItemDto update(@Min(1) @NotNull @RequestHeader(value = "X-Sharer-User-Id") Long ownerId,
                              @Valid @Min(1) @NotNull @PathVariable Long itemId, @RequestBody ItemDto itemDto) {
        log.info("Обновляем вещь по Id={}", itemId);
        return toItemDto(itemService.update(toItem(itemDto), itemId, ownerId));
    }

    @GetMapping("/{itemId}") // Метод получения вещи по ее id
    public ItemDto getById(@Min(1) @NotNull @RequestHeader(value = "X-Sharer-User-Id") Long ownerId,
                               @Valid @Min(1) @NotNull @PathVariable Long itemId) {
        log.info("Просмотр вещи по Id={}", itemId);
        return toItemDto(itemService.getById(itemId, ownerId));
    }

    @GetMapping //Метод получения списка вещей владельца
    public List<ItemDto> getList(@Min(1) @NotNull @RequestHeader(value = "X-Sharer-User-Id") Long ownerId) {
        log.info("Просмотр вещей пользователя по Id={}", ownerId);
        return itemService.getListUserById(ownerId);
    }

    @GetMapping("/search") // Метод поиска по подстроке
    public List<ItemDto> getSearch(@Min(1) @NotNull @RequestHeader(value = "X-Sharer-User-Id") Long ownerId,
                                        @RequestParam(value = "text", required = false) String text) {
        return itemService.getSearch(text);
    }

}