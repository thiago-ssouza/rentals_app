package com.example.rentals_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.translation.ViewTranslationResponse;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

public class MessageAdapter extends ArrayAdapter {

    /**
     * Need to uncomment
     */
    //ArrayList<MessageModel> messageList;
    Context context;

    /**
     * Need to uncomment
     */
    public MessageAdapter(/*ArrayList<MessageModel> messageList,*/ Context context) {
        super(context, R.layout.list_view_message_item/*, messageList*/);
//        this.messageList = messageList;
        this.context = context;

    }

    // Holder to create a number, date, and holder the object
    private static class ViewHolder {
        TextView tenantFullName, tenantMessageDate, tenantEmail, tenantPhone, tenantMessage;
    }


    /**
     * Need to uncomment
     */

//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        MessageModel message = messageList.get(position);
//
//        ViewHolder holder;
//
//        if(convertView == null){
//            holder = new ViewHolder();
//            // Inflater: Layout of the class that help us to convert the object to the view (Convert to the view)
//            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//
//            convertView = layoutInflater.inflate(R.layout.list_view_message_item, parent, false);
//            holder.tenantFullName = convertView.findViewById(R.id.textViewListViewFullName);
//            holder.tenantMessageDate = convertView.findViewById(R.id.textViewListViewDate);
//            holder.tenantEmail = convertView.findViewById(R.id.textViewListViewEmail);
//            holder.tenantPhone = convertView.findViewById(R.id.textViewListViewPhone);
//            holder.tenantMessage = convertView.findViewById(R.id.textViewListViewMessage);
//
//            // We need to set the tag
//            convertView.setTag(holder);
//        }else{
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.tenantFullName.setText(message.getTenant().getFirstName() + " " + message.getTenant().getLastName());
//        holder.tenantEmail.setText(message.getMessageDate().toString());
//        holder.tenantEmail.setText(message.getTenant().getEmail());
//        holder.tenantPhone.setText(message.getTenant().getPhone());
//        holder.tenantMessage.setText(message.getMessage());
//
//        return convertView;
//
//    }


}