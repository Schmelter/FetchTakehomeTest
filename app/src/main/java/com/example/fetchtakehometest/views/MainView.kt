package com.example.fetchtakehometest.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.fetchtakehometest.datamodel.FetchDataElement
import com.example.fetchtakehometest.viewmodels.MainViewModelState
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainView(
    uiState: MainViewModelState,
    onFetch: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        uiState.fetchDataElements?.let {
            ConstraintLayout(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                var topRef = ConstrainedLayoutReference("parent")
                var linkRef = topRef.top

                for (fetchDataElement in uiState.fetchDataElements) {
                    val rowRef = createRef()

                    ConstraintLayout(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .border(2.dp, Color.Black)
                            .constrainAs(rowRef) {
                                top.linkTo(linkRef, margin = 2.dp)
                                absoluteLeft.linkTo(parent.absoluteLeft, margin = 2.dp)
                                absoluteRight.linkTo(parent.absoluteRight, margin = 2.dp)

                                topRef = rowRef
                                linkRef = topRef.bottom

                                width = Dimension.fillToConstraints
                                height = Dimension.value(50.dp)
                            }
                    ) {
                        val (idRef, listIdRef, nameRef) = createRefs()

                        Text(
                            modifier = Modifier
                                .constrainAs(idRef) {
                                    centerVerticallyTo(parent)
                                    absoluteLeft.linkTo(parent.absoluteLeft, margin = 5.dp)

                                    height = Dimension.wrapContent
                                    width = Dimension.wrapContent
                                },
                            text = "Id: " + fetchDataElement.id.toString()
                        )

                        Text(
                            modifier = Modifier
                                .constrainAs(listIdRef) {
                                    centerVerticallyTo(parent)
                                    centerHorizontallyTo(parent)

                                    height = Dimension.wrapContent
                                    width = Dimension.wrapContent
                                },
                            text = "ListId: " + fetchDataElement.listId.toString()
                        )

                        Text(
                            modifier = Modifier
                                .constrainAs(nameRef) {
                                    centerVerticallyTo(parent)
                                    absoluteRight.linkTo(parent.absoluteRight, margin = 5.dp)

                                    height = Dimension.wrapContent
                                    width = Dimension.wrapContent
                                },
                            text = "Name: " + fetchDataElement.name.toString()
                        )
                    }
                }

                constrain(topRef) {
                    bottom.linkTo(parent.bottom)
                }
            }

            Button(
                modifier = Modifier
                    .constrainAs(createRef()) {
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                        absoluteLeft.linkTo(parent.absoluteLeft, margin = 10.dp)
                        absoluteRight.linkTo(parent.absoluteRight, margin = 10.dp)

                        height = Dimension.value(50.dp)
                        width = Dimension.fillToConstraints
                    },
                onClick = onReset
            ) {
                Text("Reset Data")
            }
        } ?: run {

            Button(
                modifier = Modifier
                    .constrainAs(createRef()) {
                        centerVerticallyTo(parent)
                        absoluteLeft.linkTo(parent.absoluteLeft, margin = 10.dp)
                        absoluteRight.linkTo(parent.absoluteRight, margin = 10.dp)

                        height = Dimension.value(50.dp)
                        width = Dimension.fillToConstraints
                    },
                onClick = onFetch
            ) {
                Text("Fetch and Display Data")
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(createRef()) {
                        centerHorizontallyTo(parent)
                        centerVerticallyTo(parent, 0.3f)

                        width = Dimension.value(32.dp)
                        height = Dimension.value(32.dp)
                    },
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

@Preview(widthDp = 327, heightDp = 500)
@Composable
fun MainViewNoData() {
    val uiState = MainViewModelState()

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainView(
            uiState,
            onFetch = {},
            onReset = {}
        )
    }
}

@Preview(widthDp = 327, heightDp = 500)
@Composable
fun MainViewNoDataLoading() {
    val uiState = MainViewModelState(
        isLoading = true
    )

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainView(
            uiState,
            onFetch = {},
            onReset = {}
        )
    }
}

@Preview(widthDp = 327, heightDp = 500)
@Composable
fun MainViewWithData() {
    val uiState = MainViewModelState(
        isLoading = false,
        fetchDataElements = listOf(
        FetchDataElement(
            id = 1,
            listId = 1,
            name = "One"
        ),
        FetchDataElement(
            id = 2,
            listId = 2,
            name = "Two"
        ),
        FetchDataElement(
            id = 3,
            listId = 3,
            name = "Three"
        ),
        FetchDataElement(
            id = 4,
            listId = 4,
            name = "Four"
        ),
        FetchDataElement(
            id = 5,
            listId = 5,
            name = "Five"
        ),
    ))

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Green)
    ) {
        MainView(
            uiState,
            onFetch = {},
            onReset = {}
        )
    }
}