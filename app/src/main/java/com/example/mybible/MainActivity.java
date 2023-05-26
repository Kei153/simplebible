package com.example.mybible;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybible.api.BibleVO;
import com.example.mybible.databinding.ActivityMainBinding;
import com.example.mybible.db.BibleDataEntity;
import com.example.mybible.db.MemoDataEntity;
import com.example.mybible.db.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
String []doc = {"mat", "mark", "luke", "john"};
List<Integer> chapter = new ArrayList<Integer>();
Context context = this;
List<BibleDataEntity> dbData;
MemoDataEntity memoDBData;
RoomDB database;

ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerDoc.setAdapter(adapter);
        //test
        binding.spinnerDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.docExam.setText(doc[i]);
                chapter = setChapterNumber(doc[i]);
                setChapterSpinner(chapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                binding.docExam.setText("");
            }
        });
    }

    private void setChapterSpinner(List<Integer> chapter) {
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item, chapter);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerChapter.setAdapter(adapter1);

        binding.spinnerChapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.chapterExam.setText(chapter.get(i).toString());
                getBibleData();
                getMemoData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                binding.chapterExam.setText("");
            }
        });
    }

    private void getMemoData() {
        String fixDoc = binding.docExam.getText().toString();
        String fixChapter = binding.chapterExam.getText().toString();

        //Memo check
        MemoData memoData = new MemoData(this);
        memoDBData = memoData.getMemoData(fixDoc, fixChapter);

        if(memoDBData == null){
          MemoDataEntity memoDataEntity = new MemoDataEntity();
          memoDataEntity.setDoc(fixDoc);
          memoDataEntity.setChapter(fixChapter);
          memoDataEntity.setMemo("");

          database = RoomDB.getInstance(context);
          database.memoDataDao().insert(memoDataEntity);

          memoDBData = memoData.getMemoData(fixDoc, fixChapter);
        }
        setMemoDataInView(memoDBData);
    }

    private void setMemoDataInView(MemoDataEntity memoDBData) {
        binding.editView.setText(memoDBData.getMemo().toString());

        binding.editView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //입력해 변화가 생기기 전에 처리
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //변화와 동시에 처리
                String str = binding.editView.getText().toString();
                database = RoomDB.getInstance(context);
                database.memoDataDao().update(str ,memoDBData.getId());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //입력이 끝났을때 처리
            }
        });
    }

    public void getBibleData(){
        String fixDoc = binding.docExam.getText().toString();
        String fixChapter = binding.chapterExam.getText().toString();

        //DBcheck
        BibleData bibleData = new BibleData(context);
        dbData = bibleData.getBibleData(fixDoc, fixChapter);

        if(dbData == null || dbData.size() == 0){
            //API호출
            APIData apiData = new APIData(fixDoc, fixChapter, context);
            apiData.getApiData();
            return;
        }
        setBibleInView(dbData);//UI업데이트
    }


    private void setBibleInView(List<BibleDataEntity> dbData) {
        binding.bibleLayout.removeAllViews();

        for(BibleDataEntity entity : dbData) {
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setText(entity.getVerse() + entity.getMessage());
            textView.setTextSize(20);
            textView.setTag(entity);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BibleDataEntity entity = (BibleDataEntity) view.getTag();
                    if(entity.isActive()){
                        entity.setActive(false);
                    }else{
                        entity.setActive(true);
                    }
                    database = RoomDB.getInstance(context);
                    database.bibleDataDao().update(entity.isActive(), entity.getId());
                    setTextColor(entity, (TextView) view);
                }
            });

            setTextColor(entity, textView);
            binding.bibleLayout.addView(textView);
        }
        }

    private void setTextColor(BibleDataEntity entity, TextView textView) {
        if(entity.isActive()){
            textView.setTextColor(Color.parseColor("#AAFF5577"));
        }else{
            textView.setTextColor(Color.parseColor("#88000000"));
        }
    }

    private List<Integer> setChapterNumber(String s) {
        List<Integer> chapter = new ArrayList<Integer>();

        if (s == doc[0]) {
            for (int i = 1; i <= 28; i++) {
                chapter.add(i);
            }
        } else if (s == doc[1]) {
            for (int i = 1; i <= 16; i++) {
                chapter.add(i);
            }
        } else if (s == doc[2]) {
            for (int i = 1; i <= 24; i++) {
                chapter.add(i);
            }
        } else if (s == doc[3]) {
            for (int i = 1; i <= 21; i++) {
                chapter.add(i);
            }
        }
        return chapter;
    }

    public void errorMessage() {
        Toast.makeText(this, "데이트 업로드 실패", Toast.LENGTH_SHORT).show();
    }
}
    

