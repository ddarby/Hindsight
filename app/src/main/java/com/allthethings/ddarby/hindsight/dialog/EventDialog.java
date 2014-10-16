package com.allthethings.ddarby.hindsight.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allthethings.ddarby.hindsight.R;

import java.util.Date;

/**
 * Created by ddarby on 10/12/14.
 */
public class EventDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener{

    public EventDialog(Context context, long date) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_event, new LinearLayout(context));
        TextView tv = (TextView) view.findViewById(R.id.dialogEventDate);
        tv.setText(tv.getText()+" "+new Date(date).toString());
        setView(view);
        setPositiveButton("Save", this);
        setInverseBackgroundForced(true);
        setTitle("Event");
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
}
