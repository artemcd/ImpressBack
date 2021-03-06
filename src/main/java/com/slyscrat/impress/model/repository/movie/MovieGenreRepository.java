package com.slyscrat.impress.model.repository.movie;

import com.slyscrat.impress.model.entity.MovieGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenreEntity, Integer> {
    @Query("select mg.id from MovieGenreEntity mg order by mg.name")
    Set<Integer> getIdsSet();
    @Query("select g from MovieGenreEntity g order by g.name")
    List<MovieGenreEntity> findAll();
    Set<MovieGenreEntity> findAllByOrderByName();
}
