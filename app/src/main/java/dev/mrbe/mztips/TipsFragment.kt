package dev.mrbe.mztips

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mrbe.mztips.data.OddsViewModel
import dev.mrbe.mztips.data.OnError
import dev.mrbe.mztips.data.OnSuccess
import dev.mrbe.mztips.models.Odds
import kotlinx.coroutines.flow.asStateFlow

class TipsFragment() : AppCompatActivity() {


    @Composable
    fun OddsList(
        oddsViewModel: OddsViewModel
    ) {
        var showDialog by remember {
            mutableStateOf(false)
        }
//        var filterPref by rememberSaveable {
//            mutableStateOf(true)
//        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.daily_odds_text)) },
                    backgroundColor = colorResource(id = R.color.amber_500),
                    actions = {
                        IconButton(onClick = {
                            showDialog = true
                            Log.d("myTAG", "Clicked")
                        }) {

                            Icon(
                                modifier = Modifier.padding(8.dp),
                                painter = painterResource(
                                    id = R.drawable.ic_baseline_filter_list_24
                                ),
                                contentDescription = "TODO",
                            )
                        }
                    }
                )

            }
        ) {
            Surface(modifier = Modifier.padding(8.dp)) {

                if (showDialog) {
                    ShowFilterDialog { showDialog = false }
                }

                val arimoFont = Font(R.font.arimo)

                //Handle fire store query responses
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
                                    val isCurrentOdds = item?.oddsResult == -1
                                    if (isCurrentOdds) {
                                        //Items
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Row {

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
                                                                if (oddsTip != null) {
                                                                    Text(
                                                                        text = oddsTip,
                                                                        style = TextStyle(
                                                                            fontStyle = arimoFont.style,
                                                                            fontSize = 20.sp,
                                                                            color = Color.Black
                                                                        )
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }


                                                }
                                            }


                                        }
                                    } else {

                                        Box(modifier = Modifier.fillMaxSize()) {
                                            Image(
                                                painter = painterResource(R.drawable.zero),
                                                contentDescription = "zero content"
                                            )
                                        }

                                    }
                                }
                            }
                        }
                    }
                    else -> {
                        Log.wtf("Tips Fragment", "wtf happened")
                    }
                }

            }
        }
    }

    @Composable
    fun ShowFilterDialog(onClickOut: () -> Unit) {
        Log.d("myTAG", "Inside Dialog")

        AlertDialog(
            onDismissRequest = onClickOut,

            title = { Text(text = "Filter Odds") },
            text = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //create params
                    val filterParams = arrayListOf<String>(
                        "Premier League",
                        "La Liga", "Seria A", "Ligue 1", "Rest of World"
                    )

                    filterParams.forEach { option: String ->
                        Spacer(modifier = Modifier.size(16.dp))

                        Row {
                            var isChecked by rememberSaveable {
                                mutableStateOf(false)
                            }

                            Checkbox(checked = isChecked,
                                onCheckedChange = { isChecked = it })

                            Spacer(modifier = Modifier.size(16.dp))
                            Text(text = option)
                        }
                    }


                }
            },
            confirmButton = {
                Button(onClick = onClickOut) {
                    Text(text = "Confirm")

                }
            }
        )
    }
}