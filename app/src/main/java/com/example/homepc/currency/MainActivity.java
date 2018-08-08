package com.example.homepc.currency;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String url = "http://data.fixer.io/api/latest?access_key=d424a3ed9809b76ae3488a58171573f5";
    TextView hello, textinr, textusd, texteur;
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hello = findViewById(R.id.hello);
        textinr = findViewById(R.id.textView);
        textusd = findViewById(R.id.textView2);
        texteur = findViewById(R.id.textView3);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                            String stringEur = editText.getText().toString();
                            Float eur = Float.parseFloat(stringEur);
                            editText.getText().clear();

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object = jsonObject.getJSONObject("rates");
                            String inr = object.getString("INR");
                            String usd = object.getString("USD");
                            String date = jsonObject.getString("date");

                            Float in = Float.parseFloat(inr);
                            Float us = Float.parseFloat(usd);

                            Float inrFinal = in * eur;
                            Float usdFinal = us * eur;

                            String usdStringFinal = "USD : " + usdFinal.toString();
                            String inrStringFinal = "INR : " + inrFinal.toString();
                            String eurStringFinal = "EUR : " + eur.toString();
                            String dateStringFinal = "Updated on: " + date;

                            hello.setText(dateStringFinal);
                            textinr.setText(inrStringFinal);
                            textusd.setText(usdStringFinal);
                            texteur.setText(eurStringFinal);

                            hello.setVisibility(View.VISIBLE);
                            textinr.setVisibility(View.VISIBLE);
                            textusd.setVisibility(View.VISIBLE);
                            texteur.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });


    }
}
