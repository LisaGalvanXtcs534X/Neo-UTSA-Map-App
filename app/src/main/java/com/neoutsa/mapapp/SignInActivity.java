package com.neoutsa.mapapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.neoutsa.mapapp.controllers.AuthController;

/** Sign In - matches the GUI mockup. */
public class SignInActivity extends AppCompatActivity {

    private boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EditText etEmail = findViewById(R.id.et_email);
        EditText etPwd = findViewById(R.id.et_password);
        Button btnSignIn = findViewById(R.id.btn_sign_in);
        TextView tvError = findViewById(R.id.tv_error);
        ImageView ivEye = findViewById(R.id.iv_eye);

        ivEye.setOnClickListener(v -> {
            passwordVisible = !passwordVisible;
            etPwd.setInputType(passwordVisible
                    ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD | InputType.TYPE_CLASS_TEXT
                    : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            etPwd.setSelection(etPwd.getText().length());
        });

        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pwd = etPwd.getText().toString();
            if (AuthController.login(email, pwd)) {
                tvError.setVisibility(View.GONE);
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                tvError.setVisibility(View.VISIBLE);
            }
        });
    }
}
