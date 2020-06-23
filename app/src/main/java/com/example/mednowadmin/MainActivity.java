package com.example.mednowadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mednowadmin.model.Medicine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewLogo;
    TextView textViewAppName,textViewPartnerApp;
    LottieAnimationView lottieNoInternet;
    String name,price;
    boolean newMed = true;

    EditText editTextMedicineName,editTextMedicinePrice;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageViewLogo = findViewById(R.id.main_image_view_logo);
        textViewAppName = findViewById(R.id.main_text_view_app_name);
        textViewPartnerApp = findViewById(R.id.main_text_view_partner_app);
        lottieNoInternet = findViewById(R.id.main_lottie_no_internet);

        imageViewLogo.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.fade_in_top));
        textViewAppName.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.fade_in_bottom));
        textViewPartnerApp.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.fade_in_bottom));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isConnected()) {
                    if(isLoggedIn()) {
                        startActivity(new Intent(MainActivity.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    } else {
                        startActivity(new Intent(MainActivity.this,Login.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                } else {
                    imageViewLogo.setVisibility(View.GONE);
                    textViewAppName.setVisibility(View.GONE);
                    textViewPartnerApp.setVisibility(View.GONE);
                    lottieNoInternet.setVisibility(View.VISIBLE);
                }
            }
        }, 3000);
    }

    public boolean isConnected() {
        return ((ConnectivityManager) Objects.requireNonNull(getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo() != null;
    }

    public boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }
}
