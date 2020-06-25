package com.lucas.magnus.academia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lucas.magnus.academia.dao.ModalidadeDAO;
import com.lucas.magnus.academia.dao.PlanoDAO;
import com.lucas.magnus.academia.model.Modalidade;
import com.lucas.magnus.academia.model.Plano;
import com.lucas.magnus.academia.util.MaskMoney;
import com.lucas.magnus.academia.util.Utils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddPlanoActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plano);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.novo_plano));

        final EditText etPlano = findViewById(R.id.etPlano);
        final EditText etValor = findViewById(R.id.etValor);
        final Button btCadastrar = findViewById(R.id.btCadastrar);
        final Spinner spinnerModalidades = findViewById(R.id.spinnerModalidades);


        final PlanoDAO dao = new PlanoDAO(this);

        //PEGA MODALIDADES
        ModalidadeDAO modalidadeDAO = new ModalidadeDAO(this);
        List<Modalidade> listaModalidades = new ArrayList<>();

        //RECUPERA DADO PARA UPDATE
        final String mPlano = getIntent().getStringExtra("plano");
        final String mModalidade = getIntent().getStringExtra("modalidade");

        if (mPlano != null) {
            spinnerModalidades.setEnabled(false);
            btCadastrar.setText(getResources().getString(R.string.atualizar));
            listaModalidades.add(new Modalidade(mModalidade));

            Plano plano = dao.select(mPlano, mModalidade);

            etPlano.setText(plano.getPlano());
            etValor.setText(String.valueOf(plano.getValorMensal()).replace(".", ","));
            etPlano.setEnabled(false);

        } else {
            listaModalidades.addAll(modalidadeDAO.selectAll());
        }

        //LISTA MODALIDADES
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mPlano == null ? Modalidade.convertToSpinner(listaModalidades, "Modalidade") : Modalidade.convertToSpinner(listaModalidades, null));
        spinnerModalidades.setAdapter(adapter);

        //MASCARA PREÇO
        etValor.addTextChangedListener(new MaskMoney(etValor));

        //CADASTRAR
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(mPlano == null){
                        if (etPlano.getText().toString().isEmpty()) {
                            Toast.makeText(AddPlanoActivity.this, "Informe o plano!", Toast.LENGTH_SHORT).show();
                        } else if (mModalidade == null && spinnerModalidades.getSelectedItemPosition() == 0) {
                            Toast.makeText(AddPlanoActivity.this, "Selecione a modalidade!", Toast.LENGTH_SHORT).show();
                        } else if (etValor.getText().toString().isEmpty()) {
                            Toast.makeText(AddPlanoActivity.this, "Informe o valor!", Toast.LENGTH_SHORT).show();
                        } else {
                            final Plano data = new Plano();
                            data.setPlano(etPlano.getText().toString());
                            data.setModalidade(spinnerModalidades.getSelectedItem().toString());
                            data.setValorMensal(Double.valueOf(etValor.getText().toString().replace(",", ".")));

                            if (dao.insert(data) > 0) {
                                onBackPressed();
                                Toast.makeText(AddPlanoActivity.this, data.getPlano() + " inserido!", Toast.LENGTH_SHORT).show();
                            }else if(dao.isDesativado(data)){
                                new AlertDialog.Builder(AddPlanoActivity.this)
                                        .setTitle("Plano desativado")
                                        .setMessage("Deseja reativar?")
                                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                if (dao.ativar(data) > 0) {
                                                    onBackPressed();
                                                } else {
                                                    Toast.makeText(AddPlanoActivity.this, "Não foi possível reativar!", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        })
                                        .setNegativeButton(R.string.nao, null).show();
                            }else{
                                Toast.makeText(AddPlanoActivity.this, "Verifique os dados inseridos!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Plano data = new Plano();
                        data.setPlano(etPlano.getText().toString());
                        data.setModalidade(spinnerModalidades.getSelectedItem().toString());
                        data.setValorMensal(Double.valueOf(etValor.getText().toString().replace(",", ".")));

                        if (dao.update(data) > 0) {
                            onBackPressed();
                            Toast.makeText(AddPlanoActivity.this, data.getPlano() + " atualizado!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(AddPlanoActivity.this, "Verifique os dados inseridos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
