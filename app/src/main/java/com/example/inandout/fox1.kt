import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class ReviewExtractor : AsyncTask<String, Void, List<String>>() {

    override fun doInBackground(vararg params: String?): List<String> {
        val placeUrl = params[0]
        val reviewList = mutableListOf<String>()

        try {
            val doc: Document = Jsoup.connect(placeUrl).get()
            val reviewLists: List<Element> = doc.select(".list_evaluation > li")

            if (reviewLists.isNotEmpty()) {
                for ((index, review) in reviewLists.withIndex()) {
                    val comment: List<Element> = review.select(".txt_comment > span")
                    val rating: List<Element> = review.select(".grade_star > em")
                    var valStr = ""

                    if (comment.isNotEmpty()) {
                        if (rating.isNotEmpty()) {
                            valStr = comment[0].text() + "/" + rating[0].text().replace("점", "")
                        } else {
                            valStr = comment[0].text() + "/n"
                        }
                        reviewList.add(valStr)
                    } else {
                        reviewList.add("Nothing")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ReviewExtractor", "Error: ${e.message}")
        }

        return reviewList
    }

    override fun onPostExecute(result: List<String>?) {
        if (result != null) {
            for (review in result) {
                println(review)
            }
        }
    }
}

fun fuga() {
    val placeUrl = "https://place.map.kakao.com/9368503"
    val reviewExtractor = ReviewExtractor()
    reviewExtractor.execute(placeUrl)
}
