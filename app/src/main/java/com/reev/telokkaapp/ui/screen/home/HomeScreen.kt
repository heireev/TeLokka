package com.reev.telokkaapp.ui.screen.home

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reev.telokkaapp.data.PlaceRepository
import com.reev.telokkaapp.ui.components.Banner
import com.reev.telokkaapp.ui.components.PlaceItem
import com.reev.telokkaapp.ui.theme.TeLokkaAppTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
){
    Column {
        Banner()
        PlaceItemList()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TeLokkaAppTheme {
        HomeScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaceItemList(
    modifier: Modifier = Modifier,
    viewModel: TeLokkaViewModel = viewModel(factory = ViewModelFactory(PlaceRepository()))
) {
    val groupedPlaces by viewModel.groupedPlaces.collectAsState()

    Box(
        modifier = modifier.padding(16.dp)
    ) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp),
        ) {
            groupedPlaces.forEach { (initial, places) ->
                stickyHeader {
                    CharacterHeader(initial)
                }
                items(places, key = { it.id }) { place ->
                    PlaceItem(
                        name = place.name,
                        category = place.category,
                        photoUrl = place.photoUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}





@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(elevation = 10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        )
    ) {
        Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
    }
}

@Composable
fun CharacterHeader(
    char: Char,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = char.toString(),
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}



