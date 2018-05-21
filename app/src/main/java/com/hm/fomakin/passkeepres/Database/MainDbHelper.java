package com.hm.fomakin.passkeepres.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hm.fomakin.passkeepres.Model.Card;
import com.hm.fomakin.passkeepres.Model.CardField;
import com.hm.fomakin.passkeepres.Model.CardFieldValueType;
import com.hm.fomakin.passkeepres.Model.CardGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainDbHelper implements IDbHelper {

    private PassKeeperDbHelper mDbHelper;
    private SQLiteDatabase dataBase;

    public MainDbHelper(Context context) {
        mDbHelper = new PassKeeperDbHelper(context);
    }

    @Override
    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();

        dataBase = mDbHelper.getReadableDatabase();
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
    public void updateCard(Card card) {

    }

    @Override
    public void addCard(Card card) {
        dataBase = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("Caption", card.getCaption());
        cv.put("CardGroupId", card.getCardGroup().getId());
        dataBase.insert("Card", null, cv);

        for (CardField field : card.getCardFields()) {
            cv.clear();
            cv.put("Caption", field.getCaption());
            cv.put("FieldValue", field.getValue());
            cv.put("ValueTypeId", field.getCardFieldValueType().getId());
            cv.put("CardId", card.getId());
            dataBase.insert("CardField", null, cv);
        }
    }

    @Override
    public List<CardGroup> getCardGroups() {
        List<CardGroup> groups = new ArrayList<>();

        dataBase = mDbHelper.getReadableDatabase();
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

        if (dataBase == null) {
            dataBase = mDbHelper.getReadableDatabase();
        }
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
    public List<CardFieldValueType> getCardFieldValueTypes() {
        List<CardFieldValueType> valueTypes = new ArrayList<>();

        dataBase = mDbHelper.getReadableDatabase();
        Cursor cursor = dataBase.rawQuery("select t.Id, t.TypeName from CardFieldValueType t", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            valueTypes.add(new CardFieldValueType(cursor.getLong(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();

        return valueTypes;
    }
}
