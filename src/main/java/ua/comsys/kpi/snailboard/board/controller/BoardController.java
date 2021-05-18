package ua.comsys.kpi.snailboard.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ua.comsys.kpi.snailboard.board.dto.CreateBoardRequest;
import ua.comsys.kpi.snailboard.board.facade.BoardFacade;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardFacade boardFacade;

    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void initialCreate(@RequestBody CreateBoardRequest request) {
        boardFacade.createInitial(request.getName(), request.getDescription(), request.getId());
    }
}
