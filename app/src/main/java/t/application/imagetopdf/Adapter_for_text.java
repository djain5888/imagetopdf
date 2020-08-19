package t.application.imagetopdf;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Adapter_for_text extends RecyclerView.Adapter<Adapter_for_text.ViewHolder> {
    private LayoutInflater mInflater;
    ArrayList<String> spinlist=new ArrayList<>();


    Context c1;
    ArrayList<model> list=new ArrayList<>();



    Adapter_for_text(Context c1, ArrayList<String>total_pages,ArrayList<model> m1)
    {
        this.c1=c1;
        this.mInflater = LayoutInflater.from(c1);
        list=m1;
        spinlist=total_pages;





    }

    @NonNull
    @Override
    public Adapter_for_text.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.text_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_for_text.ViewHolder holder, final int position) {
        final model m1=new model();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(c1);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("TAG", json);
        editor.commit();
        ArrayList<String> poslist=new ArrayList<>();
        poslist.add("Position For Text");
        poslist.add("Title");
        poslist.add(" Top center");
        poslist.add("Top Left");
        poslist.add("Top Right");
        poslist.add("Bottom left");
        poslist.add("Bottom Right");
        ArrayAdapter<String> pos=new ArrayAdapter<String>(c1,android.R.layout.simple_spinner_item,poslist);
        pos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.pos.setAdapter(pos);



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(c1 ,   android.R.layout.simple_spinner_item, spinlist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spin.setAdapter(arrayAdapter);
        holder.text.setText(list.get(position).getText());
        if(list.get(position).getAdded()==true)
        {
            holder.spin.setSelection(Integer.parseInt(list.get(position).value));
            holder.pos.setSelection(Integer.parseInt(list.get(position).getPosition())-1);
        }
        holder.pos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m1.setPosition(Integer.toString(i+1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m1.setValue(Integer.toString(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(list.get(position).getAdded()==true)
        {
            holder.check.setChecked(true);
        }
        else
        {
            holder.check.setChecked(false);
        }

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("positionis",Integer.toString(position));
                if(holder.check.isChecked())
                {

                    if(!holder.text.getText().toString().equals(""))
                    {
                    m1.setText(holder.text.getText().toString());
                    m1.setAdded(true);
                    holder.text.setEnabled(false);
                    list.add(position,m1);}
                    notifyDataSetChanged();
                    Log.i("listsizeis",Integer.toString(list.size()));


                }
                else
                {
                    Log.i("position removed",Integer.toString(position));
                    list.remove(position);
                    Log.i("listsizeis",Integer.toString(list.size()));

                    notifyDataSetChanged();

                }
            }
        });
//        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b==true)
//
//                {
//                    list.add(new model());
//                    notifyDataSetChanged();
//                }
//                else
//                {
//                    if(position<list.size())
//                    list.remove(position);
//                    notifyDataSetChanged();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        EditText text;
        CheckBox check;
        Spinner spin,pos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            pos=itemView.findViewById(R.id.position);
            text=itemView.findViewById(R.id.text);
            check=itemView.findViewById(R.id.check);
            spin=itemView.findViewById(R.id.spinner);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
