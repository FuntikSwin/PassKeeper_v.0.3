package com.hm.fomakin.passkeepres.Adapter;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.fomakin.passkeepres.Extra.FieldValueViewBuilder;
import com.hm.fomakin.passkeepres.Model.CardField;
import com.hm.fomakin.passkeepres.R;

import java.util.List;

public class CardFieldsAdapter extends BaseAdapter {

    private Context mContext;
    private List<CardField> mCardFields;

    public CardFieldsAdapter(Context mContext, List<CardField> mCardFields) {
        this.mContext = mContext;
        this.mCardFields = mCardFields;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<CardField> getmCardFields() {
        return mCardFields;
    }

    public void setmCardFields(List<CardField> mCardFields) {
        this.mCardFields = mCardFields;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.card_field_listview_item, null);

        CardField cardField = mCardFields != null ? mCardFields.get(position) : null;

        if (cardField != null) {
            TextView tvFieldCaption = view.findViewById(R.id.tvFieldCaption);
            tvFieldCaption.setText(cardField.getCaption());

            /*LinearLayout llFieldItem = view.findViewById(R.id.llCardFieldItem);
            View valueView = FieldValueViewBuilder.getView(mContext, cardField);
            llFieldItem.addView(valueView);*/

            TextView tvFieldValue = view.findViewById(R.id.tvFieldValue);
            tvFieldValue.setText(cardField.getValue());
            switch (cardField.getCardFieldValueType().getTypeName()) {
                case "TextView":
                    tvFieldValue.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case "Password":
                    tvFieldValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;
                case "Email":
                    tvFieldValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                case "MultilineText":
                    tvFieldValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    break;
            }
        }

        return view;
    }
}
