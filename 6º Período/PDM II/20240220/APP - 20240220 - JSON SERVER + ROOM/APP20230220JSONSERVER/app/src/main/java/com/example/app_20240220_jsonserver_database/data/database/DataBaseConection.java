package com.example.app_20240220_jsonserver_database.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.app_20240220_jsonserver_database.data.Adicionais;
import com.example.app_20240220_jsonserver_database.data.Agenda;

@Database(entities = {Agenda.class, Adicionais.class}, version = 1, exportSchema = false)
public abstract class DataBaseConection extends RoomDatabase {
        public abstract AgendaDAO agendaDAO();
        public abstract AdicionaisDAO adicionaisDAO();
}
