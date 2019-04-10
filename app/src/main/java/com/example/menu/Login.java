package com.example.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;

public class Login extends AppCompatActivity {


    private EditText Name;
    private EditText Password;
    private TextView incorrectAttempts;
    private Button Login;
    private int counter = 3;

    private SharedPreferenceConfig preferenceConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferenceConfig = new SharedPreferenceConfig( getApplicationContext());

        Name = (EditText) findViewById(R.id.etname);
        Password = (EditText) findViewById(R.id.etpassword);
        Login = (Button) findViewById(R.id.etlogin);

        if(preferenceConfig.readLoginStatus()){
            startActivity(new Intent(Login.this,MainActivity.class));
            finish();
        }

       /* incorrectAttempts = (TextView) findViewById(R.id.incorrectcount);
        incorrectAttempts.setText("No Of Attempts Remaining: " +counter);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });*/
    }


   /* public void validate(String username, String password){
        if(username.equals("admin") && password.equals("admin")){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }else{
            counter--;
            incorrectAttempts.setText("No Of Attempts Remaining :" +counter);
            if(counter == 0){
                Login.setEnabled(false);
            }
        }

    }*/

    public void loginUser(View view) {

        String username = Name.getText().toString();
        String userPassword = Password.getText().toString();

        if(username.equals(getResources().getString(R.string.username)) && userPassword.equals(getResources().getString(R.string.password))){
            startActivity(new Intent(Login.this,MainActivity.class));
            preferenceConfig.writeLoginStatus(true);
            finish();
        }else{
            Toast.makeText(Login.this, "Login Failed... Try Again!!",Toast.LENGTH_LONG).show();
            Name.setText("");
            Password.setText("");
        }
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
