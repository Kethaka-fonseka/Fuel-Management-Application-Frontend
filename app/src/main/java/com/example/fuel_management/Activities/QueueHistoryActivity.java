package com.example.fuel_management.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.fuel_management.Adaptors.HistoryAdapter;
import com.example.fuel_management.Adaptors.OwnerHomeAdapter;
import com.example.fuel_management.Models.QueueModel;
import com.example.fuel_management.R;
import com.example.fuel_management.Services.QueueService;
import com.example.fuel_management.Session.SessionManager;

import java.util.List;

public class QueueHistoryActivity extends AppCompatActivity {

//Initialize variables
private List<QueueModel> queueHistory;
private RecyclerView recyclerView;
private LinearLayoutManager layoutManager;
private HistoryAdapter adapter;
private QueueService queueService;
private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_history);
        overridePendingTransition(1, 1);

        //Assign variables
        queueService = new QueueService(this);
        sessionManager = new SessionManager(this);

        initData();
    }

    private void initData() {
        queueService.GetUserQueueHistory(sessionManager.getSessionUsername(), new QueueService.GetUserQueueHistoryResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(QueueHistoryActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<QueueModel> queueList) {
                queueHistory = queueList;

                initRecyclerView();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.RecyclerView_QueueHistory);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryAdapter(queueHistory,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}