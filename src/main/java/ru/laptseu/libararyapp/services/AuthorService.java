package ru.laptseu.libararyapp.services;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.laptseu.libararyapp.entities.Author;
import ru.laptseu.libararyapp.mappers.frontMappers.FrontMappersFactory;
import ru.laptseu.libararyapp.repositories.RepositoryFactory;
import ru.laptseu.libararyapp.utilities.PageUtility;

@Getter
@Service
public class AuthorService extends AbstractService<Author> {
    Class entityClass = Author.class;


    public AuthorService(RepositoryFactory repositoryFactory, PageUtility pageUtility, FrontMappersFactory frontMappersFactory) {
        super(repositoryFactory, pageUtility, frontMappersFactory);
    }


//    public AuthorDto readDto(Long id){
//        return (AuthorDto) getFrontMappersFactory().get(getEntityClass()).map(read(id));
//    }

//    @Override
//    public List readDtoPageable(int pageNum) {
//            Pageable pageable = getPageable(pageNum);
//        List<Author> listEntity=  getRepositoryFactory().get(getEntityClass()).findPage(pageable);
//        List<AuthorDto> listEntityDto= (List<AuthorDto>) listEntity.stream().map(author -> getFrontMappersFactory().get(getEntityClass()).map(author));
//        return listEntityDto;
//    }
}
