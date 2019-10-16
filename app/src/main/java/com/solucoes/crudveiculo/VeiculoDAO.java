package com.solucoes.crudveiculo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

import java.util.ArrayList;

public class VeiculoDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public VeiculoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Veiculo veiculo){
        ContentValues values = new ContentValues();
        values.put("modelo", veiculo.getModelo());
        values.put("cor", veiculo.getCor());
        values.put("ano", veiculo.getAno());
        values.put("placa", veiculo.getPlaca());
        return banco.insert("veiculo", null, values);
    }

    public List<Veiculo> obterTodos(){
        List<Veiculo> veiculos = new ArrayList<>();
        Cursor cursor = banco.query("veiculo", new String[]{"id", "modelo", "cor", "ano", "placa"},
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Veiculo v = new Veiculo();
            v.setId(cursor.getInt(0));
            v.setModelo(cursor.getString(1));
            v.setCor(cursor.getString(2));
            v.setAno(cursor.getString(3));
            v.setPlaca(cursor.getString(4));
            veiculos.add(v);
        }
        return veiculos;
    }

    public void excluir(Veiculo v){
        banco.delete("veiculo", "id = ?", new String[]{v.getId().toString()});
    }

    public void atualizar(Veiculo veiculo){
        ContentValues values = new ContentValues();
        values.put("modelo", veiculo.getModelo());
        values.put("cor", veiculo.getCor());
        values.put("ano", veiculo.getAno());
        values.put("placa", veiculo.getPlaca());
        banco.update("veiculo", values, "id = ?", new String[]{veiculo.getId().toString()});
    }

}
