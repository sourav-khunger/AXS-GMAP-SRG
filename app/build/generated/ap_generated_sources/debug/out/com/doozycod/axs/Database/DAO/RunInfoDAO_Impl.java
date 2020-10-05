package com.doozycod.axs.Database.DAO;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.doozycod.axs.Database.Entities.RunInfoEntity;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RunInfoDAO_Impl implements RunInfoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RunInfoEntity> __insertionAdapterOfRunInfoEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllRunList;

  public RunInfoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRunInfoEntity = new EntityInsertionAdapter<RunInfoEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `runInfoTable` (`batchId`,`parcelCounts`,`routeStarted`,`runNo`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RunInfoEntity value) {
        if (value.getBatchId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getBatchId());
        }
        stmt.bindLong(2, value.getParcelCounts());
        stmt.bindLong(3, value.getRouteStarted());
        stmt.bindLong(4, value.getRunNo());
      }
    };
    this.__preparedStmtOfDeleteAllRunList = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM runInfoTable";
        return _query;
      }
    };
  }

  @Override
  public void insert(final RunInfoEntity... runInfoEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRunInfoEntity.insert(runInfoEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllRunList() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllRunList.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllRunList.release(_stmt);
    }
  }

  @Override
  public LiveData<List<RunInfoEntity>> getRunInfoList() {
    final String _sql = "Select * from runInfoTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"runInfoTable"}, false, new Callable<List<RunInfoEntity>>() {
      @Override
      public List<RunInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfParcelCounts = CursorUtil.getColumnIndexOrThrow(_cursor, "parcelCounts");
          final int _cursorIndexOfRouteStarted = CursorUtil.getColumnIndexOrThrow(_cursor, "routeStarted");
          final int _cursorIndexOfRunNo = CursorUtil.getColumnIndexOrThrow(_cursor, "runNo");
          final List<RunInfoEntity> _result = new ArrayList<RunInfoEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RunInfoEntity _item;
            final String _tmpBatchId;
            _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            final int _tmpParcelCounts;
            _tmpParcelCounts = _cursor.getInt(_cursorIndexOfParcelCounts);
            final int _tmpRouteStarted;
            _tmpRouteStarted = _cursor.getInt(_cursorIndexOfRouteStarted);
            final int _tmpRunNo;
            _tmpRunNo = _cursor.getInt(_cursorIndexOfRunNo);
            _item = new RunInfoEntity(_tmpBatchId,_tmpParcelCounts,_tmpRouteStarted,_tmpRunNo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
