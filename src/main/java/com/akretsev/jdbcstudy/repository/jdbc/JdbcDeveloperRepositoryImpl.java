package com.akretsev.jdbcstudy.repository.jdbc;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Developer;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.model.Status;
import com.akretsev.jdbcstudy.repository.DeveloperRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.JdbcUtils.prepareStatement;
import static com.akretsev.jdbcstudy.utility.JdbcUtils.prepareStatementWithKey;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    final String SAVE_DEVELOPER_SQL_QUERY =
            "INSERT INTO developers(first_name, last_name, specialty_id, status) VALUES (?, ?, ?, ?)";

    final String MAP_DEV_TO_SKILLS_SQL_QUERY =
            "INSERT INTO developer_skills(developer_id, skill_id) VALUES (?, ?)";

    final String FIND_DEVELOPER_BY_ID_SQL_QUERY_LEFT_JOIN =
            "SELECT d.id, d.first_name, d.last_name, skill_id, sk.name AS skill_name, " +
                    "specialty_id, sp.name AS specialty_name, d.status AS status " +
                    "FROM developers d " +
                    "LEFT JOIN developer_skills ds ON d.id = ds.developer_id " +
                    "LEFT JOIN skills sk ON ds.skill_id = sk.id " +
                    "LEFT JOIN specialties sp ON d.specialty_id = sp.id " +
                    "WHERE d.id = ?";

    final String FIND_ACTIVE_DEVELOPERS_SQL_QUERY =
            "SELECT d.id, d.first_name, d.last_name, skill_id, sk.name AS skill_name, " +
                    "specialty_id, sp.name AS specialty_name, d.status AS status " +
                    "FROM developers d " +
                    "LEFT JOIN developer_skills ds ON d.id = ds.developer_id " +
                    "LEFT JOIN skills sk ON ds.skill_id = sk.id " +
                    "LEFT JOIN specialties sp ON d.specialty_id = sp.id " +
                    "WHERE status = 'ACTIVE'";

    final String UPDATE_DEVELOPER_SQL_QUERY =
            "UPDATE developers " +
                    "SET first_name = ?, last_name = ?, specialty_id = ?, status = ? " +
                    "WHERE id = ?";

    final String DELETE_DEVELOPER_BY_ID_SQL_QUERY = "DELETE FROM developers WHERE id = ?";

    @Override
    public Developer save(Developer developer) {
        try (PreparedStatement statement = prepareStatementWithKey(SAVE_DEVELOPER_SQL_QUERY);
             PreparedStatement skillStatement = prepareStatement(MAP_DEV_TO_SKILLS_SQL_QUERY)
        ) {
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setInt(3, developer.getSpecialty().getId());
            statement.setString(4, developer.getStatus().name());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            developer.setId(resultSet.getLong(1));
            List<Skill> skills = developer.getSkills();
            for (Skill skill : skills) {
                skillStatement.setLong(1, developer.getId());
                skillStatement.setInt(2, skill.getId());
                skillStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developer;
    }

    @Override
    public Optional<Developer> findById(Long id) {
        Developer developer;
        try (PreparedStatement statement = prepareStatement(FIND_DEVELOPER_BY_ID_SQL_QUERY_LEFT_JOIN)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            developer = mapResultSetToDevelopers(resultSet).get(0);
        } catch (SQLException e) {
            throw new DataNotFoundException("Developer id=" + id + " not found.");
        }

        return Optional.of(developer);
    }

    @Override
    public List<Developer> findAll() {
        List<Developer> developers = new ArrayList<>();
        try (PreparedStatement statement = prepareStatement(FIND_ACTIVE_DEVELOPERS_SQL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            developers = mapResultSetToDevelopers(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developers;
    }

    @Override
    public Developer update(Developer developer) {
        try (PreparedStatement statement = prepareStatement(UPDATE_DEVELOPER_SQL_QUERY)) {
            if (!developer.getFirstName().isBlank()) {
                statement.setString(1, developer.getFirstName());
            }
            if (!developer.getLastName().isBlank()) {
                statement.setString(2, developer.getLastName());
            }
            if (developer.getSpecialty() != null) {
                statement.setInt(3, developer.getSpecialty().getId());
            }
            if (developer.getStatus() != null) {
                statement.setString(4, developer.getStatus().name());
            }
            statement.setLong(5, developer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return findById(developer.getId()).orElseThrow(
                () -> new DataNotFoundException("Developer id=" + developer.getId() + " not found.")
        );
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement statement = prepareStatement(DELETE_DEVELOPER_BY_ID_SQL_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Developer> mapResultSetToDevelopers(ResultSet resultSet) throws SQLException {
        List<Developer> developers = new ArrayList<>();
        resultSet.next();
        first:
        {
            while (true) {
                Developer developer;
                List<Skill> skills;
                developer = new Developer();
                developer.setId(resultSet.getLong("id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setSpecialty(
                        new Specialty(
                                resultSet.getInt("specialty_id"),
                                resultSet.getString("specialty_name")
                        )
                );
                developer.setStatus(Status.valueOf((resultSet.getString("status"))));
                skills = new ArrayList<>();
                while (developer.getId() == resultSet.getLong("id")) {
                    Skill skill = new Skill(
                            resultSet.getInt("skill_id"),
                            resultSet.getString("skill_name")
                    );
                    skills.add(skill);

                    if (!resultSet.next()) {
                        developers.add(developer);
                        developer.setSkills(skills);
                        break first;
                    }
                }
                developer.setSkills(skills);
                developers.add(developer);
            }

        }
        return developers;
    }

}
