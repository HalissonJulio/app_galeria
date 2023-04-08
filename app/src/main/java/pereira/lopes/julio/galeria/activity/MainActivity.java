package pereira.lopes.julio.galeria.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import pereira.lopes.julio.galeria.R;
import pereira.lopes.julio.galeria.adapter.MyAdapter;
import pereira.lopes.julio.galeria.model.MyItem;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST = 1; // Usado para saber qual a tela está sendo requerida
    MyAdapter myAdapter;
    List<MyItem> itens = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvItens = findViewById(R.id.rvItens);

        myAdapter = new MyAdapter(this,itens);
        rvItens.setAdapter(myAdapter);

        rvItens .setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(),DividerItemDecoration.VERTICAL);

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem); // Obtendo o botão flutuante
        fabAddItem.setOnClickListener(new View.OnClickListener() { // Quando ele for clicado
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewItemActivity.class); //Cria uma navegação de uma tela para a outra
                startActivityForResult(i,NEW_ITEM_REQUEST); // Inicia a navegação para o resultado
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == NEW_ITEM_REQUEST){
            if (resultCode == Activity.RESULT_OK){ // Verifica se está  tudo certo
                // Pegando os dados de MyItem
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                myItem.photo = data.getData();

                itens.add(myItem); // Guardando os itens na lista de itens
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }
}
