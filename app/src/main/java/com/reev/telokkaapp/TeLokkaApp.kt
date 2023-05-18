package com.reev.telokkaapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reev.telokkaapp.model.PlacesData
import com.reev.telokkaapp.ui.theme.TeLokkaAppTheme

@Composable
fun TeLokkaApp(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ){
            items(PlacesData.places, key = {it.id}) {place ->
                PlaceItem(
                    name = place.name,
                    category = place.category,
                    photoUrl = place.photoUrl,
                    modifier = Modifier.fillMaxWidth()
                )
            }
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
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {  }
    ) {
        Card(
            shape = RoundedCornerShape(8.dp)
        ){
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