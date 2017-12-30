package com.moymac.meritapp.RoomDB.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.moymac.meritapp.RoomDB.Entities.InputEntity;

import java.util.List;

/**
 * Created by moymac on 12/24/17.
 */
@Dao
public interface InputDao {
    @Query("SELECT * FROM Input")
    List<InputEntity> getAll();
    @Insert
    void insertAll(InputEntity... inputEntities);

    @Update
    void update(InputEntity inputEntity);

    @Delete
    void delete(InputEntity inputEntity);
}
