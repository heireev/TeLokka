package com.reev.telokkaapp

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.reev.telokkaapp.data.PlaceRepository
import com.reev.telokkaapp.ui.theme.TeLokkaAppTheme
import kotlinx.coroutines.launch

@Composable
fun TeLokkaApp(
){
    Column {
        Banner()
        PlaceItemList()
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

@Preview(showBackground = true)
@Composable
fun TeLokkaAppPreview() {
    TeLokkaAppTheme {
        TeLokkaApp()
    }
}

@Composable
fun PlaceItem(
    name: String,
    category: String,
    photoUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { }
    ) {
        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Text(
                        text = category,
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceItemPreview() {
    TeLokkaAppTheme {
        PlaceItem(
            name = "Pantai Losari",
            category = "Pantai",
            photoUrl = "",
            modifier = Modifier.padding(8.dp)
        )
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

@Composable
fun Banner(
    viewModel: TeLokkaViewModel = viewModel(factory = ViewModelFactory(PlaceRepository())),
    modifier: Modifier = Modifier
){
    val query by viewModel.query
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.image_1),
            contentDescription = "Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(160.dp).fillMaxWidth()
        )
        SearchBar(query = query, onQueryChange = viewModel::search)
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(stringResource(R.string.touristspot_search))
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}