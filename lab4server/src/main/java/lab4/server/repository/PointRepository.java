package lab4.server.repository;

import static lab4.database.jooq.Tables.POINTS;
import static lab4.database.jooq.Tables.USERS;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import lab4.database.jooq.tables.records.PointsRecord;

@Repository
public class PointRepository {

    private final DSLContext dsl;

    public PointRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public PointsRecord save(String username, Double x, Double y, Double r, boolean hit) {
        Integer userId = dsl.select(USERS.ID)
                .from(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOne(USERS.ID);

        if (userId == null) {
            throw new IllegalArgumentException("User not found");
        }

        return dsl.insertInto(POINTS)
                .set(POINTS.USER_ID, userId)
                .set(POINTS.X, x)
                .set(POINTS.Y, y)
                .set(POINTS.R, r)
                .set(POINTS.HIT, hit)
                .set(POINTS.DATE, LocalDateTime.now())
                .returning()
                .fetchOne();
    }

    public List<PointsRecord> findAllByUsername(String username) {
        return dsl.select(POINTS.fields())
                .from(POINTS)
                .join(USERS).on(POINTS.USER_ID.eq(USERS.ID))
                .where(USERS.USERNAME.eq(username))
                .fetchInto(PointsRecord.class);
    }
}
