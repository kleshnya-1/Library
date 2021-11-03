package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

import java.util.stream.Collectors;

@Getter
@Service
public class AuthorService extends AbstractService<Author> {
    public AuthorService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
    }

    @Override
    public Author read(Long id) {
        Author a = super.read(id);
        a.setBookList(a.getBookList().stream().filter(bookInLibrary -> bookInLibrary.isDeleted() == false).collect(Collectors.toList()));
        return a;
    }
}
