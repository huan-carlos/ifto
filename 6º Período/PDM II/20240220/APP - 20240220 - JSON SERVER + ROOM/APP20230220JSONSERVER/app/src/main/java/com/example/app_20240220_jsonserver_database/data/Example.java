package com.example.app_20240220_jsonserver_database.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("agenda")
    @Expose
    private List<Agenda> agenda;
    @SerializedName("adicionais")
    @Expose
    private List<Adicionais> adicionais;

    public List<Agenda> getAgenda() {
        return agenda;
    }

    public void setAgenda(List<Agenda> agenda) {
        this.agenda = agenda;
    }

    public List<Adicionais> getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(List<Adicionais> adicionais) {
        this.adicionais = adicionais;
    }

    @Override
    public String toString() {
        return "Example{\n" +
                "agenda=" + agenda +
                "\n\n, adicionais=" + adicionais +
                "\n}";
    }
}