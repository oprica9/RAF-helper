package rs.raf.projekat2.ognjen_prica_10620.presentation.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rs.raf.projekat2.ognjen_prica_10620.presentation.contracts.MainContract
import rs.raf.projekat2.ognjen_prica_10620.presentation.view.states.NoteChartState


@Composable
fun ChartScreen(
    viewModel: MainContract.NoteViewModel
) {

    val item = viewModel.item.value

    var c1 = 0f;
    var c2 = 0f;
    var c3 = 0f;
    var c4 = 0f;
    var c5 = 0f;
    viewModel.getLastDaysNoteNum()
    if (item.isNotEmpty())
        when (val state = item[0]) {
            is NoteChartState.Success -> {
                if (state.chartNotes.isEmpty()) {
                    c1 = 0f
                    c2 = 0f
                    c3 = 0f
                    c4 = 0f
                    c5 = 0f
                } else {
                    if (state.chartNotes[0] != null) {
                        c1 = state.chartNotes[0]!!
                    }
                    if (state.chartNotes[1] != null) {
                        c2 = state.chartNotes[1]!!
                    }
                    if (state.chartNotes[2] != null) {
                        c3 = state.chartNotes[2]!!
                    }
                    if (state.chartNotes[3] != null) {
                        c4 = state.chartNotes[3]!!
                    }
                    if (state.chartNotes[4] != null) {
                        c5 = state.chartNotes[4]!!
                    }
                }
                Text(
                    "Svaka kolona predstavlja jedan dan, visina predstavlja koliko beleski je kreirano svakog dana",
                    Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
                )
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    drawRect(
                        color = Color(0xff457b9d),
                        size = Size(width = (canvasWidth / 8f), height = 500f * c1),
                        topLeft = Offset(
                            x = (canvasWidth / 7f),
                            y = (canvasHeight / 2f - 500f * c1)
                        ),
                    )
                    drawRect(
                        color = Color(0xff457b9d),
                        size = Size(width = (canvasWidth / 8f), height = 500f * c2),
                        topLeft = Offset(
                            x = ((canvasWidth / 7f * 2)),
                            y = (canvasHeight / 2f - 500f * c2)
                        ),
                    )
                    drawRect(
                        color = Color(0xff457b9d),
                        size = Size(width = (canvasWidth / 8f), height = 500f * c3),
                        topLeft = Offset(
                            x = ((canvasWidth / 7f * 3)),
                            y = (canvasHeight / 2f - 500f * c3)
                        ),
                    )
                    drawRect(
                        color = Color(0xff457b9d),
                        size = Size(width = (canvasWidth / 8f), height = 500f * c4),
                        topLeft = Offset(
                            x = ((canvasWidth / 7f * 4)),
                            y = (canvasHeight / 2f - 500f * c4)
                        ),
                    )
                    drawRect(
                        color = Color(0xff457b9d),
                        size = Size(width = (canvasWidth / 8f), height = 500f * c5),
                        topLeft = Offset(
                            x = ((canvasWidth / 7f * 5)),
                            y = (canvasHeight / 2f - 500f * c5)
                        ),
                    )
                }
            }
        }
}

@Preview
@Composable
fun TestPracticePreview2() {
//    ChartScreen()
}