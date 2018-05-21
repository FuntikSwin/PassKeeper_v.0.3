package com.hm.fomakin.passkeepres;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hm.fomakin.passkeepres.Adapter.CardsAdapter;
import com.hm.fomakin.passkeepres.Database.DbMockHelper;
import com.hm.fomakin.passkeepres.Database.IDbHelper;
import com.hm.fomakin.passkeepres.Model.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    //private DbMockHelper mDbHelper;
    @Inject
    IDbHelper mDbHelper;

    private ListView lvCards;
    private List<Card> cards;
    private CardsAdapter cardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getDbHelperComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Card newCard = new Card(0, "", mDbHelper.getCardGroups().get(0), new ArrayList<>());
                Intent intent = new Intent("com.hm.fomakin.passkeepers.addcard");
                intent.putExtra("Card", newCard);
                startActivity(intent);
            }
        });

        lvCards = findViewById(R.id.lvCards);
        lvCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) parent.getItemAtPosition(position);
                //Snackbar.make(view, "Card caption: " + card.getCaption(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, CardInfoActivity.class);
                intent.putExtra("Card", card);
                startActivity(intent);
            }
        });

        registerForContextMenu(lvCards);

        updateCards();

        String tmp = "1234";
    }

    @Override
    protected void onResume() {
        updateCards();
        super.onResume();
    }

    private void updateCards() {
        cards = mDbHelper.getCards();
        if (cardsAdapter == null) {
            cardsAdapter = new CardsAdapter(this, cards);
            lvCards.setAdapter(cardsAdapter);
            return;
        }
        cardsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_test) {
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lvCards) {
            ListView lv = (ListView) v;
            Card cardItem = (Card) lv.getItemAtPosition(((AdapterView.AdapterContextMenuInfo) menuInfo).position);

            menu.add("Удалить");
            MenuItem mi = menu.getItem(0);
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Card currCard = (Card) cardsAdapter.getItem(info.position);
                    mDbHelper.removeCard(currCard);
                    updateCards();

                    return true;
                }
            });
        }
    }


}
