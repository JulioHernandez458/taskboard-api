package com.company.boardList;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardListRepository extends JpaRepository<BoardList, Long> {

    List<BoardList> findByBoardIdOrderByPositionAsc(Long boardId);

    Optional<BoardList> findByIdAndBoardId(Long id, Long boardId);

    boolean existsByIdAndBoardId(Long id, Long boardId);

    int countByBoardId(Long boardId);
    
    void deleteByIdAndBoardId(Long id, Long boardId);

}
