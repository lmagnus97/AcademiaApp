package com.lucas.magnus.academia;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lucas.magnus.academia.adapter.MatriculaModalidadeAdapter;
import com.lucas.magnus.academia.dao.GraduacaoDAO;
import com.lucas.magnus.academia.dao.MatriculaDAO;
import com.lucas.magnus.academia.dao.MatriculaModalidadeDAO;
import com.lucas.magnus.academia.dao.ModalidadeDAO;
import com.lucas.magnus.academia.dao.PlanoDAO;
import com.lucas.magnus.academia.model.Graduacao;
import com.lucas.magnus.academia.model.Matricula;
import com.lucas.magnus.academia.model.MatriculaModalidade;
import com.lucas.magnus.academia.model.Modalidade;
import com.lucas.magnus.academia.model.Plano;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddMatriculaActivity extends AppCompatActivity implements MatriculaModalidadeAdapter.OnItemSelectedListener {

    private MatriculaModalidadeAdapter mAdapter;
    private Integer codMatricula;
    List<MatriculaModalidade> listaMatriculaModalidade = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matricula);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        final EditText etAluno = findViewById(R.id.etAluno);
        final EditText etDiaVencimento = findViewById(R.id.etDiaVencimento);
        final FloatingActionButton fabConfirmar = findViewById(R.id.fabConfirmar);
        final Spinner spinnerModalidades = findViewById(R.id.spinnerModalidades);
        final Spinner spinnerGraduacoes = findViewById(R.id.spinnerGraduacoes);
        final Spinner spinnerPlanos = findViewById(R.id.spinnerPlanos);
        final Button btAddModalidade = findViewById(R.id.btAddModalidade);
        final Button btGerarFatura = findViewById(R.id.btGerarFatura);
        final CardView cardGerarFatura = findViewById(R.id.cardGerarFatura);
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        final CardView cardPlano = findViewById(R.id.cardPlano);
        final CardView cardGraduacao = findViewById(R.id.cardGraduacao);

        //GET DADOS UPDATE
        codMatricula = getIntent().getIntExtra("codigo_matricula", 0);
        final String aluno = getIntent().getStringExtra("aluno");
        final int codigoAluno = getIntent().getIntExtra("codigo_aluno", 0);
        setTitle(aluno);
        etAluno.setText(aluno);

        if (codMatricula > 0) {
            cardGerarFatura.setVisibility(View.VISIBLE);
        }

        if (codMatricula > 0) {
            MatriculaDAO matriculaDAO = new MatriculaDAO(AddMatriculaActivity.this);
            Matricula matricula = matriculaDAO.select(codMatricula);
            etDiaVencimento.setText(String.valueOf(matricula.getDiaVencimento()));

            //GET MODALIDADES CADASTRADAS
            MatriculaModalidadeDAO dao = new MatriculaModalidadeDAO(this);
            listaMatriculaModalidade.addAll(dao.selectForCodigoMatricula(codMatricula));
        }

        //PROPRIEDADES DO RECYCLER
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddMatriculaActivity.this));

        //PEGA MODALIDADES
        ModalidadeDAO modalidadeDAO = new ModalidadeDAO(this);
        List<Modalidade> listaModalidades = modalidadeDAO.selectAll();
        ArrayAdapter<String> adapterModalidades = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Modalidade.convertToSpinner(listaModalidades, "Modalidade"));
        spinnerModalidades.setAdapter(adapterModalidades);

        spinnerModalidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerModalidades.getSelectedItemPosition() > 0) {

                    //PEGA GRADUACOES
                    GraduacaoDAO graduacaoDAO = new GraduacaoDAO(AddMatriculaActivity.this);
                    List<Graduacao> listaGraduacoes = graduacaoDAO.selectForModalidade(spinnerModalidades.getSelectedItem().toString());
                    ArrayAdapter<String> adapterGraduacoes = new ArrayAdapter<>(AddMatriculaActivity.this, android.R.layout.simple_spinner_dropdown_item, Graduacao.convertToSpinner(listaGraduacoes, "Graduação"));
                    spinnerGraduacoes.setAdapter(adapterGraduacoes);

                    //PEGA PLANOS
                    PlanoDAO planoDAO = new PlanoDAO(AddMatriculaActivity.this);
                    List<Plano> listaPlanos = planoDAO.selectPlanosModalidades(spinnerModalidades.getSelectedItem().toString());
                    ArrayAdapter<String> adapterPlanos = new ArrayAdapter<>(AddMatriculaActivity.this, android.R.layout.simple_spinner_dropdown_item, Plano.convertToSpinner(listaPlanos, "Plano"));
                    spinnerPlanos.setAdapter(adapterPlanos);

                    //VISIBLE
                    cardGraduacao.setVisibility(View.VISIBLE);
                    cardPlano.setVisibility(View.VISIBLE);
                } else {
                    //INVISIBLE
                    cardGraduacao.setVisibility(View.GONE);
                    cardPlano.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //ADD MODALIDADE
        btAddModalidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (spinnerModalidades.getSelectedItemPosition() == 0) {
                        Toast.makeText(AddMatriculaActivity.this, "Selecione a modalidade!", Toast.LENGTH_SHORT).show();
                    } else if (spinnerGraduacoes.getSelectedItemPosition() == 0 || cardGraduacao.getVisibility() == View.GONE) {
                        Toast.makeText(AddMatriculaActivity.this, "Informe a graduação!", Toast.LENGTH_SHORT).show();
                    } else if (spinnerPlanos.getSelectedItemPosition() == 0 || cardPlano.getVisibility() == View.GONE) {
                        Toast.makeText(AddMatriculaActivity.this, "Informe o plano!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (codMatricula == 0) {
                            if (!etDiaVencimento.getText().toString().isEmpty() && Integer.parseInt(etDiaVencimento.getText().toString()) < 31) {
                                codMatricula = registraMatricula(Integer.parseInt(etDiaVencimento.getText().toString()), codigoAluno, aluno);
                            }
                        }

                        if (codMatricula > 0) {
                            MatriculaModalidadeDAO dao = new MatriculaModalidadeDAO(AddMatriculaActivity.this);

                            MatriculaModalidade data = new MatriculaModalidade();
                            data.setPlano(new Plano(spinnerPlanos.getSelectedItem().toString()));
                            data.setModalidade(new Modalidade(spinnerModalidades.getSelectedItem().toString()));
                            data.setGraduacao(new Graduacao(spinnerGraduacoes.getSelectedItem().toString()));
                            data.setDataInicio(Calendar.getInstance().getTimeInMillis());
                            data.setMatricula(new Matricula(codMatricula));

                            PlanoDAO planoDAO = new PlanoDAO(AddMatriculaActivity.this);
                            Plano plano = planoDAO.select(spinnerPlanos.getSelectedItem().toString(), spinnerModalidades.getSelectedItem().toString());
                            data.getPlano().setValorMensal(plano.getValorMensal());

                            if (dao.insert(data) > 0) {
                                listaMatriculaModalidade.add(data);
                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(AddMatriculaActivity.this, "Informe o dia de vencimento!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(AddMatriculaActivity.this, "Ocorreu um erro, verifique os dados inseridos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //CADASTRAR
        fabConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (etDiaVencimento.getText().toString().isEmpty()) {
                        Toast.makeText(AddMatriculaActivity.this, "Informe o dia de vencimento!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (codMatricula == 0) {
                            registraMatricula(Integer.parseInt(etDiaVencimento.getText().toString()), codigoAluno, aluno);

                        } else {
                            MatriculaDAO matriculaDAO = new MatriculaDAO(AddMatriculaActivity.this);
                            if (matriculaDAO.update(codMatricula, Integer.valueOf(etDiaVencimento.getText().toString())) > 0) {
                                {
                                    Toast.makeText(AddMatriculaActivity.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddMatriculaActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(AddMatriculaActivity.this, "Verifique os dados inseridos!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(AddMatriculaActivity.this, "Verifique os dados inseridos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //LISTA MODALIDADES
        mAdapter = new MatriculaModalidadeAdapter(listaMatriculaModalidade, R.layout.item_matricula_modalidade, this);
        recyclerView.setAdapter(mAdapter);

        //GERAR FATURA
        btGerarFatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMatriculaActivity.this, GerarFaturaoActivity.class);
                intent.putExtra("aluno", aluno);
                intent.putExtra("codigo_matricula", codMatricula);
                intent.putExtra("dia_vencimento", Integer.valueOf(etDiaVencimento.getText().toString()));
                startActivity(intent);
            }
        });
    }

    public Integer registraMatricula(Integer diaVencimento, Integer codigoAluno, String aluno) {
        MatriculaDAO dao = new MatriculaDAO(AddMatriculaActivity.this);

        Matricula data = new Matricula();
        data.setDiaVencimento(diaVencimento);
        data.setDataMatricula(Calendar.getInstance().getTimeInMillis());
        data.setIdAluno(codigoAluno);

        return dao.insert(data);
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

    @Override
    public void onItemSelected(MatriculaModalidade item) {

    }
}
