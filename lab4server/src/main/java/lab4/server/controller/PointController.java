package lab4.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lab4.server.dto.ApiResponse;
import lab4.server.dto.PointRequest;
import lab4.server.dto.PointResponse;
import lab4.server.exception.UserNotFoundException;
import lab4.server.service.PointService;

@RestController
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PointResponse>>> getPoints() {
        String username = getCurrentUsername();
        return ResponseEntity.ok(ApiResponse.success(pointService.getAllPoints(username)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PointResponse>> addPoint(@RequestBody PointRequest request) {
        String username = getCurrentUsername();
        return ResponseEntity.ok(ApiResponse.success(pointService.addPoint(username, request)));
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new UserNotFoundException("User authentication missing");
        }
        return authentication.getName();
    }
}
