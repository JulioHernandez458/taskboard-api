package com.company.board;

import com.company.board.dto.BoardCreateRequest;
import com.company.board.dto.BoardResponse;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boards;

    public BoardController(BoardService boards) {
        this.boards = boards;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardResponse create(@RequestBody @Valid BoardCreateRequest request) {
        return boards.create(request);
    }
}

