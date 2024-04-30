package com.example.app_06022024_jsonhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView textViewID;
    private ListView listView;
    private final String URL = "https://jsonplaceholder.typicode.com/posts";
    private StringBuilder builder = null;
    private List<User> dadosBaixados = null;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> stringArrayList;
    private ExecutorService executorService;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewID = findViewById(R.id.dadosID);
        listView = findViewById(R.id.list_item);
        stringArrayList = new ArrayList<>();

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                //background
                methAux();

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        //UI Thread
                        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stringArrayList);
                        listView.setAdapter(arrayAdapter);
                    }
                });
            }
        });

    }

    private void methAux(){
        Conexao conexao = new Conexao();
        InputStream inputStream = conexao.obterRespostaHTTP(URL, getApplicationContext());
        Auxilia auxilia = new Auxilia();
        String textoJSON = auxilia.converter(inputStream);

        Gson gson = new Gson();

        if(textoJSON != null){
            Type type = new TypeToken<List<User>>(){}.getType();
            dadosBaixados = gson.fromJson(textoJSON,type);
            for (int i = 0; i < dadosBaixados.size(); i++){
                stringArrayList.add(dadosBaixados.get(i).toString());
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

}