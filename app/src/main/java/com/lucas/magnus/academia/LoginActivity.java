package com.lucas.magnus.academia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lucas.magnus.academia.api.API;
import com.lucas.magnus.academia.model.Usuario;
import com.lucas.magnus.academia.util.UtilShared;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        final Button btEntrar = findViewById(R.id.btEntrar);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etSenha = findViewById(R.id.etSenha);
        final Button btCriarConta = findViewById(R.id.btCriarConta);

        if (UtilShared.getIdUsuario(LoginActivity.this) > 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Campo obrigatório");
                } else if (etSenha.getText().toString().isEmpty()) {
                    etSenha.setError("Campo obrigatório");
                } else {
                    API.GetUsuario(etEmail.getText().toString(), etSenha.getText().toString(), new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                            if (response == null) {
                                Toast.makeText(LoginActivity.this, "Usuário inexistente ou dados inválidos!", Toast.LENGTH_SHORT).show();
                            } else if (response.body().isHabilitado()) {
                                UtilShared.put((int) response.body().getId(), LoginActivity.this);
                                Log.i("INFOLOG", "ID USUARIO: " + String.valueOf(response.body().getId()));

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuário não habilitado!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CriarContaActivity.class));
            }
        });

    }
}
