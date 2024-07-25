package com.example.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;

import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout overlayLayout = findViewById(R.id.overlayLayout);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isButtonHidden = sharedPreferences.getBoolean("isButtonHidden", false);
        if (isButtonHidden) {
            overlayLayout.setVisibility(View.GONE); // 如果按钮状态为隐藏，则设置按钮为不可见
        } else {
            overlayLayout.setVisibility(View.VISIBLE);
            link();
            TextView textView = findViewById(R.id.toask_button1);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isButtonHidden", true);
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, Activity_Home.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        LinearLayout overlayLayout = findViewById(R.id.overlayLayout);
        boolean isButtonHidden = sharedPreferences.getBoolean("isButtonHidden", false);

        if (isButtonHidden) {
            overlayLayout.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, Activity_Home.class);
                    startActivity(intent);
                }
            }, 2000);
        }

        super.onResume();
    }

    public void link() {
        TextView textView = findViewById(R.id.toask_text2);
        SpannableString spannableString = new SpannableString("欢迎使用音乐社区，我们将严格遵守相关法律和隐私政策保护您的个人隐私，请您阅读并同意《用户协议》与《隐私政策》。");
        ClickableSpan userAgreementSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this, "《用户协议》", Toast.LENGTH_SHORT).show();
            }
        };
        ClickableSpan privacyPolicySpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this, "《隐私政策》", Toast.LENGTH_SHORT).show();
            }
        };
        int startUserAgreement = spannableString.toString().indexOf("《用户协议》");
        int endUserAgreement = startUserAgreement + "《用户协议》".length();
        spannableString.setSpan(userAgreementSpan, startUserAgreement, endUserAgreement, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int startPrivacyPolicy = spannableString.toString().indexOf("《隐私政策》");
        int endPrivacyPolicy = startPrivacyPolicy + "《隐私政策》".length();
        spannableString.setSpan(privacyPolicySpan, startPrivacyPolicy, endPrivacyPolicy, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());  // 设置TextView可点击
    }
}