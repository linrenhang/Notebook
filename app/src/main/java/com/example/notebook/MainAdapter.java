package com.example.notebook;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.litepal.LitePal;
import java.util.List;

import static com.example.notebook.MainActivity.alists;
import static com.example.notebook.MainActivity.preList;
import static com.example.notebook.MainActivity.showId;


//copy this
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainNote> myMainList;
    static class ViewHolder extends RecyclerView.ViewHolder{

        View mainView;
        EditText text;
        EditText extendText;
        ImageView enter;
        ImageView extend;

        public ViewHolder(View view){
            super(view);
            mainView = view;
            text = view.findViewById(R.id.main_content);
            extendText = view.findViewById(R.id.main_extend_text);
            enter= view.findViewById(R.id.main_enter);
            extend=view.findViewById(R.id.main_extend);
        }
    }
    public MainAdapter() {
        myMainList= LitePal.where("belongId==?",showId).order("position").find(MainNote.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_recycler,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MainNote mainNote = myMainList.get(position);
                showId=String.valueOf(mainNote.getId());  //转换showId为当前id
                preList.add(showId);//转换prelist
                AList aList = new AList();
                aList.setList(showId);
                aList.save();
                for (int i = 0; i < preList.size(); i++) {
                    Log.d("MainActivity", preList.get(i));}
                myMainList= LitePal.where("belongId==?",showId).order("position").find(MainNote.class);//转换List数据为当前showId
                notifyDataSetChanged();//提醒recycle转换
            }
        });
        holder.extend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MainNote mainNote = myMainList.get(position);
                if(holder.extendText.getVisibility()==View.GONE){
                    holder.extendText.setVisibility(View.VISIBLE);
                    mainNote.setShow(true);
                }else{
                    holder.extendText.setVisibility(View.GONE);
                    mainNote.setShow(false);
                }
                mainNote.save();
            }
        });

        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if(myMainList.size()>0) {
            MainNote mainNote = myMainList.get(position);
            holder.text.setText(mainNote.getText());
            holder.extendText.setText(mainNote.getExtendText());
            if(mainNote.isShow()){
                holder.extendText.setVisibility(View.VISIBLE);
            }else{
                holder.extendText.setVisibility(View.GONE);
            }
        }
        holder.text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int position = holder.getAdapterPosition();
                MainNote mainNote = myMainList.get(position);
                mainNote.setText(holder.text.getText().toString());
                mainNote.updateAll("id == ?",String.valueOf(mainNote.getId()));
            }
        });
        holder.extendText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int position = holder.getAdapterPosition();
                MainNote mainNote = myMainList.get(position);
                mainNote.setExtendText(holder.extendText.getText().toString());
                mainNote.updateAll("id == ?",String.valueOf(mainNote.getId()));
            }
        });
        holder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return myMainList.size();
    }
    public void addData(int position) {
        MainNote mainNote = new MainNote();
        mainNote.setBelongId(Integer.parseInt(showId));
        mainNote.setPosition(position);
        mainNote.save();
        myMainList.add(position,mainNote);//添加到list
        notifyItemInserted(position);//提醒添加
    }
    public void backData(){
        if(preList.size()>1){
            LitePal.deleteAll(AList.class,"list==?",preList.get(preList.size()-1));
            preList.remove(preList.get(preList.size()-1));
            showId = preList.get(preList.size()-1);
            myMainList= LitePal.where("belongId==?",showId).order("position").find(MainNote.class);
            notifyDataSetChanged();
            for (int i = 0; i < preList.size(); i++) {
                Log.d("MainActivity", preList.get(i));}
        }
    }
    public void deleteEmpty(){
        List<MainNote> testList=LitePal.findAll(MainNote.class);
        for(int i=0;i<testList.size();i++){
            MainNote mainNote = testList.get(i);
            String a = mainNote.getText();
            String b = mainNote.getExtendText();
            if((a==null)&&(b==null)){
                LitePal.deleteAll(MainNote.class,"id==?",String.valueOf(mainNote.getId()));
                LitePal.deleteAll(MainNote.class,"belongId==?",String.valueOf(mainNote.getId()));
            }else if(a==null){
                if(b.length()==0){
                    LitePal.deleteAll(MainNote.class,"id==?",String.valueOf(mainNote.getId()));
                    LitePal.deleteAll(MainNote.class,"belongId==?",String.valueOf(mainNote.getId()));
                }
            }else if(b==null){
                if(a.length()==0){
                    LitePal.deleteAll(MainNote.class,"id==?",String.valueOf(mainNote.getId()));
                    LitePal.deleteAll(MainNote.class,"belongId==?",String.valueOf(mainNote.getId()));
                }
            }else if((a.length()==0)&&(b.length()==0)){
                LitePal.deleteAll(MainNote.class,"id==?",String.valueOf(mainNote.getId()));
                LitePal.deleteAll(MainNote.class,"belongId==?",String.valueOf(mainNote.getId()));
            }
            myMainList= LitePal.where("belongId==?",showId).order("position").find(MainNote.class);
            notifyDataSetChanged();
        }
    }
    public void changeShowId(Context context){
        if(showId.equals("-1")){
            showId="-2";
            AList aList = new AList();
            aList.setList("-2");
            aList.updateAll("list==?","-1");
            preList.remove(0);
            preList.add(0,"-2");
            Toast.makeText(context,"切换成功",Toast.LENGTH_SHORT).show();
            for (int i = 0; i < preList.size(); i++) {
                Log.d("MainActivity", preList.get(i));}
        }else if(showId.equals("-2")){
            showId="-1";
            AList aList = new AList();
            aList.setList("-1");
            aList.updateAll("list==?","-2");
            preList.remove(0);
            preList.add(0,"-1");
                for (int i = 0; i < preList.size(); i++) {
                    Log.d("MainActivity", preList.get(i));}

        }else{
            Toast.makeText(context,"请在主界面切换",Toast.LENGTH_SHORT).show();
        }
        myMainList= LitePal.where("belongId==?",showId).order("position").find(MainNote.class);
        notifyDataSetChanged();
    }

    public void onItemMove(int fromPosition, int toPosition) {

        notifyItemMoved(fromPosition, toPosition);
        MainNote pre = myMainList.remove(fromPosition);
        myMainList.add(toPosition,pre);

        for(int i =0;i<myMainList.size();i++){
            MainNote mainNote =myMainList.get(i);
            mainNote.setPosition(i);
            mainNote.save();
        }
    }
}


