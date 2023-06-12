import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.toColorInt
import com.example.inandout.R
import com.example.inandout.arrLocalInfo

class ColdFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_cold_fragment, container, false)
        val risk1 : TextView = view.findViewById(R.id.risk1)
        val cnt1 : TextView = view.findViewById(R.id.cnt1)
        val instruction1 : TextView = view.findViewById(R.id.instruction1)
        val riskIndex : Int = arrLocalInfo[0].risk.toInt()-1
        val light1 : androidx.appcompat.widget.AppCompatImageView = view.findViewById(R.id.light1)
        view.setBackgroundResource(
            arrayOf(
                R.drawable.shadow_caution,
                R.drawable.shadow_attention,
                R.drawable.shadow_warning,
                R.drawable.shadow_danger
            )[riskIndex]
        )
        light1.setImageResource(
            arrayOf(
                R.drawable.light_caution,
                R.drawable.light_attention,
                R.drawable.light_warning,
                R.drawable.light_danger
            )[riskIndex]
        )

        risk1.text= arrayOf("관심","주의","경고","위험")[riskIndex]
        cnt1.text= "예상 진료 건 수 : ".plus(arrLocalInfo[0].cnt)
        instruction1.text= arrLocalInfo[0].instruction
        return view
    }
}
