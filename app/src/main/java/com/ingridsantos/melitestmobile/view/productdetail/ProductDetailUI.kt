package com.ingridsantos.melitestmobile.view.productdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ingridsantos.melitestmobile.R
import com.ingridsantos.melitestmobile.view.components.ImagesCarousel
import com.ingridsantos.melitestmobile.view.components.ScreenAlertMessage

@Composable
fun ProductDetailUI(
    product: String,
    viewModel: DetailProductViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.getProductDetail(product)
    }

    val state by viewModel.productState.collectAsState()

    if (state.isError != 0) {
        ScreenAlertMessage(message = "No matches found", color = Color.Red)
    }
    if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        DetailProduct(state, onNavigateBack)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailProduct(state: ProductDetailModel, onNavigateBack: () -> Unit = {}) {
    val images = state.product.pictures.toMutableList()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            TopAppBar(title = { Text("Detalle del producto") }, navigationIcon = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        modifier = Modifier,
                        contentDescription = "volver"
                    )
                }
            })

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                if (state.product.condition == "new") {
                    Text(
                        text = "Nuevo",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp)
                    )
                } else {
                    Text(
                        text = "Usado",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.product.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.padding(16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    ImagesCarousel(
                        itemsCount = images.size,
                        itemContent = { index ->
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(images[index])
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Inside,
                                modifier = Modifier.aspectRatio(1f)
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.product.price,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Divider(
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(id = R.string.title_info_product),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )

                state.product.attributes.forEach { atr ->
                    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                        Text(
                            text = atr.name,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp, 0.dp)
                        )
                        Divider(
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                        )
                        Text(
                            text = atr.valueName,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp, 0.dp)
                        )
                    }
                    Divider(color = Color.Black)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Divider(
                    color = Color.LightGray
                )

                if (state.product.warranty.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = stringResource(id = R.string.title_warranty),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = state.product.warranty,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(8.dp, 0.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Divider(
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}
