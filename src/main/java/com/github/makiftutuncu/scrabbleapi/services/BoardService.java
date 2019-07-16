package com.github.makiftutuncu.scrabbleapi.services;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.repositories.BoardRepository;
import com.github.makiftutuncu.scrabbleapi.views.BoardResponse;
import com.github.makiftutuncu.scrabbleapi.views.CreateBoardRequest;
import com.github.makiftutuncu.scrabbleapi.views.MoveResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<BoardResponse> getActiveBoards() {
        return boardRepository
                .getActiveBoards()
                .stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    public BoardResponse createBoard(CreateBoardRequest request) {
        return new BoardResponse(boardRepository.createBoard(Board.from(request)));
    }

    public BoardResponse getBoard(int id) {
        return boardRepository
                .getBoard(id)
                .map(BoardResponse::new)
                .orElseThrow(() -> new RuntimeException(String.format("Board %d is not found!", id)));
    }

    public List<MoveResponse> getMoves(int id) {
        return boardRepository
                .getBoard(id)
                .map(board -> board.getMoves().stream().map(MoveResponse::new).collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException(String.format("Board %d is not found!", id)));
    }
}
