package local.host.thon

import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

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
            view.findViewById<TextView>(R.id.cal1).text = "1.23"
        val ans1 = view.findViewById<TextView>(R.id.ans1)
        btn1.setOnClickListener {
            val x = view.findViewById<TextInputEditText>(R.id.inp1)
            ans1.text = (x.text.toString().toFloat() * 30F * 2F).toString()
        }
        return view;
    }
}