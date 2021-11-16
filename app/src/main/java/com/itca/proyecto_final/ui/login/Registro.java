package com.itca.proyecto_final.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itca.proyecto_final.MainActivity;
import com.itca.proyecto_final.R;
import com.itca.proyecto_final.ui.home.HomeFragment;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText contraseña;
    private Button regis;

    private String name1 = "";
    private String email1 = "";
    private String contra = "";

    FirebaseAuth  Auth;
    DatabaseReference data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Auth = FirebaseAuth.getInstance();
        data = FirebaseDatabase.getInstance().getReference();

        name= (EditText) findViewById(R.id.name);
        email= (EditText) findViewById(R.id.email);
        contraseña= (EditText) findViewById(R.id.contraseña);
        regis= (Button) findViewById(R.id.regis);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1 = name.getText().toString();
                email1 = email.getText().toString();
                contra = contraseña.getText().toString();

                if (!name1.isEmpty()&&!email1.isEmpty()&&!contra.isEmpty()){
                    if(contra.length()>=6){
                        registerUser();
                    }else{
                        Toast.makeText(Registro.this, "Minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(Registro.this, "Se deben completar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });



        }
        private void registerUser(){
        Auth.createUserWithEmailAndPassword(email1,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                   Map<String, Object> map = new HashMap<>();
                   map.put("name", name);
                    map.put("email", email);
                    map.put("contraseña", contraseña);

                    String id = Auth.getCurrentUser().getUid();
                    data.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(Registro.this, MainActivity.class));
                                finish();
                                Toast.makeText(Registro.this, "Bienvenido!!!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Registro.this, "No se crearon los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Registro.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void regresar(View v) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }
}

