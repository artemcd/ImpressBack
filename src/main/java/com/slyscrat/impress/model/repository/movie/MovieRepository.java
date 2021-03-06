package com.slyscrat.impress.model.repository.movie;

import com.slyscrat.impress.model.entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    @Query("select m.id from MovieEntity m order by m.id")
    Set<Integer> getAllIds();
    Page<MovieEntity> findAllByRated_User_Id_AndRated_Rate(@NotNull Integer user_id, @NotNull Short rate, Pageable pageable);

    Page<MovieEntity> findAllByRated_User_Id_AndRated_RateGreaterThan(@NotNull Integer user_id, @NotNull Short rate, Pageable pageable);
    Page<MovieEntity> findAllByGenres_IdEquals(@NotNull Integer genres_id, Pageable pageable);

    Page<MovieEntity> findAllByNameContainsIgnoreCase(@NotNull String name, Pageable pageable);

    Page<MovieEntity> findAllByIdIn(Collection<@NotNull Integer> ids, Pageable pageable);
    List<MovieEntity> findAllByIdIn(Collection<@NotNull Integer> ids);
}
