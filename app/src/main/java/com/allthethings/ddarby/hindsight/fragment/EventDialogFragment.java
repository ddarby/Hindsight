package com.allthethings.ddarby.hindsight.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.allthethings.ddarby.hindsight.R;
import com.allthethings.ddarby.hindsight.model.Pomodoro;
import com.allthethings.ddarby.hindsight.model.data.PomodoroManager;

import java.util.Date;

/**
 * Created by ddarby on 10/12/14.
 */
public class EventDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "event_dialog";

    private TextView date;
    private EditText title;

    private boolean cancelled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupDialog();
        return inflater.inflate(R.layout.fragment_dialog_event, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        date = (TextView) view.findViewById(R.id.event_date);

        Date setDate = new Date(getArguments().getLong("date"));
        try {
            DateFormat df = new DateFormat();
            date.setText(date.getText() + df.format("yyyy-MM-dd", setDate).toString());
        } catch (Throwable t) {
            date.setText(date.getText() + setDate.toString());
        }

        title = (EditText) view.findViewById(R.id.event_title);
        view.findViewById(R.id.event_save).setOnClickListener(this);
        view.findViewById(R.id.event_close).setOnClickListener(this);
    }

    private void setupDialog() {
        getDialog().setTitle("New Event");
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.event_save) {
            getDialog().dismiss();
        } else if (view.getId() == R.id.event_close) {
            getDialog().cancel();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        cancelled = true;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (!cancelled) {
            PomodoroManager.getInstance().insertOrUpdatePomodoro(new Pomodoro(title.getText().toString(), new Date(getArguments().getLong("date"))));
        }
    }
}
