package com.allandroidprojects.sampleecomapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CartDoa {
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 void insert(CartDatabase cartDatabase);
 @Query("SELECT * FROM cartlist_table")
 List<CartDatabase> getallCartDatabase();
 @Update
 void update(CartDatabase cartDatabase);
 @Delete
 void delete(CartDatabase cartDatabase);




}
