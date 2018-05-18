package com.hm.fomakin.passkeepres.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.hm.fomakin.passkeepres.Model.Card;
import com.hm.fomakin.passkeepres.Model.CardField;
import com.hm.fomakin.passkeepres.Model.CardFieldValueType;
import com.hm.fomakin.passkeepres.Model.CardGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DbSQLiteHelper extends SQLiteOpenHelper implements IDbHelper {

    private final Context context;

    private static String DB_NAME = "PassKeepers.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase dataBase;
    private boolean needUpdate = false;

    public DbSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.context = context;

        copyDataBase();
        this.getReadableDatabase();

        try {
            updateDataBase();
        } catch (IOException ex) {
            throw new Error("UnableToUpdateDatabase");
        }
        dataBase = getWritableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (needUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists()) {
                dbFile.delete();
            }

            copyDataBase();
            needUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException ex) {
                throw new Error("ErrorCopyingDatabase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream in = context.getAssets().open(DB_NAME);
        OutputStream out = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        out.flush();
        out.close();
        in.close();
    }

    public boolean openDataBase() {
        dataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return dataBase != null;
    }

    @Override
    public synchronized void close() {
        if (dataBase != null) {
            dataBase.close();
        }
        super.close();
    }

    @Override
    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();

        Cursor cursor = dataBase.rawQuery(
                "select c.Id as CardId, c.Caption as CardCaption, c.CardGroupId, cs.GroupName " +
                "from Card c " +
                "inner join CardGroup cs ON c.CardGroupId = cs.Id", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Card card = new Card(
                    cursor.getInt(0),
                    cursor.getString(1),
                    !cursor.isNull(2)
                            ? new CardGroup(cursor.getInt(2), cursor.getString(3))
                            : null,
                    null);
            cards.add(card);
            cursor.moveToNext();
        }
        cursor.close();

        for (Iterator<Card> cardIterator = cards.iterator(); cardIterator.hasNext();) {
            Card card = cardIterator.next();
            card.setCardFields(getCardFields(card));
        }

        return cards;
    }

    @Override
    public void removeCard(Card card) {

    }

    @Override
    public List<CardGroup> getCardGroups() {
        List<CardGroup> groups = new ArrayList<>();

        Cursor cursor = dataBase.rawQuery("select * from CardGroup", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            groups.add(new CardGroup(cursor.getInt(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();

        return groups;
    }

    @Override
    public List<CardField> getCardFields(Card card) {
        List<CardField> cardFields = new ArrayList<>();

        Cursor cursor = dataBase.rawQuery(
                "select cf.Id, cf.Caption, cf.FieldValue, cf.ValueTypeId, ft.TypeName " +
                        "from CardField cf " +
                        "left join CardFieldValueType ft ON ft.Id = cf.ValueTypeId " +
                        "where cf.CardId = " + Long.toString(card.getId()), null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cardFields.add(new CardField(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    !cursor.isNull(3)
                            ? new CardFieldValueType(cursor.getInt(3), cursor.getString(4))
                            : null));
            cursor.moveToNext();
        }
        cursor.close();

        return cardFields;
    }

    @Override
    public void updateCard(Card card) {

    }

    @Override
    public List<CardFieldValueType> getCardFieldValueTypes() {
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            needUpdate = true;
        }
    }
}
