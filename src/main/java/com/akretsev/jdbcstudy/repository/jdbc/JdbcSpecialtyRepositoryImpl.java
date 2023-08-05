package com.akretsev.jdbcstudy.repository.jdbc;

import com.akretsev.jdbcstudy.exception.DataNotFoundException;
import com.akretsev.jdbcstudy.model.Specialty;
import com.akretsev.jdbcstudy.repository.SpecialtyRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.akretsev.jdbcstudy.utility.JdbcUtils.prepareStatement;
import static com.akretsev.jdbcstudy.utility.JdbcUtils.prepareStatementWithKey;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {
    final String SAVE_SPECIALTY_SQL_QUERY = "INSERT INTO specialties(name) VALUES (?)";
    final String FIND_SPECIALTY_BY_ID_SQL_QUERY = "SELECT * FROM specialties WHERE id = ?";
    final String FIND_ALL_SPECIALTIES_SQL_QUERY = "SELECT * FROM specialties";
    final String UPDATE_SPECIALTY_SQL_QUERY = "UPDATE specialties SET name = ? WHERE id = ?";
    final String DELETE_SPECIALTY_BY_ID_SQL_QUERY = "DELETE FROM specialties WHERE id = ?";

    @Override
    public Specialty save(Specialty specialty) {
        try (PreparedStatement statement = prepareStatementWithKey(SAVE_SPECIALTY_SQL_QUERY)) {
            statement.setString(1, specialty.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            specialty.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }

    @Override
    public Optional<Specialty> findById(Integer id) {
        Specialty specialty;
        try (PreparedStatement statement = prepareStatement(FIND_SPECIALTY_BY_ID_SQL_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            specialty = mapResultSetToSpecialties(resultSet).get(0);
        } catch (SQLException e) {
            throw new DataNotFoundException("Specialty id=" + id + " not found.");
        }
        return Optional.of(specialty);
    }

    @Override
    public List<Specialty> findAll() {
        List<Specialty> specialties = new ArrayList<>();
        try (PreparedStatement statement = prepareStatement(FIND_ALL_SPECIALTIES_SQL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            specialties = mapResultSetToSpecialties(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialties;
    }

    @Override
    public Specialty update(Specialty specialty) {
        try (PreparedStatement statement = prepareStatement(UPDATE_SPECIALTY_SQL_QUERY)) {
            if (specialty.getName().isBlank()) {
                statement.setString(1, specialty.getName());
            }
            statement.setInt(2, specialty.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findById(specialty.getId()).orElseThrow(
                () -> new RuntimeException("Specialty id=" + specialty.getId() + " not found.")
        );
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement statement = prepareStatement(DELETE_SPECIALTY_BY_ID_SQL_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Specialty> mapResultSetToSpecialties(ResultSet resultSet) throws SQLException {
        List<Specialty> specialties = new ArrayList<>();
        while (resultSet.next()) {
            Specialty specialty = new Specialty();
            specialty.setId(resultSet.getInt("id"));
            specialty.setName(resultSet.getString("name"));
            specialties.add(specialty);
        }
        return specialties;
    }

}
