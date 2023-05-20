package com.reev.telokkaapp.ui.screen.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.reev.telokkaapp.R
import com.reev.telokkaapp.di.Injection
import com.reev.telokkaapp.ui.ViewModelFactory
import com.reev.telokkaapp.ui.common.UiState
import com.reev.telokkaapp.ui.components.MyButton
import com.reev.telokkaapp.ui.theme.TeLokkaAppTheme

@Composable
fun DetailScreen(
    placeId: String, viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ), navigateBack: () -> Unit, navigateToPlanning: (String) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getPlaceById(placeId)
                CircularProgressIndicator()
            }
            is UiState.Success -> {
                val data = uiState.data
                if (data != null) {
                    DetailContent(
                        data.id,
                        data.photoUrl,
                        data.name,
                        data.category,
                        data.mapUrl,
                        onBackClick = navigateBack,
                        navigateToPlanning = navigateToPlanning
                    )
                }
            }
            is UiState.Error -> {
                Text(text = uiState.errorMessage)
            }
        }
    }
}

@Composable
fun DetailContent(
    placeId: String,
    photoUrl: String,
    name: String,
    category: String,
    mapUrl: String,
    onBackClick: () -> Unit,
    navigateToPlanning: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            onBackClick()
                        })
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = "Category : $category", style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold
                    ), color = MaterialTheme.colors.secondary
                )
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                    context.startActivity(intent)
                }) {
                    Text(text = "Open Map Location")
                }
                Text(
                    text = stringResource(R.string.lorem_ipsum),
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.LightGray)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            MyButton(text = stringResource(R.string.menu_plan), onClick = {
                navigateToPlanning(placeId)
            })
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailScreenPreview() {
    TeLokkaAppTheme {
        DetailContent("",
            "",
            "Pantai Losari",
            "Pantai",
            "",
            onBackClick = {},
            navigateToPlanning = {})
    }
}