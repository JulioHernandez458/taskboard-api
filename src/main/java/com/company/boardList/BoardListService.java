package com.company.boardList;


import com.company.board.BoardNotFoundException;
import com.company.board.BoardRepository;
import com.company.boardList.web.BoardListCreateRequest;
import com.company.boardList.web.BoardListResponse;
import com.company.boardList.web.BoardListUpdateRequest;
import com.company.security.CurrentUser;

import java.util.List;

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
    
    public List<BoardListResponse> listByBoard(Long boardId) {
        Long ownerId = currentUser.id();

        boolean boardExists = boards.existsByIdAndOwnerId(boardId, ownerId);
        if (!boardExists) {
            throw new BoardNotFoundException(boardId);
        }

        return lists.findByBoardIdOrderByPositionAsc(boardId).stream()
                .map(l -> new BoardListResponse(
                        l.getId(),
                        l.getBoardId(),
                        l.getTitle(),
                        l.getPosition(),
                        l.getCreatedAt()
                ))
                .toList();
    }
    
    public BoardListResponse getById(Long boardId, Long listId) {
        Long ownerId = currentUser.id();

        // 1. Validar ownership del board
        boolean boardExists = boards.existsByIdAndOwnerId(boardId, ownerId);
        if (!boardExists) {
            throw new BoardNotFoundException(boardId);
        }

        // 2. Validar que la lista pertenece a ese board
        BoardList list = lists.findByIdAndBoardId(listId, boardId)
                .orElseThrow(() -> new BoardListNotFoundException(listId));

        return new BoardListResponse(
                list.getId(),
                list.getBoardId(),
                list.getTitle(),
                list.getPosition(),
                list.getCreatedAt()
        );
    }
    
    @Transactional
    public BoardListResponse update(Long boardId, Long listId, BoardListUpdateRequest request) {
        Long ownerId = currentUser.id();

        boolean boardExists = boards.existsByIdAndOwnerId(boardId, ownerId);
        if (!boardExists) {
            throw new BoardNotFoundException(boardId);
        }

        BoardList list = lists.findByIdAndBoardId(listId, boardId)
                .orElseThrow(() -> new BoardListNotFoundException(listId));

        list.setTitle(request.title());

        BoardList saved = lists.save(list);

        return new BoardListResponse(
                saved.getId(),
                saved.getBoardId(),
                saved.getTitle(),
                saved.getPosition(),
                saved.getCreatedAt()
        );
    }
    
    @Transactional
    public void delete(Long boardId, Long listId) {
        Long ownerId = currentUser.id();

        boolean boardExists = boards.existsByIdAndOwnerId(boardId, ownerId);
        if (!boardExists) {
            throw new BoardNotFoundException(boardId);
        }

        boolean listExists = lists.existsByIdAndBoardId(listId, boardId);
        if (!listExists) {
            throw new BoardListNotFoundException(listId);
        }

        lists.deleteByIdAndBoardId(listId, boardId);
    }
}
