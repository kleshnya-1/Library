package ru.laptseu.libararyapp.Services;

import lombok.Getter;
import lombok.Setter;
import ru.laptseu.libararyapp.Entities.Books.Book;

@Getter
@Setter
public class BookService extends AbstractService<Book> {
Book entity = new Book();
}
