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
import com.doozycod.axs.Database.Entities.ReasonEntity;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ReasonDAO_Impl implements ReasonDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ReasonEntity> __insertionAdapterOfReasonEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllReasons;

  public ReasonDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReasonEntity = new EntityInsertionAdapter<ReasonEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `reasonTable` (`id`,`reasonId`,`statusId`,`reasonName`,`reasonRule`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ReasonEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getReasonId());
        stmt.bindLong(3, value.getStatusId());
        if (value.getReasonName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getReasonName());
        }
        if (value.getReasonRule() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getReasonRule());
        }
      }
    };
    this.__preparedStmtOfDeleteAllReasons = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM reasonTable";
        return _query;
      }
    };
  }

  @Override
  public void insert(final ReasonEntity reasonEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfReasonEntity.insert(reasonEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllReasons() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllReasons.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllReasons.release(_stmt);
    }
  }

  @Override
  public LiveData<List<ReasonEntity>> getReasonList(final int statusId) {
    final String _sql = "Select * from reasonTable where statusId = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, statusId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"reasonTable"}, false, new Callable<List<ReasonEntity>>() {
      @Override
      public List<ReasonEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfReasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonId");
          final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
          final int _cursorIndexOfReasonName = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonName");
          final int _cursorIndexOfReasonRule = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonRule");
          final List<ReasonEntity> _result = new ArrayList<ReasonEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ReasonEntity _item;
            final int _tmpReasonId;
            _tmpReasonId = _cursor.getInt(_cursorIndexOfReasonId);
            final int _tmpStatusId;
            _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
            final String _tmpReasonName;
            _tmpReasonName = _cursor.getString(_cursorIndexOfReasonName);
            final String _tmpReasonRule;
            _tmpReasonRule = _cursor.getString(_cursorIndexOfReasonRule);
            _item = new ReasonEntity(_tmpReasonId,_tmpStatusId,_tmpReasonName,_tmpReasonRule);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
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

  @Override
  public ReasonEntity getReason(final int reasonId) {
    final String _sql = "Select * from reasonTable where reasonId = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, reasonId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfReasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonId");
      final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
      final int _cursorIndexOfReasonName = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonName");
      final int _cursorIndexOfReasonRule = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonRule");
      final ReasonEntity _result;
      if(_cursor.moveToFirst()) {
        final int _tmpReasonId;
        _tmpReasonId = _cursor.getInt(_cursorIndexOfReasonId);
        final int _tmpStatusId;
        _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
        final String _tmpReasonName;
        _tmpReasonName = _cursor.getString(_cursorIndexOfReasonName);
        final String _tmpReasonRule;
        _tmpReasonRule = _cursor.getString(_cursorIndexOfReasonRule);
        _result = new ReasonEntity(_tmpReasonId,_tmpStatusId,_tmpReasonName,_tmpReasonRule);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
