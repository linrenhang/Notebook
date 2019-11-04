package com.example.notebook;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import org.litepal.LitePal;
import java.util.List;
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
        ImageView delete;
        ImageView extend;

        public ViewHolder(View view){
            super(view);
            mainView = view;
            text = view.findViewById(R.id.main_content);
            extendText = view.findViewById(R.id.main_extend_text);
            enter= view.findViewById(R.id.main_enter);
            delete=view.findViewById(R.id.main_delete);
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
                myMainList= LitePal.where("belongId==?",showId).order("position").find(MainNote.class);//转换List数据为当前showId
                notifyDataSetChanged();//提醒recycle转换
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MainNote mainNote = myMainList.get(position);
                List<MainNote> tList = LitePal.where("belongId==?",String.valueOf(mainNote.getId())).find(MainNote.class);
                if(tList.size()>0){
                    final String delete = "删除";
                    final EditText inputServer = new EditText(v.getContext());
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("该笔记有子项,\n请输入\"删除\"进行确认。").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                            .setNegativeButton("取消", null);
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(inputServer.getText().toString().equals(delete)){
                                int position = holder.getAdapterPosition();
                                MainNote mainNote = myMainList.get(position);
                                LitePal.deleteAll(MainNote.class,"id==?",String.valueOf(mainNote.getId()));
                                LitePal.deleteAll(MainNote.class,"belongId==?",String.valueOf(mainNote.getId()));
                                myMainList= LitePal.where("belongId==?",showId).order("position").find(MainNote.class);//转换List数据为当前showId
                                for(int i =0;i<myMainList.size();i++){
                                    MainNote mainNote1 =myMainList.get(i);
                                    mainNote1.setPosition(i);
                                    mainNote1.save();
                                }
                                notifyDataSetChanged();//提醒recycle转换
                            }
                        }
                    });
                    builder.show();
                }else{
                    AlertDialog dialog;
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(v.getContext(),R.style.Theme_AppCompat_Light_Dialog_Alert_Self);
                    builder2.setTitle("删除");
                    builder2.setMessage("确定要删除此条笔记吗?");
                    builder2.setCancelable(false);
                    builder2.setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = holder.getAdapterPosition();
                            MainNote mainNote = myMainList.get(position);
                            LitePal.deleteAll(MainNote.class,"id==?",String.valueOf(mainNote.getId()));
                            LitePal.deleteAll(MainNote.class,"belongId==?",String.valueOf(mainNote.getId()));
                            myMainList= LitePal.where("belongId==?",showId).order("position").find(MainNote.class);//转换List数据为当前showId
                            for(int i =0;i<myMainList.size();i++){
                                MainNote mainNote1 =myMainList.get(i);
                                mainNote1.setPosition(i);
                                mainNote1.save();
                            }
                            notifyDataSetChanged();//提醒recycle转换
                        }
                    });
                    builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { }
                    });
                    dialog = builder2.create();
                    dialog.show();
                }
            }
        });
        holder.extend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MainNote mainNote = myMainList.get(position);
                if(holder.extendText.getVisibility()==View.GONE){
                    holder.extendText.setVisibility(View.VISIBLE);
                }else{
                    holder.extendText.setVisibility(View.GONE);
                }
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
                Log.d("MainAdapter","chnag");
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
            preList.remove(preList.get(preList.size()-1));
            showId = preList.get(preList.size()-1);
            myMainList= LitePal.where("belongId==?",showId).order("position").find(MainNote.class);
            notifyDataSetChanged();
        }
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
        Log.d("MainAdapter"," form:"+fromPosition+" to:"+toPosition);
    }
}


