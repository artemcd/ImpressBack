package com.slyscrat.impress.model.mapper;

import com.slyscrat.impress.exception.EntityNotFoundException;
import com.slyscrat.impress.model.dto.ItemRateDto;
import com.slyscrat.impress.model.entity.MovieEntity;
import com.slyscrat.impress.model.entity.MovieRateEntity;
import com.slyscrat.impress.model.entity.UserEntity;
import com.slyscrat.impress.model.repository.UserRepository;
import com.slyscrat.impress.model.repository.movie.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MovieRateMapper extends AbstractMapper<MovieRateEntity, ItemRateDto> {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Autowired
    public MovieRateMapper(MovieRepository movieRepository,
                           ModelMapper modelMapper,
                           UserRepository userRepository) {
        super(modelMapper);
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initMapper() {
        mapper.createTypeMap(dtoClass, entityClass)
                .addMappings(m -> {
                    m.skip(MovieRateEntity::setMovie);
                    m.skip(MovieRateEntity::setUser);
                })
                .setPostConverter(convertToEntity());
        mapper.createTypeMap(entityClass, dtoClass)
                .addMappings(m -> {
                    m.skip(ItemRateDto::setItem);
                    m.skip(ItemRateDto::setRate);
                })
                .setPostConverter(convertToDto());
    }

    @Override
    protected void mapSpecificFields(MovieRateEntity source, ItemRateDto destination) {
        destination.setItem(source.getMovie().getId());
        destination.setUser(source.getUser().getId());
    }

    @Override
    protected void mapSpecificFields(ItemRateDto source, MovieRateEntity destination) {
        MovieEntity movieEntity = movieRepository.findById(source.getItem())
                .orElseThrow(() -> new EntityNotFoundException(MovieEntity.class, source.getItem()));
        UserEntity userEntity = userRepository.findById(source.getItem())
                .orElseThrow(() -> new EntityNotFoundException(MovieEntity.class, source.getItem()));

        movieEntity.getRatedBy().add(destination);
        userEntity.getMovieRates().add(destination);

        destination.setMovie(movieEntity);
        destination.setUser(userEntity);
    }
}

