package com.lucas.magnus.academia;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lucas.magnus.academia.adapter.AlunoAdapter;
import com.lucas.magnus.academia.dao.AlunoDAO;
import com.lucas.magnus.academia.model.Aluno;

import java.util.Objects;

public class SelecionaAlunoActivity extends AppCompatActivity implements AlunoAdapter.OnItemSelectedListener, View.OnClickListener {

    private AlunoAdapter mAdapterAlunos;
    private RecyclerView recyclerAlunos;
    private LinearLayout boxVazio;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleciona_aluno);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.selecione_aluno));

        //VIEWS
        recyclerAlunos = findViewById(R.id.recycler);
        final FloatingActionButton fabAddAluno = findViewById(R.id.fabAdd);
        final EditText etPesquisa = findViewById(R.id.etPesquisa);
        boxVazio = findViewById(R.id.boxVazio);

        //PROPRIEDADES RECYCLER
        recyclerAlunos.setHasFixedSize(true);
        recyclerAlunos.setLayoutManager(new LinearLayoutManager(this));

        //ONCLICK
        fabAddAluno.setOnClickListener(this);

        //PESQUISA
        final AlunoDAO alunoDAO = new AlunoDAO(this);
        etPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 1) {
                    mAdapterAlunos = new AlunoAdapter(alunoDAO.selectPesquisa(s.toString()), R.layout.item_aluno, SelecionaAlunoActivity.this);
                } else {
                    mAdapterAlunos = new AlunoAdapter(alunoDAO.selectAll(), R.layout.item_aluno, SelecionaAlunoActivity.this);
                }
                setAdapter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AlunoDAO alunoDAO = new AlunoDAO(this);
        mAdapterAlunos = new AlunoAdapter(alunoDAO.selectAll(), R.layout.item_aluno, this);
        setAdapter();

    }

    public void setAdapter() {
        if (mAdapterAlunos.getItemCount() > 0) {
            boxVazio.setVisibility(View.GONE);
            recyclerAlunos.setVisibility(View.VISIBLE);
        } else {
            boxVazio.setVisibility(View.VISIBLE);
            recyclerAlunos.setVisibility(View.GONE);
        }

        recyclerAlunos.setAdapter(mAdapterAlunos);
    }

    @Override
    public void onItemSelected(Aluno aluno) {
        Intent intent = new Intent(this, AddMatriculaActivity.class);
        intent.putExtra("codigo_aluno", aluno.getCodigoAluno());
        intent.putExtra("aluno", aluno.getAluno());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAdd:
                startActivity(new Intent(SelecionaAlunoActivity.this, AddAlunoActivity.class));
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
