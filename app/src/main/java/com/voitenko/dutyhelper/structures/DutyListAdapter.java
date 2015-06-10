package com.voitenko.dutyhelper.structures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.voitenko.dutyhelper.BL.DataConverter;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Duty;

import java.util.ArrayList;

public class DutyListAdapter extends ArrayAdapter<Duty> {

    private final Context context;
    private final ArrayList<Duty> itemsArrayList;

    public DutyListAdapter(Context context, ArrayList<Duty> itemsArrayList) {

        super(context, R.layout.duty_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.duty_row, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView startDate = (TextView) rowView.findViewById(R.id.date_start);
        TextView endDate = (TextView) rowView.findViewById(R.id.date_end);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        TextView priority = (TextView) rowView.findViewById(R.id.priority);
        TextView category = (TextView) rowView.findViewById(R.id.categoty);
        TextView isDone = (TextView) rowView.findViewById(R.id.done);

        name.setText(itemsArrayList.get(position).getName());
        startDate.setText(DataConverter.getTime(itemsArrayList.get(position).getStartDate()));
        endDate.setText(DataConverter.getTime(itemsArrayList.get(position).getEndDate()));
        if (itemsArrayList.get(position).getDescription() != null) {
            description.setText(itemsArrayList.get(position).getDescription().toString());
        } else {
            description.setText("no description for this duty");
        }
        priority.setText(itemsArrayList.get(position).getPriority().getName());
        category.setText(itemsArrayList.get(position).getCategory().getName());
        if (itemsArrayList.get(position).getIsDone() != null)
            isDone.setText(R.string.isDone);
        else isDone.setText(R.string.isNotDone);

        return rowView;
    }

    public Duty getItem(int position) {
        return itemsArrayList.get(position);
    }

}
