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
        arrayItem.add(new Item(R.drawable.natutochibi,"Uzumaki Naruto","Nh??n v???t ch??nh trong b??? anime NARUTO. L???c quan, vui v??? v?? th??ch ??n ramen"));
        arrayItem.add(new Item(R.drawable.saitama,"Saitama","L?? m???t si??u anh h??ng v?? anh d??? d??ng ????nh b???i nh???ng k??? th?? c???a m??nh ch??? b???ng m???t c?? ?????m"));
        arrayItem.add(new Item(R.drawable.kaitokid,"Kuroba Kaito","T??n tr???m kh??t ti???ng v???i bi???t t??i chuy??n ??n tr???m kim c????ng v?? ???? qu?? v???i c??i t??n Kaito Kid"));
        arrayItem.add(new Item(R.drawable.yagami,"Light Yagami","C???u nh???t ???????c Death Note v???i kh??? n??ng cho ph??p ng?????i d??ng n?? gi???t b???t k?? ai b???ng vi???c bi???t m???t v?? t??n ?????i t?????ng"));
        arrayItem.add(new Item(R.drawable.kirito,"Kazuto Kirigaya","Kirito l?? m???t trong 1.000 ng?????i th??? nghi???m beta ???????c ch???n cho phi??n b???n beta c???a Sword Art Online"));
        arrayItem.add(new Item(R.drawable.imgdoraemon,"Doraemon","C???u l?? ch?? m??o robot c???a t????ng lai do x?????ng Matsushiba s???n xu???t nh???m m???c ????ch ch??m s??c tr??? nh???."));
        arrayItem.add(new Item(R.drawable.imggoku,"Son Goku","Son Goku b??? m???t tr?? nh??? khi ?????n tr??i ?????t v?? sau ???? tr??? th??nh ng?????i b???o v??? v?? ?????i c???a Tr??i ?????t"));
        arrayItem.add(new Item(R.drawable.imgmikasa,"Mikasa Ackerman","C?? g??i g???c Ackerman n??y c?? n??ng l???c chi???n ?????u can ?????m v?? m???nh m???"));
    }

    private void Xoa(final int  position){
        AlertDialog.Builder alterDialog  = new AlertDialog.Builder(this);
        alterDialog.setTitle("Th??ng b??o ");
        alterDialog.setIcon(R.mipmap.ic_launcher);
        alterDialog.setMessage("B???n c?? mu???n x??a m???t h??ng n??y kh??ng ?");
        alterDialog.setPositiveButton("C??", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                arrayItem.remove(position);
                listViewAdapter.notifyDataSetChanged();
            }
        });
        alterDialog.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener(){
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
                        chooseimg.setText("???? ch???n ???nh");
                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED){
                        selectedImg = null;
                        chooseimg.setText("Ch??a ch???n ???nh");
                    }
                }
            }
    );
}