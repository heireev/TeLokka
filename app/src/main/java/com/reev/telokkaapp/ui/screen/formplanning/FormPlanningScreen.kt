package com.reev.telokkaapp.ui.screen.formplanning

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.reev.telokkaapp.di.Injection
import com.reev.telokkaapp.ui.ViewModelFactory
import com.reev.telokkaapp.ui.common.UiState
import com.reev.telokkaapp.ui.components.TwoButtonsRow
import com.reev.telokkaapp.ui.screen.detail.DetailViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun FormPlanningScreen(
    placeId: String,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getPlaceById(placeId)
                // Show loading indicator
                CircularProgressIndicator()
            }
            is UiState.Success -> {
                val data = uiState.data
                if (data != null) {
                    FormPlanningContent(
                        name = data.name,
                        category = data.category,
                        photoUrl = data.photoUrl,
                        onBackClick = navigateBack,
                    )
                }
            }
            is UiState.Error -> {
                // Show error message
                Text(text = uiState.errorMessage)
            }
        }
    }
}

@Composable
fun FormPlanningContent(
    name: String,
    category: String,
    photoUrl: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val titleState = remember {
        mutableStateOf("")
    }
    val descState = remember {
        mutableStateOf("")
    }

    //variable untuk menyimpan waktu
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    //atur komponen
    Column(modifier = modifier){
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                //buat nama wisata dan kategori
                Text(
                    text = "Set your plan too ...",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = "Category : $category",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colors.secondary
                )

                //spacer
                Spacer(modifier = Modifier.height(16.dp))

                //Komponen untuk pick date
                Button(onClick = {
                    dateDialogState.show()
                }) {
                    Text(text = "Pick date")
                }
                Text(text = formattedDate)

                //spacer
                Spacer(modifier = Modifier.height(16.dp))

                // Komponen input untuk judul dan deskripsi
                TextField(
                    value = titleState.value,
                    onValueChange = { titleState.value = it },
                    label = { Text("Title of Activity") }
                )
                TextField(
                    value = descState.value,
                    onValueChange = { descState.value = it },
                    label = { Text("Description") }
                )
            }
            //Buat dialog state untuk waktu tanggal
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "OK") {
                        Toast.makeText(
                            context,
                            "Clicked OK",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    negativeButton(text = "CANCEL")
                }
            ) {
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Pick a date",
                    allowedDateValidator = { date ->
                        date.isAfter(LocalDate.now()) // Memastikan tanggal yang dipilih lebih besar dari tanggal saat ini
                    }
                ) {
                    pickedDate = it
                }
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(Color.LightGray))
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TwoButtonsRow(
                buttonText1 = "Cancel",
                buttonText2 = "Confirm",
                onCancelClick = {
                    onBackClick()
                },
                onConfirmClick = {

                }
            )
        }
    }
}