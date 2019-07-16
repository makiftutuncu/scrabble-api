package com.github.makiftutuncu.scrabbleapi.services;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.models.Word;
import com.github.makiftutuncu.scrabbleapi.repositories.BoardRepository;
import com.github.makiftutuncu.scrabbleapi.repositories.WordRepository;
import com.github.makiftutuncu.scrabbleapi.utilities.HibernateUtils;
import com.github.makiftutuncu.scrabbleapi.utilities.ScrabbleException;
import com.github.makiftutuncu.scrabbleapi.views.AddMoveRequest;
import com.github.makiftutuncu.scrabbleapi.views.BoardResponse;
import com.github.makiftutuncu.scrabbleapi.views.CreateBoardRequest;
import com.github.makiftutuncu.scrabbleapi.views.MoveResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private BoardRepository boardRepository;
    private WordRepository wordRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, WordRepository wordRepository) {
        this.boardRepository = boardRepository;
        this.wordRepository  = wordRepository;
    }

    public List<BoardResponse> getActiveBoards() {
        return boardRepository
                .getActiveBoards()
                .stream()
                .map(BoardResponse::new)
                .collect(Collectors.toList());
    }

    public BoardResponse createBoard(CreateBoardRequest request) {
        Board board = new Board(request);

        try {
            return HibernateUtils.withSession(session -> {
                session.save(board);
                return new BoardResponse(board);
            });
        } catch (Exception e) {
            Throwable rootCause = NestedExceptionUtils.getRootCause(e);
            if (rootCause instanceof SQLException && rootCause.getMessage().contains("already exists")) {
                throw new ScrabbleException(HttpStatus.BAD_GATEWAY, String.format("Board '%s' already exists!", request.getName()));
            }

            throw e;
        }
    }

    public BoardResponse getBoard(int id) {
        return boardRepository
                .getBoard(id)
                .map(BoardResponse::new)
                .orElseThrow(() -> new ScrabbleException(HttpStatus.NOT_FOUND, String.format("Board %d is not found!", id)));
    }

    public BoardResponse deactivate(int id) {
        Board board = boardRepository
                .getBoard(id)
                .orElseThrow(() -> new ScrabbleException(HttpStatus.NOT_FOUND, String.format("Board %d is not found!", id)));

        board.deactivate();

        return HibernateUtils.withTransaction(session -> {
            session.update(board);
            return new BoardResponse(board);
        });
    }

    public List<MoveResponse> getMoves(int id) {
        return boardRepository
                .getBoard(id)
                .map(board -> board.getMoves().stream().map(MoveResponse::new).collect(Collectors.toList()))
                .orElseThrow(() -> new ScrabbleException(HttpStatus.NOT_FOUND, String.format("Board %d is not found!", id)));
    }

    public MoveResponse addMove(int boardId, AddMoveRequest request) {
        Board board = boardRepository
                .getBoard(boardId, false)
                .orElseThrow(() -> new ScrabbleException(HttpStatus.NOT_FOUND, String.format("Board %d is not found!", boardId)));

        Word word = wordRepository
                .getWord(request.getWord())
                .orElseThrow(() -> new ScrabbleException(HttpStatus.NOT_FOUND, String.format("Word '%s' is not found!", request.getWord())));

        Move move = board.prepareMove(word, request);

        board.addMove(move);

        try {
            return HibernateUtils.withSession(session -> {
                session.save(move);
                logger.info("Current board\n{}", board.print());
                return new MoveResponse(move);
            });
        } catch (Exception e) {
            Throwable rootCause = NestedExceptionUtils.getRootCause(e);
            if (rootCause instanceof SQLException && rootCause.getMessage().contains("already exists")) {
                throw new ScrabbleException(HttpStatus.BAD_GATEWAY, String.format("Word '%s' is already added to board %d!", word.getWord(), boardId));
            }

            throw e;
        }
    }
}
