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

class EyeInfectionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_eye_infection_fragment, container, false)
        val risk2 : TextView = view.findViewById(R.id.risk2)
        val cnt2 : TextView = view.findViewById(R.id.cnt2)
        val instruction2 : TextView = view.findViewById(R.id.instruction2)
        val light2 : AppCompatImageView = view.findViewById(R.id.light2)
        val riskIndex : Int = arrLocalInfo[1].risk.toInt()-1

        view.setBackgroundResource(
            arrayOf(
                R.drawable.shadow_caution,
                R.drawable.shadow_attention,
                R.drawable.shadow_warning,
                R.drawable.shadow_danger
            )[riskIndex]
        )

        risk2.text= arrayOf("관심","주의","경고","위험")[riskIndex]
        light2.setImageResource(
            arrayOf(
                R.drawable.light_caution,
                R.drawable.light_attention,
                R.drawable.light_warning,
                R.drawable.light_danger
            )[riskIndex]
        )

        cnt2.text= "예상 진료 건 수 : ".plus(arrLocalInfo[1].cnt)
        instruction2.text= arrLocalInfo[1].instruction
        return view
    }
}

