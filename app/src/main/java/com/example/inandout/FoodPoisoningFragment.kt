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

class FoodPoisoningFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_food_poisoning_fragment, container, false)

        val risk3 : TextView = view.findViewById(R.id.risk3)
        val cnt3 : TextView = view.findViewById(R.id.cnt3)
        val instruction3 : TextView = view.findViewById(R.id.instruction3)
        val light3 : AppCompatImageView = view.findViewById(R.id.light3)
        val riskIndex : Int = arrLocalInfo[2].risk.toInt()-1
        view.setBackgroundResource(
            arrayOf(
                R.drawable.shadow_caution,
                R.drawable.shadow_attention,
                R.drawable.shadow_warning,
                R.drawable.shadow_danger
            )[riskIndex]
        )

        risk3.text= arrayOf("관심","주의","경고","위험")[riskIndex]
        light3.setImageResource(
            arrayOf(
                R.drawable.light_caution,
                R.drawable.light_attention,
                R.drawable.light_warning,
                R.drawable.light_danger
            )[riskIndex]
        )

        cnt3.text= "예상 진료 건 수 : ".plus(arrLocalInfo[2].cnt)
        instruction3.text= arrLocalInfo[2].instruction
        return view
    }
}