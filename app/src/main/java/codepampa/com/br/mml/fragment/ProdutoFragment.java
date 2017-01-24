package codepampa.com.br.mml.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.math.BigDecimal;

import codepampa.com.br.mml.R;
import codepampa.com.br.mml.activity.ProdutoActivity;
import codepampa.com.br.mml.model.Produto;
import codepampa.com.br.mml.util.Util;

public class ProdutoFragment extends BaseFragment {

    private Produto produto;

    private ImageView imageViewFoto;
    private EditText editTextNomeProduto;
    private EditText editTextMarca;
    private EditText editTextLocalCompra;
    private EditText editTextValorProduto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_produto, container, false);

        acoes(view);

        return  view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_salvar:{

                popularProduto();
//                new CarrosTask().execute(SAVE); //executa a operação CREATE em uma thread AsyncTask
//                break;

            }
            case R.id.menuitem_excluir:{
                /*new CarrosTask().execute(DELETE); //executa a operação DELETE em uma thread AsyncTask
                break;*/
            }
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return false;
    }

    private void popularProduto() {
        produto.nome = editTextNomeProduto.getText().toString();
        produto.marca = editTextMarca.getText().toString();
        produto.localCompra = editTextLocalCompra.getText().toString();
        produto.preco = new BigDecimal(editTextValorProduto.getText().toString());
    }

    private void editarProduto(View view) {
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_acoes, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK){
            Uri arquivoUri = data.getData();

            if(arquivoUri.toString().contains("images")) {
                imageViewFoto.setImageURI(arquivoUri);
                produto.urlImagem = arquivoUri.toString();
            }

        }

    }

    private void acoes(View view) {

        if(Util.isObjectsNull(produto)) {
            produto = new Produto();
            novoProduto(view);
        } else {
            editarProduto(view);
        }

    }

    private void novoProduto(View view) {
        ((ProdutoActivity) getActivity()).getSupportActionBar().setTitle(R.string.produto);
        imageViewFoto = (ImageView) view.findViewById(R.id.imageView2);
        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);
            }
        });

        editTextNomeProduto = (EditText) view.findViewById(R.id.nome_produto_fragmentproduto);
        editTextMarca = (EditText) view.findViewById(R.id.marca_produto_fragmentproduto);
        editTextValorProduto = (EditText) view.findViewById(R.id.valor_produto_fragmentproduto);
        editTextLocalCompra = (EditText) view.findViewById(R.id.local_compra_fragmentproduto);

    }


    public  void  setProduto(Produto produto) {
        this.produto = produto;
    }

}
