package it.webformat.ticketsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import it.webformat.ticketsystem.data.dto.BadgeDto;
import it.webformat.ticketsystem.data.models.Badge;
import it.webformat.ticketsystem.exceptions.IdMustBeNullException;
import it.webformat.ticketsystem.exceptions.IdMustNotBeNullException;
import it.webformat.ticketsystem.service.BadgeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/badges")
public class BadgeController {

    private BadgeService badgeService;

    @GetMapping("/v1")
    @Operation(description = """
            This method is used to retrieve all Badges from database<br>
                """)
    public List<BadgeDto> getAllBadges() {
        return badgeService.findAll().stream().map(Badge::toDto).toList();
    }

    @PostMapping("/v1")
    @Operation(description = """
            This method is used to insert new Badge<br>
            """)
    public BadgeDto createNewBadge(@RequestBody BadgeDto badgeDto) {
        try {
            Badge badge = badgeDto.toModel();
            return badgeService.insert(badge).toDto();
        } catch (IdMustBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @PutMapping("/v1")
    @Operation(description = """
            This method is used to update existing  Badge<br>
            """)
    public BadgeDto updateBadge(@RequestBody BadgeDto badgeDto) {
        try {
            Badge badge = badgeDto.toModel();
            return badgeService.update(badge).toDto();
        } catch (IdMustNotBeNullException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage()
            );
        }
    }

    @DeleteMapping("/v1/{id}")
    @Operation(description = """
            This method is used to delete Badge by id<br>
            """)
    public Boolean deleteBadge(@PathVariable("id") Long id) {
        return badgeService.deleteById(id);
    }

    @GetMapping("/v1/{id}")
    @Operation(description = """
            This method is used to find Badge by id<br>
            """)
    public BadgeDto getBadgeById(@PathVariable("id") Long id) {
        return badgeService.findById(id).toDto();
    }



}
