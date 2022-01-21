package dev.mrbe.mztips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.mrbe.mztips.data.OddsRepo
import dev.mrbe.mztips.data.OddsViewModel
import dev.mrbe.mztips.data.OddsViewModelFactory
import dev.mrbe.mztips.nav.NavRoutes
import dev.mrbe.mztips.ui.theme.MzTipsTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MzTipsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val oddsViewModel: OddsViewModel = viewModel(
                        modelClass = OddsViewModel::class.java,
                        this, factory = OddsViewModelFactory(OddsRepo())
                    )

                    //set navigator
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NavRoutes.HomePage.route
                    ) {

                        //home screen
                        composable(NavRoutes.HomePage.route) {
                            HomeScreenContent(navController)
                        }

                        //Daily odds screen
                        composable(NavRoutes.DailyOdds.route) {
                            TipsFragment().OddsList(oddsViewModel)
                        }

                        //Previous odds screen
                        composable(NavRoutes.PreviousOdds.route) {
                            PreviousOddsFragment().PreviousOddsList(oddsViewModel)
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun HomeScreenContent(navController: NavController) {
    val font = FontFamily(Font(R.font.poppins_semi_bold, FontWeight.SemiBold))
//    val colourBackground = R.drawable.button_background

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                backgroundColor = colorResource(id = R.color.amber_500)
            )
        },
        floatingActionButton = {

        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.matchParentSize(),
                    painter = painterResource(id = R.drawable.tips_back), contentDescription = ""
                )


                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (topButton, bottomButton) = createRefs()

                    Button(
                        onClick = { navController.navigate(NavRoutes.DailyOdds.route) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(64.dp)
                            .constrainAs(topButton) {
                                top.linkTo(parent.top, margin = 8.dp)
                                start.linkTo(parent.start, margin = 16.dp)
                                end.linkTo(parent.end, margin = 16.dp)

                            },
                    ) {
                        Text(
                            text = stringResource(id = R.string.daily_odds_text),

                        )


                    }

                    Button(onClick = { navController.navigate(NavRoutes.PreviousOdds.route) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(64.dp)
                            .constrainAs(bottomButton)

                            {
                                bottom.linkTo(parent.bottom, margin = 64.dp)
                                start.linkTo(parent.start, margin = 16.dp)
                                end.linkTo(parent.end, margin = 16.dp)
//                   top.linkTo(topButton.bottom, margin = 64.dp)
                            }) {
                        Text(text = stringResource(id = R.string.button_previous_odds))

                    }
                }
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun TestPreview() {
//    HomeScreenContent(navController = )
//}


//}