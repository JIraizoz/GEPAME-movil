package es.jiraizoz.gepame;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import es.jiraizoz.gepame.AD.ADRestIncidencia;
import es.jiraizoz.gepame.LD.Incidencia;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<String> lCoordenadas;
    private ArrayList<Incidencia> lDatos;



    private static LatLng init;

    public MapsActivity(String posicionInicial, ArrayList<String> coordenadas) {
        this.lCoordenadas = coordenadas;
        double lat, lng;
        lat = Double.parseDouble(posicionInicial.substring(0, posicionInicial.indexOf(",")));
        lng = Double.parseDouble(posicionInicial.substring(posicionInicial.indexOf(",") + 1));
        init = new LatLng(lat, lng);
        this.lCoordenadas = coordenadas;
    }

    public MapsActivity() {
        this.lCoordenadas = new ArrayList<String>();
        this.lDatos = new ArrayList<Incidencia>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle b = getIntent().getExtras();
        String value = null; // or other values

        if (b != null) {
            value = b.getString("mode");
            if (value.equals("ALL")) {
                try {
                    ADRestIncidencia rest = new ADRestIncidencia();
                    rest.execute();
                    this.lDatos = rest.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else if (value.equals("SINGLE")){
                Incidencia serializada =(Incidencia) b.getSerializable("lData");

                this.lDatos = new ArrayList<Incidencia>();
                this.lDatos.add(serializada);
//                value = b.getStringArray("lData");
//                lCoordenadas = new ArrayList<String>(Arrays.asList(value));
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this.getApplicationContext(), R.raw.google_map_style));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(init));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12));


        if (lDatos.size() > 0) {
            double lat, lng;

            for (Incidencia i : lDatos) {
                lat = Double.parseDouble(i.getUtm().substring(0, i.getUtm().indexOf(",")));
                lng = Double.parseDouble(i.getUtm().substring(i.getUtm().indexOf(",") + 1));

                LatLng latLng = new LatLng(lat, lng);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(i.getDescripcion());

                mMap.addMarker(markerOptions);

                if (lDatos.size() == 1) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                }
            }

            // Add a marker in Sydney and move the camera
            /*LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));*/

        }

    }

    public static LatLng getInit() {
        return init;
    }

    public static void setInit(String coordenadas) {
        Double lat = Double.parseDouble(coordenadas.substring(0, coordenadas.indexOf(",")));
        Double lng = Double.parseDouble(coordenadas.substring(coordenadas.indexOf(",") + 1));

        MapsActivity.init = new LatLng(lat, lng);
    }
}
