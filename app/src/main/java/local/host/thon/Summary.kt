package local.host.thon

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class Summary : Fragment() {
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        super.onCreate(savedInstanceState)
        val items = arrayOf("Last 7 days", "Last 15 days", "Last 30 days","Custom Range")
        val view =  inflater.inflate(R.layout.summary, container, false)
        val pBtn = view.findViewById<MaterialCardView>(R.id.pSelectBtn)
        val stR = arrayOf<MaterialButton>(view.findViewById(R.id.strart_range),view.findViewById(R.id.end_range))
        var range = arrayOf("","")
        stR[0].setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(view.context, { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                val df = SimpleDateFormat("dd-MM-yyyy")

                stR[0].text = df.format(Date("$year/${monthOfYear+1}/${dayOfMonth}"));
                range
            }, year, month, day)

            dpd.show()
        }
        stR[1].setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(view.context, { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                val df = SimpleDateFormat("dd-MM-yyyy")

                stR[1].text = df.format(Date("$year/${monthOfYear+1}/${dayOfMonth}"));

            }, year, month, day)

            dpd.show()
        }
        pBtn.setOnClickListener {
            MaterialAlertDialogBuilder(view.context)
                .setTitle("Select Period")
                .setItems(items) { dialog, which ->
                    if(which==items.size-1){
                        view.findViewById<MaterialCardView>(R.id.custom_range_card).visibility = View.VISIBLE
                    }else{
                        view.findViewById<MaterialCardView>(R.id.custom_range_card).visibility = View.GONE
                    }
                    view.findViewById<TextView>(R.id.period_txt).text = items[which];
                }
                .show()
        }
        return view;
    }
}