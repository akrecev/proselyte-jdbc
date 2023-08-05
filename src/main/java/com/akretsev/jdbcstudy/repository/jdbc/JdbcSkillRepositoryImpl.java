package com.akretsev.jdbcstudy.repository.jdbc;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Skill;
import com.akretsev.jdbcstudy.repository.SkillRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.JdbcUtils.prepareStatement;
import static com.akretsev.jdbcstudy.utility.JdbcUtils.prepareStatementWithKey;

public class JdbcSkillRepositoryImpl implements SkillRepository {
    final String SAVE_SKILL_SQL_QUERY =
            "INSERT INTO skills(name) VALUES (?)";
    final String FIND_SKILL_BY_ID_SQL_QUERY =
            "SELECT * FROM skills WHERE id = ?";
    final String FIND_ALL_SKILLS_SQL_QUERY =
            "SELECT * FROM skills";
    final String UPDATE_SKILL_SQL_QUERY =
            "UPDATE skills SET name = ? WHERE id = ?";
    final String DELETE_SKILL_BY_ID_SQL_QUERY =
            "DELETE FROM skills WHERE id = ?";

    @Override
    public Skill save(Skill skill) {
        try (PreparedStatement statement = prepareStatementWithKey(SAVE_SKILL_SQL_QUERY)) {
            statement.setString(1, skill.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            skill.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public Optional<Skill> findById(Integer id) {
        Skill skill;
        try (PreparedStatement statement = prepareStatement(FIND_SKILL_BY_ID_SQL_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            skill = mapResultSetToSkills(resultSet).get(0);
        } catch (SQLException e) {
            throw new DataNotFoundException("Skill id=" + id + " not found.");
        }
        return Optional.of(skill);
    }

    @Override
    public List<Skill> findAll() {
        List<Skill> skills = new ArrayList<>();
        try (PreparedStatement statement = prepareStatement(FIND_ALL_SKILLS_SQL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            skills = mapResultSetToSkills(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    @Override
    public Skill update(Skill skill) {
        try (PreparedStatement statement = prepareStatement(UPDATE_SKILL_SQL_QUERY)) {
            if (skill.getName().isBlank()) {
                statement.setString(1, skill.getName());
            }
            statement.setInt(2, skill.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findById(skill.getId()).orElseThrow(
                () -> new DataNotFoundException("Skill id=" + skill.getId() + " not found.")
        );
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement statement = prepareStatement(DELETE_SKILL_BY_ID_SQL_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Skill> mapResultSetToSkills(ResultSet resultSet) throws SQLException {
        List<Skill> skills = new ArrayList<>();
        while (resultSet.next()) {
            Skill skill = new Skill();
            skill.setId(resultSet.getInt("id"));
            skill.setName(resultSet.getString("name"));
            skills.add(skill);
        }
        return skills;
    }

}
