package com.hm.fomakin.passkeepres.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.hm.fomakin.passkeepres.Model.CardField;
import com.hm.fomakin.passkeepres.R;

import java.util.List;

public class CardModifyFieldsAdapter extends BaseAdapter {

    private Context mContext;
    private List<CardField> mCardFields;

    public CardModifyFieldsAdapter(Context mContext, List<CardField> mCardFields) {
        this.mContext = mContext;
        this.mCardFields = mCardFields;
    }

    private void removeCardField(CardField cardField) {
        mCardFields.remove(cardField);
        this.notifyDataSetChanged();
    }

    public List<CardField> getCurrCardFields() {
        return mCardFields;
    }



    @Override
    public int getCount() {
        return mCardFields != null ? mCardFields.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mCardFields != null ? mCardFields.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return mCardFields != null ? mCardFields.get(position).getId() : 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.cardmodify_field_listview_item, null);

        final CardField cardField = mCardFields != null ? mCardFields.get(position) : null;
        if (cardField != null) {
            TextView tvFieldCaption = view.findViewById(R.id.tvFieldCaption);
            tvFieldCaption.setText(cardField.getCaption());

            EditText etFieldValue = view.findViewById(R.id.etFieldValue);
            etFieldValue.setText(cardField.getValue());

            ImageButton btnDeleteField = view.findViewById(R.id.btnDeleteField);
            btnDeleteField.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    removeCardField(cardField);
                }
            });

            /*Button btnChangeCaption = view.findViewById(R.id.btnChangeFieldCaption);
            btnChangeCaption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //http://www.mkyong.com/android/android-prompt-user-input-dialog-example/

                    LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    View dlgView = layoutInflater.inflate(R.layout.dialog_change_fieldcaption, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setView(dlgView);

                    EditText etFieldCaption = dlgView.findViewById(R.id.etFieldCaption);
                    etFieldCaption.setText(cardField.getCaption());

                    builder.setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setNegativeButton("Camcel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    //builder.setTitle("Change Field Caption");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });*/
        }

        return view;
    }
}
