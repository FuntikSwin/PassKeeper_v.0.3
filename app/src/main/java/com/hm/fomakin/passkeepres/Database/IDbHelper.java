package com.hm.fomakin.passkeepres.Database;

import com.hm.fomakin.passkeepres.Model.Card;
import com.hm.fomakin.passkeepres.Model.CardField;
import com.hm.fomakin.passkeepres.Model.CardFieldValueType;
import com.hm.fomakin.passkeepres.Model.CardGroup;

import java.util.List;

public interface IDbHelper {

    List<Card> getCards();
    void removeCard(Card card);
    void updateCard(Card card);
    void addCard(Card card);

    List<CardGroup> getCardGroups();

    List<CardField> getCardFields(Card card);

    List<CardFieldValueType> getCardFieldValueTypes();

}
