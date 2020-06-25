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

public class AlunosActivity extends AppCompatActivity implements AlunoAdapter.OnItemSelectedListener, View.OnClickListener {

    private AlunoAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayout boxVazio;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.alunos));

        //VIEWS
        recyclerView = findViewById(R.id.recycler);
        boxVazio = findViewById(R.id.boxVazio);
        final EditText etPesquia = findViewById(R.id.etPesquisa);
        final FloatingActionButton fabAddAluno = findViewById(R.id.fabAdd);

        //PROPRIEDADES RECYCLER
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ONCLICK
        fabAddAluno.setOnClickListener(this);

        //PESQUISA
        final AlunoDAO alunoDAO = new AlunoDAO(AlunosActivity.this);

        etPesquia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 1) {
                    mAdapter = new AlunoAdapter(alunoDAO.selectPesquisa(s.toString()), R.layout.item_aluno, AlunosActivity.this);
                }else{
                    mAdapter = new AlunoAdapter(alunoDAO.selectAll(), R.layout.item_aluno, AlunosActivity.this);
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
        mAdapter = new AlunoAdapter(alunoDAO.selectAll(), R.layout.item_aluno, this);
        setAdapter();
    }

    public void setAdapter() {
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
    public void onItemSelected(Aluno aluno) {
        Intent intent = new Intent(this, AddAlunoActivity.class);
        intent.putExtra("codigo_aluno", aluno.getCodigoAluno());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAdd:
                startActivity(new Intent(AlunosActivity.this, AddAlunoActivity.class));
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
