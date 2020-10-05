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
import com.doozycod.axs.Database.Entities.StatusEntity;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class StatusDAO_Impl implements StatusDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StatusEntity> __insertionAdapterOfStatusEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllStatuses;

  public StatusDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStatusEntity = new EntityInsertionAdapter<StatusEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `statusTable` (`id`,`statusId`,`statusName`,`shipmentType`,`statusRule`,`isException`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, StatusEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getStatusId());
        if (value.getStatusName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getStatusName());
        }
        if (value.getShipmentType() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getShipmentType());
        }
        if (value.getStatusRule() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getStatusRule());
        }
        stmt.bindLong(6, value.getIsException());
      }
    };
    this.__preparedStmtOfDeleteAllStatuses = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM statusTable";
        return _query;
      }
    };
  }

  @Override
  public void insert(final StatusEntity statusEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfStatusEntity.insert(statusEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllStatuses() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllStatuses.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllStatuses.release(_stmt);
    }
  }

  @Override
  public LiveData<List<StatusEntity>> getStatusList(final String shipmentType, final int isEx) {
    final String _sql = "Select * from statusTable where shipmentType = ? and isException = ? order by id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (shipmentType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, shipmentType);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, isEx);
    return __db.getInvalidationTracker().createLiveData(new String[]{"statusTable"}, false, new Callable<List<StatusEntity>>() {
      @Override
      public List<StatusEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
          final int _cursorIndexOfStatusName = CursorUtil.getColumnIndexOrThrow(_cursor, "statusName");
          final int _cursorIndexOfShipmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "shipmentType");
          final int _cursorIndexOfStatusRule = CursorUtil.getColumnIndexOrThrow(_cursor, "statusRule");
          final int _cursorIndexOfIsException = CursorUtil.getColumnIndexOrThrow(_cursor, "isException");
          final List<StatusEntity> _result = new ArrayList<StatusEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final StatusEntity _item;
            final int _tmpStatusId;
            _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
            final String _tmpStatusName;
            _tmpStatusName = _cursor.getString(_cursorIndexOfStatusName);
            final String _tmpShipmentType;
            _tmpShipmentType = _cursor.getString(_cursorIndexOfShipmentType);
            final String _tmpStatusRule;
            _tmpStatusRule = _cursor.getString(_cursorIndexOfStatusRule);
            final int _tmpIsException;
            _tmpIsException = _cursor.getInt(_cursorIndexOfIsException);
            _item = new StatusEntity(_tmpStatusId,_tmpStatusName,_tmpShipmentType,_tmpStatusRule,_tmpIsException);
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
  public StatusEntity getStatus(final int statusId) {
    final String _sql = "Select * from statusTable where statusId = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, statusId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
      final int _cursorIndexOfStatusName = CursorUtil.getColumnIndexOrThrow(_cursor, "statusName");
      final int _cursorIndexOfShipmentType = CursorUtil.getColumnIndexOrThrow(_cursor, "shipmentType");
      final int _cursorIndexOfStatusRule = CursorUtil.getColumnIndexOrThrow(_cursor, "statusRule");
      final int _cursorIndexOfIsException = CursorUtil.getColumnIndexOrThrow(_cursor, "isException");
      final StatusEntity _result;
      if(_cursor.moveToFirst()) {
        final int _tmpStatusId;
        _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
        final String _tmpStatusName;
        _tmpStatusName = _cursor.getString(_cursorIndexOfStatusName);
        final String _tmpShipmentType;
        _tmpShipmentType = _cursor.getString(_cursorIndexOfShipmentType);
        final String _tmpStatusRule;
        _tmpStatusRule = _cursor.getString(_cursorIndexOfStatusRule);
        final int _tmpIsException;
        _tmpIsException = _cursor.getInt(_cursorIndexOfIsException);
        _result = new StatusEntity(_tmpStatusId,_tmpStatusName,_tmpShipmentType,_tmpStatusRule,_tmpIsException);
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
