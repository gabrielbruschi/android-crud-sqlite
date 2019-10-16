package com.solucoes.crudveiculo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListarVeiculosActivity extends AppCompatActivity {

    private ListView listView;
    private VeiculoDAO dao;
    private List<Veiculo> veiculos;
    private List<Veiculo> veiculosFiltrados = new ArrayList<>();

    public ListarVeiculosActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_veiculos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.lista_veiculos);
        dao = new VeiculoDAO(this);
        veiculos = dao.obterTodos(); //todos veiculos
        veiculosFiltrados.addAll(veiculos); //só o veiculo que foi consultado

        //ArrayAdapter<Veiculo> adaptador = new ArrayAdapter<Veiculo>(this, android.R.layout.simple_list_item_1, veiculosFiltrados);
        VeiculoAdapter adaptador = new VeiculoAdapter(this, veiculosFiltrados);
        listView.setAdapter(adaptador);
        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraVeiculo(s);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void procuraVeiculo(String modelo){
        veiculosFiltrados.clear();
        for(Veiculo v : veiculos){
            if(v.getModelo().toLowerCase().contains(modelo.toLowerCase())) {
                veiculosFiltrados.add(v);
            }
        }
        listView.invalidateViews();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btnNovo:
                cadastrar();
                return true;
            case R.id.app_bar_search:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cadastrar(){

        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Veiculo veiculoExcluir = veiculosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Confirmar exclusão do veiculo?")
                .setNegativeButton("NãO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        veiculosFiltrados.remove(veiculoExcluir);
                        veiculos.remove(veiculoExcluir);
                        dao.excluir(veiculoExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Veiculo veiculoAtualizar = veiculosFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("veiculo", veiculoAtualizar);
        startActivity(it);
    }



    @Override
    public void onResume() {
        super.onResume();
        veiculos = dao.obterTodos();
        veiculosFiltrados.clear();
        veiculosFiltrados.addAll(veiculos);
        listView.invalidateViews();
    }

}
