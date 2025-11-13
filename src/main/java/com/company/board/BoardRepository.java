package com.company.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    
    Page<Board> findByOwnerId(Long ownerId, Pageable pageable);

    Page<Board> findByOwnerIdOrderByCreatedAtDesc(Long ownerId, Pageable pageable);

    Optional<Board> findByIdAndOwnerId(Long id, Long ownerId);

    boolean existsByIdAndOwnerId(Long id, Long ownerId);
}
