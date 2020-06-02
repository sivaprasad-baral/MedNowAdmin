package com.example.mednowadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mednowadmin.model.Medicine;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String name,price;
    boolean newMed = true;

    EditText editTextMedicineName,editTextMedicinePrice;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMedicineName = findViewById(R.id.main_edit_text_medicine_name);
        editTextMedicinePrice = findViewById(R.id.main_edit_text_medicine_price);

        databaseReference = FirebaseDatabase.getInstance().getReference("Medicine");
    }

    public void addMedBtn(View view) {
        name = editTextMedicineName.getText().toString();
        price = editTextMedicinePrice.getText().toString();
        if(name.isEmpty()) {
            editTextMedicineName.setError("Invalid name");
            editTextMedicineName.requestFocus();
            return;
        } else if(price.isEmpty() || Integer.parseInt(price) == 0) {
            editTextMedicinePrice.setError("Invalid price");
            editTextMedicinePrice.requestFocus();
            return;
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Medicine med = snapshot.getValue(Medicine.class);
                    if(name.equalsIgnoreCase(Objects.requireNonNull(med).getMedName())) {
                        editTextMedicineName.setError("Medicine already added");
                        editTextMedicineName.requestFocus();
                        editTextMedicineName.setText(null);
                        editTextMedicinePrice.setText(null);
                        newMed = false;
                        break;
                    }
                }
                databaseReference.removeEventListener(this);
                if(newMed) {
                    String id = databaseReference.push().getKey();
                    Medicine medicine = new Medicine(name,price,id);
                    databaseReference.child(Objects.requireNonNull(id)).setValue(medicine).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(MainActivity.this,"Added medicine to database",Toast.LENGTH_SHORT).show();
                                editTextMedicineName.setText(null);
                                editTextMedicinePrice.setText(null);
                                editTextMedicineName.requestFocus();
                                newMed = true;
                            } else {
                                Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
