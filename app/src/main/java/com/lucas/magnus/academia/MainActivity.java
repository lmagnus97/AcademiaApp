package com.lucas.magnus.academia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.lucas.magnus.academia.sync.Sync;
import com.lucas.magnus.academia.util.UtilDialog;
import com.lucas.magnus.academia.util.UtilShared;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //VIEWS
        final ImageButton btModalidades = findViewById(R.id.btModalidades);
        final Button btAddModalidade = findViewById(R.id.btAddModalidade);
        final ImageButton btAlunos = findViewById(R.id.btAlunos);
        final Button btAddAluno = findViewById(R.id.btAddAluno);
        final ImageButton btPlanos = findViewById(R.id.btPlanos);
        final Button btAddPlano = findViewById(R.id.btAddPlano);
        final ImageButton btMatriculas = findViewById(R.id.btMatriculas);
        final Button btAddMatricula = findViewById(R.id.btAddMatricula);
        final CardView cardFaturas = findViewById(R.id.cardFaturas);
        final Button btSair = findViewById(R.id.btSair);
        final TextView tvUsuario = findViewById(R.id.tvUsuario);
        final Button btSincronizar = findViewById(R.id.btSincronizar);

        //ONCLICK
        btModalidades.setOnClickListener(this);
        btAddModalidade.setOnClickListener(this);
        btAlunos.setOnClickListener(this);
        btAddAluno.setOnClickListener(this);
        btPlanos.setOnClickListener(this);
        btAddPlano.setOnClickListener(this);
        btMatriculas.setOnClickListener(this);
        btAddMatricula.setOnClickListener(this);
        cardFaturas.setOnClickListener(this);
        btSair.setOnClickListener(this);
        btSincronizar.setOnClickListener(this);

        //GET USUARIO ATUAL
        tvUsuario.setText(getResources().getString(R.string.logado, UtilShared.getIdUsuario(MainActivity.this)));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btModalidades:
                startActivity(new Intent(MainActivity.this, ModalidadesActivity.class));
                break;
            case R.id.btAddModalidade:
                startActivity(new Intent(MainActivity.this, AddModalidadeActivity.class));
                break;
            case R.id.btAlunos:
                startActivity(new Intent(MainActivity.this, AlunosActivity.class));
                break;
            case R.id.btAddAluno:
                startActivity(new Intent(MainActivity.this, AddAlunoActivity.class));
                break;
            case R.id.btPlanos:
                startActivity(new Intent(MainActivity.this, PlanosActivity.class));
                break;
            case R.id.btAddPlano:
                startActivity(new Intent(MainActivity.this, AddPlanoActivity.class));
                break;
            case R.id.btMatriculas:
                startActivity(new Intent(MainActivity.this, MatriculasActivity.class));
                break;
            case R.id.btAddMatricula:
                startActivity(new Intent(MainActivity.this, SelecionaAlunoActivity.class));
                break;
            case R.id.cardFaturas:
                startActivity(new Intent(MainActivity.this, FaturasActivity.class));
                break;
            case R.id.btSair:
                UtilShared.logoutUsuario(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.btSincronizar:
                sync();
        }
    }

    public void sync() {
        try {
            final SweetAlertDialog dialog = UtilDialog.showDialog(
                    MainActivity.this,
                    SweetAlertDialog.PROGRESS_TYPE,
                    "Sincronizando",
                    "Enviando dados..."
            );

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Sync.sync(MainActivity.this);
                    dialog.dismissWithAnimation();
                }
            }).start();
        } catch (Exception e) {
            Toast.makeText(this, "Falha ao sincronizar, tente novamente...", Toast.LENGTH_SHORT).show();
        }
    }
}
