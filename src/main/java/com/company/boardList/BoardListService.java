package com.company.boardList;


import com.company.board.BoardNotFoundException;
import com.company.board.BoardRepository;
import com.company.boardList.web.BoardListCreateRequest;
import com.company.boardList.web.BoardListResponse;
import com.company.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardListService {

    private final BoardListRepository lists;
    private final BoardRepository boards;
    private final CurrentUser currentUser;

    public BoardListService(BoardListRepository lists,
                            BoardRepository boards,
                            CurrentUser currentUser) {
        this.lists = lists;
        this.boards = boards;
        this.currentUser = currentUser;
    }

    @Transactional
    public BoardListResponse create(Long boardId, BoardListCreateRequest request) {
        Long ownerId = currentUser.id();

        boolean boardExists = boards.existsByIdAndOwnerId(boardId, ownerId);
        if (!boardExists) {
            throw new BoardNotFoundException(boardId);
        }

        int count = lists.countByBoardId(boardId);
        int position = count; // nueva lista va al final

        BoardList list = new BoardList(boardId, request.title(), position);
        BoardList saved = lists.save(list);

        return new BoardListResponse(
                saved.getId(),
                saved.getBoardId(),
                saved.getTitle(),
                saved.getPosition(),
                saved.getCreatedAt()
        );
    }
}
