package com.slyscrat.impress.service.crud.game;

import com.slyscrat.impress.model.dto.game.GameGenreDto;
import com.slyscrat.impress.model.entity.GameGenreEntity;
import com.slyscrat.impress.model.mapper.Mapper;
import com.slyscrat.impress.model.repository.game.GameGenreRepository;
import com.slyscrat.impress.service.crud.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class GameGenreCrudServiceImpl
        extends AbstractCrudService<GameGenreEntity, GameGenreDto, GameGenreRepository>
        implements GameGenreCrudService {

    @Autowired
    public GameGenreCrudServiceImpl(GameGenreRepository repository,
                               Mapper<GameGenreEntity, GameGenreDto> mapper) {
        super(repository, mapper);
    }

    @Override
    public Set<Integer> getAllIds() {
        return repository.getAllIds();
    }

    @Override
    public GameGenreDto create(GameGenreDto dto) {
        GameGenreEntity gameEntity = new GameGenreEntity();
        mapper.map(dto, gameEntity);
        return mapper.map(repository.save(gameEntity));
    }

    @Override
    public GameGenreDto update(GameGenreDto dto) {
        GameGenreEntity gameEntity = repository.findById(dto.getId()).orElse(new GameGenreEntity());
        mapper.map(dto, gameEntity);
        return mapper.map(repository.save(gameEntity));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id).ifPresent(repository::delete);
    }
}