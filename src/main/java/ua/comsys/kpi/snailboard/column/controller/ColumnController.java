package ua.comsys.kpi.snailboard.column.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ua.comsys.kpi.snailboard.column.dto.UpdateColumnPosition;
import ua.comsys.kpi.snailboard.column.dto.CreateColumnRequest;
import ua.comsys.kpi.snailboard.column.service.ColumnService;

@RestController
@RequestMapping("/column")
public class ColumnController {

    @Autowired
    ColumnService columnService;

    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void initialCreate(@RequestBody CreateColumnRequest request) {
        columnService.createInitial(request);
    }

    @Secured("ROLE_USER")
    @PatchMapping("/updatePosition")
    public void updateColumnPosition(@RequestBody UpdateColumnPosition request) {
        columnService.updateColumnPositions(request);
    }
}
