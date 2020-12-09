package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.test.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeActivity extends AppCompatActivity {

    private Button buttonPerfume;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference("Users");
    public String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TabHost tabHost = findViewById(R.id.host);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("tab1");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon1, null));
        spec.setContent(R.id.tab_content1);
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec("tab2");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon2, null));
        spec.setContent(R.id.tab_content2);
        tabHost.addTab(spec);
        firebaseAuth = FirebaseAuth.getInstance();


        buttonPerfume = (Button) findViewById(R.id.tab_content1);
        buttonPerfume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity_sign 연결
                Intent intent = new Intent(HomeActivity.this, asking.class);
                startActivity(intent);
            }
        });
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = reference.child(uid);
        int id = item.getItemId();
        if (id == R.id.action_user) {
            if (user != null) {
                name = user.getDisplayName();
                Toast.makeText(getApplicationContext(), name + " 님 반갑습니다! ", Toast.LENGTH_SHORT).show();
                return true;
            }
        } else {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}