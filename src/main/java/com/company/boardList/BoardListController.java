package com.company.boardList;


import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.company.boardList.web.BoardListCreateRequest;
import com.company.boardList.web.BoardListReorderRequest;
import com.company.boardList.web.BoardListResponse;
import com.company.boardList.web.BoardListUpdateRequest;

@RestController
@RequestMapping("/api/boards/{boardId}/lists")
public class BoardListController {

    private final BoardListService lists;

    public BoardListController(BoardListService lists) {
        this.lists = lists;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardListResponse create(
            @PathVariable Long boardId,
            @RequestBody @Valid BoardListCreateRequest request
    ) {
        return lists.create(boardId, request);
    }
    
    @GetMapping
    public List<BoardListResponse> list(@PathVariable Long boardId) {
        return lists.listByBoard(boardId);
    }
    
    @GetMapping("/{listId}")
    public BoardListResponse getById(
            @PathVariable Long boardId,
            @PathVariable Long listId
    ) {
        return lists.getById(boardId, listId);
    }
    
    @PutMapping("/{listId}")
    public BoardListResponse update(
            @PathVariable Long boardId,
            @PathVariable Long listId,
            @RequestBody @Valid BoardListUpdateRequest request
    ) {
        return lists.update(boardId, listId, request);
    }
    
    @DeleteMapping("/{listId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long boardId,
            @PathVariable Long listId
    ) {
        lists.delete(boardId, listId);
    }
    
    @PutMapping("/reorder")
    public List<BoardListResponse> reorder(
            @PathVariable Long boardId,
            @RequestBody @Valid BoardListReorderRequest request
    ) {
        return lists.reorder(boardId, request);
    }
}
