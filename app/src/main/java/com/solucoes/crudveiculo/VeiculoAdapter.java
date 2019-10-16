package com.solucoes.crudveiculo;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class VeiculoAdapter extends BaseAdapter {

    private List<Veiculo> veiculos;
    private Activity activity;

    public VeiculoAdapter(Activity activity, List<Veiculo> veiculos) {
        this.activity = activity;
        this.veiculos = veiculos;
    }

    @Override
    public int getCount() {
        return veiculos.size();
    }

    @Override
    public Object getItem(int i) {
        return veiculos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return veiculos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = activity.getLayoutInflater().inflate(R.layout.item, viewGroup, false);
        TextView modelo = v.findViewById(R.id.txt_modelo);
        TextView cor = v.findViewById(R.id.txt_cor);
        TextView ano = v.findViewById(R.id.txt_ano);
        TextView placa = v.findViewById(R.id.txt_placa);

        Veiculo a = veiculos.get(i);

        modelo.setText(a.getModelo());
        cor.setText(a.getCor());
        ano.setText(a.getAno());
        placa.setText(a.getPlaca());

        return v;
    }
}
