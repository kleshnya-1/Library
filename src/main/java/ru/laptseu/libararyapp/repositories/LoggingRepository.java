package ru.laptseu.libararyapp.repositories;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
public class LoggingRepository implements AbstractRepository {
    @Value("${logFile.address}")
    String LOGGING_FILE_ADDRESS;
    Calendar calendar = new GregorianCalendar();


    public <S extends String> S save(S entity) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(LOGGING_FILE_ADDRESS, true)) {
            entity = (S) (calendar.getTime() + " | " + entity + "\n"); //todo custom date format
            byte[] buffer = entity.getBytes();
            fileOutputStream.write(buffer);
        } catch (IOException e) {
            log.error(e);
        }
        return entity;
    }


    @Override
    public List readAllByIsDeletedFalse(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List readAllByIsDeletedFalse() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional readByIdAndIsDeletedFalse(Long id) {
        return Optional.empty();
    }

    @Override
    public List findPageable(Pageable pageable) {
        return AbstractRepository.super.findPageable(pageable);
    }

    @Override
    public List findAll() {
        return AbstractRepository.super.findAll();
    }

    @Override
    public Optional findById(Long id) {
        return AbstractRepository.super.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        AbstractRepository.super.deleteById(id);
    }

    @Override
    public Iterable findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Iterable saveAll(Iterable entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object save(Object entity) {
        return null;
    }

    @Override
    public Optional findById(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsById(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable findAllById(Iterable iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Object entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
