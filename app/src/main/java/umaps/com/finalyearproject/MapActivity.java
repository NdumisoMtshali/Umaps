package umaps.com.finalyearproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;



/**
 * Created by User on 10/2/2017.
 */




public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback,

        GoogleApiClient.OnConnectionFailedListener {
    //Newly added
    //28.7576° S, 32.0497° E


    private GoogleMap mMap;
    private LatLng myLatLng;
    private Marker myMarker;
    SpeechRecognizer speechRecognizer;
    AutoCompleteTextView editText, inputText;
    int count;
    String text;
    private Polyline polyline;

    //Add these three declarations
    private ImageView imageView;
    //private DatabaseReference reference;
    private ProgressDialog dialog;

    //TODO: Set autosuggestions

    String[] user_input = {"Gymnasium", "Rugby Field", "Soccer Field", "West Main Gate", "East F", "East E", "East D", "East C", "East B", "East A", "East Main Gate", "Department of Agriculture", "Department of Botany",
            "Department of Zoology", "SC", "Department of Hydrology", "Faculty of Commerce, Law and Admin", "Department of physics 2", "Department of Physics 1",
            "HP Lab", "Chapel", "Swimming Pool", "Student Center", "Library", "AL", "Bookshop", "LT", "NE", "Department of Nursing Science", "Thandanani Complex",
            "Department of Arts", "Department of Education", "Administration Building", "ATM", "BZ Hall", "UNIZULU PSD Office", "Clinic", "Unizulu Infrastructure Projects Social facility office",
            "Law Clinic", "PPO", "Post Office", "University of Zululand Main Gate","D Block","Science Block"};

    //Location coordinates
    LatLng UniversityOfZulMainGate = new LatLng(-28.85739357612142, 31.842819158931412);
    LatLng KwaDlangezwa_Post_Office = new LatLng(-28.85672312122827, 31.843488036755126);
    LatLng PPO = new LatLng(-28.85495579038936, 31.845184760841413);
    LatLng Law_Clinic = new LatLng(-28.85521291993815, 31.845651953216706);
    LatLng Uni_Infra_Proj_Social_Fac_office = new LatLng(-28.855959491142894, 31.845646670395706);
    LatLng Clinic = new LatLng(-28.85526599787817, 31.846465670395716);
    LatLng UNIZULU_PSD_Office = new LatLng(-28.855070512805852, 31.846825445928243);
    LatLng BZ_Hall = new LatLng(-28.854838766233073, 31.846945319380783);
    LatLng ATM = new LatLng(-28.854988869140858, 31.847109073917093);
    LatLng Admin_Build = new LatLng(-28.854182202079006, 31.84844283816489);
    LatLng Depart_of_Edu = new LatLng(-28.854535677345496, 31.84914212129098);
    LatLng Depart_of_Arts = new LatLng(-28.85514586398185, 31.84825794903648);
    LatLng Thandanani_Complex = new LatLng(-28.854683459190447, 31.84908977395559);
    LatLng Depart_of_Nursing_Sc = new LatLng(-28.85418094031047, 31.848873186871785);
    LatLng NE = new LatLng(-28.854382316119818, 31.84888780595837);
    LatLng LT = new LatLng(-28.85375080820662, 31.84973127100784);
    LatLng Bookshop = new LatLng(-28.853250086113025, 31.85038722367193);
    LatLng AL = new LatLng(-28.853619221247957, 31.85027791504373);
    LatLng Library = new LatLng(-28.852755427809377, 31.850246600861947);
    LatLng Stud_Center = new LatLng(-28.852508346785054, 31.84982770990779);
    LatLng Swim_Pool = new LatLng(-28.853096252700176, 31.848022647082324);
    LatLng Chapel = new LatLng(-28.852403883474096, 31.84850488767174);
    LatLng HP_Lab = new LatLng(-28.852566538454948, 31.85084532929125);
    LatLng Depart_of_Physics1 = new LatLng(-28.852435941425533, 31.850666383625647);
    LatLng Depart_of_Physics2 = new LatLng(-28.852252744722616, 31.85090981975329);
    LatLng Faclty_of_Comm_Law_Admin = new LatLng(-28.852429841619674, 31.851565673552493);
    LatLng Depart_of_Hydro = new LatLng(-28.852720415292218, 31.85166154954476);
    LatLng SC = new LatLng(-28.852743152255563, 31.850873109064135);
    LatLng Depart_of_Zoology = new LatLng(-28.851963031421654, 31.851722961146297);
    LatLng Depart_of_Botany = new LatLng(-28.851697704273487, 31.851586887348216);
    LatLng Depart_of_Agric = new LatLng(-28.85130300719006, 31.85225676915482);

    //East Residence
    LatLng East_Main = new LatLng(-28.851230501080913, 31.85141232929128);
    LatLng East_A = new LatLng(-28.850970759823348, 31.851255825667458);
    LatLng East_B = new LatLng(-28.85056290311771, 31.851241317640472);
    LatLng East_C = new LatLng(-28.850184839697583, 31.851342317634586);
    LatLng East_D = new LatLng(-28.849725379032023, 31.851219999843487);
    LatLng East_E = new LatLng(-28.849370252279396, 31.85126501150016);
    LatLng East_F = new LatLng(-28.850107663466886, 31.852846635425642);

    LatLng West_Main = new LatLng(-28.85177366523361, 31.84796835260466);

    LatLng Sports_Ground1 = new LatLng(-28.855830351407196, 31.84391274080342);  //Soccer
    LatLng Sports_Ground2 = new LatLng(-28.85283427387102, 31.846179614790294);  //Rugby

    LatLng Gymnasium = new LatLng(-28.8549699320156, 31.84371021116747);


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    //Called when the map is the ready to be used
    public void onMapReady(GoogleMap googleMap) {


        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);


        }

        //TODO:Add markers

        //TODO:Set Gymnasium
        BitmapDrawable bitmapdraw1 = (BitmapDrawable) getResources().getDrawable(R.drawable.exercise);
        Bitmap a = bitmapdraw1.getBitmap();
        Bitmap smallMarker1 = Bitmap.createScaledBitmap(a, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Gymnasium).title("Gymnasium").icon(BitmapDescriptorFactory.fromBitmap(smallMarker1)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Gymnasium, 10f));


        //TODO:Set Rugby field
        BitmapDrawable bitmapdraw2 = (BitmapDrawable) getResources().getDrawable(R.drawable.rugby);
        Bitmap b = bitmapdraw2.getBitmap();
        Bitmap smallMarker2 = Bitmap.createScaledBitmap(b, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Sports_Ground2).title("Rugby Field").icon(BitmapDescriptorFactory.fromBitmap(smallMarker2)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Sports_Ground2, 10f));
        //TODO:Set Soccer field
        BitmapDrawable bitmapdraw3 = (BitmapDrawable) getResources().getDrawable(R.drawable.soccer);
        Bitmap c = bitmapdraw3.getBitmap();
        Bitmap smallMarker3 = Bitmap.createScaledBitmap(c, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(Sports_Ground1).title("Soccer Field").icon(BitmapDescriptorFactory.fromBitmap(smallMarker3)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Sports_Ground1, 10f));

        //TODO:Set West Main Gate
        BitmapDrawable bitmapdraw4 = (BitmapDrawable) getResources().getDrawable(R.drawable.west);
        Bitmap d = bitmapdraw4.getBitmap();
        Bitmap smallMarker4 = Bitmap.createScaledBitmap(d, 60, 60, false);

        mMap.addMarker(new MarkerOptions().position(West_Main).title("West Main Gate").icon(BitmapDescriptorFactory.fromBitmap(smallMarker4)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(West_Main, 10f));

        //TODO:Set East F
        BitmapDrawable bitmapdraw5 = (BitmapDrawable) getResources().getDrawable(R.drawable.f);
        Bitmap e = bitmapdraw5.getBitmap();
        Bitmap smallmarker5 = Bitmap.createScaledBitmap(e, 30, 30, false);

        mMap.addMarker(new MarkerOptions().position(East_F).title("East F").icon(BitmapDescriptorFactory.fromBitmap(smallmarker5)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(East_F, 10f));

        //TODO:Set East E
        BitmapDrawable bitmapdraw6 = (BitmapDrawable) getResources().getDrawable(R.drawable.e);
        Bitmap f = bitmapdraw6.getBitmap();
        Bitmap smallmarker6 = Bitmap.createScaledBitmap(f, 30, 30, false);

        mMap.addMarker(new MarkerOptions().position(East_E).title("East_E").icon(BitmapDescriptorFactory.fromBitmap(smallmarker6)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(East_E, 10f));

        //TODO:Set East D
        BitmapDrawable bitmapdraw7 = (BitmapDrawable) getResources().getDrawable(R.drawable.d);
        Bitmap g = bitmapdraw7.getBitmap();
        Bitmap smallmarker7 = Bitmap.createScaledBitmap(g, 30, 30, false);
        mMap.addMarker(new MarkerOptions().position(East_D).title("East D").icon(BitmapDescriptorFactory.fromBitmap(smallmarker7)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(East_D, 10f));

        //TODO:Set East C
        BitmapDrawable bitmapdraw8 = (BitmapDrawable) getResources().getDrawable(R.drawable.c);
        Bitmap h = bitmapdraw8.getBitmap();
        Bitmap smallMarker8 = Bitmap.createScaledBitmap(h, 30, 30, false);
        mMap.addMarker(new MarkerOptions().position(East_C).title("East C").icon(BitmapDescriptorFactory.fromBitmap(smallMarker8)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(East_C, 10f));
        //TODO:Set East B
        BitmapDrawable bitmapdraw9 = (BitmapDrawable) getResources().getDrawable(R.drawable.b);
        Bitmap i = bitmapdraw9.getBitmap();
        Bitmap smallMarker9 = Bitmap.createScaledBitmap(i, 30, 30, false);
        mMap.addMarker(new MarkerOptions().position(East_B).title("East B").icon(BitmapDescriptorFactory.fromBitmap(smallMarker9)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(East_B, 10f));

        //TODO:Set East A
        BitmapDrawable bitmapdraw10 = (BitmapDrawable) getResources().getDrawable(R.drawable.a);
        Bitmap j = bitmapdraw10.getBitmap();
        Bitmap smallMarker10 = Bitmap.createScaledBitmap(j, 30, 30, false);

        mMap.addMarker(new MarkerOptions().position(East_A).title("East A").icon(BitmapDescriptorFactory.fromBitmap(smallMarker10)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(East_A, 10f));

        //TODO:Set East Main Gate
        BitmapDrawable bitmapdraw11 = (BitmapDrawable) getResources().getDrawable(R.drawable.east);
        Bitmap k = bitmapdraw11.getBitmap();
        Bitmap smallMarker11 = Bitmap.createScaledBitmap(k, 60, 60, false);


        mMap.addMarker(new MarkerOptions().position(East_Main).title("East Main Gate").icon(BitmapDescriptorFactory.fromBitmap(smallMarker11)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(East_Main, 10f));

        //TODO:Set Department of Agriculture
        BitmapDrawable bitmapdraw12 = (BitmapDrawable) getResources().getDrawable(R.drawable.farmer);
        Bitmap l = bitmapdraw12.getBitmap();
        Bitmap smallMarker12 = Bitmap.createScaledBitmap(l, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(Depart_of_Agric).title("Department of Agriculture").icon(BitmapDescriptorFactory.fromBitmap(smallMarker12)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Depart_of_Agric, 10f));

        //TODO:Set Department of Botany
        BitmapDrawable bitmapdraw13 = (BitmapDrawable) getResources().getDrawable(R.drawable.botany);
        Bitmap m = bitmapdraw13.getBitmap();
        Bitmap smallMarker13 = Bitmap.createScaledBitmap(m, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Depart_of_Botany).title("Department of Botany").icon(BitmapDescriptorFactory.fromBitmap(smallMarker13)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Depart_of_Botany, 10f));

        //TODO:Set Department of Zoology
        BitmapDrawable bitmapdraw14 = (BitmapDrawable) getResources().getDrawable(R.drawable.zoology);
        Bitmap n = bitmapdraw14.getBitmap();
        Bitmap smallMarker14 = Bitmap.createScaledBitmap(n, 30, 30, false);

        mMap.addMarker(new MarkerOptions().position(Depart_of_Zoology).title("Department of Zoology").icon(BitmapDescriptorFactory.fromBitmap(smallMarker14)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Depart_of_Zoology, 10f));

        //TODO:Set SC


        mMap.addMarker(new MarkerOptions().position(SC).title("SC"));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(SC, 10f));


        //TODO:Set Department of Hydrology
        BitmapDrawable bitmapdraw16 = (BitmapDrawable) getResources().getDrawable(R.drawable.water);
        Bitmap p = bitmapdraw16.getBitmap();
        Bitmap smallMarker16 = Bitmap.createScaledBitmap(p, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Depart_of_Hydro).title("Department of Hydrology").icon(BitmapDescriptorFactory.fromBitmap(smallMarker16)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Depart_of_Hydro, 10f));

        //TODO:Set Faculty of Commerce, Law and Admin
        BitmapDrawable bitmapdraw17 = (BitmapDrawable) getResources().getDrawable(R.drawable.comm);
        Bitmap q = bitmapdraw17.getBitmap();
        Bitmap smallMarker17 = Bitmap.createScaledBitmap(q, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Faclty_of_Comm_Law_Admin).title("Faculty of Commerce, Law and Admin").icon(BitmapDescriptorFactory.fromBitmap(smallMarker17)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Faclty_of_Comm_Law_Admin, 10f));

        //TODO:Set Department of Physics
        BitmapDrawable bitmapdraw18 = (BitmapDrawable) getResources().getDrawable(R.drawable.atom);
        Bitmap r = bitmapdraw18.getBitmap();
        Bitmap smallMarker18 = Bitmap.createScaledBitmap(r, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Depart_of_Physics2).title("Department of physics 2").icon(BitmapDescriptorFactory.fromBitmap(smallMarker18)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Depart_of_Physics2, 10f));
        BitmapDrawable bitmapdraw19 = (BitmapDrawable) getResources().getDrawable(R.drawable.atom);
        Bitmap s = bitmapdraw19.getBitmap();
        Bitmap smallMarker19 = Bitmap.createScaledBitmap(s, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Depart_of_Physics1).title("Department of Physics 1").icon(BitmapDescriptorFactory.fromBitmap(smallMarker19)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Depart_of_Physics1, 10f));


        //TODO:Set HP Lab
        BitmapDrawable bitmapdraw20 = (BitmapDrawable) getResources().getDrawable(R.drawable.computer);
        Bitmap t = bitmapdraw20.getBitmap();
        Bitmap smallMarker20 = Bitmap.createScaledBitmap(t, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(HP_Lab).title("HP Lab").icon(BitmapDescriptorFactory.fromBitmap(smallMarker20)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(HP_Lab, 10f));

        //TODO:Set Chapel

        BitmapDrawable bitmapdraw21 = (BitmapDrawable) getResources().getDrawable(R.drawable.townhall);
        Bitmap u = bitmapdraw21.getBitmap();
        Bitmap smallMarker21 = Bitmap.createScaledBitmap(u, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(Chapel).title("Chapel").icon(BitmapDescriptorFactory.fromBitmap(smallMarker21)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(HP_Lab, 10f));


        //TODO:Set swimming pool

        BitmapDrawable bitmapdraw22 = (BitmapDrawable) getResources().getDrawable(R.drawable.swimmer);
        Bitmap v = bitmapdraw22.getBitmap();
        Bitmap smallMarker22 = Bitmap.createScaledBitmap(v, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(Swim_Pool).title("Swimming Pool").icon(BitmapDescriptorFactory.fromBitmap(smallMarker22)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Swim_Pool, 10f));

        //TODO:Set student center
        BitmapDrawable bitmapdraw23 = (BitmapDrawable) getResources().getDrawable(R.drawable.studentcenter);
        Bitmap w = bitmapdraw23.getBitmap();
        Bitmap smallMarker23 = Bitmap.createScaledBitmap(w, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(Stud_Center).title("Student Center").icon(BitmapDescriptorFactory.fromBitmap(smallMarker23)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Stud_Center, 10f));

        //TODO:Set library
        BitmapDrawable bitmapdraw24 = (BitmapDrawable) getResources().getDrawable(R.drawable.library);
        Bitmap x = bitmapdraw24.getBitmap();
        Bitmap smallMarker24 = Bitmap.createScaledBitmap(x, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(Library).title("Library").icon(BitmapDescriptorFactory.fromBitmap(smallMarker24)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Library, 10f));

        //TODO:Set AL
        BitmapDrawable bitmapdraw25 = (BitmapDrawable) getResources().getDrawable(R.drawable.al_lecture_hall);
        Bitmap y = bitmapdraw25.getBitmap();
        Bitmap smallMarker25 = Bitmap.createScaledBitmap(y, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(AL).title("AL").icon(BitmapDescriptorFactory.fromBitmap(smallMarker25)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(AL, 10f));

        //TODO:Set bookshop
        BitmapDrawable bitmapdraw26 = (BitmapDrawable) getResources().getDrawable(R.drawable.bookshop);
        Bitmap z = bitmapdraw26.getBitmap();
        Bitmap smallMarker26 = Bitmap.createScaledBitmap(z, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Bookshop).title("Bookshop").icon(BitmapDescriptorFactory.fromBitmap(smallMarker26)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Bookshop, 10f));


        //TODO:Set LT

        BitmapDrawable bitmapdraw27 = (BitmapDrawable) getResources().getDrawable(R.drawable.lt_lecture_room);
        Bitmap aa = bitmapdraw27.getBitmap();
        Bitmap smallMarker27 = Bitmap.createScaledBitmap(aa, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(LT).title("LT").icon(BitmapDescriptorFactory.fromBitmap(smallMarker27)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(LT, 10f));


        //TODO:Set NE

        BitmapDrawable bitmapdraw28 = (BitmapDrawable) getResources().getDrawable(R.drawable.ne_lecture_hall);
        Bitmap bb = bitmapdraw28.getBitmap();
        Bitmap smallMarker28 = Bitmap.createScaledBitmap(bb, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(NE).title("NE").icon(BitmapDescriptorFactory.fromBitmap(smallMarker28)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(NE, 10f));

        //TODO:Set Department of Nursing Science

        BitmapDrawable bitmapdraw29 = (BitmapDrawable) getResources().getDrawable(R.drawable.nursing);
        Bitmap cc = bitmapdraw29.getBitmap();
        Bitmap smallMarker29 = Bitmap.createScaledBitmap(cc, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(Depart_of_Nursing_Sc).title("Department of Nursing Science").icon(BitmapDescriptorFactory.fromBitmap(smallMarker29)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Depart_of_Nursing_Sc, 10f));

        //TODO:Set Thandanani complex
        BitmapDrawable bitmapdraw30 = (BitmapDrawable) getResources().getDrawable(R.drawable.complex);
        Bitmap dd = bitmapdraw30.getBitmap();
        Bitmap smallMarker30 = Bitmap.createScaledBitmap(dd, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Thandanani_Complex).title("Thandanani Complex").icon(BitmapDescriptorFactory.fromBitmap(smallMarker30)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Thandanani_Complex, 10f));

        //TODO:Set Department of Arts
        BitmapDrawable bitmapdraw31 = (BitmapDrawable) getResources().getDrawable(R.drawable.arts_culture);
        Bitmap ee = bitmapdraw31.getBitmap();
        Bitmap smallMarker31 = Bitmap.createScaledBitmap(ee, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Depart_of_Arts).title("Department of Arts").icon(BitmapDescriptorFactory.fromBitmap(smallMarker31)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Depart_of_Arts, 10f));

        //TODO:Set Department of Education
        BitmapDrawable bitmapdraw32 = (BitmapDrawable) getResources().getDrawable(R.drawable.education);
        Bitmap ff = bitmapdraw32.getBitmap();
        Bitmap smallMarker32 = Bitmap.createScaledBitmap(ff, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Depart_of_Edu).title("Department of Education").icon(BitmapDescriptorFactory.fromBitmap(smallMarker32)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Depart_of_Edu, 10f));

        //TODO:Set Administration Building
        BitmapDrawable bitmapdraw33 = (BitmapDrawable) getResources().getDrawable(R.drawable.admin);
        Bitmap gg = bitmapdraw33.getBitmap();
        Bitmap smallMarker33 = Bitmap.createScaledBitmap(gg, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Admin_Build).title("Administration Building").icon(BitmapDescriptorFactory.fromBitmap(smallMarker33)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Admin_Build, 10f));
        //TODO:Set ATM
        BitmapDrawable bitmapdraw34 = (BitmapDrawable) getResources().getDrawable(R.drawable.atm);
        Bitmap hh = bitmapdraw34.getBitmap();
        Bitmap smallMarker34 = Bitmap.createScaledBitmap(hh, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(ATM).title("ATM").icon(BitmapDescriptorFactory.fromBitmap(smallMarker34)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(ATM, 10f));

        //TODO:Set BZ Hall
        BitmapDrawable bitmapdraw35 = (BitmapDrawable) getResources().getDrawable(R.drawable.bz);
        Bitmap ii = bitmapdraw35.getBitmap();
        Bitmap smallMarker35 = Bitmap.createScaledBitmap(ii, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(BZ_Hall).title("BZ Hall").icon(BitmapDescriptorFactory.fromBitmap(smallMarker35)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(BZ_Hall, 10f));

        //TODO:Set UNIZULU_PSD_Office
        BitmapDrawable bitmapdraw36 = (BitmapDrawable) getResources().getDrawable(R.drawable.badge);
        Bitmap jj = bitmapdraw36.getBitmap();
        Bitmap smallMarker36 = Bitmap.createScaledBitmap(jj, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(UNIZULU_PSD_Office).title("UNIZULU PSD Office").icon(BitmapDescriptorFactory.fromBitmap(smallMarker36)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(UNIZULU_PSD_Office, 10f));

        //TODO:Set clinic
        BitmapDrawable bitmapdraw37 = (BitmapDrawable) getResources().getDrawable(R.drawable.pharmacy);
        Bitmap kk = bitmapdraw37.getBitmap();
        Bitmap smallMarker37 = Bitmap.createScaledBitmap(kk, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(Clinic).title("Clinic").icon(BitmapDescriptorFactory.fromBitmap(smallMarker37)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Clinic, 10f));

        //TODO:Set Unizulu Infra Project Social Fac Office
        BitmapDrawable bitmapdraw38 = (BitmapDrawable) getResources().getDrawable(R.drawable.infrastructure);
        Bitmap ll = bitmapdraw38.getBitmap();
        Bitmap smallMarker38 = Bitmap.createScaledBitmap(ll, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(Uni_Infra_Proj_Social_Fac_office).title("Unizulu Infrastructure Projects Social facility office").icon(BitmapDescriptorFactory.fromBitmap(smallMarker38)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Uni_Infra_Proj_Social_Fac_office, 10f));

        //TODO: Set Law clinic
        BitmapDrawable bitmapdraw39 = (BitmapDrawable) getResources().getDrawable(R.drawable.lawclinic);
        Bitmap mm = bitmapdraw39.getBitmap();
        Bitmap smallMarker39 = Bitmap.createScaledBitmap(mm, 50, 50, false);
        mMap.addMarker(new MarkerOptions().position(Law_Clinic).title("Law Clinic").icon(BitmapDescriptorFactory.fromBitmap(smallMarker39)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(Law_Clinic, 10f));

        //TODO:Set PPO
        BitmapDrawable bitmapdraw40 = (BitmapDrawable) getResources().getDrawable(R.drawable.ppo);
        Bitmap nn = bitmapdraw40.getBitmap();
        Bitmap smallMarker40 = Bitmap.createScaledBitmap(nn, 50, 50, false);

        mMap.addMarker(new MarkerOptions().position(PPO).title("PPO").icon(BitmapDescriptorFactory.fromBitmap(smallMarker40)));
        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(PPO, 10f));

        //TODO:Set KwaDlangezwa Post Office


        //TODO:Set University of Zululand Main gate and Post office


        //Styling
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e1) {
            Log.e(TAG, "Can't find style. Error: ", e1);
        }


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UniversityOfZulMainGate, 13));

        //Add this method onMapReady
        //mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
        //@Override
        //public void onMapClick(@NonNull LatLng latLng) {
        //dialog.show();
        //reference.addValueEventListener(new ValueEventListener() {
        //@Override
        //public void onDataChange(@NonNull DataSnapshot snapshot) {
        //for (DataSnapshot ds:snapshot.getChildren()){
        //Block picture = ds.getValue(Block.class);
        //if (picture != null){

        //This is the line that set the retrieved image into your imageView
        //Picasso.get().load(picture.getBlock_image()).fit().into(imageView);

        //Your imageView is only visible when there is an image retrieved
        //imageView.setVisibility(View.VISIBLE);
        //}
        //}
        //}
        //@Override
        //public void onCancelled(@NonNull DatabaseError error) {

        //}
        //});
        //dialog.dismiss();
        //}
        //});


    }


    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 20f;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));


    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps, mInfo, mPlacePicker;


    //vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    //Newly added
    private com.google.android.gms.location.LocationRequest locationRequest;
    //LocationRequest logs
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                Log.d(TAG, "onLocationResult: " + location.toString());
                myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng point1 = new LatLng(-28.85517406350115, 31.846404311865406);
                LatLng point2 = new LatLng(-28.854994157739316, 31.846697364261303);
                LatLng point3 = new LatLng(-28.85453176080025, 31.847479046470216);
                LatLng point4 = new LatLng(-28.85448610999176, 31.8476670464702);
                LatLng point5 = new LatLng(-28.85446341156963, 31.847826682052403);
                LatLng point6 = new LatLng(-28.854597237104038, 31.848588317634608);
                LatLng point7 = new LatLng(-28.854682062446173, 31.84866431763457);
                LatLng point8 = new LatLng(-28.854596221168233, 31.84904431763454);
                LatLng UniversityOfZulMainGate = new LatLng(-28.85739357612142, 31.842819158931412);

                //try {

                //mMap.addPolyline((new PolylineOptions()).add(myLatLng, Clinic).
                //width(10).
                //color(Color.RED).
                //geodesic(true));

                //} catch (
                //NullPointerException ex) {

                // }
                //}

            }
        }
    };
    private GoogleApiClient mGoogleApiClient;
    private Marker mMarker;
    //Newly added
    private GoogleMap mGoogleMap;

    private ImageView microphone, direction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);










        //TODO:Set a search field for autosuggestions

        editText = findViewById(R.id.input_search);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, user_input);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.input_search);
        textView.setThreshold(3);
        textView.setAdapter(adapter);

        direction = (ImageView) findViewById(R.id.getdirections);

        //TODO:Set a button for directions



        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //TODO: Calling the google maps direction API and receiving the response
                LocationCallback locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null) {
                            return;
                        }
                        for (Location location : locationResult.getLocations()) {
                            Log.d(TAG, "onLocationResult: " + location.toString());
                            myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            LatLng point1 = new LatLng(-28.85517406350115, 31.846404311865406);

                        }

                        //}
                    }
                };


                for (int num1 = 0; num1 < user_input.length; num1++ ){
                    text = textView.getText().toString().trim();

                    if (text.equals(user_input[num1])){
                        String url = getUrl(Clinic, myLatLng, "driving");


                        //TODO: Invoking the url

                        new FetchURL(MapActivity.this).execute(url, "driving");

                        Toast.makeText(MapActivity.this, "Respond", Toast.LENGTH_SHORT).show();

                    }


                }




            }
        });






        getLocationPermission();




        //Create location request
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        //Microphone permissions

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);


        microphone = (ImageView) findViewById(R.id.microphone);

        //TODO:Set a microphone button to respond
        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    microphone.setImageDrawable(getDrawable(R.drawable.microphone));

                    //Begin listening

                    speechRecognizer.startListening(speechRecognizerIntent);

                    count = 1;
                } else {
                    microphone.setImageDrawable(getDrawable(R.drawable.mute));

                    speechRecognizer.stopListening();
                    //Stop listening

                    count = 0;
                }

            }
        });
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {

                ArrayList<String> data = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);

                editText.setText(data.get(0));


            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });


        //Initialize the declared resources
        //imageView = findViewById(R.id.imageVW);
        //reference = FirebaseDatabase.getInstance().getReference("Pictures");
        //dialog = new ProgressDialog(this);
        // dialog.setTitle("Please wait...");

    }

    //TODO: Calling the google directions and receiving a response.

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {


        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;

    }


    // onStart and onStop

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            getLastLocation();
            checkSettingsAndStartLocationUpdates();
        } else {
            getLocationPermission();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }


    // Check setup and begin location updates
    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //Settings of device are satisfied and we can start location updates
                startLocationUpdates();
            }
            // Newly added

        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(MapActivity.this, 1234);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    // Start location updates
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    // Stop location updates
    private void stopLocationUpdates() {
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    //Newly added
    // Get last location
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationTask = mFusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    //We have a location
                    Log.d(TAG, "onSuccess: " + location.toString());
                    Log.d(TAG, "onSuccess: " + location.getLatitude());
                    Log.d(TAG, "onSuccess: " + location.getLongitude());


                } else {
                    Log.d(TAG, "onSuccess: Location was null...");
                }

            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.getLocalizedMessage());
            }
        });
    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }


    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            //Newly added
                            getLastLocation();
                            checkSettingsAndStartLocationUpdates();

                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();


                }
            }
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    @Override
    public void onTaskDone(Object... values) {

        if (polyline != null)
            polyline.remove();
        polyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

}
















