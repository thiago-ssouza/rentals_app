package com.example.rentals_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rentals_app.model.ApartmentModel;
import com.example.rentals_app.model.MessageModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ApartmentAdapter extends ArrayAdapter {

    ArrayList<ApartmentModel> apartmentList;
    Context context;


    public ApartmentAdapter(ArrayList<ApartmentModel> apartmentList, Context context) {
        super(context, R.layout.list_view_apartment, apartmentList);
        this.apartmentList = apartmentList;
        this.context = context;

    }

    // Holder to create a number, date, and holder the object
    private static class ViewHolder {
        TextView apartmentTitle, apartmentPrice, apartmentLocation, apartmentAddress;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ApartmentModel message = apartmentList.get(position);

        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            // Inflater: Layout of the class that help us to convert the object to the view (Convert to the view)
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());

            convertView = layoutInflater.inflate(R.layout.list_view_apartment, parent, false);
            holder.apartmentTitle = convertView.findViewById(R.id.apartmentTitle);
            holder.apartmentPrice = convertView.findViewById(R.id.apartmentPrice);
            holder.apartmentLocation = convertView.findViewById(R.id.apartmentLocation);
            holder.apartmentAddress = convertView.findViewById(R.id.apartmentAddress);

            // We need to set the tag
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.apartmentTitle.setText(message.getTitle());
        holder.apartmentPrice.setText(String.valueOf(message.getPrice()));
        holder.apartmentLocation.setText(message.getLocation().getName());
        holder.apartmentAddress.setText(message.getAddress());

        return convertView;

    }


}