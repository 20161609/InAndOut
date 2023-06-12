import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.example.inandout.R
import com.example.inandout.arrLocalInfo

class AsthmaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_asthma_fragment, container, false)

        val risk4 : TextView = view.findViewById(R.id.risk4)
        val cnt4 : TextView = view.findViewById(R.id.cnt4)
        val instruction4 : TextView = view.findViewById(R.id.instruction4)
        val light4 : AppCompatImageView = view.findViewById(R.id.light4)
        val riskIndex : Int = arrLocalInfo[3].risk.toInt()-1
        view.setBackgroundResource(
            arrayOf(
                R.drawable.shadow_caution,
                R.drawable.shadow_attention,
                R.drawable.shadow_warning,
                R.drawable.shadow_danger
            )[riskIndex]
        )

        risk4.text=arrayOf("관심","주의","경고","위험")[riskIndex]
        light4.setImageResource(
            arrayOf(
                R.drawable.light_caution,
                R.drawable.light_attention,
                R.drawable.light_warning,
                R.drawable.light_danger
            )[riskIndex]
        )

        cnt4.text= "예상 진료 건 수 : ".plus(arrLocalInfo[3].cnt)
        instruction4.text= arrLocalInfo[3].instruction

        return view
    }
}
