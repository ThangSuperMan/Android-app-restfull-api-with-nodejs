package com.sinhvien.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    // This variable depends on the current ipv4 of the computer
    private String BASE_URL = "http://192.168.1.3:3001";

    // UI components
    Button loginButton;
    Button signupButton;
    TextView anotherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrofit setup
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        anotherTextView = (TextView) findViewById(R.id.another_text_view);

        loginButton = (Button) findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener((View v) -> {
            handleLoginDialog();
        });

        signupButton = (Button) findViewById(R.id.buttonSignUp);
        signupButton.setOnClickListener((View v) -> {
            handleSignupDialog();
        });

    }

    // Functions
    private void handleLoginDialog() {
        View view = getLayoutInflater().inflate(R.layout.login_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setView(view).show();

        Button loginButton = view.findViewById(R.id.login_button);
        EditText emailEditView = view.findViewById(R.id.email_edit);
        EditText passwordEditView = view.findViewById(R.id.password_edit);

        loginButton.setOnClickListener((View v) -> {

            HashMap<String, String> map = new HashMap<>();

            map.put("email", emailEditView .getText().toString());
            map.put("password", passwordEditView.getText().toString());

            // Create an java object
            Call<LoginResult> call = retrofitInterface.executeLogin(map);

            // Execute http request
            call.enqueue(new Callback<LoginResult>() {

                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

        });

    }

    private void handleSignupDialog() {

        View view = getLayoutInflater().inflate(R.layout.signup_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setView(view).show();

        Button signupButton = view.findViewById(R.id.signup_button);
        EditText nameEditText = view.findViewById(R.id.name_edit);
        EditText emailEditView = view.findViewById(R.id.email_edit);
        EditText passwordEditView = view.findViewById(R.id.password_edit);

        signupButton.setOnClickListener((View v) -> {

            HashMap<String, String> map = new HashMap<>();

            map.put("name", nameEditText .getText().toString());
            map.put("email", emailEditView .getText().toString());
            map.put("password", passwordEditView.getText().toString());

            Call<Void> call = retrofitInterface.executeSignup(map);

            // Execute http request
            call.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}