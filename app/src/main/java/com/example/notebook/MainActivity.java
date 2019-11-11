package com.example.notebook;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String showId;
    public static List<String> preList = new ArrayList<>();
    public static List<AList> alists = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private MainAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        alists = LitePal.findAll(AList.class);
        if(alists.size()>0){
            preList.clear();
            for(int i=0;i<alists.size();i++){
                preList.add(i,alists.get(i).getList());
            }
            showId=preList.get(preList.size()-1);
            for (int i = 0; i < preList.size(); i++) {
                Log.d("MainActivity", "main1"+preList.get(i));}
        }else{
            AList aList = new AList();
            preList.add(0,"-1");
            aList.setList("-1");
            aList.save();
            Log.d("MainActivity",aList.getList());
            showId="-1";
            for (int i = 0; i < preList.size(); i++) {
                Log.d("MainActivity", "main2"+preList.get(i));}
        }
        //toolbar
        Toolbar toolbar = findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);

        //recycle
        recyclerView = findViewById(R.id.main_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);

        //拖动
        ItemTouchHelper myItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallBack(adapter));
        myItemTouchHelper.attachToRecyclerView(recyclerView);


        //drawer
        drawerLayout = findViewById(R.id.material_drawer);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu_white);
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_frame);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_frame:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_normal:
                        Intent intent = new Intent(MainActivity.this,SetsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;

                }
                return true;
            }
        });

    }



    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                adapter.addData(adapter.getItemCount());
                break;
            case R.id.back:
                adapter.backData();
                break;
            case R.id.delete:
                adapter.deleteEmpty();
                break;
            case R.id.trans:
                if(showId.equals("-2")){
                    finish();
                    Intent intent = new Intent(MainActivity.this,CheckActivity.class);
                    startActivity(intent);
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                adapter.changeShowId(MainActivity.this);
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(preList.size()>1){
            adapter.backData();
        }
        else {
            AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.Theme_AppCompat_Light_Dialog_Alert_Self);
            builder.setTitle("退出");
            builder.setMessage("确定要退出程序吗?");
            builder.setCancelable(false);
            builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog = builder.create();
            dialog.show();
        }
    }
}
