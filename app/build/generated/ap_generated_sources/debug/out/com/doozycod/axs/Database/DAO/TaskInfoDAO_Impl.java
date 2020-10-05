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
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskInfoDAO_Impl implements TaskInfoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TaskInfoEntity> __insertionAdapterOfTaskInfoEntity;

  private final EntityDeletionOrUpdateAdapter<TaskInfoEntity> __deletionAdapterOfTaskInfoEntity;

  private final EntityDeletionOrUpdateAdapter<TaskInfoEntity> __updateAdapterOfTaskInfoEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLocation;

  public TaskInfoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTaskInfoEntity = new EntityInsertionAdapter<TaskInfoEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `taskInfoTable` (`taskId`,`taskType`,`appId`,`seqNo`,`batchId`,`runNo`,`name`,`address`,`apptNo`,`postalCode`,`city`,`email`,`phoneNo`,`latitude`,`longitude`,`barcode`,`agent`,`manifest`,`reffNo`,`quantity`,`weight`,`amount`,`currency`,`serviceLevel`,`instructions`,`workStatus`,`arrivalTime`,`completeTime`,`dataId`,`stopId`,`dataEntered`,`statusId`,`reasonId`,`qtyEntered`,`weightEntered`,`imageTaken`,`imagePath`,`areaType`,`driverComment`,`driverNotice`,`cashCollect`,`consolicatedId`,`waitingTime`,`cod`,`disAmt`,`accessorial`,`codCurrency`,`mileage`,`locationKey`,`recordStatus`,`signature`,`signatureName`,`signatureTime`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskInfoEntity value) {
        stmt.bindLong(1, value.getTaskId());
        if (value.getTaskType() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTaskType());
        }
        stmt.bindLong(3, value.getAppId());
        stmt.bindLong(4, value.getSeqNo());
        if (value.getBatchId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getBatchId());
        }
        stmt.bindLong(6, value.getRunNo());
        if (value.getName() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getName());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getAddress());
        }
        if (value.getApptNo() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getApptNo());
        }
        if (value.getPostalCode() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getPostalCode());
        }
        if (value.city == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.city);
        }
        if (value.getEmail() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getEmail());
        }
        if (value.getPhoneNo() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getPhoneNo());
        }
        if (value.getLatitude() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getLatitude());
        }
        if (value.getLongitude() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getLongitude());
        }
        if (value.getBarcode() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getBarcode());
        }
        if (value.getAgent() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getAgent());
        }
        if (value.getManifest() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getManifest());
        }
        if (value.getReffNo() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getReffNo());
        }
        stmt.bindLong(20, value.getQuantity());
        if (value.getWeight() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getWeight());
        }
        if (value.getAmount() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getAmount());
        }
        if (value.getCurrency() == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindString(23, value.getCurrency());
        }
        if (value.getServiceLevel() == null) {
          stmt.bindNull(24);
        } else {
          stmt.bindString(24, value.getServiceLevel());
        }
        if (value.getInstructions() == null) {
          stmt.bindNull(25);
        } else {
          stmt.bindString(25, value.getInstructions());
        }
        if (value.getWorkStatus() == null) {
          stmt.bindNull(26);
        } else {
          stmt.bindString(26, value.getWorkStatus());
        }
        if (value.getArrivalTime() == null) {
          stmt.bindNull(27);
        } else {
          stmt.bindString(27, value.getArrivalTime());
        }
        if (value.getCompleteTime() == null) {
          stmt.bindNull(28);
        } else {
          stmt.bindString(28, value.getCompleteTime());
        }
        stmt.bindLong(29, value.getDataId());
        stmt.bindLong(30, value.getStopId());
        if (value.getDataEntered() == null) {
          stmt.bindNull(31);
        } else {
          stmt.bindString(31, value.getDataEntered());
        }
        stmt.bindLong(32, value.getStatusId());
        stmt.bindLong(33, value.getReasonId());
        stmt.bindLong(34, value.getQtyEntered());
        stmt.bindDouble(35, value.getWeightEntered());
        stmt.bindLong(36, value.getImageTaken());
        if (value.getImagePath() == null) {
          stmt.bindNull(37);
        } else {
          stmt.bindString(37, value.getImagePath());
        }
        if (value.getAreaType() == null) {
          stmt.bindNull(38);
        } else {
          stmt.bindString(38, value.getAreaType());
        }
        if (value.getDriverComment() == null) {
          stmt.bindNull(39);
        } else {
          stmt.bindString(39, value.getDriverComment());
        }
        if (value.getDriverNotice() == null) {
          stmt.bindNull(40);
        } else {
          stmt.bindString(40, value.getDriverNotice());
        }
        if (value.getCashCollect() == null) {
          stmt.bindNull(41);
        } else {
          stmt.bindString(41, value.getCashCollect());
        }
        stmt.bindLong(42, value.getConsolicatedId());
        stmt.bindDouble(43, value.getWaitingTime());
        stmt.bindDouble(44, value.getCod());
        if (value.getDisAmt() == null) {
          stmt.bindNull(45);
        } else {
          stmt.bindString(45, value.getDisAmt());
        }
        if (value.getAccessorial() == null) {
          stmt.bindNull(46);
        } else {
          stmt.bindString(46, value.getAccessorial());
        }
        if (value.getCodCurrency() == null) {
          stmt.bindNull(47);
        } else {
          stmt.bindString(47, value.getCodCurrency());
        }
        stmt.bindDouble(48, value.getMileage());
        if (value.getLocationKey() == null) {
          stmt.bindNull(49);
        } else {
          stmt.bindString(49, value.getLocationKey());
        }
        stmt.bindLong(50, value.getRecordStatus());
        if (value.getSignature() == null) {
          stmt.bindNull(51);
        } else {
          stmt.bindString(51, value.getSignature());
        }
        if (value.getSignatureName() == null) {
          stmt.bindNull(52);
        } else {
          stmt.bindString(52, value.getSignatureName());
        }
        if (value.getSignatureTime() == null) {
          stmt.bindNull(53);
        } else {
          stmt.bindString(53, value.getSignatureTime());
        }
      }
    };
    this.__deletionAdapterOfTaskInfoEntity = new EntityDeletionOrUpdateAdapter<TaskInfoEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `taskInfoTable` WHERE `taskId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskInfoEntity value) {
        stmt.bindLong(1, value.getTaskId());
      }
    };
    this.__updateAdapterOfTaskInfoEntity = new EntityDeletionOrUpdateAdapter<TaskInfoEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `taskInfoTable` SET `taskId` = ?,`taskType` = ?,`appId` = ?,`seqNo` = ?,`batchId` = ?,`runNo` = ?,`name` = ?,`address` = ?,`apptNo` = ?,`postalCode` = ?,`city` = ?,`email` = ?,`phoneNo` = ?,`latitude` = ?,`longitude` = ?,`barcode` = ?,`agent` = ?,`manifest` = ?,`reffNo` = ?,`quantity` = ?,`weight` = ?,`amount` = ?,`currency` = ?,`serviceLevel` = ?,`instructions` = ?,`workStatus` = ?,`arrivalTime` = ?,`completeTime` = ?,`dataId` = ?,`stopId` = ?,`dataEntered` = ?,`statusId` = ?,`reasonId` = ?,`qtyEntered` = ?,`weightEntered` = ?,`imageTaken` = ?,`imagePath` = ?,`areaType` = ?,`driverComment` = ?,`driverNotice` = ?,`cashCollect` = ?,`consolicatedId` = ?,`waitingTime` = ?,`cod` = ?,`disAmt` = ?,`accessorial` = ?,`codCurrency` = ?,`mileage` = ?,`locationKey` = ?,`recordStatus` = ?,`signature` = ?,`signatureName` = ?,`signatureTime` = ? WHERE `taskId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskInfoEntity value) {
        stmt.bindLong(1, value.getTaskId());
        if (value.getTaskType() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTaskType());
        }
        stmt.bindLong(3, value.getAppId());
        stmt.bindLong(4, value.getSeqNo());
        if (value.getBatchId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getBatchId());
        }
        stmt.bindLong(6, value.getRunNo());
        if (value.getName() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getName());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getAddress());
        }
        if (value.getApptNo() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getApptNo());
        }
        if (value.getPostalCode() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getPostalCode());
        }
        if (value.city == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.city);
        }
        if (value.getEmail() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getEmail());
        }
        if (value.getPhoneNo() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getPhoneNo());
        }
        if (value.getLatitude() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getLatitude());
        }
        if (value.getLongitude() == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.getLongitude());
        }
        if (value.getBarcode() == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.getBarcode());
        }
        if (value.getAgent() == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.getAgent());
        }
        if (value.getManifest() == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, value.getManifest());
        }
        if (value.getReffNo() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindString(19, value.getReffNo());
        }
        stmt.bindLong(20, value.getQuantity());
        if (value.getWeight() == null) {
          stmt.bindNull(21);
        } else {
          stmt.bindString(21, value.getWeight());
        }
        if (value.getAmount() == null) {
          stmt.bindNull(22);
        } else {
          stmt.bindString(22, value.getAmount());
        }
        if (value.getCurrency() == null) {
          stmt.bindNull(23);
        } else {
          stmt.bindString(23, value.getCurrency());
        }
        if (value.getServiceLevel() == null) {
          stmt.bindNull(24);
        } else {
          stmt.bindString(24, value.getServiceLevel());
        }
        if (value.getInstructions() == null) {
          stmt.bindNull(25);
        } else {
          stmt.bindString(25, value.getInstructions());
        }
        if (value.getWorkStatus() == null) {
          stmt.bindNull(26);
        } else {
          stmt.bindString(26, value.getWorkStatus());
        }
        if (value.getArrivalTime() == null) {
          stmt.bindNull(27);
        } else {
          stmt.bindString(27, value.getArrivalTime());
        }
        if (value.getCompleteTime() == null) {
          stmt.bindNull(28);
        } else {
          stmt.bindString(28, value.getCompleteTime());
        }
        stmt.bindLong(29, value.getDataId());
        stmt.bindLong(30, value.getStopId());
        if (value.getDataEntered() == null) {
          stmt.bindNull(31);
        } else {
          stmt.bindString(31, value.getDataEntered());
        }
        stmt.bindLong(32, value.getStatusId());
        stmt.bindLong(33, value.getReasonId());
        stmt.bindLong(34, value.getQtyEntered());
        stmt.bindDouble(35, value.getWeightEntered());
        stmt.bindLong(36, value.getImageTaken());
        if (value.getImagePath() == null) {
          stmt.bindNull(37);
        } else {
          stmt.bindString(37, value.getImagePath());
        }
        if (value.getAreaType() == null) {
          stmt.bindNull(38);
        } else {
          stmt.bindString(38, value.getAreaType());
        }
        if (value.getDriverComment() == null) {
          stmt.bindNull(39);
        } else {
          stmt.bindString(39, value.getDriverComment());
        }
        if (value.getDriverNotice() == null) {
          stmt.bindNull(40);
        } else {
          stmt.bindString(40, value.getDriverNotice());
        }
        if (value.getCashCollect() == null) {
          stmt.bindNull(41);
        } else {
          stmt.bindString(41, value.getCashCollect());
        }
        stmt.bindLong(42, value.getConsolicatedId());
        stmt.bindDouble(43, value.getWaitingTime());
        stmt.bindDouble(44, value.getCod());
        if (value.getDisAmt() == null) {
          stmt.bindNull(45);
        } else {
          stmt.bindString(45, value.getDisAmt());
        }
        if (value.getAccessorial() == null) {
          stmt.bindNull(46);
        } else {
          stmt.bindString(46, value.getAccessorial());
        }
        if (value.getCodCurrency() == null) {
          stmt.bindNull(47);
        } else {
          stmt.bindString(47, value.getCodCurrency());
        }
        stmt.bindDouble(48, value.getMileage());
        if (value.getLocationKey() == null) {
          stmt.bindNull(49);
        } else {
          stmt.bindString(49, value.getLocationKey());
        }
        stmt.bindLong(50, value.getRecordStatus());
        if (value.getSignature() == null) {
          stmt.bindNull(51);
        } else {
          stmt.bindString(51, value.getSignature());
        }
        if (value.getSignatureName() == null) {
          stmt.bindNull(52);
        } else {
          stmt.bindString(52, value.getSignatureName());
        }
        if (value.getSignatureTime() == null) {
          stmt.bindNull(53);
        } else {
          stmt.bindString(53, value.getSignatureTime());
        }
        stmt.bindLong(54, value.getTaskId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM taskInfoTable";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLocation = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE taskInfoTable SET arrivalTime = ? where locationKey = ? ";
        return _query;
      }
    };
  }

  @Override
  public void insert(final TaskInfoEntity... taskInfoEntity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTaskInfoEntity.insert(taskInfoEntity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTaskInfos(final TaskInfoEntity... taskInfoEntities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTaskInfoEntity.handleMultiple(taskInfoEntities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateTaskInfo(final TaskInfoEntity... taskInfoEntitis) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTaskInfoEntity.handleMultiple(taskInfoEntitis);
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
  public void updateLocation(final String locationKey, final String arrivalTime) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLocation.acquire();
    int _argIndex = 1;
    if (arrivalTime == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, arrivalTime);
    }
    _argIndex = 2;
    if (locationKey == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, locationKey);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateLocation.release(_stmt);
    }
  }

  @Override
  public LiveData<List<TaskInfoEntity>> getTaskInfoList() {
    final String _sql = "Select * from taskInfoTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"taskInfoTable"}, false, new Callable<List<TaskInfoEntity>>() {
      @Override
      public List<TaskInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "taskId");
          final int _cursorIndexOfTaskType = CursorUtil.getColumnIndexOrThrow(_cursor, "taskType");
          final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
          final int _cursorIndexOfSeqNo = CursorUtil.getColumnIndexOrThrow(_cursor, "seqNo");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfRunNo = CursorUtil.getColumnIndexOrThrow(_cursor, "runNo");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfApptNo = CursorUtil.getColumnIndexOrThrow(_cursor, "apptNo");
          final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhoneNo = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNo");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfAgent = CursorUtil.getColumnIndexOrThrow(_cursor, "agent");
          final int _cursorIndexOfManifest = CursorUtil.getColumnIndexOrThrow(_cursor, "manifest");
          final int _cursorIndexOfReffNo = CursorUtil.getColumnIndexOrThrow(_cursor, "reffNo");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfServiceLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceLevel");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfWorkStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "workStatus");
          final int _cursorIndexOfArrivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalTime");
          final int _cursorIndexOfCompleteTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completeTime");
          final int _cursorIndexOfDataId = CursorUtil.getColumnIndexOrThrow(_cursor, "dataId");
          final int _cursorIndexOfStopId = CursorUtil.getColumnIndexOrThrow(_cursor, "stopId");
          final int _cursorIndexOfDataEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "dataEntered");
          final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
          final int _cursorIndexOfReasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonId");
          final int _cursorIndexOfQtyEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "qtyEntered");
          final int _cursorIndexOfWeightEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "weightEntered");
          final int _cursorIndexOfImageTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "imageTaken");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfAreaType = CursorUtil.getColumnIndexOrThrow(_cursor, "areaType");
          final int _cursorIndexOfDriverComment = CursorUtil.getColumnIndexOrThrow(_cursor, "driverComment");
          final int _cursorIndexOfDriverNotice = CursorUtil.getColumnIndexOrThrow(_cursor, "driverNotice");
          final int _cursorIndexOfCashCollect = CursorUtil.getColumnIndexOrThrow(_cursor, "cashCollect");
          final int _cursorIndexOfConsolicatedId = CursorUtil.getColumnIndexOrThrow(_cursor, "consolicatedId");
          final int _cursorIndexOfWaitingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "waitingTime");
          final int _cursorIndexOfCod = CursorUtil.getColumnIndexOrThrow(_cursor, "cod");
          final int _cursorIndexOfDisAmt = CursorUtil.getColumnIndexOrThrow(_cursor, "disAmt");
          final int _cursorIndexOfAccessorial = CursorUtil.getColumnIndexOrThrow(_cursor, "accessorial");
          final int _cursorIndexOfCodCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "codCurrency");
          final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
          final int _cursorIndexOfLocationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "locationKey");
          final int _cursorIndexOfRecordStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "recordStatus");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfSignatureName = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureName");
          final int _cursorIndexOfSignatureTime = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureTime");
          final List<TaskInfoEntity> _result = new ArrayList<TaskInfoEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TaskInfoEntity _item;
            final long _tmpTaskId;
            _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
            final String _tmpTaskType;
            _tmpTaskType = _cursor.getString(_cursorIndexOfTaskType);
            final int _tmpAppId;
            _tmpAppId = _cursor.getInt(_cursorIndexOfAppId);
            final int _tmpSeqNo;
            _tmpSeqNo = _cursor.getInt(_cursorIndexOfSeqNo);
            final String _tmpBatchId;
            _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            final int _tmpRunNo;
            _tmpRunNo = _cursor.getInt(_cursorIndexOfRunNo);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpApptNo;
            _tmpApptNo = _cursor.getString(_cursorIndexOfApptNo);
            final String _tmpPostalCode;
            _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
            final String _tmpCity;
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpPhoneNo;
            _tmpPhoneNo = _cursor.getString(_cursorIndexOfPhoneNo);
            final String _tmpLatitude;
            _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
            final String _tmpLongitude;
            _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
            final String _tmpBarcode;
            _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            final String _tmpAgent;
            _tmpAgent = _cursor.getString(_cursorIndexOfAgent);
            final String _tmpManifest;
            _tmpManifest = _cursor.getString(_cursorIndexOfManifest);
            final String _tmpReffNo;
            _tmpReffNo = _cursor.getString(_cursorIndexOfReffNo);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpWeight;
            _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final String _tmpCurrency;
            _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            final String _tmpServiceLevel;
            _tmpServiceLevel = _cursor.getString(_cursorIndexOfServiceLevel);
            final String _tmpInstructions;
            _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            final String _tmpWorkStatus;
            _tmpWorkStatus = _cursor.getString(_cursorIndexOfWorkStatus);
            final String _tmpArrivalTime;
            _tmpArrivalTime = _cursor.getString(_cursorIndexOfArrivalTime);
            final String _tmpCompleteTime;
            _tmpCompleteTime = _cursor.getString(_cursorIndexOfCompleteTime);
            final long _tmpDataId;
            _tmpDataId = _cursor.getLong(_cursorIndexOfDataId);
            final long _tmpStopId;
            _tmpStopId = _cursor.getLong(_cursorIndexOfStopId);
            final String _tmpDataEntered;
            _tmpDataEntered = _cursor.getString(_cursorIndexOfDataEntered);
            final int _tmpStatusId;
            _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
            final int _tmpReasonId;
            _tmpReasonId = _cursor.getInt(_cursorIndexOfReasonId);
            final int _tmpQtyEntered;
            _tmpQtyEntered = _cursor.getInt(_cursorIndexOfQtyEntered);
            final double _tmpWeightEntered;
            _tmpWeightEntered = _cursor.getDouble(_cursorIndexOfWeightEntered);
            final int _tmpImageTaken;
            _tmpImageTaken = _cursor.getInt(_cursorIndexOfImageTaken);
            final String _tmpImagePath;
            _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            final String _tmpAreaType;
            _tmpAreaType = _cursor.getString(_cursorIndexOfAreaType);
            final String _tmpDriverComment;
            _tmpDriverComment = _cursor.getString(_cursorIndexOfDriverComment);
            final String _tmpDriverNotice;
            _tmpDriverNotice = _cursor.getString(_cursorIndexOfDriverNotice);
            final String _tmpCashCollect;
            _tmpCashCollect = _cursor.getString(_cursorIndexOfCashCollect);
            final int _tmpConsolicatedId;
            _tmpConsolicatedId = _cursor.getInt(_cursorIndexOfConsolicatedId);
            final double _tmpWaitingTime;
            _tmpWaitingTime = _cursor.getDouble(_cursorIndexOfWaitingTime);
            final double _tmpCod;
            _tmpCod = _cursor.getDouble(_cursorIndexOfCod);
            final String _tmpDisAmt;
            _tmpDisAmt = _cursor.getString(_cursorIndexOfDisAmt);
            final String _tmpAccessorial;
            _tmpAccessorial = _cursor.getString(_cursorIndexOfAccessorial);
            final String _tmpCodCurrency;
            _tmpCodCurrency = _cursor.getString(_cursorIndexOfCodCurrency);
            final double _tmpMileage;
            _tmpMileage = _cursor.getDouble(_cursorIndexOfMileage);
            _item = new TaskInfoEntity(_tmpTaskId,_tmpTaskType,_tmpAppId,_tmpName,_tmpAddress,_tmpApptNo,_tmpPostalCode,_tmpCity,_tmpEmail,_tmpPhoneNo,_tmpLatitude,_tmpLongitude,_tmpBarcode,_tmpAgent,_tmpManifest,_tmpReffNo,_tmpQuantity,_tmpWeight,_tmpAmount,_tmpCurrency,_tmpServiceLevel,_tmpInstructions,_tmpWorkStatus,_tmpArrivalTime,_tmpCompleteTime,_tmpDataId,_tmpStopId,_tmpDataEntered,_tmpStatusId,_tmpReasonId,_tmpQtyEntered,_tmpWeightEntered,_tmpImageTaken,_tmpImagePath,_tmpAreaType,_tmpDriverComment,_tmpDriverNotice,_tmpCashCollect,_tmpConsolicatedId,_tmpWaitingTime,_tmpCod,_tmpDisAmt,_tmpAccessorial,_tmpCodCurrency,_tmpMileage,_tmpSeqNo,_tmpBatchId,_tmpRunNo);
            final String _tmpLocationKey;
            _tmpLocationKey = _cursor.getString(_cursorIndexOfLocationKey);
            _item.setLocationKey(_tmpLocationKey);
            final int _tmpRecordStatus;
            _tmpRecordStatus = _cursor.getInt(_cursorIndexOfRecordStatus);
            _item.setRecordStatus(_tmpRecordStatus);
            final String _tmpSignature;
            _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
            _item.setSignature(_tmpSignature);
            final String _tmpSignatureName;
            _tmpSignatureName = _cursor.getString(_cursorIndexOfSignatureName);
            _item.setSignatureName(_tmpSignatureName);
            final String _tmpSignatureTime;
            _tmpSignatureTime = _cursor.getString(_cursorIndexOfSignatureTime);
            _item.setSignatureTime(_tmpSignatureTime);
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
  public List<TaskInfoEntity> getTaskInfoList1() {
    final String _sql = "Select * from taskInfoTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "taskId");
      final int _cursorIndexOfTaskType = CursorUtil.getColumnIndexOrThrow(_cursor, "taskType");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final int _cursorIndexOfSeqNo = CursorUtil.getColumnIndexOrThrow(_cursor, "seqNo");
      final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
      final int _cursorIndexOfRunNo = CursorUtil.getColumnIndexOrThrow(_cursor, "runNo");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfApptNo = CursorUtil.getColumnIndexOrThrow(_cursor, "apptNo");
      final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPhoneNo = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNo");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
      final int _cursorIndexOfAgent = CursorUtil.getColumnIndexOrThrow(_cursor, "agent");
      final int _cursorIndexOfManifest = CursorUtil.getColumnIndexOrThrow(_cursor, "manifest");
      final int _cursorIndexOfReffNo = CursorUtil.getColumnIndexOrThrow(_cursor, "reffNo");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfServiceLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceLevel");
      final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
      final int _cursorIndexOfWorkStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "workStatus");
      final int _cursorIndexOfArrivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalTime");
      final int _cursorIndexOfCompleteTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completeTime");
      final int _cursorIndexOfDataId = CursorUtil.getColumnIndexOrThrow(_cursor, "dataId");
      final int _cursorIndexOfStopId = CursorUtil.getColumnIndexOrThrow(_cursor, "stopId");
      final int _cursorIndexOfDataEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "dataEntered");
      final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
      final int _cursorIndexOfReasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonId");
      final int _cursorIndexOfQtyEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "qtyEntered");
      final int _cursorIndexOfWeightEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "weightEntered");
      final int _cursorIndexOfImageTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "imageTaken");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
      final int _cursorIndexOfAreaType = CursorUtil.getColumnIndexOrThrow(_cursor, "areaType");
      final int _cursorIndexOfDriverComment = CursorUtil.getColumnIndexOrThrow(_cursor, "driverComment");
      final int _cursorIndexOfDriverNotice = CursorUtil.getColumnIndexOrThrow(_cursor, "driverNotice");
      final int _cursorIndexOfCashCollect = CursorUtil.getColumnIndexOrThrow(_cursor, "cashCollect");
      final int _cursorIndexOfConsolicatedId = CursorUtil.getColumnIndexOrThrow(_cursor, "consolicatedId");
      final int _cursorIndexOfWaitingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "waitingTime");
      final int _cursorIndexOfCod = CursorUtil.getColumnIndexOrThrow(_cursor, "cod");
      final int _cursorIndexOfDisAmt = CursorUtil.getColumnIndexOrThrow(_cursor, "disAmt");
      final int _cursorIndexOfAccessorial = CursorUtil.getColumnIndexOrThrow(_cursor, "accessorial");
      final int _cursorIndexOfCodCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "codCurrency");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfLocationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "locationKey");
      final int _cursorIndexOfRecordStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "recordStatus");
      final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
      final int _cursorIndexOfSignatureName = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureName");
      final int _cursorIndexOfSignatureTime = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureTime");
      final List<TaskInfoEntity> _result = new ArrayList<TaskInfoEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TaskInfoEntity _item;
        final long _tmpTaskId;
        _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
        final String _tmpTaskType;
        _tmpTaskType = _cursor.getString(_cursorIndexOfTaskType);
        final int _tmpAppId;
        _tmpAppId = _cursor.getInt(_cursorIndexOfAppId);
        final int _tmpSeqNo;
        _tmpSeqNo = _cursor.getInt(_cursorIndexOfSeqNo);
        final String _tmpBatchId;
        _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
        final int _tmpRunNo;
        _tmpRunNo = _cursor.getInt(_cursorIndexOfRunNo);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpAddress;
        _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        final String _tmpApptNo;
        _tmpApptNo = _cursor.getString(_cursorIndexOfApptNo);
        final String _tmpPostalCode;
        _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
        final String _tmpCity;
        _tmpCity = _cursor.getString(_cursorIndexOfCity);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        final String _tmpPhoneNo;
        _tmpPhoneNo = _cursor.getString(_cursorIndexOfPhoneNo);
        final String _tmpLatitude;
        _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
        final String _tmpLongitude;
        _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
        final String _tmpBarcode;
        _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
        final String _tmpAgent;
        _tmpAgent = _cursor.getString(_cursorIndexOfAgent);
        final String _tmpManifest;
        _tmpManifest = _cursor.getString(_cursorIndexOfManifest);
        final String _tmpReffNo;
        _tmpReffNo = _cursor.getString(_cursorIndexOfReffNo);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        final String _tmpWeight;
        _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
        final String _tmpAmount;
        _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
        final String _tmpCurrency;
        _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
        final String _tmpServiceLevel;
        _tmpServiceLevel = _cursor.getString(_cursorIndexOfServiceLevel);
        final String _tmpInstructions;
        _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
        final String _tmpWorkStatus;
        _tmpWorkStatus = _cursor.getString(_cursorIndexOfWorkStatus);
        final String _tmpArrivalTime;
        _tmpArrivalTime = _cursor.getString(_cursorIndexOfArrivalTime);
        final String _tmpCompleteTime;
        _tmpCompleteTime = _cursor.getString(_cursorIndexOfCompleteTime);
        final long _tmpDataId;
        _tmpDataId = _cursor.getLong(_cursorIndexOfDataId);
        final long _tmpStopId;
        _tmpStopId = _cursor.getLong(_cursorIndexOfStopId);
        final String _tmpDataEntered;
        _tmpDataEntered = _cursor.getString(_cursorIndexOfDataEntered);
        final int _tmpStatusId;
        _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
        final int _tmpReasonId;
        _tmpReasonId = _cursor.getInt(_cursorIndexOfReasonId);
        final int _tmpQtyEntered;
        _tmpQtyEntered = _cursor.getInt(_cursorIndexOfQtyEntered);
        final double _tmpWeightEntered;
        _tmpWeightEntered = _cursor.getDouble(_cursorIndexOfWeightEntered);
        final int _tmpImageTaken;
        _tmpImageTaken = _cursor.getInt(_cursorIndexOfImageTaken);
        final String _tmpImagePath;
        _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
        final String _tmpAreaType;
        _tmpAreaType = _cursor.getString(_cursorIndexOfAreaType);
        final String _tmpDriverComment;
        _tmpDriverComment = _cursor.getString(_cursorIndexOfDriverComment);
        final String _tmpDriverNotice;
        _tmpDriverNotice = _cursor.getString(_cursorIndexOfDriverNotice);
        final String _tmpCashCollect;
        _tmpCashCollect = _cursor.getString(_cursorIndexOfCashCollect);
        final int _tmpConsolicatedId;
        _tmpConsolicatedId = _cursor.getInt(_cursorIndexOfConsolicatedId);
        final double _tmpWaitingTime;
        _tmpWaitingTime = _cursor.getDouble(_cursorIndexOfWaitingTime);
        final double _tmpCod;
        _tmpCod = _cursor.getDouble(_cursorIndexOfCod);
        final String _tmpDisAmt;
        _tmpDisAmt = _cursor.getString(_cursorIndexOfDisAmt);
        final String _tmpAccessorial;
        _tmpAccessorial = _cursor.getString(_cursorIndexOfAccessorial);
        final String _tmpCodCurrency;
        _tmpCodCurrency = _cursor.getString(_cursorIndexOfCodCurrency);
        final double _tmpMileage;
        _tmpMileage = _cursor.getDouble(_cursorIndexOfMileage);
        _item = new TaskInfoEntity(_tmpTaskId,_tmpTaskType,_tmpAppId,_tmpName,_tmpAddress,_tmpApptNo,_tmpPostalCode,_tmpCity,_tmpEmail,_tmpPhoneNo,_tmpLatitude,_tmpLongitude,_tmpBarcode,_tmpAgent,_tmpManifest,_tmpReffNo,_tmpQuantity,_tmpWeight,_tmpAmount,_tmpCurrency,_tmpServiceLevel,_tmpInstructions,_tmpWorkStatus,_tmpArrivalTime,_tmpCompleteTime,_tmpDataId,_tmpStopId,_tmpDataEntered,_tmpStatusId,_tmpReasonId,_tmpQtyEntered,_tmpWeightEntered,_tmpImageTaken,_tmpImagePath,_tmpAreaType,_tmpDriverComment,_tmpDriverNotice,_tmpCashCollect,_tmpConsolicatedId,_tmpWaitingTime,_tmpCod,_tmpDisAmt,_tmpAccessorial,_tmpCodCurrency,_tmpMileage,_tmpSeqNo,_tmpBatchId,_tmpRunNo);
        final String _tmpLocationKey;
        _tmpLocationKey = _cursor.getString(_cursorIndexOfLocationKey);
        _item.setLocationKey(_tmpLocationKey);
        final int _tmpRecordStatus;
        _tmpRecordStatus = _cursor.getInt(_cursorIndexOfRecordStatus);
        _item.setRecordStatus(_tmpRecordStatus);
        final String _tmpSignature;
        _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
        _item.setSignature(_tmpSignature);
        final String _tmpSignatureName;
        _tmpSignatureName = _cursor.getString(_cursorIndexOfSignatureName);
        _item.setSignatureName(_tmpSignatureName);
        final String _tmpSignatureTime;
        _tmpSignatureTime = _cursor.getString(_cursorIndexOfSignatureTime);
        _item.setSignatureTime(_tmpSignatureTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public TaskInfoEntity getTaskInfo(final String taskId) {
    final String _sql = "Select * from taskInfoTable where taskId= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (taskId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, taskId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "taskId");
      final int _cursorIndexOfTaskType = CursorUtil.getColumnIndexOrThrow(_cursor, "taskType");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final int _cursorIndexOfSeqNo = CursorUtil.getColumnIndexOrThrow(_cursor, "seqNo");
      final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
      final int _cursorIndexOfRunNo = CursorUtil.getColumnIndexOrThrow(_cursor, "runNo");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfApptNo = CursorUtil.getColumnIndexOrThrow(_cursor, "apptNo");
      final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPhoneNo = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNo");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
      final int _cursorIndexOfAgent = CursorUtil.getColumnIndexOrThrow(_cursor, "agent");
      final int _cursorIndexOfManifest = CursorUtil.getColumnIndexOrThrow(_cursor, "manifest");
      final int _cursorIndexOfReffNo = CursorUtil.getColumnIndexOrThrow(_cursor, "reffNo");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfServiceLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceLevel");
      final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
      final int _cursorIndexOfWorkStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "workStatus");
      final int _cursorIndexOfArrivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalTime");
      final int _cursorIndexOfCompleteTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completeTime");
      final int _cursorIndexOfDataId = CursorUtil.getColumnIndexOrThrow(_cursor, "dataId");
      final int _cursorIndexOfStopId = CursorUtil.getColumnIndexOrThrow(_cursor, "stopId");
      final int _cursorIndexOfDataEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "dataEntered");
      final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
      final int _cursorIndexOfReasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonId");
      final int _cursorIndexOfQtyEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "qtyEntered");
      final int _cursorIndexOfWeightEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "weightEntered");
      final int _cursorIndexOfImageTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "imageTaken");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
      final int _cursorIndexOfAreaType = CursorUtil.getColumnIndexOrThrow(_cursor, "areaType");
      final int _cursorIndexOfDriverComment = CursorUtil.getColumnIndexOrThrow(_cursor, "driverComment");
      final int _cursorIndexOfDriverNotice = CursorUtil.getColumnIndexOrThrow(_cursor, "driverNotice");
      final int _cursorIndexOfCashCollect = CursorUtil.getColumnIndexOrThrow(_cursor, "cashCollect");
      final int _cursorIndexOfConsolicatedId = CursorUtil.getColumnIndexOrThrow(_cursor, "consolicatedId");
      final int _cursorIndexOfWaitingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "waitingTime");
      final int _cursorIndexOfCod = CursorUtil.getColumnIndexOrThrow(_cursor, "cod");
      final int _cursorIndexOfDisAmt = CursorUtil.getColumnIndexOrThrow(_cursor, "disAmt");
      final int _cursorIndexOfAccessorial = CursorUtil.getColumnIndexOrThrow(_cursor, "accessorial");
      final int _cursorIndexOfCodCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "codCurrency");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfLocationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "locationKey");
      final int _cursorIndexOfRecordStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "recordStatus");
      final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
      final int _cursorIndexOfSignatureName = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureName");
      final int _cursorIndexOfSignatureTime = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureTime");
      final TaskInfoEntity _result;
      if(_cursor.moveToFirst()) {
        final long _tmpTaskId;
        _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
        final String _tmpTaskType;
        _tmpTaskType = _cursor.getString(_cursorIndexOfTaskType);
        final int _tmpAppId;
        _tmpAppId = _cursor.getInt(_cursorIndexOfAppId);
        final int _tmpSeqNo;
        _tmpSeqNo = _cursor.getInt(_cursorIndexOfSeqNo);
        final String _tmpBatchId;
        _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
        final int _tmpRunNo;
        _tmpRunNo = _cursor.getInt(_cursorIndexOfRunNo);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpAddress;
        _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        final String _tmpApptNo;
        _tmpApptNo = _cursor.getString(_cursorIndexOfApptNo);
        final String _tmpPostalCode;
        _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
        final String _tmpCity;
        _tmpCity = _cursor.getString(_cursorIndexOfCity);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        final String _tmpPhoneNo;
        _tmpPhoneNo = _cursor.getString(_cursorIndexOfPhoneNo);
        final String _tmpLatitude;
        _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
        final String _tmpLongitude;
        _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
        final String _tmpBarcode;
        _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
        final String _tmpAgent;
        _tmpAgent = _cursor.getString(_cursorIndexOfAgent);
        final String _tmpManifest;
        _tmpManifest = _cursor.getString(_cursorIndexOfManifest);
        final String _tmpReffNo;
        _tmpReffNo = _cursor.getString(_cursorIndexOfReffNo);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        final String _tmpWeight;
        _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
        final String _tmpAmount;
        _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
        final String _tmpCurrency;
        _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
        final String _tmpServiceLevel;
        _tmpServiceLevel = _cursor.getString(_cursorIndexOfServiceLevel);
        final String _tmpInstructions;
        _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
        final String _tmpWorkStatus;
        _tmpWorkStatus = _cursor.getString(_cursorIndexOfWorkStatus);
        final String _tmpArrivalTime;
        _tmpArrivalTime = _cursor.getString(_cursorIndexOfArrivalTime);
        final String _tmpCompleteTime;
        _tmpCompleteTime = _cursor.getString(_cursorIndexOfCompleteTime);
        final long _tmpDataId;
        _tmpDataId = _cursor.getLong(_cursorIndexOfDataId);
        final long _tmpStopId;
        _tmpStopId = _cursor.getLong(_cursorIndexOfStopId);
        final String _tmpDataEntered;
        _tmpDataEntered = _cursor.getString(_cursorIndexOfDataEntered);
        final int _tmpStatusId;
        _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
        final int _tmpReasonId;
        _tmpReasonId = _cursor.getInt(_cursorIndexOfReasonId);
        final int _tmpQtyEntered;
        _tmpQtyEntered = _cursor.getInt(_cursorIndexOfQtyEntered);
        final double _tmpWeightEntered;
        _tmpWeightEntered = _cursor.getDouble(_cursorIndexOfWeightEntered);
        final int _tmpImageTaken;
        _tmpImageTaken = _cursor.getInt(_cursorIndexOfImageTaken);
        final String _tmpImagePath;
        _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
        final String _tmpAreaType;
        _tmpAreaType = _cursor.getString(_cursorIndexOfAreaType);
        final String _tmpDriverComment;
        _tmpDriverComment = _cursor.getString(_cursorIndexOfDriverComment);
        final String _tmpDriverNotice;
        _tmpDriverNotice = _cursor.getString(_cursorIndexOfDriverNotice);
        final String _tmpCashCollect;
        _tmpCashCollect = _cursor.getString(_cursorIndexOfCashCollect);
        final int _tmpConsolicatedId;
        _tmpConsolicatedId = _cursor.getInt(_cursorIndexOfConsolicatedId);
        final double _tmpWaitingTime;
        _tmpWaitingTime = _cursor.getDouble(_cursorIndexOfWaitingTime);
        final double _tmpCod;
        _tmpCod = _cursor.getDouble(_cursorIndexOfCod);
        final String _tmpDisAmt;
        _tmpDisAmt = _cursor.getString(_cursorIndexOfDisAmt);
        final String _tmpAccessorial;
        _tmpAccessorial = _cursor.getString(_cursorIndexOfAccessorial);
        final String _tmpCodCurrency;
        _tmpCodCurrency = _cursor.getString(_cursorIndexOfCodCurrency);
        final double _tmpMileage;
        _tmpMileage = _cursor.getDouble(_cursorIndexOfMileage);
        _result = new TaskInfoEntity(_tmpTaskId,_tmpTaskType,_tmpAppId,_tmpName,_tmpAddress,_tmpApptNo,_tmpPostalCode,_tmpCity,_tmpEmail,_tmpPhoneNo,_tmpLatitude,_tmpLongitude,_tmpBarcode,_tmpAgent,_tmpManifest,_tmpReffNo,_tmpQuantity,_tmpWeight,_tmpAmount,_tmpCurrency,_tmpServiceLevel,_tmpInstructions,_tmpWorkStatus,_tmpArrivalTime,_tmpCompleteTime,_tmpDataId,_tmpStopId,_tmpDataEntered,_tmpStatusId,_tmpReasonId,_tmpQtyEntered,_tmpWeightEntered,_tmpImageTaken,_tmpImagePath,_tmpAreaType,_tmpDriverComment,_tmpDriverNotice,_tmpCashCollect,_tmpConsolicatedId,_tmpWaitingTime,_tmpCod,_tmpDisAmt,_tmpAccessorial,_tmpCodCurrency,_tmpMileage,_tmpSeqNo,_tmpBatchId,_tmpRunNo);
        final String _tmpLocationKey;
        _tmpLocationKey = _cursor.getString(_cursorIndexOfLocationKey);
        _result.setLocationKey(_tmpLocationKey);
        final int _tmpRecordStatus;
        _tmpRecordStatus = _cursor.getInt(_cursorIndexOfRecordStatus);
        _result.setRecordStatus(_tmpRecordStatus);
        final String _tmpSignature;
        _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
        _result.setSignature(_tmpSignature);
        final String _tmpSignatureName;
        _tmpSignatureName = _cursor.getString(_cursorIndexOfSignatureName);
        _result.setSignatureName(_tmpSignatureName);
        final String _tmpSignatureTime;
        _tmpSignatureTime = _cursor.getString(_cursorIndexOfSignatureTime);
        _result.setSignatureTime(_tmpSignatureTime);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoByBatchId(final String batchId) {
    final String _sql = "Select address, postalCode, city, locationKey, COUNT(*) as groupCount, latitude, longitude, MAX(arrivalTime) as arrivalTime  from taskInfoTable where batchId= ? GROUP BY locationKey ORDER BY seqNo ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (batchId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, batchId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"taskInfoTable"}, false, new Callable<List<TaskInfoGroupByLocationKey>>() {
      @Override
      public List<TaskInfoGroupByLocationKey> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfLocationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "locationKey");
          final int _cursorIndexOfGroupCount = CursorUtil.getColumnIndexOrThrow(_cursor, "groupCount");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfArrivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalTime");
          final List<TaskInfoGroupByLocationKey> _result = new ArrayList<TaskInfoGroupByLocationKey>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TaskInfoGroupByLocationKey _item;
            _item = new TaskInfoGroupByLocationKey();
            _item.address = _cursor.getString(_cursorIndexOfAddress);
            _item.postalCode = _cursor.getString(_cursorIndexOfPostalCode);
            _item.city = _cursor.getString(_cursorIndexOfCity);
            final String _tmpLocationKey;
            _tmpLocationKey = _cursor.getString(_cursorIndexOfLocationKey);
            _item.setLocationKey(_tmpLocationKey);
            _item.groupCount = _cursor.getInt(_cursorIndexOfGroupCount);
            final String _tmpLatitude;
            _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpLongitude;
            _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final String _tmpArrivalTime;
            _tmpArrivalTime = _cursor.getString(_cursorIndexOfArrivalTime);
            _item.setArrivalTime(_tmpArrivalTime);
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
  public LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoGroupByLocationKeys() {
    final String _sql = "Select address, postalCode, city, locationKey, COUNT(*) as groupCount, latitude, longitude, MAX(arrivalTime) as arrivalTime  from taskInfoTable GROUP BY locationKey ORDER BY seqNo ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"taskInfoTable"}, false, new Callable<List<TaskInfoGroupByLocationKey>>() {
      @Override
      public List<TaskInfoGroupByLocationKey> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfLocationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "locationKey");
          final int _cursorIndexOfGroupCount = CursorUtil.getColumnIndexOrThrow(_cursor, "groupCount");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfArrivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalTime");
          final List<TaskInfoGroupByLocationKey> _result = new ArrayList<TaskInfoGroupByLocationKey>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TaskInfoGroupByLocationKey _item;
            _item = new TaskInfoGroupByLocationKey();
            _item.address = _cursor.getString(_cursorIndexOfAddress);
            _item.postalCode = _cursor.getString(_cursorIndexOfPostalCode);
            _item.city = _cursor.getString(_cursorIndexOfCity);
            final String _tmpLocationKey;
            _tmpLocationKey = _cursor.getString(_cursorIndexOfLocationKey);
            _item.setLocationKey(_tmpLocationKey);
            _item.groupCount = _cursor.getInt(_cursorIndexOfGroupCount);
            final String _tmpLatitude;
            _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpLongitude;
            _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final String _tmpArrivalTime;
            _tmpArrivalTime = _cursor.getString(_cursorIndexOfArrivalTime);
            _item.setArrivalTime(_tmpArrivalTime);
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
  public LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoSearchByWorkStatusGroupByLocationKeys(final String workStatus) {
    final String _sql = "Select address, postalCode, city, locationKey, COUNT(*) as groupCount, latitude, longitude, MAX(arrivalTime) as arrivalTime from taskInfoTable where workStatus= ? GROUP BY locationKey";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (workStatus == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, workStatus);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"taskInfoTable"}, false, new Callable<List<TaskInfoGroupByLocationKey>>() {
      @Override
      public List<TaskInfoGroupByLocationKey> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfLocationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "locationKey");
          final int _cursorIndexOfGroupCount = CursorUtil.getColumnIndexOrThrow(_cursor, "groupCount");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfArrivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalTime");
          final List<TaskInfoGroupByLocationKey> _result = new ArrayList<TaskInfoGroupByLocationKey>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TaskInfoGroupByLocationKey _item;
            _item = new TaskInfoGroupByLocationKey();
            _item.address = _cursor.getString(_cursorIndexOfAddress);
            _item.postalCode = _cursor.getString(_cursorIndexOfPostalCode);
            _item.city = _cursor.getString(_cursorIndexOfCity);
            final String _tmpLocationKey;
            _tmpLocationKey = _cursor.getString(_cursorIndexOfLocationKey);
            _item.setLocationKey(_tmpLocationKey);
            _item.groupCount = _cursor.getInt(_cursorIndexOfGroupCount);
            final String _tmpLatitude;
            _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
            _item.setLatitude(_tmpLatitude);
            final String _tmpLongitude;
            _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
            _item.setLongitude(_tmpLongitude);
            final String _tmpArrivalTime;
            _tmpArrivalTime = _cursor.getString(_cursorIndexOfArrivalTime);
            _item.setArrivalTime(_tmpArrivalTime);
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
  public LiveData<List<TaskInfoEntity>> getTaskInfoByLocationKey(final String locationKey) {
    final String _sql = "Select * from taskInfoTable where locationKey= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (locationKey == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, locationKey);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"taskInfoTable"}, false, new Callable<List<TaskInfoEntity>>() {
      @Override
      public List<TaskInfoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "taskId");
          final int _cursorIndexOfTaskType = CursorUtil.getColumnIndexOrThrow(_cursor, "taskType");
          final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
          final int _cursorIndexOfSeqNo = CursorUtil.getColumnIndexOrThrow(_cursor, "seqNo");
          final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
          final int _cursorIndexOfRunNo = CursorUtil.getColumnIndexOrThrow(_cursor, "runNo");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfApptNo = CursorUtil.getColumnIndexOrThrow(_cursor, "apptNo");
          final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfPhoneNo = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNo");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfAgent = CursorUtil.getColumnIndexOrThrow(_cursor, "agent");
          final int _cursorIndexOfManifest = CursorUtil.getColumnIndexOrThrow(_cursor, "manifest");
          final int _cursorIndexOfReffNo = CursorUtil.getColumnIndexOrThrow(_cursor, "reffNo");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfServiceLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceLevel");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfWorkStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "workStatus");
          final int _cursorIndexOfArrivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalTime");
          final int _cursorIndexOfCompleteTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completeTime");
          final int _cursorIndexOfDataId = CursorUtil.getColumnIndexOrThrow(_cursor, "dataId");
          final int _cursorIndexOfStopId = CursorUtil.getColumnIndexOrThrow(_cursor, "stopId");
          final int _cursorIndexOfDataEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "dataEntered");
          final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
          final int _cursorIndexOfReasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonId");
          final int _cursorIndexOfQtyEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "qtyEntered");
          final int _cursorIndexOfWeightEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "weightEntered");
          final int _cursorIndexOfImageTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "imageTaken");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfAreaType = CursorUtil.getColumnIndexOrThrow(_cursor, "areaType");
          final int _cursorIndexOfDriverComment = CursorUtil.getColumnIndexOrThrow(_cursor, "driverComment");
          final int _cursorIndexOfDriverNotice = CursorUtil.getColumnIndexOrThrow(_cursor, "driverNotice");
          final int _cursorIndexOfCashCollect = CursorUtil.getColumnIndexOrThrow(_cursor, "cashCollect");
          final int _cursorIndexOfConsolicatedId = CursorUtil.getColumnIndexOrThrow(_cursor, "consolicatedId");
          final int _cursorIndexOfWaitingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "waitingTime");
          final int _cursorIndexOfCod = CursorUtil.getColumnIndexOrThrow(_cursor, "cod");
          final int _cursorIndexOfDisAmt = CursorUtil.getColumnIndexOrThrow(_cursor, "disAmt");
          final int _cursorIndexOfAccessorial = CursorUtil.getColumnIndexOrThrow(_cursor, "accessorial");
          final int _cursorIndexOfCodCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "codCurrency");
          final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
          final int _cursorIndexOfLocationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "locationKey");
          final int _cursorIndexOfRecordStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "recordStatus");
          final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
          final int _cursorIndexOfSignatureName = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureName");
          final int _cursorIndexOfSignatureTime = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureTime");
          final List<TaskInfoEntity> _result = new ArrayList<TaskInfoEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TaskInfoEntity _item;
            final long _tmpTaskId;
            _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
            final String _tmpTaskType;
            _tmpTaskType = _cursor.getString(_cursorIndexOfTaskType);
            final int _tmpAppId;
            _tmpAppId = _cursor.getInt(_cursorIndexOfAppId);
            final int _tmpSeqNo;
            _tmpSeqNo = _cursor.getInt(_cursorIndexOfSeqNo);
            final String _tmpBatchId;
            _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
            final int _tmpRunNo;
            _tmpRunNo = _cursor.getInt(_cursorIndexOfRunNo);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpApptNo;
            _tmpApptNo = _cursor.getString(_cursorIndexOfApptNo);
            final String _tmpPostalCode;
            _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
            final String _tmpCity;
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpPhoneNo;
            _tmpPhoneNo = _cursor.getString(_cursorIndexOfPhoneNo);
            final String _tmpLatitude;
            _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
            final String _tmpLongitude;
            _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
            final String _tmpBarcode;
            _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            final String _tmpAgent;
            _tmpAgent = _cursor.getString(_cursorIndexOfAgent);
            final String _tmpManifest;
            _tmpManifest = _cursor.getString(_cursorIndexOfManifest);
            final String _tmpReffNo;
            _tmpReffNo = _cursor.getString(_cursorIndexOfReffNo);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpWeight;
            _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
            final String _tmpAmount;
            _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            final String _tmpCurrency;
            _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            final String _tmpServiceLevel;
            _tmpServiceLevel = _cursor.getString(_cursorIndexOfServiceLevel);
            final String _tmpInstructions;
            _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            final String _tmpWorkStatus;
            _tmpWorkStatus = _cursor.getString(_cursorIndexOfWorkStatus);
            final String _tmpArrivalTime;
            _tmpArrivalTime = _cursor.getString(_cursorIndexOfArrivalTime);
            final String _tmpCompleteTime;
            _tmpCompleteTime = _cursor.getString(_cursorIndexOfCompleteTime);
            final long _tmpDataId;
            _tmpDataId = _cursor.getLong(_cursorIndexOfDataId);
            final long _tmpStopId;
            _tmpStopId = _cursor.getLong(_cursorIndexOfStopId);
            final String _tmpDataEntered;
            _tmpDataEntered = _cursor.getString(_cursorIndexOfDataEntered);
            final int _tmpStatusId;
            _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
            final int _tmpReasonId;
            _tmpReasonId = _cursor.getInt(_cursorIndexOfReasonId);
            final int _tmpQtyEntered;
            _tmpQtyEntered = _cursor.getInt(_cursorIndexOfQtyEntered);
            final double _tmpWeightEntered;
            _tmpWeightEntered = _cursor.getDouble(_cursorIndexOfWeightEntered);
            final int _tmpImageTaken;
            _tmpImageTaken = _cursor.getInt(_cursorIndexOfImageTaken);
            final String _tmpImagePath;
            _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            final String _tmpAreaType;
            _tmpAreaType = _cursor.getString(_cursorIndexOfAreaType);
            final String _tmpDriverComment;
            _tmpDriverComment = _cursor.getString(_cursorIndexOfDriverComment);
            final String _tmpDriverNotice;
            _tmpDriverNotice = _cursor.getString(_cursorIndexOfDriverNotice);
            final String _tmpCashCollect;
            _tmpCashCollect = _cursor.getString(_cursorIndexOfCashCollect);
            final int _tmpConsolicatedId;
            _tmpConsolicatedId = _cursor.getInt(_cursorIndexOfConsolicatedId);
            final double _tmpWaitingTime;
            _tmpWaitingTime = _cursor.getDouble(_cursorIndexOfWaitingTime);
            final double _tmpCod;
            _tmpCod = _cursor.getDouble(_cursorIndexOfCod);
            final String _tmpDisAmt;
            _tmpDisAmt = _cursor.getString(_cursorIndexOfDisAmt);
            final String _tmpAccessorial;
            _tmpAccessorial = _cursor.getString(_cursorIndexOfAccessorial);
            final String _tmpCodCurrency;
            _tmpCodCurrency = _cursor.getString(_cursorIndexOfCodCurrency);
            final double _tmpMileage;
            _tmpMileage = _cursor.getDouble(_cursorIndexOfMileage);
            _item = new TaskInfoEntity(_tmpTaskId,_tmpTaskType,_tmpAppId,_tmpName,_tmpAddress,_tmpApptNo,_tmpPostalCode,_tmpCity,_tmpEmail,_tmpPhoneNo,_tmpLatitude,_tmpLongitude,_tmpBarcode,_tmpAgent,_tmpManifest,_tmpReffNo,_tmpQuantity,_tmpWeight,_tmpAmount,_tmpCurrency,_tmpServiceLevel,_tmpInstructions,_tmpWorkStatus,_tmpArrivalTime,_tmpCompleteTime,_tmpDataId,_tmpStopId,_tmpDataEntered,_tmpStatusId,_tmpReasonId,_tmpQtyEntered,_tmpWeightEntered,_tmpImageTaken,_tmpImagePath,_tmpAreaType,_tmpDriverComment,_tmpDriverNotice,_tmpCashCollect,_tmpConsolicatedId,_tmpWaitingTime,_tmpCod,_tmpDisAmt,_tmpAccessorial,_tmpCodCurrency,_tmpMileage,_tmpSeqNo,_tmpBatchId,_tmpRunNo);
            final String _tmpLocationKey;
            _tmpLocationKey = _cursor.getString(_cursorIndexOfLocationKey);
            _item.setLocationKey(_tmpLocationKey);
            final int _tmpRecordStatus;
            _tmpRecordStatus = _cursor.getInt(_cursorIndexOfRecordStatus);
            _item.setRecordStatus(_tmpRecordStatus);
            final String _tmpSignature;
            _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
            _item.setSignature(_tmpSignature);
            final String _tmpSignatureName;
            _tmpSignatureName = _cursor.getString(_cursorIndexOfSignatureName);
            _item.setSignatureName(_tmpSignatureName);
            final String _tmpSignatureTime;
            _tmpSignatureTime = _cursor.getString(_cursorIndexOfSignatureTime);
            _item.setSignatureTime(_tmpSignatureTime);
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
  public List<TaskInfoEntity> getTaskInfoByRecordStatus(final int recordStatus) {
    final String _sql = "Select * from taskInfoTable where recordStatus= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, recordStatus);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "taskId");
      final int _cursorIndexOfTaskType = CursorUtil.getColumnIndexOrThrow(_cursor, "taskType");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final int _cursorIndexOfSeqNo = CursorUtil.getColumnIndexOrThrow(_cursor, "seqNo");
      final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
      final int _cursorIndexOfRunNo = CursorUtil.getColumnIndexOrThrow(_cursor, "runNo");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfApptNo = CursorUtil.getColumnIndexOrThrow(_cursor, "apptNo");
      final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPhoneNo = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNo");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
      final int _cursorIndexOfAgent = CursorUtil.getColumnIndexOrThrow(_cursor, "agent");
      final int _cursorIndexOfManifest = CursorUtil.getColumnIndexOrThrow(_cursor, "manifest");
      final int _cursorIndexOfReffNo = CursorUtil.getColumnIndexOrThrow(_cursor, "reffNo");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfServiceLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceLevel");
      final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
      final int _cursorIndexOfWorkStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "workStatus");
      final int _cursorIndexOfArrivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalTime");
      final int _cursorIndexOfCompleteTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completeTime");
      final int _cursorIndexOfDataId = CursorUtil.getColumnIndexOrThrow(_cursor, "dataId");
      final int _cursorIndexOfStopId = CursorUtil.getColumnIndexOrThrow(_cursor, "stopId");
      final int _cursorIndexOfDataEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "dataEntered");
      final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
      final int _cursorIndexOfReasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonId");
      final int _cursorIndexOfQtyEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "qtyEntered");
      final int _cursorIndexOfWeightEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "weightEntered");
      final int _cursorIndexOfImageTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "imageTaken");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
      final int _cursorIndexOfAreaType = CursorUtil.getColumnIndexOrThrow(_cursor, "areaType");
      final int _cursorIndexOfDriverComment = CursorUtil.getColumnIndexOrThrow(_cursor, "driverComment");
      final int _cursorIndexOfDriverNotice = CursorUtil.getColumnIndexOrThrow(_cursor, "driverNotice");
      final int _cursorIndexOfCashCollect = CursorUtil.getColumnIndexOrThrow(_cursor, "cashCollect");
      final int _cursorIndexOfConsolicatedId = CursorUtil.getColumnIndexOrThrow(_cursor, "consolicatedId");
      final int _cursorIndexOfWaitingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "waitingTime");
      final int _cursorIndexOfCod = CursorUtil.getColumnIndexOrThrow(_cursor, "cod");
      final int _cursorIndexOfDisAmt = CursorUtil.getColumnIndexOrThrow(_cursor, "disAmt");
      final int _cursorIndexOfAccessorial = CursorUtil.getColumnIndexOrThrow(_cursor, "accessorial");
      final int _cursorIndexOfCodCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "codCurrency");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfLocationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "locationKey");
      final int _cursorIndexOfRecordStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "recordStatus");
      final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
      final int _cursorIndexOfSignatureName = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureName");
      final int _cursorIndexOfSignatureTime = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureTime");
      final List<TaskInfoEntity> _result = new ArrayList<TaskInfoEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TaskInfoEntity _item;
        final long _tmpTaskId;
        _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
        final String _tmpTaskType;
        _tmpTaskType = _cursor.getString(_cursorIndexOfTaskType);
        final int _tmpAppId;
        _tmpAppId = _cursor.getInt(_cursorIndexOfAppId);
        final int _tmpSeqNo;
        _tmpSeqNo = _cursor.getInt(_cursorIndexOfSeqNo);
        final String _tmpBatchId;
        _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
        final int _tmpRunNo;
        _tmpRunNo = _cursor.getInt(_cursorIndexOfRunNo);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpAddress;
        _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        final String _tmpApptNo;
        _tmpApptNo = _cursor.getString(_cursorIndexOfApptNo);
        final String _tmpPostalCode;
        _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
        final String _tmpCity;
        _tmpCity = _cursor.getString(_cursorIndexOfCity);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        final String _tmpPhoneNo;
        _tmpPhoneNo = _cursor.getString(_cursorIndexOfPhoneNo);
        final String _tmpLatitude;
        _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
        final String _tmpLongitude;
        _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
        final String _tmpBarcode;
        _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
        final String _tmpAgent;
        _tmpAgent = _cursor.getString(_cursorIndexOfAgent);
        final String _tmpManifest;
        _tmpManifest = _cursor.getString(_cursorIndexOfManifest);
        final String _tmpReffNo;
        _tmpReffNo = _cursor.getString(_cursorIndexOfReffNo);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        final String _tmpWeight;
        _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
        final String _tmpAmount;
        _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
        final String _tmpCurrency;
        _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
        final String _tmpServiceLevel;
        _tmpServiceLevel = _cursor.getString(_cursorIndexOfServiceLevel);
        final String _tmpInstructions;
        _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
        final String _tmpWorkStatus;
        _tmpWorkStatus = _cursor.getString(_cursorIndexOfWorkStatus);
        final String _tmpArrivalTime;
        _tmpArrivalTime = _cursor.getString(_cursorIndexOfArrivalTime);
        final String _tmpCompleteTime;
        _tmpCompleteTime = _cursor.getString(_cursorIndexOfCompleteTime);
        final long _tmpDataId;
        _tmpDataId = _cursor.getLong(_cursorIndexOfDataId);
        final long _tmpStopId;
        _tmpStopId = _cursor.getLong(_cursorIndexOfStopId);
        final String _tmpDataEntered;
        _tmpDataEntered = _cursor.getString(_cursorIndexOfDataEntered);
        final int _tmpStatusId;
        _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
        final int _tmpReasonId;
        _tmpReasonId = _cursor.getInt(_cursorIndexOfReasonId);
        final int _tmpQtyEntered;
        _tmpQtyEntered = _cursor.getInt(_cursorIndexOfQtyEntered);
        final double _tmpWeightEntered;
        _tmpWeightEntered = _cursor.getDouble(_cursorIndexOfWeightEntered);
        final int _tmpImageTaken;
        _tmpImageTaken = _cursor.getInt(_cursorIndexOfImageTaken);
        final String _tmpImagePath;
        _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
        final String _tmpAreaType;
        _tmpAreaType = _cursor.getString(_cursorIndexOfAreaType);
        final String _tmpDriverComment;
        _tmpDriverComment = _cursor.getString(_cursorIndexOfDriverComment);
        final String _tmpDriverNotice;
        _tmpDriverNotice = _cursor.getString(_cursorIndexOfDriverNotice);
        final String _tmpCashCollect;
        _tmpCashCollect = _cursor.getString(_cursorIndexOfCashCollect);
        final int _tmpConsolicatedId;
        _tmpConsolicatedId = _cursor.getInt(_cursorIndexOfConsolicatedId);
        final double _tmpWaitingTime;
        _tmpWaitingTime = _cursor.getDouble(_cursorIndexOfWaitingTime);
        final double _tmpCod;
        _tmpCod = _cursor.getDouble(_cursorIndexOfCod);
        final String _tmpDisAmt;
        _tmpDisAmt = _cursor.getString(_cursorIndexOfDisAmt);
        final String _tmpAccessorial;
        _tmpAccessorial = _cursor.getString(_cursorIndexOfAccessorial);
        final String _tmpCodCurrency;
        _tmpCodCurrency = _cursor.getString(_cursorIndexOfCodCurrency);
        final double _tmpMileage;
        _tmpMileage = _cursor.getDouble(_cursorIndexOfMileage);
        _item = new TaskInfoEntity(_tmpTaskId,_tmpTaskType,_tmpAppId,_tmpName,_tmpAddress,_tmpApptNo,_tmpPostalCode,_tmpCity,_tmpEmail,_tmpPhoneNo,_tmpLatitude,_tmpLongitude,_tmpBarcode,_tmpAgent,_tmpManifest,_tmpReffNo,_tmpQuantity,_tmpWeight,_tmpAmount,_tmpCurrency,_tmpServiceLevel,_tmpInstructions,_tmpWorkStatus,_tmpArrivalTime,_tmpCompleteTime,_tmpDataId,_tmpStopId,_tmpDataEntered,_tmpStatusId,_tmpReasonId,_tmpQtyEntered,_tmpWeightEntered,_tmpImageTaken,_tmpImagePath,_tmpAreaType,_tmpDriverComment,_tmpDriverNotice,_tmpCashCollect,_tmpConsolicatedId,_tmpWaitingTime,_tmpCod,_tmpDisAmt,_tmpAccessorial,_tmpCodCurrency,_tmpMileage,_tmpSeqNo,_tmpBatchId,_tmpRunNo);
        final String _tmpLocationKey;
        _tmpLocationKey = _cursor.getString(_cursorIndexOfLocationKey);
        _item.setLocationKey(_tmpLocationKey);
        final int _tmpRecordStatus;
        _tmpRecordStatus = _cursor.getInt(_cursorIndexOfRecordStatus);
        _item.setRecordStatus(_tmpRecordStatus);
        final String _tmpSignature;
        _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
        _item.setSignature(_tmpSignature);
        final String _tmpSignatureName;
        _tmpSignatureName = _cursor.getString(_cursorIndexOfSignatureName);
        _item.setSignatureName(_tmpSignatureName);
        final String _tmpSignatureTime;
        _tmpSignatureTime = _cursor.getString(_cursorIndexOfSignatureTime);
        _item.setSignatureTime(_tmpSignatureTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<TaskInfoEntity> getTaskInfoCompleted(final String locationKey,
      final String workStatus) {
    final String _sql = "Select * from taskInfoTable where locationKey= ? AND workStatus= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (locationKey == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, locationKey);
    }
    _argIndex = 2;
    if (workStatus == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, workStatus);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "taskId");
      final int _cursorIndexOfTaskType = CursorUtil.getColumnIndexOrThrow(_cursor, "taskType");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final int _cursorIndexOfSeqNo = CursorUtil.getColumnIndexOrThrow(_cursor, "seqNo");
      final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
      final int _cursorIndexOfRunNo = CursorUtil.getColumnIndexOrThrow(_cursor, "runNo");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfApptNo = CursorUtil.getColumnIndexOrThrow(_cursor, "apptNo");
      final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPhoneNo = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNo");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
      final int _cursorIndexOfAgent = CursorUtil.getColumnIndexOrThrow(_cursor, "agent");
      final int _cursorIndexOfManifest = CursorUtil.getColumnIndexOrThrow(_cursor, "manifest");
      final int _cursorIndexOfReffNo = CursorUtil.getColumnIndexOrThrow(_cursor, "reffNo");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfServiceLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceLevel");
      final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
      final int _cursorIndexOfWorkStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "workStatus");
      final int _cursorIndexOfArrivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalTime");
      final int _cursorIndexOfCompleteTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completeTime");
      final int _cursorIndexOfDataId = CursorUtil.getColumnIndexOrThrow(_cursor, "dataId");
      final int _cursorIndexOfStopId = CursorUtil.getColumnIndexOrThrow(_cursor, "stopId");
      final int _cursorIndexOfDataEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "dataEntered");
      final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
      final int _cursorIndexOfReasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonId");
      final int _cursorIndexOfQtyEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "qtyEntered");
      final int _cursorIndexOfWeightEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "weightEntered");
      final int _cursorIndexOfImageTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "imageTaken");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
      final int _cursorIndexOfAreaType = CursorUtil.getColumnIndexOrThrow(_cursor, "areaType");
      final int _cursorIndexOfDriverComment = CursorUtil.getColumnIndexOrThrow(_cursor, "driverComment");
      final int _cursorIndexOfDriverNotice = CursorUtil.getColumnIndexOrThrow(_cursor, "driverNotice");
      final int _cursorIndexOfCashCollect = CursorUtil.getColumnIndexOrThrow(_cursor, "cashCollect");
      final int _cursorIndexOfConsolicatedId = CursorUtil.getColumnIndexOrThrow(_cursor, "consolicatedId");
      final int _cursorIndexOfWaitingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "waitingTime");
      final int _cursorIndexOfCod = CursorUtil.getColumnIndexOrThrow(_cursor, "cod");
      final int _cursorIndexOfDisAmt = CursorUtil.getColumnIndexOrThrow(_cursor, "disAmt");
      final int _cursorIndexOfAccessorial = CursorUtil.getColumnIndexOrThrow(_cursor, "accessorial");
      final int _cursorIndexOfCodCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "codCurrency");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfLocationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "locationKey");
      final int _cursorIndexOfRecordStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "recordStatus");
      final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
      final int _cursorIndexOfSignatureName = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureName");
      final int _cursorIndexOfSignatureTime = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureTime");
      final List<TaskInfoEntity> _result = new ArrayList<TaskInfoEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TaskInfoEntity _item;
        final long _tmpTaskId;
        _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
        final String _tmpTaskType;
        _tmpTaskType = _cursor.getString(_cursorIndexOfTaskType);
        final int _tmpAppId;
        _tmpAppId = _cursor.getInt(_cursorIndexOfAppId);
        final int _tmpSeqNo;
        _tmpSeqNo = _cursor.getInt(_cursorIndexOfSeqNo);
        final String _tmpBatchId;
        _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
        final int _tmpRunNo;
        _tmpRunNo = _cursor.getInt(_cursorIndexOfRunNo);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpAddress;
        _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        final String _tmpApptNo;
        _tmpApptNo = _cursor.getString(_cursorIndexOfApptNo);
        final String _tmpPostalCode;
        _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
        final String _tmpCity;
        _tmpCity = _cursor.getString(_cursorIndexOfCity);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        final String _tmpPhoneNo;
        _tmpPhoneNo = _cursor.getString(_cursorIndexOfPhoneNo);
        final String _tmpLatitude;
        _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
        final String _tmpLongitude;
        _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
        final String _tmpBarcode;
        _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
        final String _tmpAgent;
        _tmpAgent = _cursor.getString(_cursorIndexOfAgent);
        final String _tmpManifest;
        _tmpManifest = _cursor.getString(_cursorIndexOfManifest);
        final String _tmpReffNo;
        _tmpReffNo = _cursor.getString(_cursorIndexOfReffNo);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        final String _tmpWeight;
        _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
        final String _tmpAmount;
        _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
        final String _tmpCurrency;
        _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
        final String _tmpServiceLevel;
        _tmpServiceLevel = _cursor.getString(_cursorIndexOfServiceLevel);
        final String _tmpInstructions;
        _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
        final String _tmpWorkStatus;
        _tmpWorkStatus = _cursor.getString(_cursorIndexOfWorkStatus);
        final String _tmpArrivalTime;
        _tmpArrivalTime = _cursor.getString(_cursorIndexOfArrivalTime);
        final String _tmpCompleteTime;
        _tmpCompleteTime = _cursor.getString(_cursorIndexOfCompleteTime);
        final long _tmpDataId;
        _tmpDataId = _cursor.getLong(_cursorIndexOfDataId);
        final long _tmpStopId;
        _tmpStopId = _cursor.getLong(_cursorIndexOfStopId);
        final String _tmpDataEntered;
        _tmpDataEntered = _cursor.getString(_cursorIndexOfDataEntered);
        final int _tmpStatusId;
        _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
        final int _tmpReasonId;
        _tmpReasonId = _cursor.getInt(_cursorIndexOfReasonId);
        final int _tmpQtyEntered;
        _tmpQtyEntered = _cursor.getInt(_cursorIndexOfQtyEntered);
        final double _tmpWeightEntered;
        _tmpWeightEntered = _cursor.getDouble(_cursorIndexOfWeightEntered);
        final int _tmpImageTaken;
        _tmpImageTaken = _cursor.getInt(_cursorIndexOfImageTaken);
        final String _tmpImagePath;
        _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
        final String _tmpAreaType;
        _tmpAreaType = _cursor.getString(_cursorIndexOfAreaType);
        final String _tmpDriverComment;
        _tmpDriverComment = _cursor.getString(_cursorIndexOfDriverComment);
        final String _tmpDriverNotice;
        _tmpDriverNotice = _cursor.getString(_cursorIndexOfDriverNotice);
        final String _tmpCashCollect;
        _tmpCashCollect = _cursor.getString(_cursorIndexOfCashCollect);
        final int _tmpConsolicatedId;
        _tmpConsolicatedId = _cursor.getInt(_cursorIndexOfConsolicatedId);
        final double _tmpWaitingTime;
        _tmpWaitingTime = _cursor.getDouble(_cursorIndexOfWaitingTime);
        final double _tmpCod;
        _tmpCod = _cursor.getDouble(_cursorIndexOfCod);
        final String _tmpDisAmt;
        _tmpDisAmt = _cursor.getString(_cursorIndexOfDisAmt);
        final String _tmpAccessorial;
        _tmpAccessorial = _cursor.getString(_cursorIndexOfAccessorial);
        final String _tmpCodCurrency;
        _tmpCodCurrency = _cursor.getString(_cursorIndexOfCodCurrency);
        final double _tmpMileage;
        _tmpMileage = _cursor.getDouble(_cursorIndexOfMileage);
        _item = new TaskInfoEntity(_tmpTaskId,_tmpTaskType,_tmpAppId,_tmpName,_tmpAddress,_tmpApptNo,_tmpPostalCode,_tmpCity,_tmpEmail,_tmpPhoneNo,_tmpLatitude,_tmpLongitude,_tmpBarcode,_tmpAgent,_tmpManifest,_tmpReffNo,_tmpQuantity,_tmpWeight,_tmpAmount,_tmpCurrency,_tmpServiceLevel,_tmpInstructions,_tmpWorkStatus,_tmpArrivalTime,_tmpCompleteTime,_tmpDataId,_tmpStopId,_tmpDataEntered,_tmpStatusId,_tmpReasonId,_tmpQtyEntered,_tmpWeightEntered,_tmpImageTaken,_tmpImagePath,_tmpAreaType,_tmpDriverComment,_tmpDriverNotice,_tmpCashCollect,_tmpConsolicatedId,_tmpWaitingTime,_tmpCod,_tmpDisAmt,_tmpAccessorial,_tmpCodCurrency,_tmpMileage,_tmpSeqNo,_tmpBatchId,_tmpRunNo);
        final String _tmpLocationKey;
        _tmpLocationKey = _cursor.getString(_cursorIndexOfLocationKey);
        _item.setLocationKey(_tmpLocationKey);
        final int _tmpRecordStatus;
        _tmpRecordStatus = _cursor.getInt(_cursorIndexOfRecordStatus);
        _item.setRecordStatus(_tmpRecordStatus);
        final String _tmpSignature;
        _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
        _item.setSignature(_tmpSignature);
        final String _tmpSignatureName;
        _tmpSignatureName = _cursor.getString(_cursorIndexOfSignatureName);
        _item.setSignatureName(_tmpSignatureName);
        final String _tmpSignatureTime;
        _tmpSignatureTime = _cursor.getString(_cursorIndexOfSignatureTime);
        _item.setSignatureTime(_tmpSignatureTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<TaskInfoEntity> getTaskInfoLimit1() {
    final String _sql = "Select * from taskInfoTable limit 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "taskId");
      final int _cursorIndexOfTaskType = CursorUtil.getColumnIndexOrThrow(_cursor, "taskType");
      final int _cursorIndexOfAppId = CursorUtil.getColumnIndexOrThrow(_cursor, "appId");
      final int _cursorIndexOfSeqNo = CursorUtil.getColumnIndexOrThrow(_cursor, "seqNo");
      final int _cursorIndexOfBatchId = CursorUtil.getColumnIndexOrThrow(_cursor, "batchId");
      final int _cursorIndexOfRunNo = CursorUtil.getColumnIndexOrThrow(_cursor, "runNo");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfApptNo = CursorUtil.getColumnIndexOrThrow(_cursor, "apptNo");
      final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postalCode");
      final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPhoneNo = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNo");
      final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
      final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
      final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
      final int _cursorIndexOfAgent = CursorUtil.getColumnIndexOrThrow(_cursor, "agent");
      final int _cursorIndexOfManifest = CursorUtil.getColumnIndexOrThrow(_cursor, "manifest");
      final int _cursorIndexOfReffNo = CursorUtil.getColumnIndexOrThrow(_cursor, "reffNo");
      final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
      final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfServiceLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "serviceLevel");
      final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
      final int _cursorIndexOfWorkStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "workStatus");
      final int _cursorIndexOfArrivalTime = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalTime");
      final int _cursorIndexOfCompleteTime = CursorUtil.getColumnIndexOrThrow(_cursor, "completeTime");
      final int _cursorIndexOfDataId = CursorUtil.getColumnIndexOrThrow(_cursor, "dataId");
      final int _cursorIndexOfStopId = CursorUtil.getColumnIndexOrThrow(_cursor, "stopId");
      final int _cursorIndexOfDataEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "dataEntered");
      final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
      final int _cursorIndexOfReasonId = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonId");
      final int _cursorIndexOfQtyEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "qtyEntered");
      final int _cursorIndexOfWeightEntered = CursorUtil.getColumnIndexOrThrow(_cursor, "weightEntered");
      final int _cursorIndexOfImageTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "imageTaken");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
      final int _cursorIndexOfAreaType = CursorUtil.getColumnIndexOrThrow(_cursor, "areaType");
      final int _cursorIndexOfDriverComment = CursorUtil.getColumnIndexOrThrow(_cursor, "driverComment");
      final int _cursorIndexOfDriverNotice = CursorUtil.getColumnIndexOrThrow(_cursor, "driverNotice");
      final int _cursorIndexOfCashCollect = CursorUtil.getColumnIndexOrThrow(_cursor, "cashCollect");
      final int _cursorIndexOfConsolicatedId = CursorUtil.getColumnIndexOrThrow(_cursor, "consolicatedId");
      final int _cursorIndexOfWaitingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "waitingTime");
      final int _cursorIndexOfCod = CursorUtil.getColumnIndexOrThrow(_cursor, "cod");
      final int _cursorIndexOfDisAmt = CursorUtil.getColumnIndexOrThrow(_cursor, "disAmt");
      final int _cursorIndexOfAccessorial = CursorUtil.getColumnIndexOrThrow(_cursor, "accessorial");
      final int _cursorIndexOfCodCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "codCurrency");
      final int _cursorIndexOfMileage = CursorUtil.getColumnIndexOrThrow(_cursor, "mileage");
      final int _cursorIndexOfLocationKey = CursorUtil.getColumnIndexOrThrow(_cursor, "locationKey");
      final int _cursorIndexOfRecordStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "recordStatus");
      final int _cursorIndexOfSignature = CursorUtil.getColumnIndexOrThrow(_cursor, "signature");
      final int _cursorIndexOfSignatureName = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureName");
      final int _cursorIndexOfSignatureTime = CursorUtil.getColumnIndexOrThrow(_cursor, "signatureTime");
      final List<TaskInfoEntity> _result = new ArrayList<TaskInfoEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TaskInfoEntity _item;
        final long _tmpTaskId;
        _tmpTaskId = _cursor.getLong(_cursorIndexOfTaskId);
        final String _tmpTaskType;
        _tmpTaskType = _cursor.getString(_cursorIndexOfTaskType);
        final int _tmpAppId;
        _tmpAppId = _cursor.getInt(_cursorIndexOfAppId);
        final int _tmpSeqNo;
        _tmpSeqNo = _cursor.getInt(_cursorIndexOfSeqNo);
        final String _tmpBatchId;
        _tmpBatchId = _cursor.getString(_cursorIndexOfBatchId);
        final int _tmpRunNo;
        _tmpRunNo = _cursor.getInt(_cursorIndexOfRunNo);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpAddress;
        _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        final String _tmpApptNo;
        _tmpApptNo = _cursor.getString(_cursorIndexOfApptNo);
        final String _tmpPostalCode;
        _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
        final String _tmpCity;
        _tmpCity = _cursor.getString(_cursorIndexOfCity);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        final String _tmpPhoneNo;
        _tmpPhoneNo = _cursor.getString(_cursorIndexOfPhoneNo);
        final String _tmpLatitude;
        _tmpLatitude = _cursor.getString(_cursorIndexOfLatitude);
        final String _tmpLongitude;
        _tmpLongitude = _cursor.getString(_cursorIndexOfLongitude);
        final String _tmpBarcode;
        _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
        final String _tmpAgent;
        _tmpAgent = _cursor.getString(_cursorIndexOfAgent);
        final String _tmpManifest;
        _tmpManifest = _cursor.getString(_cursorIndexOfManifest);
        final String _tmpReffNo;
        _tmpReffNo = _cursor.getString(_cursorIndexOfReffNo);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        final String _tmpWeight;
        _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
        final String _tmpAmount;
        _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
        final String _tmpCurrency;
        _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
        final String _tmpServiceLevel;
        _tmpServiceLevel = _cursor.getString(_cursorIndexOfServiceLevel);
        final String _tmpInstructions;
        _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
        final String _tmpWorkStatus;
        _tmpWorkStatus = _cursor.getString(_cursorIndexOfWorkStatus);
        final String _tmpArrivalTime;
        _tmpArrivalTime = _cursor.getString(_cursorIndexOfArrivalTime);
        final String _tmpCompleteTime;
        _tmpCompleteTime = _cursor.getString(_cursorIndexOfCompleteTime);
        final long _tmpDataId;
        _tmpDataId = _cursor.getLong(_cursorIndexOfDataId);
        final long _tmpStopId;
        _tmpStopId = _cursor.getLong(_cursorIndexOfStopId);
        final String _tmpDataEntered;
        _tmpDataEntered = _cursor.getString(_cursorIndexOfDataEntered);
        final int _tmpStatusId;
        _tmpStatusId = _cursor.getInt(_cursorIndexOfStatusId);
        final int _tmpReasonId;
        _tmpReasonId = _cursor.getInt(_cursorIndexOfReasonId);
        final int _tmpQtyEntered;
        _tmpQtyEntered = _cursor.getInt(_cursorIndexOfQtyEntered);
        final double _tmpWeightEntered;
        _tmpWeightEntered = _cursor.getDouble(_cursorIndexOfWeightEntered);
        final int _tmpImageTaken;
        _tmpImageTaken = _cursor.getInt(_cursorIndexOfImageTaken);
        final String _tmpImagePath;
        _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
        final String _tmpAreaType;
        _tmpAreaType = _cursor.getString(_cursorIndexOfAreaType);
        final String _tmpDriverComment;
        _tmpDriverComment = _cursor.getString(_cursorIndexOfDriverComment);
        final String _tmpDriverNotice;
        _tmpDriverNotice = _cursor.getString(_cursorIndexOfDriverNotice);
        final String _tmpCashCollect;
        _tmpCashCollect = _cursor.getString(_cursorIndexOfCashCollect);
        final int _tmpConsolicatedId;
        _tmpConsolicatedId = _cursor.getInt(_cursorIndexOfConsolicatedId);
        final double _tmpWaitingTime;
        _tmpWaitingTime = _cursor.getDouble(_cursorIndexOfWaitingTime);
        final double _tmpCod;
        _tmpCod = _cursor.getDouble(_cursorIndexOfCod);
        final String _tmpDisAmt;
        _tmpDisAmt = _cursor.getString(_cursorIndexOfDisAmt);
        final String _tmpAccessorial;
        _tmpAccessorial = _cursor.getString(_cursorIndexOfAccessorial);
        final String _tmpCodCurrency;
        _tmpCodCurrency = _cursor.getString(_cursorIndexOfCodCurrency);
        final double _tmpMileage;
        _tmpMileage = _cursor.getDouble(_cursorIndexOfMileage);
        _item = new TaskInfoEntity(_tmpTaskId,_tmpTaskType,_tmpAppId,_tmpName,_tmpAddress,_tmpApptNo,_tmpPostalCode,_tmpCity,_tmpEmail,_tmpPhoneNo,_tmpLatitude,_tmpLongitude,_tmpBarcode,_tmpAgent,_tmpManifest,_tmpReffNo,_tmpQuantity,_tmpWeight,_tmpAmount,_tmpCurrency,_tmpServiceLevel,_tmpInstructions,_tmpWorkStatus,_tmpArrivalTime,_tmpCompleteTime,_tmpDataId,_tmpStopId,_tmpDataEntered,_tmpStatusId,_tmpReasonId,_tmpQtyEntered,_tmpWeightEntered,_tmpImageTaken,_tmpImagePath,_tmpAreaType,_tmpDriverComment,_tmpDriverNotice,_tmpCashCollect,_tmpConsolicatedId,_tmpWaitingTime,_tmpCod,_tmpDisAmt,_tmpAccessorial,_tmpCodCurrency,_tmpMileage,_tmpSeqNo,_tmpBatchId,_tmpRunNo);
        final String _tmpLocationKey;
        _tmpLocationKey = _cursor.getString(_cursorIndexOfLocationKey);
        _item.setLocationKey(_tmpLocationKey);
        final int _tmpRecordStatus;
        _tmpRecordStatus = _cursor.getInt(_cursorIndexOfRecordStatus);
        _item.setRecordStatus(_tmpRecordStatus);
        final String _tmpSignature;
        _tmpSignature = _cursor.getString(_cursorIndexOfSignature);
        _item.setSignature(_tmpSignature);
        final String _tmpSignatureName;
        _tmpSignatureName = _cursor.getString(_cursorIndexOfSignatureName);
        _item.setSignatureName(_tmpSignatureName);
        final String _tmpSignatureTime;
        _tmpSignatureTime = _cursor.getString(_cursorIndexOfSignatureTime);
        _item.setSignatureTime(_tmpSignatureTime);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
