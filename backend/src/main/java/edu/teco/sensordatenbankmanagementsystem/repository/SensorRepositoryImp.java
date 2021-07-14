package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class SensorRepositoryImp implements SensorRepository {

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Sensor> findAll() {
        return null;
    }

    @Override
    public List<Sensor> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Sensor> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Sensor> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Sensor sensor) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends Sensor> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Sensor> S save(S s) {
        return null;
    }

    @Override
    public <S extends Sensor> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Sensor> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public <S extends Sensor> List<S> saveAllAndFlush(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Sensor> iterable) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Sensor getOne(Long aLong) {
        return null;
    }

    @Override
    public Sensor getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Sensor> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Sensor> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Sensor> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Sensor> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Sensor> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Sensor> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public Optional<Sensor> findById(Long id) {
        return Optional.empty();
    }
}
