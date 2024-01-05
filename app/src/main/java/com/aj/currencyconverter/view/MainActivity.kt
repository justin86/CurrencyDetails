package com.aj.currencyconverter.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.aj.currencyconverter.ui.theme.CurrencyConverterTheme
import com.google.accompanist.pager.ExperimentalPagerApi


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.aj.currencyconverter.R
import com.aj.currencyconverter.utils.GetSelectedValue
import com.aj.currencyconverter.viewmodel.BankViewModel
import com.google.accompanist.pager.*
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel : BankViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar(
                            backgroundColor = MaterialTheme.colors.primary,
                            title = { Text(stringResource(R.string.app_name), color = Color.White) }
                        )
                    }
                ) {
                    MainScreen()
                }

            }
        }
    }


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    val tabData = getTabList()
    val pagerState = rememberPagerState(pageCount = tabData.size)
    Column(modifier = Modifier.fillMaxSize()) {
        TabLayout(tabData, pagerState)
        TabContent(tabData, pagerState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(tabData: List<Pair<String, ImageVector>>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        divider = {
            Spacer(modifier =Modifier.height(3.dp))
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 5.dp,
                color = Color.White
            )
        },


        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        tabData.forEachIndexed { index, s ->
            LeadingIconTab(selected = pagerState.currentPage == index, onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            },
                icon = {
                    Icon(imageVector = s.second, contentDescription = null)
                },
                text = {
                    Text(text = s.first, color = Color.White,  modifier = Modifier.requiredWidth(IntrinsicSize.Max))
                })
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(tabData: List<Pair<String, ImageVector>>, pagerState: PagerState) {
    HorizontalPager(state = pagerState) { index ->
        when (index) {
            0 -> {
                IBANValidator(viewModel)
            }

            1 -> {
                Currency(viewModel)
            }

          2 -> {
                Converter(viewModel)
            }


        }

    }

}


private fun getTabList(): List<Pair<String, ImageVector>> {
    return listOf(
        "IBAN" to Icons.Default.Settings,
        "Currency" to Icons.Default.List,
        "Converter" to Icons.Default.Build
    )
}

@OptIn(ExperimentalPagerApi::class)
@Preview()
@Composable
fun PreviewTab() {
    CurrencyConverterTheme {
        val tabData = getTabList()
        val pagerState = rememberPagerState(pageCount = tabData.size)
        TabLayout(tabData, pagerState)
    }
}

@Preview()
@Composable
fun PreviewContent() {
    CurrencyConverterTheme {
        MainScreen()
    }
}

}

@Composable
fun Indicator(
    size: Dp = 32.dp, // indicator size
    sweepAngle: Float = 90f, // angle (lenght) of indicator arc
    color: Color = MaterialTheme.colors.primary, // color of indicator arc line
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth //width of cicle and ar lines
) {


    // docs recomend use transition animation for infinite loops
    // https://developer.android.com/jetpack/compose/animation
    val transition = rememberInfiniteTransition(label = "")

    // define the changing value from 0 to 360.
    // This is the angle of the beginning of indicator arc
    // this value will change over time from 0 to 360 and repeat indefinitely.
    // it changes starting position of the indicator arc and the animation is obtained
    val currentArcStartAngle by transition.animateValue(
        0,
        360,
        Int.VectorConverter,
        infiniteRepeatable(
            animation = tween(
                durationMillis = 1100,
                easing = LinearEasing
            )
        ), label = "currentArcStartAngle"
    )



    // define stroke with given width and arc ends type considering device DPI
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Square)
    }

    // draw on canvas
    Canvas(
        Modifier
            .progressSemantics() // (optional) for Accessibility services
            .size(size) // canvas size
            .padding(strokeWidth / 2) //padding. otherwise, not the whole circle will fit in the canvas
    ) {
        // draw "background" (gray) circle with defined stroke.
        // without explicit center and radius it fit canvas bounds
        drawCircle(Color.LightGray, style = stroke)

        // draw arc with the same stroke
        drawArc(
            color,
            // arc start angle
            // -90 shifts the start position towards the y-axis
            startAngle = currentArcStartAngle.toFloat() - 90,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = stroke
        )
    }
}

@Composable
fun AppSpinner(name: String, list: List<String>, listener: GetSelectedValue){
    var expanded by remember { mutableStateOf(false) }
    val suggestions = list //listOf("Item1","Item2","Item3")
     var _selectedText  by remember { mutableStateOf("") }
   /* var selectedFromText by remember { mutableStateOf("") }
    var selectedToText by remember { mutableStateOf("") }*/

    var textfieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown


    Box() {
        OutlinedTextField(
            readOnly = true,
            enabled = false,
            value = _selectedText,
            onValueChange = {
                _selectedText = it

                            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .clickable {
                    expanded = !expanded
                },
            label = {Text(name)},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
        ) {
            suggestions.forEachIndexed{ pos, label ->
                DropdownMenuItem(onClick = {
                    _selectedText = label
                    listener.setSelectedItemP(label)
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}