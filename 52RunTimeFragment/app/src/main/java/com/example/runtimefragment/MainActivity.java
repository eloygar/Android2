package com.example.runtimefragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    Button button;
    FragmentoUno miFragmentoUno;
    FragmentoDos miFragmentoDos;

    int showingFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.buttonSwitch);
        button.setOnClickListener(v -> {
            switchFragment(v);
        });
    }

    public void switchFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (showingFragment == 1) {
            miFragmentoUno= new FragmentoUno();
            fragmentTransaction.replace(R.id.frameLayout, miFragmentoUno);
            showingFragment = 2;
        } else {
            miFragmentoDos = new FragmentoDos();
            fragmentTransaction.replace(R.id.frameLayout, miFragmentoDos);
            showingFragment = 1;
        }
        fragmentTransaction.commit();
    }

}