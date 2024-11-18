import android.Manifest
import android.content.Context
import androidx.core.content.ContextCompat

class CheckerPermissionLocation (val context : Context){

    fun dowehavelocationPermission(context: Context) : Boolean {

        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED



    }
}