package com.example.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.catchthekenny.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    int score=0,gecilmesiGerekenSkor=20;
    int imageSpeed=2000;
    ImageView[] imageViews;
    Handler handler;
    Runnable runnable;
    int level=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.skorText.setVisibility(View.INVISIBLE);
        binding.kalanSureText.setVisibility(View.INVISIBLE);



        imageViews=new ImageView[]{
                binding.imageView,binding.imageView1,binding.imageView2,
                binding.imageView3,binding.imageView4,binding.imageView5,
                binding.imageView6,binding.imageView7,binding.imageView8,
        };

        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.startButton.setClickable(false);
                gecilmesiGerekenSkor=gecilmesiGerekenSkor/2;
                imageSpeed/=2;
                score=0;
                binding.skorText.setVisibility(View.VISIBLE);
                binding.kalanSureText.setVisibility(View.VISIBLE);
                hideImages();
                new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long l) {
                        binding.kalanSureText.setText("Kalan süre: "+l/1000);
                    }

                    @Override
                    public void onFinish() {
                        binding.startButton.setClickable(true);
                        binding.kalanSureText.setText("Zaman Bitti");
                        handler.removeCallbacks(runnable);
                        for (ImageView imageView:imageViews){
                            imageView.setVisibility(View.INVISIBLE);
                        }
                        if(score>gecilmesiGerekenSkor){
                            Toast.makeText(MainActivity.this,"Tebrikler Başardınız Hız Arttırılıyor",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Üzgünüm Kaybettiniz",Toast.LENGTH_LONG).show();
                            Intent intent=getIntent();
                            finish();
                            startActivity(intent);

                        }


                    }
                }.start();
            }
        });



    }

    public void yeniTur(){

    }



    public void hideImages(){
        handler=new Handler();

        runnable=new Runnable() {
            @Override
            public void run() {
                for (ImageView imageView:imageViews){
                    imageView.setVisibility(View.INVISIBLE);
                }
                Random random=new Random();
                int rndNmbr=random.nextInt(9);
                imageViews[rndNmbr].setVisibility(View.VISIBLE);
                handler.postDelayed(this,imageSpeed);

            }
        };
        handler.post(runnable);



    }

    public void skoruArttır(View view) {
        score++;
        binding.skorText.setText("Skorunuz: "+ score);
    }
}