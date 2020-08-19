package t.application.imagetopdf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private List<Uri> bitmap;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context c1;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context,List<Uri> bit) {
        this.bitmap=bit;
        c1=context;
        this.mInflater = LayoutInflater.from(context);
      //  this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.imageshow, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //String animal =
        Picasso.get().load(bitmap.get(position)).fit().into(holder.i1);

   //     holder.i1.setImageURI(bitmap.get(position));
     //   holder.i1.setImageBitmap(bitmap.get(position));
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmap.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),editingactivity.class);
                //intent.putExtra("Bitmap",bitmap);
             //   Bundle args = new Bundle();
              //  args.putSerializable("ARRAYLIST",(Serializable)bitmap);
              // args.putParcelable("Birds", bitmap.get(position));
             //   intent.putExtra("BUNDLE",bitmap.get(position));
                //Convert to byte array
             //   ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                .compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
              //  intent.putExtra("image",bitmap.get(position));
                //intent.putExtra("sss",Integer.toString(position));
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri send=bitmap.get(position);

                intent.putParcelableArrayListExtra("image", (ArrayList<? extends Parcelable>) bitmap);
                intent.putExtra("pos",position);

                view.getContext().startActivity(intent);
            }
        });
//        holder.i2.setImageBitmap(bitmap.get(position+1));
//        holder.i3.setImageBitmap(bitmap.get(position+2));
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 70, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return bitmap.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       // TextView myTextView;
        ImageView i1,close,i3;

        ViewHolder(View itemView) {
            super(itemView);
            i1=itemView.findViewById(R.id.iv_image1);
            close=itemView.findViewById(R.id.close);
//            i3=itemView.findViewById(R.id.iv_image3);

          //  myTextView = itemView.findViewById(R.id.tvAnimalName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position


    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}




