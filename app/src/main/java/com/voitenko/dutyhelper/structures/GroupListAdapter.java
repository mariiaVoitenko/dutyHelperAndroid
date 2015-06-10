package com.voitenko.dutyhelper.structures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.models.Group;

import java.util.ArrayList;
public class GroupListAdapter extends ArrayAdapter<Group> {

    private final Context context;
    private final ArrayList<Group> itemsArrayList;

    public GroupListAdapter(Context context, ArrayList<Group> itemsArrayList) {

        super(context, R.layout.group_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.group_row, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.name);

        name.setText(itemsArrayList.get(position).getName());

        return rowView;
    }

    public Group getItem(int position) {
        return itemsArrayList.get(position);
    }

}
