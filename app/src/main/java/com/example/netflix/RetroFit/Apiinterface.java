package com.example.netflix.RetroFit;

import static com.example.netflix.RetroFit.RetrofitClient.BASE_URL;

import com.example.netflix.Model.AllCategory;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Apiinterface {
    @GET(BASE_URL)
    Observable<List<AllCategory>> getAllCategoryMovies();
}
