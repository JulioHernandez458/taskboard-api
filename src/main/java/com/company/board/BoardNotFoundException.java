package com.company.board;

public class BoardNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public BoardNotFoundException(Long id) {
        super("Board not found with id: " + id);
    }
}