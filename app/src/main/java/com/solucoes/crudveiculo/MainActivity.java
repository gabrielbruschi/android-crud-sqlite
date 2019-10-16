package com.solucoes.crudveiculo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText modelo;
    private EditText cor;
    private EditText ano;
    private EditText placa;

    private VeiculoDAO dao;

    private Veiculo veiculo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        modelo = findViewById(R.id.editModelo);
        cor = findViewById(R.id.editCor);
        ano = findViewById(R.id.editAno);
        placa = findViewById(R.id.editPlaca);

        dao = new VeiculoDAO(this);

        Intent it = getIntent();
        if(it.hasExtra("veiculo")){
            veiculo = (Veiculo) it.getSerializableExtra("veiculo");
            modelo.setText(veiculo.getModelo());
            cor.setText(veiculo.getCor());
            ano.setText(veiculo.getAno());
            placa.setText(veiculo.getPlaca());

        }

    }

    public void Salvar(View view){

        if (veiculo == null) {
            veiculo = new Veiculo();
            veiculo.setModelo(modelo.getText().toString());
            veiculo.setCor(cor.getText().toString());
            veiculo.setAno(ano.getText().toString());
            veiculo.setPlaca(placa.getText().toString());

            Long id = dao.inserir(veiculo);
            Toast.makeText(this, "Veiculo incluido com id" + id, Toast.LENGTH_SHORT).show();
        }else{
            veiculo.setModelo(modelo.getText().toString());
            veiculo.setCor(cor.getText().toString());
            veiculo.setAno(ano.getText().toString());
            veiculo.setPlaca(placa.getText().toString());
            dao.atualizar(veiculo);
            Toast.makeText(this, "Veiculo atualizado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
