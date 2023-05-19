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
import com.reev.telokkaapp.di.Injection
import com.reev.telokkaapp.ui.ViewModelFactory
import com.reev.telokkaapp.ui.screen.home.HomeViewModel

@Composable
fun Banner(
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
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