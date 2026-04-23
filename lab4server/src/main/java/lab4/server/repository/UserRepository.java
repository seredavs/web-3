package lab4.server.repository;

import static lab4.database.jooq.Tables.USERS;

import java.util.Optional;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import lab4.database.jooq.tables.records.UsersRecord;

@Repository
public class UserRepository {

    private final DSLContext dsl;

    public UserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<UsersRecord> findByUsername(String username) {
        return dsl.selectFrom(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOptional();
    }

    public boolean existsByUsername(String username) {
        return dsl.fetchExists(
                dsl.selectFrom(USERS)
                        .where(USERS.USERNAME.eq(username)));
    }

    public UsersRecord save(String username, String password) {
        return dsl.insertInto(USERS)
                .set(USERS.USERNAME, username)
                .set(USERS.PASSWORD, password)
                .returning()
                .fetchOne();
    }
}
