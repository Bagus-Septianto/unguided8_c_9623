package com.bagusseptianto.gd8_c_9623;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    private CardView cvCreateUser, cvShowListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, -1);

        cvCreateUser = findViewById(R.id.cvCreateUser);
        cvShowListener = findViewById(R.id.cvShowListUser);

        cvCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateUserActivity.class);
                startActivity(i);
            }
        });

        cvShowListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ShowListUserActivity.class);
                startActivity(i);
            }
        });
    }
}
