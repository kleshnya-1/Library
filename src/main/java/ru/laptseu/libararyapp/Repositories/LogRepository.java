package ru.laptseu.libararyapp.Repositories;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Log4j2
public class LogRepository implements PagingAndSortingRepository<String, Long> {
    @Value("${logFile.address}")
    String LOGGING_FILE_ADDRESS;

    @Override
    public <S extends String> S save(S entity) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(LOGGING_FILE_ADDRESS, true)) {
            entity = (S) (entity + "\n");
            byte[] buffer = entity.getBytes();
            fileOutputStream.write(buffer);
        } catch (IOException e) {
            log.error(e);
        }
        return entity;
    }

    @Override
    public Iterable<String> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<String> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends String> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> findById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<String> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<String> findAllById(Iterable<Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends String> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
