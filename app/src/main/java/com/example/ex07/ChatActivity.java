package com.example.ex07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();

        getSupportActionBar().setTitle("채팅 : " + user.getEmail());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText contents = findViewById(R.id.edtContents);
                String strContents = contents.getText().toString();
                if(strContents.equals("")) {
                    Toast.makeText(ChatActivity.this, "내용을 입력하세요!", Toast.LENGTH_SHORT).show();
                } else {
                    // 전송
                    ChatVO vo = new ChatVO();
                    vo.setEmail(user.getEmail());
                    vo.setContents(strContents);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = sdf.format(new Date());
                    vo.setDate(strDate);
//                    System.out.println("................" + vo.toString());
                    ref = db.getReference("/chat").push();
                    vo.setKey(ref.getKey());
                    ref.setValue(vo);
                    contents.setText("");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}