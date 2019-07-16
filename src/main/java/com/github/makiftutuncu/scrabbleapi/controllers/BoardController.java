package com.github.makiftutuncu.scrabbleapi.controllers;

import com.github.makiftutuncu.scrabbleapi.services.BoardService;
import com.github.makiftutuncu.scrabbleapi.views.AddMoveRequest;
import com.github.makiftutuncu.scrabbleapi.views.BoardResponse;
import com.github.makiftutuncu.scrabbleapi.views.CreateBoardRequest;
import com.github.makiftutuncu.scrabbleapi.views.MoveResponse;
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

    private BoardService boardService;

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

    @RequestMapping(value = "/{id}/deactivate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    BoardResponse deactivate(@PathVariable(name = "id") int id) {
        logger.info("Deactivating board {}", id);
        return boardService.deactivate(id);
    }

    @RequestMapping(value = "/{id}/moves", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    List<MoveResponse> getMoves(@PathVariable(name = "id") int id) {
        logger.info("Getting moves of board {}", id);
        return boardService.getMoves(id);
    }

    @RequestMapping(value = "/{id}/moves", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    MoveResponse addMove(@PathVariable(name = "id") int boardId, @RequestBody AddMoveRequest request) {
        logger.info("Adding move {} to board {}", request, boardId);
        return boardService.addMove(boardId, request);
    }
}
