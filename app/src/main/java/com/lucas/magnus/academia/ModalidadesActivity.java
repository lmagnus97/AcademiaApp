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
import com.lucas.magnus.academia.adapter.ModalidadeAdapter;
import com.lucas.magnus.academia.dao.ModalidadeDAO;
import com.lucas.magnus.academia.model.Modalidade;

import java.util.Objects;

public class ModalidadesActivity extends AppCompatActivity implements ModalidadeAdapter.OnItemSelectedListener, View.OnClickListener {

    private ModalidadeAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayout boxVazio;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modalidade);
        setTitle(getResources().getString(R.string.modalidades));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //VIEWS
        recyclerView = findViewById(R.id.recycler);
        boxVazio = findViewById(R.id.boxVazio);
        final FloatingActionButton fabAddModalidade = findViewById(R.id.fabAddModalidade);

        //PROPRIEDADES DO RECYCLER
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ModalidadesActivity.this));

        //ONCLICK
        fabAddModalidade.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ModalidadeDAO modalidadeDAO = new ModalidadeDAO(ModalidadesActivity.this);
        mAdapter = new ModalidadeAdapter(modalidadeDAO.selectAll(), R.layout.item_modalidade, this);

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
    public void onItemSelected(Modalidade modalidade) {
        Intent intent = new Intent(this, AddModalidadeActivity.class);
        intent.putExtra("modalidade", modalidade.getModalidade());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddModalidade:
                startActivity(new Intent(ModalidadesActivity.this, AddModalidadeActivity.class));
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
