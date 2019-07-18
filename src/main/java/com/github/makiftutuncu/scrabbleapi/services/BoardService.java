package com.github.makiftutuncu.scrabbleapi.services;

import com.github.makiftutuncu.scrabbleapi.models.Board;
import com.github.makiftutuncu.scrabbleapi.models.Move;
import com.github.makiftutuncu.scrabbleapi.models.Word;
import com.github.makiftutuncu.scrabbleapi.repositories.BoardRepository;
import com.github.makiftutuncu.scrabbleapi.repositories.WordRepository;
import com.github.makiftutuncu.scrabbleapi.utilities.HibernateUtils;
import com.github.makiftutuncu.scrabbleapi.utilities.ScrabbleException;
import com.github.makiftutuncu.scrabbleapi.views.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("boardService")
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
                throw new ScrabbleException(HttpStatus.BAD_REQUEST, String.format("Board '%s' already exists!", request.getName()));
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

    public List<MoveResponse> getMoves(int id, int count) {
        return getMoves(id).subList(0, count);
    }

    public List<WordResponse> getWords(int id) {
        return getMoves(id)
                .stream()
                .map(move -> new WordResponse(move.getWord()))
                .collect(Collectors.toList());
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
                throw new ScrabbleException(HttpStatus.BAD_REQUEST, String.format("Word '%s' is already added to board %d!", word.getWord(), boardId));
            }

            throw e;
        }
    }

    public List<MoveResponse> addMoves(int boardId, List<AddMoveRequest> requests) {
        Board board = boardRepository
                .getBoard(boardId, false)
                .orElseThrow(() -> new ScrabbleException(HttpStatus.NOT_FOUND, String.format("Board %d is not found!", boardId)));

        List<String> wordStrings = requests.stream().map(AddMoveRequest::getWord).collect(Collectors.toList());

        Map<String, Word> wordMap = wordRepository.getWords(wordStrings).stream().reduce(
                new HashMap<>(),
                (map, word) -> { map.put(word.getWord(), word); return map; },
                (map1, map2) -> { map1.putAll(map2); return map1; }
        );

        if (wordStrings.size() != wordMap.size()) {
            throw new ScrabbleException(HttpStatus.BAD_REQUEST, "Some of given words were not valid!");
        }

        requests.forEach(request -> {
            Word word = wordMap.get(request.getWord());
            Move move = board.prepareMove(word, request);
            board.addMove(move);
        });

        try {
            return HibernateUtils.withTransaction(session -> {
                session.saveOrUpdate(board);
                logger.info("Current board\n{}", board.print());
                return board.getMoves().stream().map(MoveResponse::new).collect(Collectors.toList());
            });
        } catch (Exception e) {
            Throwable rootCause = NestedExceptionUtils.getRootCause(e);
            if (rootCause instanceof SQLException && rootCause.getMessage().contains("already exists")) {
                throw new ScrabbleException(HttpStatus.BAD_REQUEST, String.format("One of the words is already added to board %d!", boardId));
            }

            throw e;
        }
    }
}
