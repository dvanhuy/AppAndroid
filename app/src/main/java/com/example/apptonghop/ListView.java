package com.example.apptonghop;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListView extends AppCompatActivity {

    android.widget.ListView lvitem;
    ArrayList<Item> arrayItem;
    ListViewAdapter listViewAdapter;
    Button additem;
    Uri selectedImg=null;
    Button chooseimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_list_view);
        AnhXa();
        ImageView back = findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listViewAdapter = new ListViewAdapter(this,R.layout.itemlist,arrayItem);
        lvitem.setAdapter(listViewAdapter);

        lvitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListView.this,arrayItem.get(i).getHoTen(),Toast.LENGTH_SHORT).show();
            }
        });

        lvitem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Xoa(i);
                return false;
            }
        });

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImg = null;
                showDialog();
            }
        });
    }

    private void AnhXa(){
        lvitem = findViewById(R.id.listviewsv);
        additem= (Button) findViewById(R.id.additem);
        arrayItem = new ArrayList<>();
        arrayItem.add(new Item(R.drawable.natutochibi,"Uzumaki Naruto","Nhân vật chính trong bộ anime NARUTO. Lạc quan, vui vẻ và thích ăn ramen"));
        arrayItem.add(new Item(R.drawable.saitama,"Saitama","Là một siêu anh hùng và anh dễ dàng đành bại những kẻ thù của mình chỉ bằng một cú đấm"));
        arrayItem.add(new Item(R.drawable.kaitokid,"Kuroba Kaito","Tên trộm khét tiếng với biệt tài chuyên ăn trộm kim cương và đá quý với cái tên Kaito Kid"));
        arrayItem.add(new Item(R.drawable.natutochibi,"Đinh Văn Huy 4","Mã sinh viên: 2050531200157, Giới tính: Nam"));
        arrayItem.add(new Item(R.drawable.natutochibi,"Đinh Văn Huy 5","Mã sinh viên: 2050531200157, Giới tính: Nam"));
        arrayItem.add(new Item(R.drawable.natutochibi,"Đinh Văn Huy 6","Mã sinh viên: 2050531200157, Giới tính: Nam"));
        arrayItem.add(new Item(R.drawable.natutochibi,"Đinh Văn Huy 7","Mã sinh viên: 2050531200157, Giới tính: Nam"));
        arrayItem.add(new Item(R.drawable.natutochibi,"Đinh Văn Huy 8","Mã sinh viên: 2050531200157, Giới tính: Nam"));
        arrayItem.add(new Item(R.drawable.natutochibi,"Đinh Văn Huy 9","Mã sinh viên: 2050531200157, Giới tính: Nam"));
    }

    private void Xoa(final int  position){
        AlertDialog.Builder alterDialog  = new AlertDialog.Builder(this);
        alterDialog.setTitle("Thông báo ");
        alterDialog.setIcon(R.mipmap.ic_launcher);
        alterDialog.setMessage("Bạn có muốn xóa mặt hàng này không ?");
        alterDialog.setPositiveButton("Có", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                arrayItem.remove(position);
                listViewAdapter.notifyDataSetChanged();
            }
        });
        alterDialog.setNegativeButton("Không", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alterDialog.show();
    }

    public void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
        dialog.setContentView(R.layout.additem);
        EditText username = (EditText) dialog.findViewById(R.id.editTextTextPersonName2);
        EditText description = (EditText) dialog.findViewById(R.id.editTextTextPersonName3);
        Button submit = (Button) dialog.findViewById(R.id.button2);
        chooseimg =(Button) dialog.findViewById(R.id.editTextTextPersonName);
        chooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getResult.launch(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImg ==null){
                    arrayItem.add(new Item(R.drawable.quanlyuser,username.getText().toString() ,description.getText().toString()));
                }
                else {
                    arrayItem.add(new Item(selectedImg,username.getText().toString() ,description.getText().toString()));
                }
                listViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void chooseImg(){

    }
    private ActivityResultLauncher<Intent> getResult =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (result.getResultCode() == Activity.RESULT_OK){
                        selectedImg = data.getData();
                        chooseimg.setText("Đã chọn ảnh");
                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED){
                        selectedImg = null;
                        chooseimg.setText("Chưa chọn ảnh");
                    }
                }
            }
    );
}