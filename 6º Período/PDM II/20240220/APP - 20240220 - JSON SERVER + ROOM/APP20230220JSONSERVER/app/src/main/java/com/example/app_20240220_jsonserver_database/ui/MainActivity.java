package com.example.app_20240220_jsonserver_database.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app_20240220_jsonserver_database.R;
import com.example.app_20240220_jsonserver_database.data.Adicionais;
import com.example.app_20240220_jsonserver_database.data.Agenda;
import com.example.app_20240220_jsonserver_database.data.Auxilia;
import com.example.app_20240220_jsonserver_database.data.Conexao;
import com.example.app_20240220_jsonserver_database.data.Example;
import com.example.app_20240220_jsonserver_database.data.database.AdicionaisDAO;
import com.example.app_20240220_jsonserver_database.data.database.AgendaDAO;
import com.example.app_20240220_jsonserver_database.data.database.DataBaseConection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ListView listViewAgenda, listViewAdicionais;
    private ArrayList<String> agendaArrayList, adicionaisArrayList;
    private Example dadosConvertidos = null;
    private ArrayAdapter<String> arrayAdapterAgendas, arrayAdapterAdicionais;
    private ExecutorService executorService;
    private Handler handler;
    private DataBaseConection dataBaseConection;
    private AdicionaisDAO adicionaisDAO;
    private AgendaDAO agendaDAO;
    private final String URL = "https://my-json-server.typicode.com/huan-carlos/jsonserver/db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApplicationContext().deleteDatabase("mydatabase");

        listViewAdicionais = findViewById(R.id.list_adicioanis);
        listViewAgenda = findViewById(R.id.list_agenda);

        agendaArrayList = new ArrayList<>();
        adicionaisArrayList = new ArrayList<>();

        executorService = Executors.newSingleThreadExecutor();

        handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //background
                APIConsumer();
                SaveData();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread
                        arrayAdapterAgendas = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, agendaArrayList);
                        listViewAgenda.setAdapter(arrayAdapterAgendas);
                        arrayAdapterAdicionais = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, adicionaisArrayList);
                        listViewAdicionais.setAdapter(arrayAdapterAdicionais);
                    }
                });
            }
        });
    }

    private void APIConsumer(){
        Conexao conexao = new Conexao();
        InputStream inputStream = conexao.obterRespostaHTTP(URL);
        Auxilia auxilia = new Auxilia();
        String textoJSON = auxilia.converter(inputStream);

        Gson gson = new Gson();

        if(textoJSON != null) {
            Type type = new TypeToken<Example>() {
            }.getType();
            dadosConvertidos = gson.fromJson(textoJSON, type);

            for (Agenda a: dadosConvertidos.getAgenda()) {
                agendaArrayList.add(a.toString());
            }

            for (Adicionais add: dadosConvertidos.getAdicionais()) {
                adicionaisArrayList.add(add.toString());
            }

        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Não foi possível obter JSON", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void SaveData(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dataBaseConection = Room.databaseBuilder(getApplicationContext(), DataBaseConection.class, "mydatabase").build();
                adicionaisDAO = dataBaseConection.adicionaisDAO();
                agendaDAO = dataBaseConection.agendaDAO();

                adicionaisDAO.insertAll(dadosConvertidos.getAdicionais());
            }
        });
    }
}