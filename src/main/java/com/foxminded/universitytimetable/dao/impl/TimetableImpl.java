package com.foxminded.universitytimetable.dao.impl;

import com.foxminded.universitytimetable.dao.TimetableDAO;
import com.foxminded.universitytimetable.models.DayTimetable;

import java.util.Date;
import java.util.List;

/*
ПРЕДЛАГАЮ УБРАТЬ ЭТОТ КЛАСС
Получается что дао и импл для Timetable не нужен т. к. Timetable - это обЬект который формируется на основе запроса
полученного с клиента. Получается что иметь такую таблицу имеет смысл только для кэша. Можно сделать таблицу cash c
3 полями 1 - id, 2 - request, 3 - response.
 */
public class TimetableImpl implements TimetableDAO {
    public void add(DayTimetable dayTimetable) {

    }

    public List<DayTimetable> getAll() {
        return null;
    }

    public DayTimetable getById(int id) {
        return null;
    }

    public DayTimetable getByDate(Date date) {
        return null;
    }

    public void update(DayTimetable dayTimetable) {

    }

    public void remove(DayTimetable dayTimetable) {

    }
}
