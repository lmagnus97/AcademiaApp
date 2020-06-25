package com.lucas.magnus.academia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lucas.magnus.academia.adapter.CidadeAdapter;
import com.lucas.magnus.academia.dao.CidadeDAO;
import com.lucas.magnus.academia.model.Cidade;

import java.util.Objects;

public class SelecionaCidadeActivity extends AppCompatActivity implements CidadeAdapter.OnItemSelectedListener {

    private CidadeAdapter mAdapter;
    private RecyclerView recycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleciona_cidade);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.selecione_cidade));

        //VIEWS
        recycler = findViewById(R.id.recycler);
        final EditText etPesquisa = findViewById(R.id.etPesquisa);

        //PROPRIEDADES RECYCLER
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        final CidadeDAO cidadeDAO = new CidadeDAO(this);
        etPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 2){
                    mAdapter = new CidadeAdapter(cidadeDAO.select(s.toString()), R.layout.item_cidade, SelecionaCidadeActivity.this);
                    recycler.setAdapter(mAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onItemSelected(Cidade item) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("cidade", item.getCidade());
        returnIntent.putExtra("estado", item.getEstado());
        returnIntent.putExtra("pais", item.getPais());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
