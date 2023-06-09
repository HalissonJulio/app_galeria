package pereira.lopes.julio.galeria.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import pereira.lopes.julio.galeria.R;
import pereira.lopes.julio.galeria.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        NewItemActivityViewModel vm = new ViewModelProvider( this ).get(NewItemActivityViewModel.class);

        Uri selectPhotoLocation = vm.getSelectPhotoLocation();
;
        if(selectPhotoLocation != null) {
            ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
            imvfotoPreview.setImageURI(selectPhotoLocation);
        }

        ImageButton imgCl = findViewById(R.id.imbCl); // Pega o botão com a imagem
        imgCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT); // Abre a galeria para o usuario escolher uma foto
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,PHOTO_PICKER_REQUEST); // Vai pra tela mandando a imagem e o numero identifcador
            }
        });

        Button btnAddItem = findViewById(R.id.btnAddItem);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Uri selectPhotoLocation = vm.getSelectPhotoLocation();
                if(selectPhotoLocation == null){ // Analizando se os campos foram preenchidos
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!",Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                if(title.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário inserir um titulo",Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                if(description.isEmpty()){
                    Toast.makeText(NewItemActivity.this,"É necessário inserir uma descrição",Toast.LENGTH_LONG).show();
                    return;
                }
                // Guarda os dados acima e envia para o main
                Intent i = new Intent();
                i.setData(selectPhotoLocation);
                i.putExtra("title",title);
                i.putExtra("description",description);
                setResult(Activity.RESULT_OK,i); // Falando que está tudo certo e volta pra main
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){ // 3 parametros, 1° é qual a chamada de startactivityforresult a resposta se refere, 2°cod que fala se a activity retornou com sucesso e 3° dados retornados da activity
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_PICKER_REQUEST){ // Vemos se é igual a chamada
            if (resultCode == Activity.RESULT_OK){ // Vemos se a Activity retornou verta
                Uri photoSelected = data.getData(); // Pegando o URI (endereço) da imagem
                ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview); // Pegando o campo img

                imvfotoPreview.setImageURI( photoSelected ); // Colocando o endereço no campo imagem

                NewItemActivityViewModel vm = new ViewModelProvider( this ).get( NewItemActivityViewModel.class );
                vm.selectPhotoLocation(photoSelected);

            }
        }
    }
}