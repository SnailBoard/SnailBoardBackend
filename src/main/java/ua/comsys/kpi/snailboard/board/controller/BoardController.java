package ua.comsys.kpi.snailboard.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ua.comsys.kpi.snailboard.board.dto.CreateBoardRequest;
import ua.comsys.kpi.snailboard.board.dto.BoardInfo;
import ua.comsys.kpi.snailboard.board.dto.GetBoardResponse;
import ua.comsys.kpi.snailboard.board.facade.BoardFacade;

import java.util.List;

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

    @Secured("ROLE_USER")
    @GetMapping("byTeam/{teamId}")
    public GetBoardResponse initialCreate(@PathVariable String teamId) {
        return boardFacade.getBoardsByTeam(teamId);
    }
}
