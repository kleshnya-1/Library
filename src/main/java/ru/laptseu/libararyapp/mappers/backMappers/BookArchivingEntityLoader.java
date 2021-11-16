package ru.laptseu.libararyapp.mappers.backMappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.laptseu.libararyapp.models.entities.Author;
import ru.laptseu.libararyapp.models.entities.BookInLibrary;
import ru.laptseu.libararyapp.services.AuthorService;
import ru.laptseu.libararyapp.services.PublisherService;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookArchivingEntityLoader {
    @Autowired
    AuthorService authorService;
    @Autowired
    PublisherService publisherService;


    public BookInLibrary findEntity(BookInLibrary source) {
        if (source.getPublisher() != null && source.getPublisher().getName() == null) {
            source.setPublisher(publisherService.read(source.getPublisher().getId()));
        }
        if (source.getAuthorList() != null) {
            List <Author> newAuthorList=new ArrayList<>();
            source.getAuthorList().stream().forEach(author -> {
                if (author != null && author.getBookList() == null) {
                    newAuthorList.add(authorService.read(author.getId()));// TODO: 10.11.2021 querry to base as list of ID
                }
            });
            source.setAuthorList(newAuthorList);
        }
        return source;
    }

    public List<BookInLibrary> findEntity(List<BookInLibrary> source) {
        source.forEach(bookInLibrary -> findEntity(bookInLibrary));
        return source;
    }
}
