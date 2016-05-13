package com.example.krystiano.asystentremontu.myhistory.actual_history;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krystiano.asystentremontu.R;
import com.example.krystiano.asystentremontu.database.AssistantUserConfig;
import com.example.krystiano.asystentremontu.myhistory.MyHistoryMainClass;
import com.example.krystiano.asystentremontu.myhistory.newelement.ConfigListSingleton;
import com.example.krystiano.asystentremontu.myhistory.newelement.NewElementActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


/**
 * Created by Beryl
 * on 03.04.2016
 */
public class AdapterForRecyclerView extends RecyclerView.Adapter<AdapterForRecyclerView.ViewHolder> {

    private static Button delete;
    private static Button send;
    private static Button edit;
    private static List<AssistantUserConfig> configToRecyclerView;
    private static Context context;
    private File file;
    private Picasso.Builder builder;
    public static final String EDIT_ITEM ="editItem";
    private static ActualHistoryActivity.RefleshRecyclerViewListener refleshRecyclerViewListener;


    public AdapterForRecyclerView(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        return viewHolder;


    }

    public void setConfig(List<AssistantUserConfig> config, ActualHistoryActivity.RefleshRecyclerViewListener refleshRecyclerViewListener) {
        configToRecyclerView = config;
        this.refleshRecyclerViewListener = refleshRecyclerViewListener;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.priceTv.setText(configToRecyclerView.get(position).price);
        holder.commentTv.setText(configToRecyclerView.get(position).comment);
        holder.dateTv.setText(configToRecyclerView.get(position).date);
        Log.i("AdapterForRecyclerView ", " positions in onBindViewHolder: " + position);
        file = new File(configToRecyclerView.get(position).pathToImg);
        builder.build().load(file).fit().into(holder.placeToInsertPhotoInHistory);



    }

    @Override
    public int getItemCount() {
        return configToRecyclerView.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView priceTv, commentTv, dateTv;
        public ImageView placeToInsertPhotoInHistory;
        public RelativeLayout relativeToPutButtons;

        public ViewHolder(View itemView) {
            super(itemView);
            this.priceTv = (TextView) itemView.findViewById(R.id.textview_with_prize_in_history);
            this.commentTv = (TextView) itemView.findViewById(R.id.textview_with_description_in_history);
            this.dateTv = (TextView) itemView.findViewById(R.id.textview_with_date_in_history);
            this.placeToInsertPhotoInHistory = (ImageView) itemView.findViewById(R.id.place_to_insert_photo_in_history_imageview);
            this.relativeToPutButtons = (RelativeLayout) itemView.findViewById(R.id.relative_to_put_programmatically_items);

            itemView.setOnClickListener(this);
            placeToInsertPhotoInHistory.setOnClickListener(this);
            placeToInsertPhotoInHistory.setTag(getAdapterPosition());

        }


        @Override
        public void onClick(View v) {
            int poz = getAdapterPosition();
            String getTag = placeToInsertPhotoInHistory.getTag().toString();
            if (getTag == placeToInsertPhotoInHistory.getTag().toString()) {

                if (relativeToPutButtons.findViewById(R.id.delete_button_in_history) != null) {
                    ((RelativeLayout) relativeToPutButtons.findViewById(R.id.relative_to_put_programmatically_items)).removeAllViews();
                } else {
                    createAddiditonalOptions(relativeToPutButtons, getAdapterPosition());
                }
            } else if (v.getId() == relativeToPutButtons.getId()) {
                return;
                // Toast.makeText(v.getContext(), "pressed on Parent ", Toast.LENGTH_SHORT).show();
            } else {
                return;
                // Toast.makeText(v.getContext(), "please click on image ", Toast.LENGTH_SHORT).show();
            }


            Log.i(getClass().getSimpleName(), "onClick " + getAdapterPosition() + " " + v.toString());

        }

    }

