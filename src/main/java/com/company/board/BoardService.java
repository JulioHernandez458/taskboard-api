package com.company.board;

import com.company.board.dto.BoardCreateRequest;
import com.company.board.dto.BoardResponse;
import com.company.board.dto.BoardUpdateRequest;
import com.company.board.web.BoardPageResponse;
import com.company.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    
    public BoardPageResponse list(int page, int size) {
        Long ownerId = currentUser.id();

        // protegemos un poco los valores
        int pageNumber = Math.max(page, 0);
        int pageSize = Math.min(Math.max(size, 1), 50); // mínimo 1, máximo 50

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Board> result = boards.findByOwnerIdOrderByCreatedAtDesc(ownerId, pageable);

        var content = result.getContent().stream()
                .map(b -> new BoardResponse(
                        b.getId(),
                        b.getTitle(),
                        b.isArchived(),
                        b.getCreatedAt()
                ))
                .toList();

        return new BoardPageResponse(
                content,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }
    
    
    public BoardResponse getById(Long id) {
        Long ownerId = currentUser.id();

        Board board = boards.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new BoardNotFoundException(id));

        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.isArchived(),
                board.getCreatedAt()
        );
    }
    
    @Transactional
    public BoardResponse update(Long id, BoardUpdateRequest request) {
        Long ownerId = currentUser.id();

        Board board = boards.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new BoardNotFoundException(id));

        board.setTitle(request.title());
        board.setArchived(request.archived());

        Board saved = boards.save(board);

        return new BoardResponse(
                saved.getId(),
                saved.getTitle(),
                saved.isArchived(),
                saved.getCreatedAt()
        );
    }
    
    @Transactional
    public void delete(Long id) {
        Long ownerId = currentUser.id();

        boolean exists = boards.existsByIdAndOwnerId(id, ownerId);
        if (!exists) {
            throw new BoardNotFoundException(id);
        }

        boards.deleteByIdAndOwnerId(id, ownerId);
    }
    
}

