package com.michaelmbugua.expenseTrackerApi.repositories;

import com.michaelmbugua.expenseTrackerApi.domain.Parent;
import com.michaelmbugua.expenseTrackerApi.exceptions.EtAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class ParentRepositoryImpl implements ParentRepository {

    private static final String SQL_CREATE = "INSERT INTO et_parents (first_name, last_name, email, password) VALUES(?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM et_parents WHERE email = ?";
    private static final String SQL_FIND_BY_ID = "SELECT parent_id, first_name, last_name, email, password FROM et_parents WHERE parent_id = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT parent_id, first_name, last_name, email, password FROM et_parents WHERE email = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Parent> parentRowMapper = ((rs, rowNum) -> {
        return new Parent(
                rs.getInt("parent_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password")
        );
    });

    @Override
    public Number create(String firstName, String lastName, String email, String password) throws EtAuthException {

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, hashedPassword);
                return ps;
            }, keyHolder);

            return (Number) keyHolder.getKeys().get("GENERATED_KEY");

        } catch (Exception e) {
            throw new EtAuthException("Invalid Details. Failed to create account." + e);
        }
    }

    @Override
    public Parent findByEmailAndPassword(String email, String password) throws EtAuthException {

        try {
            Parent parent = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, parentRowMapper);
            assert parent != null;
            if (!BCrypt.checkpw(password, parent.getPassword()))
                throw new EtAuthException("Invalid email/password");
            return parent;
        } catch (EmptyResultDataAccessException e) {
            throw new EtAuthException("Invalid email/ password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public Parent findById(Number parentId) {
//        System.out.println(parentId);
//        System.out.println((jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{parentId}, parentRowMapper).toString()));
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{parentId}, parentRowMapper);
    }

    @Override
    public Boolean checkEmail(String email) {

        Integer count = jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);

        return count == 0;
    }
}