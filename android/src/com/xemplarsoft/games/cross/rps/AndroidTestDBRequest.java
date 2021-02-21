package com.xemplarsoft.games.cross.rps;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.xemplarsoft.utils.testdb.KVPair;
import com.xemplarsoft.utils.testdb.TestDB;

import java.util.HashMap;
import java.util.Map;

public class AndroidTestDBRequest extends StringRequest {
    private final KVPair[] pairs;
    public AndroidTestDBRequest(KVPair[] pairs, String url, Response.Listener<String> l){
        super(Method.POST, url, l, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.getMessage());
            }
        });
        this.pairs = pairs;
    }

    protected Map<String, String> getParams() {
        for(KVPair p : pairs){
            System.out.println("Params Got: " + (p == null ? "null" : p.toString()));
        }

        if(KVPair.get(pairs, "method") == null) return KVPair.toMap(pairs);
        System.out.println("Params Got, Method Name: " + KVPair.get(pairs, "method").value);
        return KVPair.toMap(pairs);
    }

    public Map<String, String> getHeaders() {
        Map<String,String> params = new HashMap<>();
        params.put("Content-Type","application/x-www-form-urlencoded");
        return params;
    }
}
