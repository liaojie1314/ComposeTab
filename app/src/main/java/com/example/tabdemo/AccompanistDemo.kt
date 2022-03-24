package com.example.tabdemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tabdemo.ui.theme.TabDemoTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class AccompanistDemo : ComponentActivity() {
    val items = listOf("苹果", "枇杷", "樱桃", "草莓")

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val tabStr = remember {
                        mutableStateOf("苹果")
                    }
                    val scope = rememberCoroutineScope()
                    val state = rememberPagerState()
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column() {
                            TabRow(selectedTabIndex = items.indexOf(tabStr.value),
                                modifier = Modifier.fillMaxWidth(),
                                indicator = { tabIndicator ->
                                    TabRowDefaults.Indicator(
                                        Modifier.tabIndicatorOffset(tabIndicator[items.indexOf(tabStr.value)])
                                    )
                                },
                                divider = {}) {
                                items.forEachIndexed { index, title ->
                                    val selected = index == items.indexOf(tabStr.value)
                                    Tab(selected = selected, onClick = {
                                        tabStr.value = items[index]
                                        scope.launch {
                                            state.scrollToPage(index)
                                        }
                                    },
                                        text = { Text(text = items[index]) })
                                }
                            }
                        }
                        HorizontalPager(state = state, count = items.size) { page ->
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                when (page) {
                                    0 -> Image(
                                        painter = painterResource(id = R.mipmap.pic_1),
                                        contentDescription = ""
                                    )
                                    1 -> Image(
                                        painter = painterResource(id = R.mipmap.pic_2),
                                        contentDescription = ""
                                    )
                                    2 -> Image(
                                        painter = painterResource(id = R.mipmap.pic_3),
                                        contentDescription = ""
                                    )
                                    3 -> Image(
                                        painter = painterResource(id = R.mipmap.pic_4),
                                        contentDescription = ""
                                    )
                                }
                                Text(text = items[page])
                            }
                        }
                        tabStr.value = items[state.currentPage]
                        //Toast.makeText(this, "select:${state.currentPage}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    TabDemoTheme {
        Greeting("Android")
    }
}