package pvtltd.ecodinghub.com.cashmanager.helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String database = "cashflow.db";
    private static final String id = "id";
    private static final String name = "name";
    private static final String monthlyCashflow = "monthlyCashflow";
    private static final String tableIncome = "tableIncome";
    private static final String tableExpenses = "tableExpenses";
    private static final String tableAssets = "tableAssets";
    private static final String tableLiabilities = "tableLiabilities";
    private static final String amount = "amount";

    private Context my;
    private SQLiteDatabase dbr = this.getReadableDatabase();
    private SQLiteDatabase dbw = this.getWritableDatabase();

    public DatabaseHelper(Context context) {
        super(context, database, null, 1);
        my = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tableIncome + " ( " + id + " INTEGER ," + name + " TEXT ," + monthlyCashflow + " INTEGER )");
        db.execSQL("create table " + tableExpenses + " ( " + id + " INTEGER  ," + name + " TEXT ," + monthlyCashflow + " INTEGER )");
        db.execSQL("create table " + tableAssets + " ( " + id + " INTEGER ," + name + " TEXT ," + monthlyCashflow + " INTEGER ," + amount + " INTEGER )");
        db.execSQL("create table " + tableLiabilities + " ( " + id + " INTEGER ," + name + " TEXT ," + monthlyCashflow + " INTEGER ," + amount + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean insertAmount(String name, int monthlyCashflow, String table) {
        int itemId = dbw.rawQuery("SELECT * FROM " + table, null).getCount();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", itemId + 1);
        contentValues.put(this.name, name);
        contentValues.put(this.monthlyCashflow, monthlyCashflow);
        long result = dbw.insert(table, null, contentValues);
        return result != -1;
    }

    public boolean insertAmount(String name, int amount, int monthlyCashflow, String table) {
        int itemId = dbw.rawQuery("SELECT * FROM " + table, null).getCount();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", itemId + 1);
        contentValues.put(this.name, name);
        contentValues.put(this.amount, amount);
        contentValues.put(this.monthlyCashflow, monthlyCashflow);
        long result = dbw.insert(table, null, contentValues);
        return result != -1;
    }

    public Cursor viewAmount(String tableColumn) {
        return dbr.rawQuery("SELECT * FROM " + tableColumn, null);
    }

    public int updateItem(String itemName, int monthlyCashflow, String table, String id) {
        ContentValues values = new ContentValues();
        values.put(this.name, itemName);
        values.put(this.monthlyCashflow, monthlyCashflow);
        return dbw.update(table, values, " id = ? ", new String[]{id});
    }

    public int updateItemLarge(String itemName, int amount, String table, String id, int monthlyCashflow) {
        ContentValues values = new ContentValues();
        values.put(this.name, itemName);
        values.put(this.amount, amount);
        values.put(this.monthlyCashflow, monthlyCashflow);
        return dbw.update(table, values, " id = ? ", new String[]{id});
    }

    public Cursor columnSum(String table, String column) {
        return dbr.rawQuery("SELECT SUM(" + column + ") FROM " + table, null);
    }


    public Cursor getItem(String tableName, String id) {
        return dbr.rawQuery("SELECT * from " + tableName + " WHERE id = ? ", new String[]{id});
    }


    public void deleteItem(String table, int pos) {
        dbw.delete(table, " id = ? ", new String[]{String.valueOf(pos)});
        Cursor cursor = dbr.rawQuery("SELECT id from " + table, null);
        for (int i = pos; i <= cursor.getCount(); i++) {
            ContentValues values = new ContentValues();
            values.put("id", i);
            dbw.update(table, values, " id = ? ", new String[]{String.valueOf(i + 1)});
        }
    }

    public void resetAllPreferences() {
        dbw.delete(tableAssets, null, null);
        dbw.delete(tableExpenses, null, null);
        dbw.delete(tableIncome, null, null);
        dbw.delete(tableLiabilities, null, null);
    }


}
