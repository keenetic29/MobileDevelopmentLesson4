package com.mirea.andreevai.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mirea.andreevai.thread.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread mainThread = Thread.currentThread();
        binding.textView.setText("Имя текущего потока: " + mainThread.getName());
        // Меняем имя и выводим в текстовом поле
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО-01-20, НОМЕР ПО СПИСКУ: 2, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: 1+1");
        binding.textView.append("\n Новое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { new Thread(new Runnable() {
                public void run() {
                    int numberThread = count++;
                    Log.d("ThreadProject", String.format("Запущен поток No %d студентом группы No %s номер по списку No %s ", numberThread, "БСБО-01-20","2"));
                    long endTime = System.currentTimeMillis() + 20 * 1000; while (System.currentTimeMillis() < endTime) {
                        synchronized (this) { try {
                            wait(endTime - System.currentTimeMillis());
                            Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime); } catch (Exception e) {
                            throw new RuntimeException(e); }
                        }
                        Log.d("ThreadProject", "Выполнен поток No " + numberThread); }
                } }).start();
            } });


        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        float lesson = Float.parseFloat(binding.etPar.getText().toString())/Float.parseFloat(binding.etDays.getText().toString());
                        binding.textView2.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.textView2.setText("Среднее кол-во пар в день " +Float.toString(lesson));
                            }
                        });
                    }

                };
                Thread thread  = new Thread(runnable);
                thread.start();


            }
        });


    }

}