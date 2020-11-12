package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<perfume> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //액티비티 6,7
        setContentView(R.layout.activity_main);

        //탭 구현
        TabHost tabHost = findViewById(R.id.host);
        tabHost.setup();
        TabHost.TabSpec spec = tabHost.newTabSpec("tab1");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon1, null));
        spec.setContent(R.id.tab1);
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec("tab2");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon2, null));
        spec.setContent(R.id.tab2);
        tabHost.addTab(spec);

        //향수 리스트 출력 구현(리사이클러뷰)
        recyclerView = findViewById(R.id.recyclerView); // id 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // perfumeInfo 객체를 담을 ArrayList(->어댑터)

        database = FirebaseDatabase.getInstance(); //firebase DB와 연동

        databaseReference = database.getReference("perfume"); // Firebase 의 DB 테이블과 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Firebase 의 DB를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트를 초기화
                //반복문으로 데이터의 list 추출
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    perfume info = snapshot.getValue(perfume.class); // 만들어둔 perfume 객체에 데이터를 담는다.
                    arrayList.add(info); // 담은 데이터를 배열리스트에 넣고, 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //에러가 발생할 경우
                Log.e("MainActivity", String.valueOf(databaseError.toException()));
            }
        });

        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

    }
}