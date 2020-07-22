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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lucas.magnus.academia.adapter.MatriculaAdapter;
import com.lucas.magnus.academia.dao.MatriculaDAO;
import com.lucas.magnus.academia.model.Matricula;

import java.util.Objects;

public class MatriculasActivity extends AppCompatActivity implements MatriculaAdapter.OnItemSelectedListener, View.OnClickListener {

    private MatriculaAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayout boxVazio;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriculas);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.matriculas));

        //VIEWS
        recyclerView = findViewById(R.id.recycler);
        boxVazio = findViewById(R.id.boxVazio);
        final FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        //PROPRIEDADES DO RECYCLER
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MatriculasActivity.this));

        //ONCLICK
        fabAdd.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MatriculaDAO dao = new MatriculaDAO(MatriculasActivity.this);
        mAdapter = new MatriculaAdapter(dao.selectAll(), R.layout.item_matricula, this);

        if (mAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            boxVazio.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            boxVazio.setVisibility(View.GONE);
        }

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemSelected(Matricula item) {
        Intent intent = new Intent(this, AddMatriculaActivity.class);
        intent.putExtra("codigo_matricula", item.getCodigoMatricula());
        intent.putExtra("aluno", item.getAluno().getAluno());
        intent.putExtra("codigo_aluno", item.getIdAluno());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAdd:
                startActivity(new Intent(MatriculasActivity.this, SelecionaAlunoActivity.class));
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
