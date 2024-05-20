package com.example.app_20240220_jsonserver_database.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.app_20240220_jsonserver_database.data.Agenda;

import java.util.List;

@Dao
public interface AgendaDAO {
    @Query("SELECT * FROM agenda")
    List<Agenda> getAll();

    @Insert
    void insert(Agenda agenda);

    @Delete
    void delete(Agenda agenda);

    @Update
    void update(Agenda agenda);
}
