package com.company.board;

import com.company.board.dto.BoardCreateRequest;
import com.company.board.dto.BoardResponse;
import com.company.board.web.BoardPageResponse;

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
    
    @GetMapping
    public BoardPageResponse list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return boards.list(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardResponse create(@RequestBody @Valid BoardCreateRequest request) {
        return boards.create(request);
    }
}

