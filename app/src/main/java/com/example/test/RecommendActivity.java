package com.example.test;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.RecommendAdapter;
import com.example.test.perfume;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecommendActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button home_button;
    private Button map_button;
    private RecommendAdapter recommendAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //임의로 지정

    ArrayList<perfume> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);


        home_button = (Button)findViewById(R.id.homeButton);
        map_button = (Button)findViewById(R.id.mapButton);

        //리사이클러뷰 설정
        recyclerView = (RecyclerView)findViewById(R.id.recycler_recommend);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        //파이어베이스 데이터베이스 연결
        firebaseDatabase = FirebaseDatabase.getInstance(); //firebase DB와 연동
        databaseReference = firebaseDatabase.getReference("향수");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int cnt = 0;
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    perfume info = dataSnapshot.getValue(perfume.class);
                    if(info.getType().equals("플로랄"))
                    {
                        String[] list = info.getScent().split(",");
                        for(int i=0; i<list.length; i++)
                        {
                            String test = list[i].trim();
                            if(test.equals("장미"))
                                arrayList.add(info);
                        }
                    }
                }
                Collections.sort(arrayList,new Descending()); //별점 순위 대로 출력
                recommendAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recommendAdapter = new RecommendAdapter(arrayList, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(recommendAdapter); //어댑터 설정 완료


    }
    //별점순 정렬
    class Descending implements Comparator<perfume> {
        @Override
        public int compare(perfume p, perfume q) {
            String a=String.valueOf(p.getEstimating());
            String b=String.valueOf(q.getEstimating());
            return b.compareTo(a);
        }

    }
}
