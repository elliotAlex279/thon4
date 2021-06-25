package local.host.thon

import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.lang.Exception
import java.util.*

class Estimate : Fragment() {
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
            Request.Method.GET, "${addr}/env",
            { response ->
                pre1.text = "5.14"
            },{})
        pre1.text = "5.14"
        val ans = arrayOf(view.findViewById<TextView>(R.id.ans1),view.findViewById<TextView>(R.id.ans2),view.findViewById<TextView>(R.id.ans3))
        ans[0].text = "2"
        btn1.setOnClickListener {
            val x = view.findViewById<TextInputEditText>(R.id.inp1)
            try{
                val k = x.text.toString().toFloat()
                if(pre1.text.toString().toFloat()>k){
                        val m = ((pre1.text.toString().toFloat() - x.text.toString().toFloat())  * 30F * 2F)
                        ans[1].text = m.toString()
                        ans[2].text = (70000F/m).toInt().toString()
                }else{
                    Snackbar.make(view,"You're not earning anything",Snackbar.LENGTH_SHORT).show()
                }

            }catch (e : Exception){
                Snackbar.make(view,"Value is must needed thing in this calculation",Snackbar.LENGTH_SHORT).show()
            }
        }
        return view;
    }
}