package inc.appscode0.actionbound;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Etc_main_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_etc_main_page);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        String action = intent.getStringExtra("action");
      //  Toast.makeText(this, action, Toast.LENGTH_SHORT).show();

        if(action.equals("settings"))
        {
            Fragment fr;
            fr= new Etc_main_page.FragmentSettings();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();
        }

        else

        if(action.equals("info"))
        {
            Fragment fr;
            fr= new Etc_main_page.FragmentInfo();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();
        }
        else
        if(action.equals("location"))
        {
            Fragment fr;
            fr= new Etc_main_page.FragmentNearby();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


    public static class FragmentSettings extends Fragment
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");
            BootstrapEditText usernamebootstrapEditText= (BootstrapEditText)rootView.findViewById(R.id.editText10);
            BootstrapEditText passwordbootstrapEditText= (BootstrapEditText)rootView.findViewById(R.id.editText10);
            BootstrapButton bootstrapButton= (BootstrapButton)rootView.findViewById(R.id.button8);
            BootstrapButton bootstrapButtonsignup= (BootstrapButton)rootView.findViewById(R.id.button8);

           final String username=usernamebootstrapEditText.getText().toString();
            final String password=passwordbootstrapEditText.getText().toString();

            bootstrapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog progress = new ProgressDialog(getActivity());
                    progress.setMessage("Signing you in");
                    progress.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://actionbound.herokuapp.com/loginUser",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progress.dismiss();


                                    try {
                                        JSONObject json = new JSONObject(response);
                                        String ssd = json.getString("success");

                                        if (ssd.equals("0")) {

                                            TastyToast.makeText(getActivity(), "Login failed. Please try again", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
                                        } else
                                        {



                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progress.dismiss();

                                }
                            }){
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("email",username);
                            params.put("password",password);
                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);
                }
            });

            return rootView;
        }
    }


    public static class FragmentInfo extends Fragment
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_info, container, false);

            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Info");

            return rootView;
        }
    }


    public static class FragmentNearby extends Fragment
    {
        Double latitude,longtitude;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragmentbounds_nearby, container, false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Nearby Treasures");





            return rootView;
        }



    }


}
