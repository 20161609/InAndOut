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

class DermatitisFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_dermatitis_fragment, container, false)
        val risk5 : TextView = view.findViewById(R.id.risk5)
        val cnt5 : TextView = view.findViewById(R.id.cnt5)
        val instruction5 : TextView = view.findViewById(R.id.instruction5)
        val light5 : AppCompatImageView = view.findViewById(R.id.light5)
        val riskIndex : Int = arrLocalInfo[4].risk.toInt()-1

        view.setBackgroundResource(
            arrayOf(
                R.drawable.shadow_caution,
                R.drawable.shadow_attention,
                R.drawable.shadow_warning,
                R.drawable.shadow_danger
            )[riskIndex]
        )
        risk5.text= arrayOf("관심","주의","경고","위험")[riskIndex]
        light5.setImageResource(
            arrayOf(
                R.drawable.light_caution,
                R.drawable.light_attention,
                R.drawable.light_warning,
                R.drawable.light_danger
            )[riskIndex]
        )

        cnt5.text= "예상 진료 건 수 : ".plus(arrLocalInfo[4].cnt)
        instruction5.text= arrLocalInfo[4].instruction
        return view
    }
}