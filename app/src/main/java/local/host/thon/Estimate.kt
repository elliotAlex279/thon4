package local.host.thon

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class Estimate : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        val view =  inflater.inflate(R.layout.estimate, container, false)
        val btn1 = view.findViewById<MaterialButton>(R.id.cal_btn1)
        val pre1 = view.findViewById<TextView>(R.id.cal1)
        val queue = Volley.newRequestQueue(view.context)

        val stringRequest = StringRequest(
            Request.Method.GET, "${addr}/ave",
            { response ->
                view.findViewById<CardView>(R.id.est1).visibility = View.VISIBLE
                val an = (JSONArray(response)[0]) as JSONObject
                val avg_units = an["4*unit/ID "].toString().toFloat();
                pre1.text = avg_units.toString()
                val ans = arrayOf(view.findViewById<TextView>(R.id.ans1),view.findViewById<TextView>(R.id.ans2),view.findViewById<TextView>(R.id.ans3))
                ans[0].text = "2"
                btn1.setOnClickListener {
                    val x = view.findViewById<TextInputEditText>(R.id.inp1)
                    try{
                        val k = x.text.toString().toFloat()
                        if(pre1.text.toString().toFloat()>k){
                            val m = ((avg_units - x.text.toString().toFloat())  * 30F * 2F)
                            ans[1].text = m.toString()
                            ans[2].text = (70000F/m).toInt().toString()
                            view.findViewById<CardView>(R.id.est3).visibility = View.VISIBLE
                        }else{
                            Snackbar.make(view,"You're not earning anything",Snackbar.LENGTH_SHORT).show()
                            MaterialAlertDialogBuilder(view.context)
                                .setMessage("You're not earning anything.")
                                .setCancelable(true)
                                .setNegativeButton("Close") { dialog, _ -> dialog.cancel() }
                                .create()
                                .show()
                            ans[1].text = "Not Earning"
                            ans[2].text = "-"
                        }

                    }catch (e : Exception){
                        Snackbar.make(view,"Value is must needed thing in this calculation",Snackbar.LENGTH_SHORT).show()
                    }
                }
            },{})
        queue.add(stringRequest)

        return view;
    }
}