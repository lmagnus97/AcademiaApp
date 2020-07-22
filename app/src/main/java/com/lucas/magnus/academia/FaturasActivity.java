package com.lucas.magnus.academia;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lucas.magnus.academia.adapter.FaturasAdapter;
import com.lucas.magnus.academia.dao.FaturaDAO;
import com.lucas.magnus.academia.model.FaturaMatricula;

import java.util.Objects;

public class FaturasActivity extends AppCompatActivity implements FaturasAdapter.OnItemSelectedListener, View.OnClickListener {

    private FaturasAdapter mAdapter;
    private RecyclerView recyclerAlunos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faturas);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.faturas_geradas));

        //VIEWS
        recyclerAlunos = findViewById(R.id.recycler);
        LinearLayout boxVazio = findViewById(R.id.boxVazio);

        //PROPRIEDADES RECYCLER
        recyclerAlunos.setHasFixedSize(true);
        recyclerAlunos.setLayoutManager(new LinearLayoutManager(this));

        FaturaDAO dao = new FaturaDAO(this);
        mAdapter = new FaturasAdapter(dao.selectAll(), R.layout.item_fatura, FaturasActivity.this);
        recyclerAlunos.setAdapter(mAdapter);

        if(mAdapter.getItemCount() > 0){
            boxVazio.setVisibility(View.GONE);
            recyclerAlunos.setVisibility(View.VISIBLE);
        }else{
            boxVazio.setVisibility(View.VISIBLE);
            recyclerAlunos.setVisibility(View.GONE);
        }

    }

    @Override
    public void onItemSelected(FaturaMatricula faturaMatricula) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAdd:
                startActivity(new Intent(FaturasActivity.this, AddAlunoActivity.class));
                break;
        }
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
