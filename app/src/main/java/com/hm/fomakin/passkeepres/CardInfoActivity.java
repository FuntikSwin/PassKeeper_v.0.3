package com.hm.fomakin.passkeepres;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hm.fomakin.passkeepres.Adapter.CardInfoFieldsAdapter;
import com.hm.fomakin.passkeepres.Model.Card;

public class CardInfoActivity extends AppCompatActivity {

    private Card mCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        Toolbar toolbar = findViewById(R.id.ToolbarCardInfo);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mCard = (Card) intent.getSerializableExtra("Card");

        TextView tvCardCaption = findViewById(R.id.tvCardCaption);
        tvCardCaption.setText(mCard.getCaption());

        TextView tvGroupName = findViewById(R.id.tvCardGroupName);
        tvGroupName.setText(
                mCard.getCardGroup() != null && !mCard.getCardGroup().getGroupName().isEmpty()
                        ? mCard.getCardGroup().getGroupName()
                        : "");

        ListView lvCardFields = findViewById(R.id.lvCardInfoFields);
        CardInfoFieldsAdapter adapter = new CardInfoFieldsAdapter(this, mCard.getCardFields());
        lvCardFields.setAdapter(adapter);

        lvCardFields.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvValue = view.findViewById(R.id.tvFieldValue);
                if (tvValue.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    tvValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else if (tvValue.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
                    tvValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/
        int itemId = item.getItemId();
        if (itemId == R.id.action_edit_card) {
            Intent intent = new Intent(CardInfoActivity.this, CardModifyActivity.class);
            intent.putExtra("Card", mCard);
            startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }
}