    public static void createAddiditonalOptions(RelativeLayout relativeLayout, final int position) {
        RelativeLayout.LayoutParams params;

        Log.i(context.getClass().getSimpleName(), "widoki przycisków stworzone");
        delete = new Button(relativeLayout.getContext());
        edit = new Button(relativeLayout.getContext());
        send = new Button(relativeLayout.getContext());
        delete.setId(R.id.delete_button_in_history);
        edit.setId(R.id.edit_button_in_history);
        send.setId(R.id.send_button_in_history);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(context.getClass().getSimpleName(), "delete clicked");
                new AlertDialog.Builder(context)
                        .setTitle("usunięcie wiersza")
                        .setMessage("usunąć ten wiersz?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                refleshRecyclerViewListener.onReflesh(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(context.getClass().getSimpleName(), "edit clicked");
                Intent editElement = new Intent(context,NewElementActivity.class);
                editElement.putExtra(EDIT_ITEM,position);
                context.startActivity(editElement);
                ((Activity)context).finish();

            }
        });
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(context.getClass().getSimpleName(), "send clicked");
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View inflater = LayoutInflater.from(context).inflate(R.layout.dialog_to_send_email, (ViewGroup) v.getParent(), false);
                ImageView placeToShowThumbnailForSend;
                final EditText typeMessageToSend;
                final EditText subjectOfTheMessage;
                final EditText messageReceiver;
                placeToShowThumbnailForSend = (ImageView) inflater.findViewById(R.id.place_to_insert_photo_to_send_via_email);
                typeMessageToSend = (EditText) inflater.findViewById(R.id.message_to_send_by_email);
                subjectOfTheMessage = (EditText) inflater.findViewById(R.id.place_to_insert_subject_of_email);
                messageReceiver = (EditText) inflater.findViewById(R.id.place_to_insert_adress_email_edittext);
                if (placeToShowThumbnailForSend != null) {
                    File file = new File(configToRecyclerView.get(position).pathToImg);
                    typeMessageToSend.setText(configToRecyclerView.get(position).comment);
                    Picasso.with(context).load(file).fit().into(placeToShowThumbnailForSend);

                }
                builder.setView(inflater)
                        .setPositiveButton("WYŚLIJ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (subjectOfTheMessage.getText().toString() != null && messageReceiver.getText().toString() != null) {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("application/image");
                                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{messageReceiver.getText().toString()});
                                    intent.putExtra(Intent.EXTRA_SUBJECT, subjectOfTheMessage.getText().toString());
                                    intent.putExtra(Intent.EXTRA_TEXT, typeMessageToSend.getText().toString());
                                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + configToRecyclerView.get(position).pathToImg));
                                    context.startActivity(Intent.createChooser(intent, "wysyłanie e-maila"));


                                }

                            }
                        })
                        .setNegativeButton("ANULUJ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.setCancelable(true);
                builder.create();

                builder.show();
            }
        });
        delete.setText("USUŃ");
        edit.setText("EDYTUJ");
        send.setText("WYŚLIJ");
        delete.setLayoutParams(new RelativeLayout.LayoutParams(210, ViewGroup.LayoutParams.WRAP_CONTENT));
        params = (RelativeLayout.LayoutParams) delete.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        delete.setLayoutParams(params);

        edit.setLayoutParams(new RelativeLayout.LayoutParams(210, ViewGroup.LayoutParams.WRAP_CONTENT));
        params = (RelativeLayout.LayoutParams) edit.getLayoutParams();
        params.addRule(RelativeLayout.RIGHT_OF, R.id.delete_button_in_history);
        edit.setLayoutParams(params);

        send.setLayoutParams(new RelativeLayout.LayoutParams(210, ViewGroup.LayoutParams.WRAP_CONTENT));
        params = (RelativeLayout.LayoutParams) send.getLayoutParams();
        params.addRule(RelativeLayout.RIGHT_OF, R.id.edit_button_in_history);
        send.setLayoutParams(params);
        ((RelativeLayout) relativeLayout.findViewById(R.id.relative_to_put_programmatically_items)).addView(delete);
        ((RelativeLayout) relativeLayout.findViewById(R.id.relative_to_put_programmatically_items)).addView(edit);
        ((RelativeLayout) relativeLayout.findViewById(R.id.relative_to_put_programmatically_items)).addView(send);

    }

}