package lab4.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lab4.database.jooq.tables.records.PointsRecord;
import lab4.server.dto.PointRequest;
import lab4.server.dto.PointResponse;
import lab4.server.repository.PointRepository;

@Service
public class PointService {

    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public PointResponse addPoint(String username, PointRequest request) {
        if (request == null || request.getX() == null || request.getY() == null || request.getR() == null) {
            throw new IllegalArgumentException("Coordinates and radius must not be null");
        }
        boolean hit = checkHit(request.getX(), request.getY(), request.getR());
        PointsRecord record = pointRepository.save(username, request.getX(), request.getY(), request.getR(), hit);
        return mapToResponse(record);
    }

    public List<PointResponse> getAllPoints(String username) {
        return pointRepository.findAllByUsername(username).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private boolean checkHit(Double x, Double y, Double r) {
        if (r <= 0)
            return false;
        if (x <= 0 && y >= 0) {
            return x * x + y * y <= r * r;
        }
        if (x >= 0 && y >= 0) {
            return y <= -2 * x + r;
        }
        if (x >= 0 && y <= 0) {
            return x <= r && y >= -r;
        }
        return false;
    }

    private PointResponse mapToResponse(PointsRecord record) {
        return new PointResponse(
                record.getId(),
                record.getX(),
                record.getY(),
                record.getR(),
                record.getHit(),
                record.getDate());
    }
}
