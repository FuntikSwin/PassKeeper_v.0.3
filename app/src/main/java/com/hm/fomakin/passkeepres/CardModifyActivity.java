package com.hm.fomakin.passkeepres;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.TextView;

import com.hm.fomakin.passkeepres.Adapter.CardModifyFieldsAdapter;
import com.hm.fomakin.passkeepres.Database.IDbHelper;
import com.hm.fomakin.passkeepres.Model.Card;
import com.hm.fomakin.passkeepres.Model.CardField;
import com.hm.fomakin.passkeepres.Model.CardFieldValueType;
import com.hm.fomakin.passkeepres.Model.CardGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CardModifyActivity extends AppCompatActivity {

    private boolean isAddNewCard;
    private Card mCard;
    private static final String emptyCardGroupCaption = "Без группы";

    @Inject
    IDbHelper dbHelper;

    private EditText etCardCaption;
    private Spinner spinnerCardGroup;
    private ArrayAdapter<String> adapterSpinner;
    private ListView lvFields;
    private CardModifyFieldsAdapter adapterModifyFields;
    private List<CardGroup> dbCardGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_modify);
        App.getDbHelperComponent().inject(this);

        Intent intent = getIntent();
        String intentAction = intent.getAction();
        if (intentAction.isEmpty()) {
            finish();
            return;
        }
        isAddNewCard = intentAction == "com.hm.fomakin.passkeepers.addcard";

        mCard = (Card) intent.getSerializableExtra("Card");

        Toolbar toolbar = findViewById(R.id.ToolbarCardModify);
        setSupportActionBar(toolbar);

        spinnerCardGroup = findViewById(R.id.spinnerCardGroup);
        etCardCaption = findViewById(R.id.etCardCaption);

        dbCardGroups = dbHelper.getCardGroups();
        List<String> dataSpinner = new ArrayList<>();
        for (int i = 0; i < dbCardGroups.size(); i++) {
            String groupName = dbCardGroups.get(i).getGroupName();
            if (groupName.trim().length() == 0) {
                groupName = emptyCardGroupCaption;
            }
            dataSpinner.add(groupName);
        }

        adapterSpinner = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                dataSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCardGroup.setAdapter(adapterSpinner);

        lvFields = findViewById(R.id.lvCardModifyFields);
        adapterModifyFields = new CardModifyFieldsAdapter(this, mCard.getCardFields());
        lvFields.setAdapter(adapterModifyFields);

        Button btnAddField = findViewById(R.id.btnAddField);
        btnAddField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(CardModifyActivity.this, view);
                Menu menu = popupMenu.getMenu();

                List<CardFieldValueType> valueTypes = dbHelper.getCardFieldValueTypes();
                for (CardFieldValueType vType : valueTypes) {
                    menu.add(vType.getTypeName());
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        CardFieldValueType currFieldType = null;
                        for (CardFieldValueType vType : valueTypes) {
                            if (vType.getTypeName().equals(menuItem.getTitle().toString())) {
                                currFieldType = vType;
                                break;
                            }
                        }
                        if (currFieldType != null) {

                            LayoutInflater layoutInflater = LayoutInflater.from(CardModifyActivity.this);
                            final View dlgView = layoutInflater.inflate(R.layout.dialog_add_card_field, null);

                            final EditText etFieldCaption = dlgView.findViewById(R.id.etFieldCaption);
                            final CardFieldValueType finalFieldType = currFieldType;
                            AlertDialog dialog = new AlertDialog.Builder(CardModifyActivity.this)
                                    .setTitle("Add " + finalFieldType.getTypeName() + " field")
                                    .setView(dlgView)
                                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            addCardField(finalFieldType, etFieldCaption.getText().toString());
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            return;
                                        }
                                    })
                                    .create();
                            dialog.show();
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        prepareModifyData();
    }

    private void addCardField(CardFieldValueType cardFieldValueType, String fieldCaption) {
        CardField cardField = new CardField(0, fieldCaption, "", cardFieldValueType);
        mCard.getCardFields().add(cardField);
        adapterModifyFields.notifyDataSetChanged();
    }

    private void prepareModifyData() {
        etCardCaption.setText(mCard.getCaption());

        for (int i = 0; i < adapterSpinner.getCount(); i++) {
            String spinnerItem = adapterSpinner.getItem(i);
            if ((mCard.getCardGroup() == null || mCard.getCardGroup().getGroupName().trim().length() == 0)
                    && spinnerItem.equals(emptyCardGroupCaption)) {
                spinnerCardGroup.setSelection(i);
                break;
            }

            if (mCard.getCardGroup().getGroupName().equals(spinnerItem)) {
                spinnerCardGroup.setSelection(i);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card_modify, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void updateCard() {
        mCard = new Card();
        mCard.setCaption(etCardCaption.getText().toString());
        String groupName = (String) spinnerCardGroup.getSelectedItem();
        for (CardGroup cg : dbCardGroups) {
            if ((groupName.equals(emptyCardGroupCaption) && cg.getGroupName().equals(" ")) || cg.getGroupName().equals(groupName)) {
                mCard.setCardGroup(cg);
                break;
            }
        }

        List<CardField> cardFields = new ArrayList<>();
        for (int i = 0; i < lvFields.getChildCount(); i++) {
            EditText etFieldValue = lvFields.getChildAt(i).findViewById(R.id.etFieldValue);
            CardField cf = (CardField) adapterModifyFields.getItem(i);
            cf.setValue(etFieldValue.getText().toString());
            cardFields.add(cf);
        }
        mCard.setCardFields(cardFields);

        if (isAddNewCard) {
            dbHelper.addCard(mCard);
        } else {
            dbHelper.updateCard(mCard);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_save_card:
                updateCard();
                if (!isAddNewCard) {
                    Intent intent = new Intent(CardModifyActivity.this, CardInfoActivity.class);
                    intent.putExtra("Card", mCard);
                    startActivityForResult(intent, 1);
                    break;
                }
                finish();
                break;
            case R.id.action_cancel:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        EditText et = lvFields.getChildAt(0).findViewById(R.id.etFieldValue);
        String val = et.getText().toString();
        super.onBackPressed();
    }
}