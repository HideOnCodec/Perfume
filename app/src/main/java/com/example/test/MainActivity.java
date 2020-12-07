package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SearchView searchView;
    private Spinner spinner;

    private ArrayList<perfume> arrayList = new ArrayList<>();
    private ArrayList<perfume> copy_List = new ArrayList<>();
    //현재 선택된 향수 저장
    public static Context context_main;
    public String current;
    public String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context_main = this;

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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView); // id 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        searchView = (SearchView)findViewById(R.id.search_view) ;
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        //layoutManager 설정
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //파이어베이스 데이터베이스 연결
        database = FirebaseDatabase.getInstance(); //firebase DB와 연동

        databaseReference = database.getReference("향수"); // Firebase 의 DB 테이블과 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Firebase 의 DB를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트를 초기화
                //반복문으로 데이터의 list 추출
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    perfume info = snapshot.getValue(perfume.class); // 만들어둔 perfume 객체에 데이터를 담는다.
                    arrayList.add(info); // 담은 데이터를 배열리스트에 넣고, 리사이클러뷰로 보낼 준비
                    copy_List.add(info);

                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //에러가 발생할 경우
                Log.e("MainActivity", String.valueOf(databaseError.toException()));
            }
        });


        //드롭다운 버튼 구현
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinner.getSelectedItem().toString();
                if (text.equals("별점순")) //별점순 출력
                {
                    Collections.sort(copy_List, new Descending());
                    adapter = new CustomAdapter(copy_List, getApplicationContext());
                    //현재 선택된 향수 저장
                    adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int pos) {
                            current = copy_List.get(pos).getName();
                            findPath();
                            Intent intent = new Intent(getApplicationContext(),testActivity.class);
                            startActivity(intent);
                        }

                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);

                }
                else if(text.equals("이름순"))//이름순 출력
                {
                    adapter = new CustomAdapter(arrayList, getApplicationContext());
                    //현재 선택된 향수 저장
                    adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int pos) {
                            current = arrayList.get(pos).getName();
                            findPath();
                            Intent intent = new Intent(getApplicationContext(),testActivity.class);
                            startActivity(intent);
                        }

                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);

                }
            }


            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }


    class Descending implements Comparator<perfume> {
        @Override
        public int compare(perfume p, perfume q) {
            String a=String.valueOf(p.getEstimating());
            String b=String.valueOf(q.getEstimating());
            return b.compareTo(a);
        }

    }

    public void findPath(){
        database = FirebaseDatabase.getInstance(); //firebase DB와 연동
        databaseReference = database.getReference("향수"); // Firebase 의 DB 테이블과 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //반복문으로 데이터의 list 추출
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    perfume info = snapshot.getValue(perfume.class); // 만들어둔 perfume 객체에 데이터를 담는다.
                    if(info.getName().equals(current))
                        path = snapshot.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //에러가 발생할 경우
                Log.e("MainActivity", String.valueOf(databaseError.toException()));
            }
        });
    }

}