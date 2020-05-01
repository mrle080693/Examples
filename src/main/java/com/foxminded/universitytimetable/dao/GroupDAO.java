package com.foxminded.universitytimetable.dao;

import com.foxminded.universitytimetable.models.Group;

import java.util.List;

/*
Можно сделать save() вместо add() и update()
if id = 0 - addQuery
else - updateQuery

Можно сделать перегруженный load()
Без вход. параметров - getAll
Со входящим интом - getById
Со стрингой - getByName
 */

public interface GroupDAO {
    void add(Group group);

    List<Group> getAll();

    Group getById(int id);

    List<Group> getByName(String name);

    void update(Group group);

    void remove(Group group);
}
