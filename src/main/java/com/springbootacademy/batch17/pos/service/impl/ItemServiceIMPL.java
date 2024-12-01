package com.springbootacademy.batch17.pos.service.impl;

import com.springbootacademy.batch17.pos.dto.paginated.PaginatedResponseItemDTO;
import com.springbootacademy.batch17.pos.dto.request.ItemSaveRequestDTO;
import com.springbootacademy.batch17.pos.dto.response.ItemGetResponseDTO;
import com.springbootacademy.batch17.pos.entity.Item;
import com.springbootacademy.batch17.pos.exception.NotFoundException;
import com.springbootacademy.batch17.pos.repo.ItemRepo;
import com.springbootacademy.batch17.pos.service.ItemService;
import com.springbootacademy.batch17.pos.util.mappers.ItemMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceIMPL implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public String saveItem(ItemSaveRequestDTO itemSaveRequestDTO) {
//        Item item = new Item(
//                1,
//                itemSaveRequestDTO.getItemName(),
//                itemSaveRequestDTO.getMeasuringUnitType(),
//                itemSaveRequestDTO.getBalanceQty(),
//                itemSaveRequestDTO.getSupplierPrice(),
//                itemSaveRequestDTO.getSellingPrice(),
//                true
//        );
//        itemRepo.save(item);
//
//        return itemSaveRequestDTO.getItemName() + " Item saved successfully";
        Item item = modelMapper.map(itemSaveRequestDTO, Item.class);
        if (!itemRepo.existsById(item.getItemId())) {
            itemRepo.save(item);
            return item.getItemName() + " saved Successfully";
        } else {
            throw new DuplicateKeyException("Already Added");
        }
    }

    @Override
    public List<ItemGetResponseDTO> getItemByNameAndStatus(String itemName) {
        boolean b = true;
        List<Item> items = itemRepo.findAllByItemNameEqualsAndActiveStateEquals(itemName, b);

        if (items.size() > 0) {
            List<ItemGetResponseDTO> itemGetResponseDTOS = modelMapper.map(items, new TypeToken<List<ItemGetResponseDTO>>() {
            }.getType());

            return itemGetResponseDTOS;
        } else {
            throw new RuntimeException("Item is not active");
        }

    }

    @Override
    public List<ItemGetResponseDTO> getItemByNameAndStatusByMapstruct(String itemName) {
        boolean b = true;
        List<Item> items = itemRepo.findAllByItemNameEqualsAndActiveStateEquals(itemName, b);

        if (items.size() > 0) {
            List<ItemGetResponseDTO> itemGetResponseDTOS = itemMapper.entityListToDtoList(items);
            return itemGetResponseDTOS;
        } else {
            throw new RuntimeException("Item is not active");
        }
    }

    @Override
    public List<ItemGetResponseDTO> getItemByStatus(boolean activeState) {
        List<Item> items = itemRepo.findAllByActiveStateEquals(activeState);
        if (items.size() > 0) {
            List<ItemGetResponseDTO> itemGetResponseDTOS = itemMapper.entityListToDtoList(items);
            return itemGetResponseDTOS;
        } else {
            throw new NotFoundException("Item is not active");
        }
    }

    @Override
    public PaginatedResponseItemDTO getItemByActiveStateWithPaginated(boolean activeState, int page, int size) {
        Page<Item> items = itemRepo.findAllByActiveStateEquals(activeState, PageRequest.of(page, size));
        if (items.getSize() < 1) {
            throw new NotFoundException("No Item Found");
        }
        PaginatedResponseItemDTO paginatedResponseItemDTO = new PaginatedResponseItemDTO(
                itemMapper.pageListToDtoList(items),
                itemRepo.countAllByActiveStateEquals(activeState)
        );
        return paginatedResponseItemDTO;


    }

    @Override
    public PaginatedResponseItemDTO getAllItemsByPaginated(int page, int size) {
        Page<Item> items = itemRepo.findAll(PageRequest.of(page,size));
        if(items.getSize() < 1){
            throw new NotFoundException("No Item Found");
        }
        PaginatedResponseItemDTO paginatedResponseItemDTO = new PaginatedResponseItemDTO(
                itemMapper.pageListToDtoList(items),
                itemRepo.count()
        );
        return paginatedResponseItemDTO;
    }
}
