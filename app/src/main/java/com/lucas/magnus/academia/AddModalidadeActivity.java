package com.lucas.magnus.academia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lucas.magnus.academia.adapter.GraduacaoAdapter;
import com.lucas.magnus.academia.api.API;
import com.lucas.magnus.academia.dao.GraduacaoDAO;
import com.lucas.magnus.academia.dao.ModalidadeDAO;
import com.lucas.magnus.academia.model.Graduacao;
import com.lucas.magnus.academia.model.Modalidade;
import com.lucas.magnus.academia.model.Resposta;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddModalidadeActivity extends AppCompatActivity implements GraduacaoAdapter.OnItemSelectedListener {

    private GraduacaoAdapter mAdapter;
    private RecyclerView recyclerView;
    private Modalidade mModalidade = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modalidade);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.nova_modalidade));

        //VIEWS
        final EditText etModalidade = findViewById(R.id.etModalidade);
        final FloatingActionButton fabConfirmar = findViewById(R.id.fabConfirmar);
        final EditText etGraduacao = findViewById(R.id.etGraduacao);
        final ImageButton btAddGraduacao = findViewById(R.id.btAdd);
        recyclerView = findViewById(R.id.recycler);

        //DADOS PARA UPDATE
        final String modalidade = getIntent().getStringExtra("modalidade");

        if (modalidade != null) {
            mModalidade = new Modalidade(modalidade);
            etModalidade.setText(mModalidade.getModalidade());
            etModalidade.setEnabled(false);
        }

        //PROPRIEDADES DO RECYCLER
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddModalidadeActivity.this));

        //CADASTRA / ATUALIZA
        fabConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.getItemCount() > 0) {
                    onBackPressed();
                } else {
                    Toast.makeText(AddModalidadeActivity.this, "Adicione ao menos 1 graduação!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //GET GRADUACOES
        final GraduacaoDAO graduacaoDAO = new GraduacaoDAO(AddModalidadeActivity.this);
        final List<Graduacao> listaGraduacoes = graduacaoDAO.selectForModalidade(etModalidade.getText().toString());
        mAdapter = new GraduacaoAdapter(listaGraduacoes, R.layout.item_graduacao, this);
        recyclerView.setAdapter(mAdapter);

        final ModalidadeDAO modalidadeDAO = new ModalidadeDAO(AddModalidadeActivity.this);

        //ADD GRADUACAO
        btAddGraduacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etGraduacao.getText().toString().isEmpty() && !etModalidade.getText().toString().isEmpty()) {
                    if (mModalidade == null) {
                        final Modalidade novaModalidade = new Modalidade(etModalidade.getText().toString());
                        if (modalidadeDAO.insert(novaModalidade) > 0) {
                            mModalidade = novaModalidade;
                            etModalidade.setEnabled(false);
                        } else if (modalidadeDAO.isDesativado(novaModalidade.getModalidade())) {
                            new AlertDialog.Builder(AddModalidadeActivity.this)
                                    .setTitle("Modalidade desativada")
                                    .setMessage("Deseja reativar?")
                                    .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            if (modalidadeDAO.ativarModalidade(novaModalidade.getModalidade()) > 0) {
                                                onBackPressed();
                                            } else {
                                                Toast.makeText(AddModalidadeActivity.this, "Ocorreu um erro, tente novamente...", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    })
                                    .setNegativeButton(R.string.nao, null).show();
                        } else {
                            Toast.makeText(AddModalidadeActivity.this, "Não foi possível adicionar a modalidade!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (mModalidade != null) {
                        final Graduacao graduacao = new Graduacao();
                        graduacao.setGraduacao(etGraduacao.getText().toString());
                        graduacao.setModalidade(new Modalidade(mModalidade.getModalidade()));

                        if (graduacaoDAO.insert(graduacao) > 0) {
                            etGraduacao.setText("");
                            listaGraduacoes.add(graduacao);
                            mAdapter.notifyDataSetChanged();
                        } else if (graduacaoDAO.isDesativado(graduacao)) {
                            new AlertDialog.Builder(AddModalidadeActivity.this)
                                    .setTitle("Graduação desativada")
                                    .setMessage("Deseja reativar?")
                                    .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            if (graduacaoDAO.ativar(graduacao) > 0) {
                                                etGraduacao.setText("");
                                                listaGraduacoes.add(graduacao);
                                                mAdapter.notifyDataSetChanged();
                                            } else {
                                                Toast.makeText(AddModalidadeActivity.this, "Não foi possível reativar!", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    })
                                    .setNegativeButton(R.string.nao, null).show();
                        } else {
                            Toast.makeText(AddModalidadeActivity.this, "Não foi possível adicionar a graduação!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(AddModalidadeActivity.this, "Campos não informados!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(Graduacao item) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
