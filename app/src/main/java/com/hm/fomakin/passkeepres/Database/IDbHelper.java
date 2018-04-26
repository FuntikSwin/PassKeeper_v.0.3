package com.hm.fomakin.passkeepres.Database;

import com.hm.fomakin.passkeepres.Model.Card;
import com.hm.fomakin.passkeepres.Model.CardField;
import com.hm.fomakin.passkeepres.Model.CardGroup;

import java.util.List;

public interface IDbHelper {

    List<Card> getCards();
    List<CardGroup> getCardGroups();
    List<CardField> getCardFields(Card card);

}
