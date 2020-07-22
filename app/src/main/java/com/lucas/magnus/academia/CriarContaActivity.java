package com.lucas.magnus.academia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lucas.magnus.academia.api.API;
import com.lucas.magnus.academia.model.Resposta;
import com.lucas.magnus.academia.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CriarContaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_criar_conta);

        final Button btCriarConta = findViewById(R.id.btCriarConta);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etSenha = findViewById(R.id.etSenha);
        final EditText etSenhaConfirmar = findViewById(R.id.etSenhaConfirmar);


        btCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etSenha.getText().toString().equals(etSenhaConfirmar.getText().toString())){
                    etSenha.setError("Senhas não conferem!");
                }else{

                    Usuario usuario = new Usuario();
                    usuario.setNome(etEmail.getText().toString());
                    usuario.setSenha(etSenha.getText().toString());
                    usuario.setHabilitado(true);

                    API.PostUsuario(usuario, new Callback<Resposta>() {
                        @Override
                        public void onResponse(Call<Resposta> call, Response<Resposta> response) {

                            if(response != null && response.body().isSucesso()){
                                Toast.makeText(CriarContaActivity.this, "CRIADO COM SUCESSO!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CriarContaActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(CriarContaActivity.this, response.body().getMensagem(), Toast.LENGTH_SHORT).show();
                                Log.i("INFOLOG", "ERRO:" + response.body().getMensagem());
                            }
                        }

                        @Override
                        public void onFailure(Call<Resposta> call, Throwable t) {
                            Toast.makeText(CriarContaActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.i("INFOLOG", "ERRO:" + t.getMessage());
                        }
                    });

                }
            }
        });

    }
}
