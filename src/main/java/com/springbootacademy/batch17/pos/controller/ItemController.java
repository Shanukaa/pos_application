package com.springbootacademy.batch17.pos.controller;

import com.springbootacademy.batch17.pos.dto.paginated.PaginatedResponseItemDTO;
import com.springbootacademy.batch17.pos.dto.request.ItemSaveRequestDTO;
import com.springbootacademy.batch17.pos.dto.response.ItemGetResponseDTO;
import com.springbootacademy.batch17.pos.service.ItemService;
import com.springbootacademy.batch17.pos.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/item")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;

//    @PostMapping(
//            path = "save-item"
//    )
//    public String saveItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO){
//        String message = itemService.saveItem(itemSaveRequestDTO);
//        return message;
//    }

   @PostMapping(
            path = "save-item"
    )
    public ResponseEntity<StandardResponse> saveItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO){
        String message = itemService.saveItem(itemSaveRequestDTO);

//        ResponseEntity<StandardResponse> response = new ResponseEntity<StandardResponse>(
//                new StandardResponse(201,"Success",message), HttpStatus.CREATED
//       );

       //you can use this way also
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201,"Success",message),  //TBody(Genetics)
                HttpStatus.CREATED  //HTTP status
       );
    }

    @GetMapping(
            path = "/get-by-name",
            params = "name"
    )
    private List<ItemGetResponseDTO> getItemByNameAndStatus(@RequestParam(value = "name") String itemName){
        List<ItemGetResponseDTO> itemDTOS = itemService.getItemByNameAndStatus(itemName);
        return itemDTOS;
    }

    @GetMapping(
            path = "/get-by-name-with-mapstruct",
            params = "name"
    )
    private List<ItemGetResponseDTO> getItemByNameAndStatusByMapstruct(@RequestParam(value = "name") String itemName){
        List<ItemGetResponseDTO> itemDTOS = itemService.getItemByNameAndStatusByMapstruct(itemName);
        return itemDTOS;
    }

    @GetMapping(
            path = "/get-by-name-with-active-state",
            params = {"activeState","page","size"}
    )
    private ResponseEntity<StandardResponse> getItemByStatus(
            @RequestParam(value = "activeState") boolean activeState,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ){
       // List<ItemGetResponseDTO> itemDTOS = itemService.getItemByStatus(activeState, page, size);
        PaginatedResponseItemDTO paginatedResponseItemDTO = itemService.getItemByActiveStateWithPaginated(activeState, page, size);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200, "success",paginatedResponseItemDTO),
                HttpStatus.OK
        );
    }

    @GetMapping(
            path = "/get-all-items-By-paginated",
            params = {"page","size"}
    )
    private ResponseEntity<StandardResponse> getAllItemsByPaginated(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size

    ){
       PaginatedResponseItemDTO paginatedResponseItemDTO = itemService.getAllItemsByPaginated(page,size);
     return new ResponseEntity<StandardResponse>(
             new StandardResponse(200,"success",paginatedResponseItemDTO),
             HttpStatus.OK
     );
    }

}
