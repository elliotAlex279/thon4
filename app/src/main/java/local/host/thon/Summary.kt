package local.host.thon

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.Path
import android.os.Bundle
import android.os.StrictMode
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import java.text.SimpleDateFormat
import java.util.*

import org.eazegraph.lib.charts.BarChart
import org.eazegraph.lib.models.BarModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat


class Summary : Fragment() {
    @SuppressLint("SetTextI18n", "SimpleDateFormat", "SetJavaScriptEnabled")

    fun createGraph(days : Int,view : View){
        val pr = view.findViewById<LinearLayout>(R.id.graphCanvas)
        pr.removeAllViews()
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val ftt = Date()
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        val dt = dateFormat.format(cal.time);
        val c = Calendar.getInstance()
        c.add(Calendar.DATE,-1)
        c.time = ftt
        var dayOfWeek = c[Calendar.DAY_OF_WEEK]
        val week = arrayOf("","Sun","Mon","Tue","Wed","Thu","Fri","Sat");
        println("Day of week in number:$dayOfWeek")
        val dayWeekText = SimpleDateFormat("E").format(ftt)
        println("Day of week in text:$dayWeekText")
        val queue = Volley.newRequestQueue(view.context)
        val url = "${addr}/graph/${dt},${days}"
        if(days==7){
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val mBarChart = BarChart(view.context)
                    val ll = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,512)
                    var daydt = 0;
                    val arr = JSONArray(response)
                    for(i in 0 until days){
                        dayOfWeek--;
                        if(dayOfWeek==0) dayOfWeek=7
                    }
                    for(i in 1 until arr.length()){
                        val m = arr[i] as JSONObject;
                        val n = arr[i-1] as JSONObject;
                        daydt = m["unit"].toString().toInt() - n["unit"].toString().toInt()
                        mBarChart.addBar(BarModel(week[dayOfWeek],daydt.toFloat(), -0xedcbaa))
                        if(dayOfWeek==7) dayOfWeek=1;
                        else dayOfWeek++;
                    }
                    mBarChart.layoutParams = ll
                    pr.addView(mBarChart)
                    mBarChart.startAnimation()
                    Log.d("A",response)
                },{

                })
            queue.add(stringRequest)

        }else if(days==15){
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val mBarChart = BarChart(view.context)
                    val ll = LinearLayout.LayoutParams(2500,512)
                    var daydt = 0;
                    val arr = JSONArray(response)
                    for(i in 0 until days) c.add(Calendar.DATE,-1)

                    for(i in 1 until arr.length()){
                        val m = arr[i] as JSONObject;
                        val n = arr[i-1] as JSONObject;
                        daydt = m["unit"].toString().toInt() - n["unit"].toString().toInt()
                        val dtF = SimpleDateFormat("d MMM")
                        mBarChart.addBar(BarModel(dtF.format(c.time),daydt.toFloat(), -0xedcbaa))
                        c.add(Calendar.DATE,1);
                    }
                    mBarChart.layoutParams = ll
                    pr.addView(mBarChart)
                    mBarChart.startAnimation()
                    Log.d("A",response)
                },{

                })
            queue.add(stringRequest)
        }else{

        }
    }

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
        val range = arrayOf("","")
        val one = view.findViewById<RadioGroup>(R.id.one)
        one.check(R.id.daily);
        stR[0].setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(view.context, { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                val df = SimpleDateFormat("dd-MM-yyyy")

                stR[0].text = df.format(Date("$year/${monthOfYear+1}/${dayOfMonth}"));
                range[0] = "$year-${monthOfYear+1}-${dayOfMonth}";
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
                    when(which){
                        0->{
                           createGraph(7,view);
                           }
                        1->{
                            createGraph(15,view);
                        }
                        2->{
                            createGraph(30,view);
                        }
                    }
                    view.findViewById<TextView>(R.id.period_txt).text = items[which];
                }
                .show()
        }

//        val mBarChart = view.findViewById(R.id.barchart) as BarChart
//        val mBarChart1 = view.findViewById(R.id.barchart1) as BarChart
//        mBarChart.addBar(BarModel("A",0f, -0xedcbaa))
//        mBarChart.addBar(BarModel("A",2f, -0xcbcbaa))
//        mBarChart.addBar(BarModel("A",3.3f, -0xa9cbaa))
//        mBarChart.addBar(BarModel("A",1.1f, -0x78c0aa))
//        mBarChart.addBar(BarModel("A",2.7f, -0xa9480f))
//        mBarChart.addBar(BarModel("A",2f, -0xcbcbaa))
//        mBarChart.addBar(BarModel("A",0.4f, -0xe00b54))
//        mBarChart.addBar(BarModel("A",4f, -0xe45b1a))
//        mBarChart.addBar(BarModel("A",4f, -0xe45b1a))
//        mBarChart.addBar(BarModel("A",2.3f, -0xedcbaa))
//        mBarChart.addBar(BarModel("A",2f, -0xcbcbaa))
//        mBarChart.addBar(BarModel("A",3.3f, -0xa9cbaa))
//        mBarChart1.addBar(BarModel("A",1.1f, -0x78c0aa))
//        mBarChart1.addBar(BarModel("A",2.7f, -0xa9480f))
//        mBarChart1.addBar(BarModel("A",2f, -0xcbcbaa))
//        mBarChart1.addBar(BarModel("A",0.4f, -0xe00b54))
//        mBarChart1.addBar(BarModel("A",4f, -0xe45b1a))
//        mBarChart1.addBar(BarModel("A",1.1f, -0x78c0aa))
//        mBarChart1.addBar(BarModel("A",2.7f, -0xa9480f))
//        mBarChart1.addBar(BarModel("A",2f, -0xcbcbaa))
//        mBarChart.isShowValues = true
//        mBarChart.startAnimation()
//        mBarChart1.startAnimation()
        return view;
    }
}