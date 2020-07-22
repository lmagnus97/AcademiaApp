package com.lucas.magnus.academia;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lucas.magnus.academia.adapter.MatriculaModalidadeAdapter;
import com.lucas.magnus.academia.dao.FaturaDAO;
import com.lucas.magnus.academia.dao.MatriculaModalidadeDAO;
import com.lucas.magnus.academia.model.FaturaMatricula;
import com.lucas.magnus.academia.model.MatriculaModalidade;
import com.lucas.magnus.academia.util.Utils;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class GerarFaturaoActivity extends AppCompatActivity implements MatriculaModalidadeAdapter.OnItemSelectedListener {

    private Calendar mDataFatura;
    private Button btData;
    private MatriculaModalidadeAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_fatura);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.gerar_fatura));

        //VIEWS
        final EditText etNome = findViewById(R.id.etNome);
        final Button btGerarFatura = findViewById(R.id.btGerarFatura);
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        final TextView tvTotal = findViewById(R.id.tvTotal);
        final LinearLayout boxFaturaGerada = findViewById(R.id.boxFaturaGerada);
        btData = findViewById(R.id.btData);

        //PROPRIEDADES DO RECYCLER
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GerarFaturaoActivity.this));
        final MatriculaModalidadeDAO dao = new MatriculaModalidadeDAO(GerarFaturaoActivity.this);

        //RECUPERA CODIGO DO ALUNO
        final int codMatricula = getIntent().getIntExtra("codigo_matricula", 0);
        final int diaVencimento = getIntent().getIntExtra("dia_vencimento", 0);
        final String aluno = getIntent().getStringExtra("aluno");

        if (aluno != null) {
            etNome.setText(aluno);
        }

        //SELECIONA DATA
        btData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataFatura = Calendar.getInstance();
                new DatePickerDialog(GerarFaturaoActivity.this, date, mDataFatura
                        .get(Calendar.YEAR), mDataFatura.get(Calendar.MONTH),
                        mDataFatura.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //GERAR FATURA DO USUARIO
        btGerarFatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LISTA MATRICULAS - MODALIDADES
                List<MatriculaModalidade> lista = dao.selectForCodigoMatricula(codMatricula);
                mAdapter = new MatriculaModalidadeAdapter(lista, R.layout.item_matricula_fatura, GerarFaturaoActivity.this);
                recyclerView.setAdapter(mAdapter);

                double total = 0;
                for (MatriculaModalidade item : lista) {
                    total += item.getPlano().getValorMensal();
                }

                tvTotal.setText(Utils.convertToMoney(total));
                mDataFatura.set(Calendar.DAY_OF_MONTH, diaVencimento);

                FaturaMatricula faturaMatricula = new FaturaMatricula();
                faturaMatricula.setCodigoMatricula(codMatricula);
                faturaMatricula.setDataVencimento(mDataFatura);
                faturaMatricula.setValor(total);

                FaturaDAO faturaDAO = new FaturaDAO(GerarFaturaoActivity.this);

                if (faturaDAO.insert(faturaMatricula) > 0) {
                    boxFaturaGerada.setVisibility(View.VISIBLE);
                    Toast.makeText(GerarFaturaoActivity.this, "Fatura gerada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GerarFaturaoActivity.this, "Fatura já gerada!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mDataFatura.set(Calendar.YEAR, year);
            mDataFatura.set(Calendar.MONTH, monthOfYear);
            mDataFatura.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String data = String.valueOf(mDataFatura.get(Calendar.DAY_OF_MONTH)) + "/" +
                String.valueOf(mDataFatura.get(Calendar.MONTH) + 1)
                + "/" + String.valueOf(mDataFatura.get(Calendar.YEAR));

        btData.setText(data);
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
