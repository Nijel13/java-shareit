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

/**
 * TODO Sprint add-controllers.
 */
@RestController
@Slf4j
@RequestMapping("/items")
@AllArgsConstructor
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping // Метод добавления вещи
    public ItemDto addItem(@Min(1) @NotNull @RequestHeader(value = "X-Sharer-User-Id", required = false) Long ownerId,
                           @RequestBody ItemDto itemDto) {
        log.info("Добавляем вещь: {}", itemDto.getName());
        return toItemDto(itemService.addItem(toItem(itemDto), ownerId));
    }

    @PatchMapping("/{itemId}") // Метод обновления вещи по id
    public ItemDto updateItem(@Min(1) @NotNull @RequestHeader(value = "X-Sharer-User-Id", required = false) Long ownerId,
                              @Valid @Min(1) @NotNull @PathVariable Long itemId, @RequestBody ItemDto itemDto) {
        log.info("Обновляем вещь по Id={}", itemId);
        return toItemDto(itemService.updateItem(toItem(itemDto), itemId, ownerId));
    }

    @GetMapping("/{itemId}") // Метод получения вещи по ее id
    public ItemDto getItemById(@Min(1) @NotNull @RequestHeader(value = "X-Sharer-User-Id", required = false) Long ownerId,
                               @Valid @Min(1) @NotNull @PathVariable Long itemId) {
        log.info("Просмотр вещи по Id={}", itemId);
        return toItemDto(itemService.getItemById(itemId, ownerId));
    }

    @GetMapping //Метод получения списка вещей владельца
    public List<ItemDto> getListItems(@Min(1) @NotNull @RequestHeader(value = "X-Sharer-User-Id", required = false) Long ownerId) {
        log.info("Просмотр вещей пользователя по Id={}", ownerId);
        return itemService.getListItemsUserById(ownerId);
    }

    @GetMapping("/search") // Метод поиска по подстроке
    public List<ItemDto> getSearchItems(@Min(1) @NotNull @RequestHeader(value = "X-Sharer-User-Id", required = false) Long ownerId,
                                        @RequestParam(value = "text", required = false) String text) {
        return itemService.getSearchItems(text);
    }

}