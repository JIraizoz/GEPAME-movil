package es.jiraizoz.gepame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import es.jiraizoz.gepame.AD.ADRestIncidencia;
import es.jiraizoz.gepame.Adapters.Lista_Adapter;
import es.jiraizoz.gepame.LD.Incidencia;


public class ListadoIncidentesActivity extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);

        ArrayList<Incidencia> datosIncidencias = new ArrayList<Incidencia>();

        try {
            ADRestIncidencia rest = new ADRestIncidencia();
            rest.execute();
            datosIncidencias = rest.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setListas(datosIncidencias);


    }


    public void setListas(ArrayList<Incidencia> listadoIncidentes) {
        this.lista = (ListView) findViewById(R.id.selectView);


        this.lista.setAdapter(new Lista_Adapter(this.getApplication().getBaseContext(), R.layout.entrada_lista, listadoIncidentes) {
            @Override
            public void onEntrada(Object entrada, View view) {

                if (entrada != null) {
                    Incidencia incidencia = (Incidencia) entrada;

                    TextView id = (TextView) view.findViewById(R.id.tw_entrada_id);
                    TextView fecha = (TextView) view.findViewById(R.id.tw_entrada_fecha);
                    TextView descripcion = (TextView) view.findViewById(R.id.tw_entrada_descripcion);
                    DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, new Locale("es", "ES"));

                    id.setText(incidencia.getTipo() + "-" + incidencia.getId());
                    fecha.setText(df.format(incidencia.getFecha()));
                    descripcion.setText(incidencia.getDescripcion());


                }
            }
        });

        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Incidencia elegido = (Incidencia) pariente.getItemAtPosition(posicion);

                ArrayList<String> lPosiciones = new ArrayList<String>();
                lPosiciones.add(elegido.getUtm());
                String[] sArray = new String[1];
                lPosiciones.toArray(sArray);

                MapsActivity mapsActivity = new MapsActivity(getString(R.string.posicionInicialMapa), lPosiciones);
                Intent intent = new Intent(view.getContext(), mapsActivity.getClass());
                intent.putExtra("mode", "SINGLE");
                intent.putExtra("lData", elegido);
//                intent.putExtra("lData", sArray);
                startActivity(intent);
            }
        });
    }
}
