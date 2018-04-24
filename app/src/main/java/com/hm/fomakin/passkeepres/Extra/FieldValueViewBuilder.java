package com.hm.fomakin.passkeepres.Extra;

import android.content.Context;
import android.nfc.Tag;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hm.fomakin.passkeepres.Model.CardField;
import com.hm.fomakin.passkeepres.R;

public class FieldValueViewBuilder {

    private static Context mContext;
    private static String mFieldValue;

    public static View getView(Context context, CardField cardField) {
        mContext = context;
        mFieldValue = cardField.getValue();
        switch (cardField.getCardFieldValueType().getTypeName()) {
            case "TextView":
                return getTextViewValue();
            case "Password":
                return getPasswordValue();
            case "Email":
                return getEmailValue();
            case "MultilineText":
                return getMultilineTextValue();
            default:
                return getDefaultValueView();
        }
    }

    private static View getDefaultValueView() {
        TextView view = new TextView(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setInputType(InputType.TYPE_CLASS_TEXT);
        view.setText(mFieldValue);
        return view;
    }

    private static View getTextViewValue() {
        TextView view = new TextView(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setText(mFieldValue);
        return view;
    }

    private static View getPasswordValue() {
        TextView view = new TextView(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        view.setText(mFieldValue);
        return view;
    }

    private static View getEmailValue() {
        TextView view = new TextView(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        view.setText(mFieldValue);
        return view;
    }

    private static View getMultilineTextValue() {
        TextView view = new TextView(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        view.setText(mFieldValue);
        return view;
    }

}
