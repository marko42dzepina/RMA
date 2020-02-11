package dzepina.plovila.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dzepina.plovila.R;
import dzepina.plovila.viewmodel.PloviloViewModel;


public class CUDFragment extends Fragment {

    static final int SLIKANJE =1;

    private String trenutnaPutanjaSlike;

    @BindView(R.id.naziv)
    EditText naziv;
    @BindView(R.id.tip_plovila)
    Spinner tipPlovila;
    @BindView(R.id.godina)
    EditText godina;
    @BindView(R.id.opis)
    EditText opis;
    @BindView(R.id.slika)
    ImageView slika;
    @BindView(R.id.novoPlovilo)
    Button novoPlovilo;
    @BindView(R.id.uslikaj)
    Button uslikaj;
    @BindView(R.id.promjenaPlovila)
    Button promjenaPlovila;
    @BindView(R.id.obrisiPlovilo)
    Button obrisiPlovilo;

    PloviloViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud,
                container, false);
        ButterKnife.bind(this, view);

        model = ((MainActivity) getActivity()).getModel();

        if (model.getPlovilo().getId() == 0) {
            definirajNovoPlovilo();
            return view;
        }
        definirajPromjenaBrisanjePlovila();

        return view;
    }

    private void definirajPromjenaBrisanjePlovila() {
        novoPlovilo.setVisibility(View.GONE);
        tipPlovila.setSelection(model.getPlovilo().getTip());
        naziv.setText(model.getPlovilo().getNaziv());
        godina.setText(model.getPlovilo().getGodinaGradnje());
        opis.setText(model.getPlovilo().getOpis());
        Picasso.get().load(model.getPlovilo().getSlika()).error(R.drawable.plovilo).into(slika);

        uslikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uslikaj();
            }
        });

        promjenaPlovila.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promjenaPlovila();
            }
        });

        obrisiPlovilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obrisiPlovilo();
            }
        });


    }

    private void promjenaPlovila() {
        model.getPlovilo().setNaziv(naziv.getText().toString());
        model.getPlovilo().setTip(tipPlovila.getSelectedItemPosition());
        model.getPlovilo().setGodinaGradnje(godina.getText().toString());
        model.getPlovilo().setOpis(opis.getText().toString());
        model.promjeniPlovilo();
        nazad();
    }

    private void definirajNovoPlovilo() {
        promjenaPlovila.setVisibility(View.GONE);
        obrisiPlovilo.setVisibility(View.GONE);
        uslikaj.setVisibility(View.GONE);
        novoPlovilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novoPlovilo();
            }
        });
    }

    private void novoPlovilo() {
        model.getPlovilo().setNaziv(naziv.getText().toString());
        tipPlovila.setSelection(0);
        model.getPlovilo().setTip(tipPlovila.getSelectedItemPosition());
        model.getPlovilo().setGodinaGradnje(godina.getText().toString());
        model.getPlovilo().setOpis(opis.getText().toString());
        model.dodajNovoPlovilo();
        nazad();
    }

    private void obrisiPlovilo() {
        model.getPlovilo().setNaziv(naziv.getText().toString());
        model.getPlovilo().setTip(tipPlovila.getSelectedItemPosition());
        model.getPlovilo().setGodinaGradnje(godina.getText().toString());
        model.getPlovilo().setOpis(opis.getText().toString());
        model.obrisiPlovilo();
        nazad();
    }

    @OnClick(R.id.nazad)
    public void nazad() {
        ((MainActivity) getActivity()).read();
    }

    private void uslikaj() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) == null) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;

        }

            File slika = null;
            try {
                slika = kreirajDatotekuSlike();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;
            }

            if (slika == null) {
                Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
                return;
            }

            Uri slikaURI = FileProvider.getUriForFile(getActivity(),"dzepina.plovila.provider", slika);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, slikaURI);
            startActivityForResult(takePictureIntent, SLIKANJE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SLIKANJE && resultCode == Activity.RESULT_OK) {

            model.getPlovilo().setSlika("file://" + trenutnaPutanjaSlike);
            model.promjeniPlovilo();
            Picasso.get().load(model.getPlovilo().getSlika()).into(slika);

        }
    }

    private File kreirajDatotekuSlike() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imeSlike = "PLOVILO_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imeSlike,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        trenutnaPutanjaSlike = image.getAbsolutePath();
        return image;
    }

}