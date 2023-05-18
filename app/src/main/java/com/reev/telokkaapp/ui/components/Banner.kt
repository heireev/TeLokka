package com.reev.telokkaapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reev.telokkaapp.R
import com.reev.telokkaapp.data.PlaceRepository
import com.reev.telokkaapp.ui.screen.home.TeLokkaViewModel
import com.reev.telokkaapp.ui.screen.home.ViewModelFactory

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
            modifier = Modifier
                .height(130.dp)
                .fillMaxWidth()
        )
        SearchBar(query = query, onQueryChange = viewModel::search)
    }
}