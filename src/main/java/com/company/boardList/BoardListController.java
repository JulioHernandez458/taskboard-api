package com.company.boardList;


import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.company.boardList.web.BoardListCreateRequest;
import com.company.boardList.web.BoardListResponse;

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
}
