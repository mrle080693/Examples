package com.foxminded.universitytimetable.database.impl.repositories;

import com.foxminded.universitytimetable.database.queries.JPQLQueries;
import com.foxminded.universitytimetable.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query(JPQLQueries.GET_GROUPS_BY_NAME)
    List<Group> getByName(String name);

    @Transactional
    @Modifying
    @Query(JPQLQueries.DELETE_GROUP)
    Group remove(int id);
}
