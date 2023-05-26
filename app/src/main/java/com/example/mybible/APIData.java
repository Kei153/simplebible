package com.example.mybible;

import android.content.Context;
import android.util.Log;

import com.example.mybible.api.APIClient;
import com.example.mybible.api.APIService;
import com.example.mybible.api.BibleVO;
import com.example.mybible.db.BibleDataEntity;
import com.example.mybible.db.RoomDB;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIData {
    String fixDoc;
    String fixChapter;
    Context context;

    APIService apiService;
    RoomDB database;


    public APIData(String fixDoc, String fixChapter, Context context) {
        this.fixDoc = fixDoc;
        this.fixChapter = fixChapter;
        this.context = context;
    }

    public void getApiData() {
        apiService = APIClient.getClient("https://yesu.io/").create(APIService.class);

        //통신 요청
        Call<List<BibleVO>> call = apiService.doGetJsonData("kor", fixDoc,
                fixChapter + ":1", fixChapter + ":80");

        //응답 콜백 구현
        call.enqueue(new Callback<List<BibleVO>>() {
            @Override
            public void onResponse(Call<List<BibleVO>> call, Response<List<BibleVO>> response) {
                List<BibleVO> resource = response.body();

                if (response.isSuccessful()) {
                    setAPIInDB(resource);
                }
            }

            @Override
            public void onFailure(Call<List<BibleVO>> call, Throwable t) {
               //통신실패 메세지 띄우기
                if(context instanceof MainActivity){
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.errorMessage();
                }
            }
        });
    }

    private void setAPIInDB(List<BibleVO> resource) {
        database = RoomDB.getInstance(context);

        for (BibleVO bibleVO : resource) {
            database.bibleDataDao().insert(bibleVO.setEntity(fixDoc));
        }

        if(context instanceof MainActivity){
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.getBibleData();
        }
    }
}
