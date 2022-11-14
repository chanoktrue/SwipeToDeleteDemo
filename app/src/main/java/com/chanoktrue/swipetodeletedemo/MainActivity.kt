package com.chanoktrue.swipetodeletedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chanoktrue.swipetodeletedemo.ui.theme.SwipeToDeleteDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeToDeleteDemoTheme {
                SwipeToDelete()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDelete() {
    val lists = mutableStateListOf(
       "AAA",
       "BBB",
       "CCC",
       "DDD",
       "EEE",
       "FFF"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nood Developer Yotube",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) {
        LazyColumn{
            itemsIndexed(items = lists, key = {_, listItem ->
                listItem.hashCode()
            }){ index, item ->
                val state = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            lists.remove(item)
                        }
                        true
                    }
                )
                SwipeToDismiss(state = state, background = {
                    val color = when (state.dismissDirection) {
                        DismissDirection.StartToEnd -> Color.Transparent
                        DismissDirection.EndToStart -> Color.Red
                        null -> Color.Magenta
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = color)
                            .padding(10.dp)
                    ){
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                },
                dismissContent = {
                    SampleItems(item)
                },
                directions = setOf(DismissDirection.EndToStart))
                Divider()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SampleItems(item: String) {
    ListItem(
        text = {
            Text(text = item)
        },
        overlineText = {
            Text(text = "Welcome To")
        },
        icon = {
            Icon(imageVector = Icons.Outlined.Person, contentDescription = "Person")
        },
        trailing = {
            Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = "DownArrow")
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SwipeToDeleteDemoTheme {
        SwipeToDelete()
    }
}