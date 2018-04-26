package com.hm.fomakin.passkeepres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hm.fomakin.passkeepres.Model.CardGroup;

public class CardModifyActivity extends AppCompatActivity {

    private String intentAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_modify);

        Intent intent = getIntent();
        intentAction = intent.getAction();

        Spinner spinnerCardGroup = findViewById(R.id.spinnerCardGroup);

        //String[] data = {"Program", "WebSite"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        /*ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new CardGroup[] {
                new CardGroup(1, "Program"),
                new CardGroup(2, "WebSite")
        });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCardGroup.setAdapter(adapter);*/
        /*for (int i = 0; i <= adapter.getCount(); i++) {
            CardGroup cg = (CardGroup) adapter.getItem(i);
            if (cg.getGroupName().equals("Program")) {
                spinnerCardGroup.setSelection(i);
                break;
            }
        }*/
    }
}
