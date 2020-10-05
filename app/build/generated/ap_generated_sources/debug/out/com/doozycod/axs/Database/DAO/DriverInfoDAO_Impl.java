package com.doozycod.axs.Database.DAO;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.doozycod.axs.Database.Entities.DriverInfoEntity;
import com.doozycod.axs.POJO.AllowedCompanyInfo;
import com.doozycod.axs.Utils.Converters;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DriverInfoDAO_Impl implements DriverInfoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DriverInfoEntity> __insertionAdapterOfDriverInfoEntity;

  private final EntityDeletionOrUpdateAdapter<DriverInfoEntity> __deletionAdapterOfDriverInfoEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public DriverInfoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDriverInfoEntity = new EntityInsertionAdapter<DriverInfoEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `driverInfoTable` (`imei`,`firstName`,`lastName`,`onDuty`,`companyId`,`companyName`,`enableGPS`,`allowedCompanies`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DriverInfoEntity value) {
        if (value.getImei() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getImei());
        }
        if (value.getFirstName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLastName());
        }
        stmt.bindLong(4, value.getOnDuty());
        stmt.bindLong(5, value.getCompanyId());
        if (value.getCompanyName() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCompanyName());
        }
        stmt.bindLong(7, value.getEnableGPS());
        final String _tmp;
        _tmp = Converters.fromArrayList(value.getListOfAllowedCompanyInfo());
        if (_tmp == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp);
        }
      }
    };
    this.__deletionAdapterOfDriverInfoEntity = new EntityDeletionOrUpdateAdapter<DriverInfoEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `driverInfoTable` WHERE `imei` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DriverInfoEntity value) {
        if (value.getImei() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getImei());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM driverInfoTable";
        return _query;
      }
    };
  }

  @Override
  public void insert(final DriverInfoEntity driverInfoEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfDriverInfoEntity.insert(driverInfoEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteDriverInfos(final DriverInfoEntity... driverInfoEntities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfDriverInfoEntity.handleMultiple(driverInfoEntities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<DriverInfoEntity>> getDriverInfo() {
    final String _sql = "Select * from driverInfoTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"driverInfoTable"}, false, new Callable<List<DriverInfoEntity>>() {
      @Override
      public List<DriverInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfImei = CursorUtil.getColumnIndexOrThrow(_cursor, "imei");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
          final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
          final int _cursorIndexOfOnDuty = CursorUtil.getColumnIndexOrThrow(_cursor, "onDuty");
          final int _cursorIndexOfCompanyId = CursorUtil.getColumnIndexOrThrow(_cursor, "companyId");
          final int _cursorIndexOfCompanyName = CursorUtil.getColumnIndexOrThrow(_cursor, "companyName");
          final int _cursorIndexOfEnableGPS = CursorUtil.getColumnIndexOrThrow(_cursor, "enableGPS");
          final int _cursorIndexOfListOfAllowedCompanyInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "allowedCompanies");
          final List<DriverInfoEntity> _result = new ArrayList<DriverInfoEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DriverInfoEntity _item;
            final String _tmpImei;
            _tmpImei = _cursor.getString(_cursorIndexOfImei);
            final String _tmpFirstName;
            _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            final String _tmpLastName;
            _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
            final int _tmpOnDuty;
            _tmpOnDuty = _cursor.getInt(_cursorIndexOfOnDuty);
            final int _tmpCompanyId;
            _tmpCompanyId = _cursor.getInt(_cursorIndexOfCompanyId);
            final String _tmpCompanyName;
            _tmpCompanyName = _cursor.getString(_cursorIndexOfCompanyName);
            final int _tmpEnableGPS;
            _tmpEnableGPS = _cursor.getInt(_cursorIndexOfEnableGPS);
            final ArrayList<AllowedCompanyInfo> _tmpListOfAllowedCompanyInfo;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfListOfAllowedCompanyInfo);
            _tmpListOfAllowedCompanyInfo = Converters.fromString(_tmp);
            _item = new DriverInfoEntity(_tmpImei,_tmpFirstName,_tmpLastName,_tmpOnDuty,_tmpCompanyId,_tmpCompanyName,_tmpEnableGPS,_tmpListOfAllowedCompanyInfo);
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
  public List<DriverInfoEntity> getSingleDriverInfo() {
    final String _sql = "Select * from driverInfoTable ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfImei = CursorUtil.getColumnIndexOrThrow(_cursor, "imei");
      final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
      final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
      final int _cursorIndexOfOnDuty = CursorUtil.getColumnIndexOrThrow(_cursor, "onDuty");
      final int _cursorIndexOfCompanyId = CursorUtil.getColumnIndexOrThrow(_cursor, "companyId");
      final int _cursorIndexOfCompanyName = CursorUtil.getColumnIndexOrThrow(_cursor, "companyName");
      final int _cursorIndexOfEnableGPS = CursorUtil.getColumnIndexOrThrow(_cursor, "enableGPS");
      final int _cursorIndexOfListOfAllowedCompanyInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "allowedCompanies");
      final List<DriverInfoEntity> _result = new ArrayList<DriverInfoEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DriverInfoEntity _item;
        final String _tmpImei;
        _tmpImei = _cursor.getString(_cursorIndexOfImei);
        final String _tmpFirstName;
        _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        final String _tmpLastName;
        _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        final int _tmpOnDuty;
        _tmpOnDuty = _cursor.getInt(_cursorIndexOfOnDuty);
        final int _tmpCompanyId;
        _tmpCompanyId = _cursor.getInt(_cursorIndexOfCompanyId);
        final String _tmpCompanyName;
        _tmpCompanyName = _cursor.getString(_cursorIndexOfCompanyName);
        final int _tmpEnableGPS;
        _tmpEnableGPS = _cursor.getInt(_cursorIndexOfEnableGPS);
        final ArrayList<AllowedCompanyInfo> _tmpListOfAllowedCompanyInfo;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfListOfAllowedCompanyInfo);
        _tmpListOfAllowedCompanyInfo = Converters.fromString(_tmp);
        _item = new DriverInfoEntity(_tmpImei,_tmpFirstName,_tmpLastName,_tmpOnDuty,_tmpCompanyId,_tmpCompanyName,_tmpEnableGPS,_tmpListOfAllowedCompanyInfo);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
