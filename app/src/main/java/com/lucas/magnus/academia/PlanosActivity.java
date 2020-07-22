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
import com.lucas.magnus.academia.adapter.PlanoAdapter;
import com.lucas.magnus.academia.dao.PlanoDAO;
import com.lucas.magnus.academia.model.Plano;

import java.util.Objects;

public class PlanosActivity extends AppCompatActivity implements PlanoAdapter.OnItemSelectedListener, View.OnClickListener {

    private PlanoAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayout boxVazio;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planos);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.planos));

        //VIEWS
        recyclerView = findViewById(R.id.recycler);
        boxVazio = findViewById(R.id.boxVazio);
        final FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        //PROPRIEDADES DO RECYCLER
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlanosActivity.this));

        //ONCLICK
        fabAdd.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        PlanoDAO dao = new PlanoDAO(PlanosActivity.this);
        mAdapter = new PlanoAdapter(dao.selectAll(), R.layout.item_plano, this);

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
    public void onItemSelected(Plano item) {
        Intent intent = new Intent(PlanosActivity.this, AddPlanoActivity.class);
        intent.putExtra("plano", item.getPlano());
        intent.putExtra("modalidade", item.getModalidade().getModalidade());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAdd:
                startActivity(new Intent(PlanosActivity.this, AddPlanoActivity.class));
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
