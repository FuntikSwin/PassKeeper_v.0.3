package com.hm.fomakin.passkeepres.Database;

import com.hm.fomakin.passkeepres.Model.Card;
import com.hm.fomakin.passkeepres.Model.CardField;
import com.hm.fomakin.passkeepres.Model.CardFieldValueType;
import com.hm.fomakin.passkeepres.Model.CardGroup;

import java.util.ArrayList;
import java.util.List;

public class DbMockHelper implements IDbHelper {

    private List<Card> mockCards;
    private List<CardFieldValueType> cardFieldValueTypes;
    private List<CardField> cardFields;
    private List<CardGroup> cardGroups;

    public DbMockHelper() {
        doPrepareCardGroups();
        doPrepareFieldValueTypes();
        doPrepareMockData();
    }

    private void doPrepareCardGroups() {
        cardGroups = new ArrayList<>();
        cardGroups.add(new CardGroup(1, " "));
        cardGroups.add(new CardGroup(2, "Program"));
        cardGroups.add(new CardGroup(3, "WebSite"));
    }

    private CardGroup getCardGroup(String groupName) {
        for (CardGroup group : cardGroups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    private void doPrepareFieldValueTypes() {
        cardFieldValueTypes = new ArrayList<>();
        cardFieldValueTypes.add(new CardFieldValueType(1, "TextView"));
        cardFieldValueTypes.add(new CardFieldValueType(2, "Password"));
        cardFieldValueTypes.add(new CardFieldValueType(3, "PasswordNumeric"));
        cardFieldValueTypes.add(new CardFieldValueType(4, "Email"));
        cardFieldValueTypes.add(new CardFieldValueType(5, "MultilineText"));
    }

    private CardFieldValueType getCardFieldValueType(String typeName) {
        for (CardFieldValueType type : cardFieldValueTypes) {
            if (type.getTypeName().equals(typeName)) {
                return type;
            }
        }
        return null;
    }

    private void doPrepareMockData() {
        cardFields = new ArrayList<>();
        cardFields.add(new CardField(1, "Login", "admin", getCardFieldValueType("TextView")));
        cardFields.add(new CardField(2, "Password", "Ab123456", getCardFieldValueType("Password")));
        cardFields.add(new CardField(3, "EMail", "funtik@gmail.com", getCardFieldValueType("Email")));
        cardFields.add(new CardField(4, "Description", "sdfjakdjf a akfkadhflkha adkfhakdflakdhfkajdf sdfsadfakj dfasdf hakhdfhasdhf haksdfhkahsdfadfjhkasdhfk asf jkahdf END", getCardFieldValueType("MultilineText")));

        mockCards = new ArrayList<>();
        mockCards.add(new Card(1, "Card1", getCardGroup("Program"), cardFields));
        mockCards.add(new Card(2, "Card2", getCardGroup("WebSite"), cardFields));

        cardFields = new ArrayList<>();
        cardFields.add(new CardField(1, "Login", "admin", getCardFieldValueType("TextView")));
        cardFields.add(new CardField(2, "Password", "Ab123456", getCardFieldValueType("Password")));

        mockCards.add(new Card(3, "Card3", getCardGroup(""), cardFields));
    }

    public List<Card> getCards() {
        List<Card> cards = mockCards;
        return cards;
    }

    @Override
    public void removeCard(Card card) {
        mockCards.remove(card);
    }

    public List<CardGroup> getCardGroups() {
        return cardGroups;
    }

    @Override
    public List<CardField> getCardFields(Card card) {
        return null;
    }
}
