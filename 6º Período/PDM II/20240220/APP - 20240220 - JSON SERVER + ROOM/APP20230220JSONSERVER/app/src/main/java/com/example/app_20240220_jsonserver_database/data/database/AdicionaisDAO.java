package com.example.app_20240220_jsonserver_database.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.app_20240220_jsonserver_database.data.Adicionais;
import com.example.app_20240220_jsonserver_database.data.Agenda;

import java.util.List;

@Dao
public interface AdicionaisDAO {
    @Query("SELECT * FROM adicionais")
    List<Agenda> getAll();

    @Insert
    void insert(Adicionais adicionais);

    @Insert
    void insertAll(List<Adicionais> adicionais);

    @Delete
    void delete(Adicionais adicionais);

    @Update
    void update(Adicionais adicionais);
}
