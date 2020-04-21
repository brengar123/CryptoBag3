package com.example.cryptobag;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cryptobag.Entities.Coin;

import java.util.List;

@Dao
public interface CoinDao {

    @Query("SELECT * FROM coin")
    List<Coin> getCoins();

    @Query("SELECT * FROM coin WHERE id == :coinId")
    Coin getCoin(String coinId);

    @Insert
    void insertAll(Coin... coins);

    @Delete
    void deleteAll(Coin... coins);
}
