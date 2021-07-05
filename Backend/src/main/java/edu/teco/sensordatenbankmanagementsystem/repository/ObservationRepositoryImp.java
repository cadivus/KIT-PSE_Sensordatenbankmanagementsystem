package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class ObservationRepositoryImp implements ObservationRepository {

    @Override
    public Optional<Observation> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Observation> findAll() {
        return null;
    }

    @Override
    public List<Observation> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Observation> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Observation> findAllById(Iterable<Long> iterable) {
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
    public void delete(Observation observation) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends Observation> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Observation> S save(S s) {
        return null;
    }

    @Override
    public <S extends Observation> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Observation> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public <S extends Observation> List<S> saveAllAndFlush(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Observation> iterable) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Observation getOne(Long aLong) {
        return null;
    }

    @Override
    public Observation getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Observation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Observation> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Observation> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Observation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Observation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Observation> boolean exists(Example<S> example) {
        return false;
    }
}
