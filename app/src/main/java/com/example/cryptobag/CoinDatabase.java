package com.example.cryptobag;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cryptobag.Entities.Coin;

@Database(entities = {Coin.class}, version = 1)
public abstract class CoinDatabase extends RoomDatabase {
    public abstract CoinDao coinDao();
}