package com.springbootacademy.batch17.pos.util.mappers;

import com.springbootacademy.batch17.pos.dto.response.ItemGetResponseDTO;
import com.springbootacademy.batch17.pos.entity.Item;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    List<ItemGetResponseDTO> entityListToDtoList(List<Item> items);
    List<ItemGetResponseDTO> pageListToDtoList(Page<Item> items);

    // Page<Item> items ------>   List<ItemGetResponseDTO> list;
}
