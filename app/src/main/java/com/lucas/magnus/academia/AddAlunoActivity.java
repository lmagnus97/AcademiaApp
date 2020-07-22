package com.lucas.magnus.academia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.lucas.magnus.academia.dao.AlunoDAO;
import com.lucas.magnus.academia.model.Aluno;
import com.lucas.magnus.academia.model.Cidade;
import com.lucas.magnus.academia.util.Utils;

import java.util.Calendar;
import java.util.Objects;

public class AddAlunoActivity extends AppCompatActivity {

    private Calendar mCalendarAniversario;
    private Cidade mCidade = null;
    private Button etCidade;
    private Button btDataNascimento;
    int LAUNCH_SELECIONA_CIDADE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aluno);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.adicionar_aluno));

        //VIEWS
        final EditText etNome = findViewById(R.id.etNome);
        final EditText etBairro = findViewById(R.id.etBairro);
        final EditText etCelular = findViewById(R.id.etCelular);
        final EditText etComplemento = findViewById(R.id.etComplemento);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etEndereco = findViewById(R.id.etEndereco);
        final EditText etNumero = findViewById(R.id.etNumero);
        final EditText etObservacao = findViewById(R.id.etObservacao);
        final EditText etTelefone = findViewById(R.id.etTelefone);
        final EditText etCep = findViewById(R.id.etCep);
        final RadioGroup rgSexo = findViewById(R.id.rgSexo);
        final Button btCadastrar = findViewById(R.id.btCadastrar);
        final Button btExcluir = findViewById(R.id.btExcluir);
        final CardView cardExcluir = findViewById(R.id.cardExcluir);
        btDataNascimento = findViewById(R.id.btDataNascimento);
        etCidade = findViewById(R.id.btCidade);

        //RECUPERA CODIGO DO ALUNO PARA UPDATE
        final int codAluno = getIntent().getIntExtra("codigo_aluno", 0);

        //ATUALIZA CAMPOS
        if (codAluno > 0) {
            btCadastrar.setText(getResources().getString(R.string.atualizar));
            cardExcluir.setVisibility(View.VISIBLE);

            AlunoDAO alunoDAO = new AlunoDAO(this);
            Aluno aluno = alunoDAO.select(codAluno);

            if (aluno.getSexo() != null) {
                if (aluno.getSexo().equals("M")) {
                    rgSexo.check(R.id.rbMasculino);
                } else {
                    rgSexo.check(R.id.rbFeminino);
                }
            }

            if (aluno.getCidade() != null) {
                mCidade = new Cidade(aluno.getCidade(), aluno.getEstado(), aluno.getPais());
                etCidade.setText(mCidade.getCidade() + "/" + mCidade.getEstado());
            }

            if (aluno.getDataNascimento() != null) {
                mCalendarAniversario = Calendar.getInstance();
                mCalendarAniversario.setTimeInMillis(aluno.getDataNascimento());
                btDataNascimento.setText(Utils.calendarToString(mCalendarAniversario));
            }

            if (aluno.getAluno() != null) {
                etNome.setText(aluno.getAluno());
            }

            if (aluno.getBairro() != null) {
                etBairro.setText(aluno.getBairro());
            }

            if (aluno.getCelular() != null) {
                etCelular.setText(aluno.getCelular());
            }

            if (aluno.getCep() != null) {
                etCep.setText(aluno.getCep());
            }

            if (aluno.getComplemento() != null) {
                etComplemento.setText(aluno.getComplemento());
            }

            if (aluno.getEmail() != null) {
                etEmail.setText(aluno.getEmail());
            }

            if (aluno.getEndereco() != null) {
                etEndereco.setText(aluno.getEndereco());
            }

            if (aluno.getNumero() != null) {
                etNumero.setText(aluno.getNumero());
            }

            if (aluno.getObservacao() != null) {
                etObservacao.setText(aluno.getObservacao());
            }

            if (aluno.getTelefone() != null) {
                etTelefone.setText(aluno.getTelefone());
            }
        }

        //SELECIONA DATA DE NASCIMENTO
        btDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAniversario = Calendar.getInstance();
                mCalendarAniversario.set(1990, 0, 1);

                new DatePickerDialog(AddAlunoActivity.this, date, mCalendarAniversario
                        .get(Calendar.YEAR), mCalendarAniversario.get(Calendar.MONTH),
                        mCalendarAniversario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //CADASTRA ALUNO
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AlunoDAO alunoDAO = new AlunoDAO(AddAlunoActivity.this);
                    RadioButton rbSelected = findViewById(rgSexo.getCheckedRadioButtonId());

                    if (etNome.getText().length() < 3) {
                        etNome.setError("Insira seu nome");
                    } else if (mCalendarAniversario == null) {
                        Toast.makeText(AddAlunoActivity.this, "Informe a data de nascimento!", Toast.LENGTH_SHORT).show();
                    } else if (etCelular.length() < 8) {
                        etCelular.setError("Informe um celular válido!");
                    } else if (etEmail.getText().length() < 3) {
                        etEmail.setError("Informe um email válido!");
                    } else if (rbSelected == null) {
                        Toast.makeText(AddAlunoActivity.this, "Informe o sexo!", Toast.LENGTH_SHORT).show();
                    } else if (mCidade == null) {
                        Toast.makeText(AddAlunoActivity.this, "Selecione a cidade!", Toast.LENGTH_SHORT).show();
                    } else if (etEndereco.getText().length() < 3) {
                        etEndereco.setError("Informe um endereço válido!");
                    } else if (etBairro.getText().length() < 3) {
                        etBairro.setError("Informe um bairro válido!");
                    } else if (etNumero.getText().length() < 1) {
                        etNumero.setError("Informe o nº!");
                    } else if (etCep.getText().toString().length() < 6) {
                        etCep.setError("Informe o CEP!");
                    } else {

                        final Aluno aluno = new Aluno();
                        aluno.setAluno(etNome.getText().toString());
                        aluno.setBairro(etBairro.getText().toString());
                        aluno.setCelular(etCelular.getText().toString());
                        aluno.setCep(etCep.getText().toString());
                        aluno.setCidade(mCidade.getCidade());
                        aluno.setComplemento(etComplemento.getText().toString());
                        aluno.setDataNascimento(mCalendarAniversario.getTimeInMillis());
                        aluno.setEmail(etEmail.getText().toString());
                        aluno.setEndereco(etEndereco.getText().toString());
                        aluno.setEstado(mCidade.getEstado());
                        aluno.setNumero(etNumero.getText().toString());
                        aluno.setObservacao(etObservacao.getText().toString());
                        aluno.setPais(mCidade.getPais());
                        aluno.setSexo(Utils.convertToSexo(rbSelected.getText().toString(), AddAlunoActivity.this));
                        aluno.setTelefone(etTelefone.getText().toString());

                        aluno.setIdUsuario(1);

                        if (codAluno == 0) {
                            if (alunoDAO.insert(aluno) > 0) {
                                Toast.makeText(AddAlunoActivity.this, "Aluno cadastrado!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        } else {
                            aluno.setCodigoAluno(codAluno);
                            if (alunoDAO.update(aluno) > 0) {
                                Toast.makeText(AddAlunoActivity.this, aluno.getAluno() + " atualizado!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }
                    }

                } catch (Exception e) {
                    Log.i("INFOLOG", "ERRO AO INSERIR" + e.getMessage());
                }
            }
        });

        //EXCLUIR USUARIO
        btExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AddAlunoActivity.this)
                        .setTitle("Excluir aluno")
                        .setMessage("Deseja mesmo excluir este aluno?")
                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                AlunoDAO alunoDAO = new AlunoDAO(AddAlunoActivity.this);
                                alunoDAO.delete(codAluno);
                                onBackPressed();
                            }
                        })
                        .setNegativeButton(R.string.nao, null).show();

            }
        });

        //SELECIONAR CIDADE
        etCidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddAlunoActivity.this, SelecionaCidadeActivity.class);
                startActivityForResult(i, LAUNCH_SELECIONA_CIDADE);
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mCalendarAniversario.set(Calendar.YEAR, year);
            mCalendarAniversario.set(Calendar.MONTH, monthOfYear);
            mCalendarAniversario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String data = String.valueOf(mCalendarAniversario.get(Calendar.DAY_OF_MONTH)) + "/" +
                String.valueOf(mCalendarAniversario.get(Calendar.MONTH) + 1)
                + "/" + String.valueOf(mCalendarAniversario.get(Calendar.YEAR));

        btDataNascimento.setText(data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SELECIONA_CIDADE) {
            if (resultCode == Activity.RESULT_OK) {
                mCidade = new Cidade(data.getStringExtra("cidade"), data.getStringExtra("estado"), data.getStringExtra("pais"));
                etCidade.setText(mCidade.getCidade() + "/" + mCidade.getEstado());
            }
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
