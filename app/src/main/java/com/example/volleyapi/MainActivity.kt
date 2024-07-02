package com.example.volleyapi

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var userList = arrayListOf<User>()

    private val apiSample = "https://jsonplaceholder.typicode.com/posts"

    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        val param = HashMap<String?, Any?>()


        val reqQueue: RequestQueue = Volley.newRequestQueue((this))
        val request = JsonObjectRequest(Request.Method.GET,apiSample, JSONObject(param), { res ->
            Log.e("Volley Sample", res.toString())

            val jsonArray = JSONArray(res)


            for (i in 0 until jsonArray.length()){
                val jsonObj = jsonArray.getJSONObject(i)

                val user = User(
                    jsonObj.getInt("userId"),
                    jsonObj.getString("id"),
                    jsonObj.getString("title"),
                    jsonObj.getString("body"),
//                    jsonObj.getString("avatar")
                    )

                userList.add(user)

            }

            recyclerView?.layoutManager = LinearLayoutManager(this)
            recyclerView?.adapter = UserAdapter(userList)


        }, {err ->
            Log.d("Volley Sample", err.message.toString())
        })

        reqQueue.add(request)
    }
}