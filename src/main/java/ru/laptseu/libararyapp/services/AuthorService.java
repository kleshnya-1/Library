package ru.laptseu.libararyapp.services;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import java.util.stream.Collectors;

@Log4j2
@Getter
@Service
public class AuthorService extends AbstractService<Author> {
    public AuthorService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
    }

    @Override
    @Transactional(value = "libraryTransactionManager")
    public Author read(Long id) {
        Author a = super.read(id);
        if (a == null) {
            return null;
        }
        if (a.getBookList() != null) {
            a.setBookList(a.getBookList().stream().filter(bookInLibrary -> !bookInLibrary.isDeleted()).collect(Collectors.toList()));
        }
        return a;
    }
}
