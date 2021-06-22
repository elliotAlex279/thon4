package local.host.thon

import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun main(args : Array<String>){

//    val publicIP = (URL("http://www.checkip.org").readText().split("<span style=\"color: #5d9bD3;\">")[1]).split("</span>")[0]
//    val location = JSONObject(URL("http://api.ipstack.com/$publicIP?access_key=d8d57e041b8d5da9101ba4fbde602b6b").readText());
//    val dt = JSONObject(URL("https://api.sunrise-sunset.org/json?lat=${location["latitude"]}&lng=${location["longitude"]}").readText())
//    print("https://api.sunrise-sunset.org/json?lat=${location["latitude"]}&lng=${location["longitude"]}");

}