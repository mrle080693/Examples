package com.foxminded.universitytimetable.database.impl.repositories;

import com.foxminded.universitytimetable.database.queries.JPQLQueries;
import com.foxminded.universitytimetable.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Transactional
    @Modifying
    @Query(JPQLQueries.DELETE_LESSON)
    Lesson remove(int id);
}
