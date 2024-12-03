package com.example.dictionary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etWord;
    private Button btnSearch;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWord = findViewById(R.id.et_word);
        btnSearch = findViewById(R.id.btn_search);
        tvResult = findViewById(R.id.tv_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DictionaryApi api = retrofit.create(DictionaryApi.class);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = etWord.getText().toString().trim();
                if (word.isEmpty()) {
                    tvResult.setText("Please enter a word");
                    return;
                }

                api.getDefinition(word).enqueue(new Callback<List<WordResponse>>() {
                    @Override
                    public void onResponse(Call<List<WordResponse>> call, Response<List<WordResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            StringBuilder result = new StringBuilder();
                            for (WordResponse.Meaning meaning : response.body().get(0).meanings) {
                                result.append(meaning.partOfSpeech).append(":\n");
                                for (WordResponse.Meaning.Definition definition : meaning.definitions) {
                                    result.append("- ").append(definition.definition).append("\n");
                                }
                            }
                            tvResult.setText(result.toString());
                        } else {
                            tvResult.setText("Word not found");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WordResponse>> call, Throwable t) {
                        Log.e("API_ERROR", t.getMessage());
                        tvResult.setText("Failed to fetch data");
                    }
                });
            }
        });
    }
}
