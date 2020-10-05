package com.doozycod.axs.Database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.doozycod.axs.Database.DAO.DriverInfoDAO;
import com.doozycod.axs.Database.DAO.DriverInfoDAO_Impl;
import com.doozycod.axs.Database.DAO.ReasonDAO;
import com.doozycod.axs.Database.DAO.ReasonDAO_Impl;
import com.doozycod.axs.Database.DAO.RunInfoDAO;
import com.doozycod.axs.Database.DAO.RunInfoDAO_Impl;
import com.doozycod.axs.Database.DAO.StatusDAO;
import com.doozycod.axs.Database.DAO.StatusDAO_Impl;
import com.doozycod.axs.Database.DAO.TaskInfoDAO;
import com.doozycod.axs.Database.DAO.TaskInfoDAO_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class Database_Impl extends Database {
  private volatile DriverInfoDAO _driverInfoDAO;

  private volatile TaskInfoDAO _taskInfoDAO;

  private volatile RunInfoDAO _runInfoDAO;

  private volatile StatusDAO _statusDAO;

  private volatile ReasonDAO _reasonDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `driverInfoTable` (`imei` TEXT NOT NULL, `firstName` TEXT, `lastName` TEXT, `onDuty` INTEGER NOT NULL, `companyId` INTEGER NOT NULL, `companyName` TEXT, `enableGPS` INTEGER NOT NULL, `allowedCompanies` TEXT, PRIMARY KEY(`imei`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `taskInfoTable` (`taskId` INTEGER NOT NULL, `taskType` TEXT, `appId` INTEGER NOT NULL, `seqNo` INTEGER NOT NULL, `batchId` TEXT, `runNo` INTEGER NOT NULL, `name` TEXT, `address` TEXT, `apptNo` TEXT, `postalCode` TEXT, `city` TEXT, `email` TEXT, `phoneNo` TEXT, `latitude` TEXT, `longitude` TEXT, `barcode` TEXT, `agent` TEXT, `manifest` TEXT, `reffNo` TEXT, `quantity` INTEGER NOT NULL, `weight` TEXT, `amount` TEXT, `currency` TEXT, `serviceLevel` TEXT, `instructions` TEXT, `workStatus` TEXT, `arrivalTime` TEXT, `completeTime` TEXT, `dataId` INTEGER NOT NULL, `stopId` INTEGER NOT NULL, `dataEntered` TEXT, `statusId` INTEGER NOT NULL, `reasonId` INTEGER NOT NULL, `qtyEntered` INTEGER NOT NULL, `weightEntered` REAL NOT NULL, `imageTaken` INTEGER NOT NULL, `imagePath` TEXT, `areaType` TEXT, `driverComment` TEXT, `driverNotice` TEXT, `cashCollect` TEXT, `consolicatedId` INTEGER NOT NULL, `waitingTime` REAL NOT NULL, `cod` REAL NOT NULL, `disAmt` TEXT, `accessorial` TEXT, `codCurrency` TEXT, `mileage` REAL NOT NULL, `locationKey` TEXT, `recordStatus` INTEGER NOT NULL, `signature` TEXT, `signatureName` TEXT, `signatureTime` TEXT, PRIMARY KEY(`taskId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `runInfoTable` (`batchId` TEXT NOT NULL, `parcelCounts` INTEGER NOT NULL, `routeStarted` INTEGER NOT NULL, `runNo` INTEGER NOT NULL, PRIMARY KEY(`batchId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `statusTable` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `statusId` INTEGER NOT NULL, `statusName` TEXT, `shipmentType` TEXT, `statusRule` TEXT, `isException` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `reasonTable` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `reasonId` INTEGER NOT NULL, `statusId` INTEGER NOT NULL, `reasonName` TEXT, `reasonRule` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'fcc5cd80954d8948a23d1cc33f28c9a3')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `driverInfoTable`");
        _db.execSQL("DROP TABLE IF EXISTS `taskInfoTable`");
        _db.execSQL("DROP TABLE IF EXISTS `runInfoTable`");
        _db.execSQL("DROP TABLE IF EXISTS `statusTable`");
        _db.execSQL("DROP TABLE IF EXISTS `reasonTable`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsDriverInfoTable = new HashMap<String, TableInfo.Column>(8);
        _columnsDriverInfoTable.put("imei", new TableInfo.Column("imei", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDriverInfoTable.put("firstName", new TableInfo.Column("firstName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDriverInfoTable.put("lastName", new TableInfo.Column("lastName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDriverInfoTable.put("onDuty", new TableInfo.Column("onDuty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDriverInfoTable.put("companyId", new TableInfo.Column("companyId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDriverInfoTable.put("companyName", new TableInfo.Column("companyName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDriverInfoTable.put("enableGPS", new TableInfo.Column("enableGPS", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDriverInfoTable.put("allowedCompanies", new TableInfo.Column("allowedCompanies", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDriverInfoTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDriverInfoTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDriverInfoTable = new TableInfo("driverInfoTable", _columnsDriverInfoTable, _foreignKeysDriverInfoTable, _indicesDriverInfoTable);
        final TableInfo _existingDriverInfoTable = TableInfo.read(_db, "driverInfoTable");
        if (! _infoDriverInfoTable.equals(_existingDriverInfoTable)) {
          return new RoomOpenHelper.ValidationResult(false, "driverInfoTable(com.doozycod.axs.Database.Entities.DriverInfoEntity).\n"
                  + " Expected:\n" + _infoDriverInfoTable + "\n"
                  + " Found:\n" + _existingDriverInfoTable);
        }
        final HashMap<String, TableInfo.Column> _columnsTaskInfoTable = new HashMap<String, TableInfo.Column>(53);
        _columnsTaskInfoTable.put("taskId", new TableInfo.Column("taskId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("taskType", new TableInfo.Column("taskType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("appId", new TableInfo.Column("appId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("seqNo", new TableInfo.Column("seqNo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("batchId", new TableInfo.Column("batchId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("runNo", new TableInfo.Column("runNo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("apptNo", new TableInfo.Column("apptNo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("postalCode", new TableInfo.Column("postalCode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("city", new TableInfo.Column("city", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("phoneNo", new TableInfo.Column("phoneNo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("latitude", new TableInfo.Column("latitude", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("longitude", new TableInfo.Column("longitude", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("barcode", new TableInfo.Column("barcode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("agent", new TableInfo.Column("agent", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("manifest", new TableInfo.Column("manifest", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("reffNo", new TableInfo.Column("reffNo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("weight", new TableInfo.Column("weight", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("amount", new TableInfo.Column("amount", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("currency", new TableInfo.Column("currency", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("serviceLevel", new TableInfo.Column("serviceLevel", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("instructions", new TableInfo.Column("instructions", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("workStatus", new TableInfo.Column("workStatus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("arrivalTime", new TableInfo.Column("arrivalTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("completeTime", new TableInfo.Column("completeTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("dataId", new TableInfo.Column("dataId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("stopId", new TableInfo.Column("stopId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("dataEntered", new TableInfo.Column("dataEntered", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("statusId", new TableInfo.Column("statusId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("reasonId", new TableInfo.Column("reasonId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("qtyEntered", new TableInfo.Column("qtyEntered", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("weightEntered", new TableInfo.Column("weightEntered", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("imageTaken", new TableInfo.Column("imageTaken", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("imagePath", new TableInfo.Column("imagePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("areaType", new TableInfo.Column("areaType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("driverComment", new TableInfo.Column("driverComment", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("driverNotice", new TableInfo.Column("driverNotice", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("cashCollect", new TableInfo.Column("cashCollect", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("consolicatedId", new TableInfo.Column("consolicatedId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("waitingTime", new TableInfo.Column("waitingTime", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("cod", new TableInfo.Column("cod", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("disAmt", new TableInfo.Column("disAmt", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("accessorial", new TableInfo.Column("accessorial", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("codCurrency", new TableInfo.Column("codCurrency", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("mileage", new TableInfo.Column("mileage", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("locationKey", new TableInfo.Column("locationKey", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("recordStatus", new TableInfo.Column("recordStatus", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("signature", new TableInfo.Column("signature", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("signatureName", new TableInfo.Column("signatureName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskInfoTable.put("signatureTime", new TableInfo.Column("signatureTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTaskInfoTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTaskInfoTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTaskInfoTable = new TableInfo("taskInfoTable", _columnsTaskInfoTable, _foreignKeysTaskInfoTable, _indicesTaskInfoTable);
        final TableInfo _existingTaskInfoTable = TableInfo.read(_db, "taskInfoTable");
        if (! _infoTaskInfoTable.equals(_existingTaskInfoTable)) {
          return new RoomOpenHelper.ValidationResult(false, "taskInfoTable(com.doozycod.axs.Database.Entities.TaskInfoEntity).\n"
                  + " Expected:\n" + _infoTaskInfoTable + "\n"
                  + " Found:\n" + _existingTaskInfoTable);
        }
        final HashMap<String, TableInfo.Column> _columnsRunInfoTable = new HashMap<String, TableInfo.Column>(4);
        _columnsRunInfoTable.put("batchId", new TableInfo.Column("batchId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRunInfoTable.put("parcelCounts", new TableInfo.Column("parcelCounts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRunInfoTable.put("routeStarted", new TableInfo.Column("routeStarted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRunInfoTable.put("runNo", new TableInfo.Column("runNo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRunInfoTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRunInfoTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRunInfoTable = new TableInfo("runInfoTable", _columnsRunInfoTable, _foreignKeysRunInfoTable, _indicesRunInfoTable);
        final TableInfo _existingRunInfoTable = TableInfo.read(_db, "runInfoTable");
        if (! _infoRunInfoTable.equals(_existingRunInfoTable)) {
          return new RoomOpenHelper.ValidationResult(false, "runInfoTable(com.doozycod.axs.Database.Entities.RunInfoEntity).\n"
                  + " Expected:\n" + _infoRunInfoTable + "\n"
                  + " Found:\n" + _existingRunInfoTable);
        }
        final HashMap<String, TableInfo.Column> _columnsStatusTable = new HashMap<String, TableInfo.Column>(6);
        _columnsStatusTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatusTable.put("statusId", new TableInfo.Column("statusId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatusTable.put("statusName", new TableInfo.Column("statusName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatusTable.put("shipmentType", new TableInfo.Column("shipmentType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatusTable.put("statusRule", new TableInfo.Column("statusRule", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStatusTable.put("isException", new TableInfo.Column("isException", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStatusTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStatusTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStatusTable = new TableInfo("statusTable", _columnsStatusTable, _foreignKeysStatusTable, _indicesStatusTable);
        final TableInfo _existingStatusTable = TableInfo.read(_db, "statusTable");
        if (! _infoStatusTable.equals(_existingStatusTable)) {
          return new RoomOpenHelper.ValidationResult(false, "statusTable(com.doozycod.axs.Database.Entities.StatusEntity).\n"
                  + " Expected:\n" + _infoStatusTable + "\n"
                  + " Found:\n" + _existingStatusTable);
        }
        final HashMap<String, TableInfo.Column> _columnsReasonTable = new HashMap<String, TableInfo.Column>(5);
        _columnsReasonTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReasonTable.put("reasonId", new TableInfo.Column("reasonId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReasonTable.put("statusId", new TableInfo.Column("statusId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReasonTable.put("reasonName", new TableInfo.Column("reasonName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReasonTable.put("reasonRule", new TableInfo.Column("reasonRule", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReasonTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReasonTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReasonTable = new TableInfo("reasonTable", _columnsReasonTable, _foreignKeysReasonTable, _indicesReasonTable);
        final TableInfo _existingReasonTable = TableInfo.read(_db, "reasonTable");
        if (! _infoReasonTable.equals(_existingReasonTable)) {
          return new RoomOpenHelper.ValidationResult(false, "reasonTable(com.doozycod.axs.Database.Entities.ReasonEntity).\n"
                  + " Expected:\n" + _infoReasonTable + "\n"
                  + " Found:\n" + _existingReasonTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "fcc5cd80954d8948a23d1cc33f28c9a3", "ca51e508b12b390832fcc567aa89459f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "driverInfoTable","taskInfoTable","runInfoTable","statusTable","reasonTable");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `driverInfoTable`");
      _db.execSQL("DELETE FROM `taskInfoTable`");
      _db.execSQL("DELETE FROM `runInfoTable`");
      _db.execSQL("DELETE FROM `statusTable`");
      _db.execSQL("DELETE FROM `reasonTable`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public DriverInfoDAO driverInfoDAO() {
    if (_driverInfoDAO != null) {
      return _driverInfoDAO;
    } else {
      synchronized(this) {
        if(_driverInfoDAO == null) {
          _driverInfoDAO = new DriverInfoDAO_Impl(this);
        }
        return _driverInfoDAO;
      }
    }
  }

  @Override
  public TaskInfoDAO taskInfoDAO() {
    if (_taskInfoDAO != null) {
      return _taskInfoDAO;
    } else {
      synchronized(this) {
        if(_taskInfoDAO == null) {
          _taskInfoDAO = new TaskInfoDAO_Impl(this);
        }
        return _taskInfoDAO;
      }
    }
  }

  @Override
  public RunInfoDAO runInfoDAO() {
    if (_runInfoDAO != null) {
      return _runInfoDAO;
    } else {
      synchronized(this) {
        if(_runInfoDAO == null) {
          _runInfoDAO = new RunInfoDAO_Impl(this);
        }
        return _runInfoDAO;
      }
    }
  }

  @Override
  public StatusDAO statusDAO() {
    if (_statusDAO != null) {
      return _statusDAO;
    } else {
      synchronized(this) {
        if(_statusDAO == null) {
          _statusDAO = new StatusDAO_Impl(this);
        }
        return _statusDAO;
      }
    }
  }

  @Override
  public ReasonDAO reasonDAO() {
    if (_reasonDAO != null) {
      return _reasonDAO;
    } else {
      synchronized(this) {
        if(_reasonDAO == null) {
          _reasonDAO = new ReasonDAO_Impl(this);
        }
        return _reasonDAO;
      }
    }
  }
}
