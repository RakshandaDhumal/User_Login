package com.example.user_login;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
     String url="http://192.168.0.152:3001/User_login";
     EditText loginEt,passwordEt,mobileEt,emailEt;
     Button submit;
    String Userid;
    String UserPassword;
    String UserEmail;
    String UserMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        loginEt=findViewById(R.id.UserET);
        passwordEt=findViewById(R.id.Password);
        mobileEt=findViewById(R.id.Mobile);
        emailEt=findViewById(R.id.Email);

        submit=findViewById(R.id.Loginbut);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Userid = loginEt.getText().toString();
                 UserPassword = passwordEt.getText().toString();
                 UserEmail = emailEt.getText().toString();
                UserMobile = mobileEt.getText().toString();

                if (Userid.isEmpty() && UserPassword.isEmpty() && UserEmail.isEmpty() && UserMobile.isEmpty()) {
                    Toast.makeText(MainActivity.this, "please enter the fail", Toast.LENGTH_SHORT).show();
                } else {
                    postUser(Userid,UserPassword);
                }
            }
        });
        }
    private void postUser(String id,String pass) {

        requestQueue= Volley.newRequestQueue(this);
       JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url,null, new Response.Listener<JSONObject>() {
           @Override
             public void onResponse(JSONObject response) {
               Toast.makeText(MainActivity.this, "Responce"+response, Toast.LENGTH_SHORT).show();

               try {

                   String id1=response.getString("UserName");
                   String Pass1=response.getString("Password");

                   Toast.makeText(MainActivity.this, ""+id1+Pass1, Toast.LENGTH_SHORT).show();
                   Toast.makeText(MainActivity.this, ""+id+pass, Toast.LENGTH_SHORT).show();

                   if (Objects.equals(id, id1 )){

                       if (Objects.equals(pass, Pass1)){
                           Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                       }else{
                           Toast.makeText(MainActivity.this, "incorrect password", Toast.LENGTH_SHORT).show();
                       }

                   }else{
                       Toast.makeText(MainActivity.this, "Not Exist !!", Toast.LENGTH_SHORT).show();
                   }



               } catch (JSONException e) {
                   throw new RuntimeException(e);
               }

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(MainActivity.this, "Error"+error, Toast.LENGTH_SHORT).show();
           }
       });

        requestQueue.add(request);
    }
}