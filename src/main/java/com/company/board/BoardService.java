package com.company.board;

import com.company.board.dto.BoardCreateRequest;
import com.company.board.dto.BoardResponse;
import com.company.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    private final BoardRepository boards;
    private final CurrentUser currentUser;

    public BoardService(BoardRepository boards, CurrentUser currentUser) {
        this.boards = boards;
        this.currentUser = currentUser;
    }

    @Transactional
    public BoardResponse create(BoardCreateRequest request) {
        Long ownerId = currentUser.id();

        Board board = new Board(ownerId, request.title());
        Board saved = boards.save(board);

        return new BoardResponse(
                saved.getId(),
                saved.getTitle(),
                saved.isArchived(),
                saved.getCreatedAt()
        );
    }
}

