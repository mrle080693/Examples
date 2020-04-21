package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.DayTimetable;

import java.util.Date;
import java.util.List;

/*
Получается что дао и импл для Timetable не нужен т. к. Timetable - это обЬект который формируется на основе запроса
полученного с клиента. Получается что иметь такую таблицу имеет смысл только для кэша. Можно сделать таблицу cash c
3 полями 1 - id, 2 - request, 3 - response.
 */
public interface TimetableDAO {
    void add(DayTimetable dayTimetable);

    List<DayTimetable> getAll();

    DayTimetable getById(int id);

    DayTimetable getByDate(Date date);

    void update(DayTimetable dayTimetable);

    void remove(DayTimetable dayTimetable);
}
