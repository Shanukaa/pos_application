package com.springbootacademy.batch17.pos.service;

import com.springbootacademy.batch17.pos.dto.paginated.PaginatedResponseItemDTO;
import com.springbootacademy.batch17.pos.dto.request.ItemSaveRequestDTO;
import com.springbootacademy.batch17.pos.dto.response.ItemGetResponseDTO;

import java.util.List;

public interface ItemService {
    String saveItem(ItemSaveRequestDTO itemSaveRequestDTO);

    List<ItemGetResponseDTO> getItemByNameAndStatus(String itemName);

    List<ItemGetResponseDTO> getItemByNameAndStatusByMapstruct(String itemName);

    List<ItemGetResponseDTO> getItemByStatus(boolean activeState);

    PaginatedResponseItemDTO getItemByActiveStateWithPaginated(boolean activeState, int page, int size);

    PaginatedResponseItemDTO getAllItemsByPaginated(int page, int size);
}
