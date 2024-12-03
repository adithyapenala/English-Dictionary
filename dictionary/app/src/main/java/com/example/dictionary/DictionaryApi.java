package com.example.dictionary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryApi {
    @GET("entries/en/{word}")
    Call<List<WordResponse>> getDefinition(@Path("word") String word);
}
