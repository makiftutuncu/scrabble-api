package com.github.makiftutuncu.scrabbleapi.controllers;

import com.github.makiftutuncu.scrabbleapi.services.BoardService;
import com.github.makiftutuncu.scrabbleapi.views.BoardResponse;
import com.github.makiftutuncu.scrabbleapi.views.CreateBoardRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/boards")
public class BoardController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<BoardResponse> getActiveBoards() {
        logger.info("Getting active boards");
        return boardService.getActiveBoards();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    BoardResponse createBoard(@RequestBody CreateBoardRequest request) {
        logger.info("Creating a new board with {}", request);
        return boardService.createBoard(request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    BoardResponse getBoard(@PathVariable(name = "id") int id) {
        logger.info("Getting board {}", id);
        return boardService.getBoard(id);
    }
}
