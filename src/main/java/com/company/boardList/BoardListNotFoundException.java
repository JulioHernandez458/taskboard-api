package com.company.boardList;


public class BoardListNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public BoardListNotFoundException(Long id) {
        super("List not found with id: " + id);
    }
}
