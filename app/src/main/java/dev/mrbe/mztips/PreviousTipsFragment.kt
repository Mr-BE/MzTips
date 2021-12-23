package dev.mrbe.mztips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.ads.AdRequest
import dev.mrbe.mztips.data.*
import dev.mrbe.mztips.databinding.FragmentPrviousTipsBinding
import dev.mrbe.mztips.databinding.FragmentTipsBinding
import dev.mrbe.mztips.models.Odds
import kotlinx.coroutines.flow.asStateFlow



class PreviousOddsFragment : Fragment() {
    private lateinit var binding: FragmentPrviousTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPrviousTipsBinding.inflate(inflater, container, false)

        //ads
        val topAdsView =  binding.tipsTopAdsPrevious
        val bottomAdsView = binding.tipsBottomAdsPrevious

        val adRequest = AdRequest.Builder()
            .build()

        topAdsView.loadAd(adRequest)
        bottomAdsView.loadAd(adRequest)
        //compose section
        binding.composeViewPrevious.apply {
            //dispose the composition when the view's lifecycle owner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                MaterialTheme {
                    OddsList()
                }
            }
        }
        return binding.root

    }





    @Composable
    private fun OddsList(

        oddsViewModel: OddsViewModel = viewModel(modelClass = OddsViewModel::class.java,
            this, factory = OddsViewModelFactory(OddsRepo())
        )
    ) {
        val arimoFont = Font(R.font.arimo)
        val textBackgroundColor = Color(R.color.orange_500)
        when (val oddsList = oddsViewModel
            .oddsStateFlow.asStateFlow().collectAsState().value) {

            is OnError -> {
                Text(text = "Error: Please try again later")
            }
            is OnSuccess -> {
                val listOfOdds = oddsList.querySnapshot?.toObjects(Odds::class.java)
                listOfOdds?.let {
                    //load list
                    LazyColumn {
                        items(listOfOdds) { item: Odds? ->

                            //ensure result has been updated
                            if (item?.oddsResult != -1){
                            //Items
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Row() {

                                    Column(Modifier.fillMaxWidth()) {

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(0.dp, 8.dp, 0.dp, 0.dp)

                                                .background(Color.Black),
                                            verticalAlignment = Alignment.CenterVertically

                                        ) {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                item?.date.let { oddsDate ->
                                                    if (oddsDate != null) {
                                                        Text(
                                                            text = oddsDate,
                                                            style = TextStyle(
                                                                fontStyle = arimoFont.style,
                                                                fontSize = 14.sp,
                                                                color = Color.White
                                                            ),
                                                        )
                                                    }
                                                }
                                            }

                                        }

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(0.dp, 2.dp, 0.dp, 8.dp)
                                                .background(colorResource(id = R.color.text_background)),
                                        ) {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {

                                                item?.oddsTip.let { oddsTip ->
                                                    if (item?.oddsResult == 1) {

                                                    if (oddsTip != null) {

                                                        Text(
                                                            text = oddsTip,
                                                            style = TextStyle(
                                                                fontStyle = arimoFont.style,
                                                                fontSize = 20.sp,
                                                                color = Color.Green
                                                            )
                                                        )
                                                    }
                                                } else {
                                                        Text(
                                                            text = oddsTip.toString(),
                                                            style = TextStyle(
                                                                fontStyle = arimoFont.style,
                                                                fontSize = 20.sp,
                                                                color = Color.Red
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                        }


                                    }
                                }


                            }
                        }

                        }
                    }
                }
            }
        }

    }
}