package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    var currentArtwork by rememberSaveable { mutableStateOf(1) }
    var imageRes by rememberSaveable { mutableStateOf(R.drawable.oeuvre_1) }
    var titleRes by rememberSaveable { mutableStateOf(R.string.artwork_1_title) }
    var artistRes by rememberSaveable { mutableStateOf(R.string.artwork_1_artist) }
    var yearRes by rememberSaveable { mutableStateOf(R.string.artwork_1_year) }

    fun showArtwork(id: Int) {
        currentArtwork = id
        when (id) {
            1 -> {
                imageRes = R.drawable.oeuvre_1
                titleRes = R.string.artwork_1_title
                artistRes = R.string.artwork_1_artist
                yearRes = R.string.artwork_1_year
            }
            2 -> {
                imageRes = R.drawable.oeuvre_2
                titleRes = R.string.artwork_2_title
                artistRes = R.string.artwork_2_artist
                yearRes = R.string.artwork_2_year
            }
            else -> {
                imageRes = R.drawable.oeuvre_3
                titleRes = R.string.artwork_3_title
                artistRes = R.string.artwork_3_artist
                yearRes = R.string.artwork_3_year
            }
        }
    }


    val onPreviousClick: () -> Unit = {
        when (currentArtwork) {
            1 -> showArtwork(3)
            2 -> showArtwork(1)
            else -> showArtwork(2)
        }
    }
    val onNextClick: () -> Unit = {
        when (currentArtwork) {
            1 -> showArtwork(2)
            2 -> showArtwork(3)
            else -> showArtwork(1)
        }
    }

    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArtworkWall(
                imageRes = imageRes,
                contentDescriptionRes = titleRes,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ArtworkDescriptor(
                    titleRes = titleRes,
                    artistRes = artistRes,
                    yearRes = yearRes,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                DisplayController(
                    onPreviousClick = onPreviousClick,
                    onNextClick = onNextClick
                )
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ArtworkWall(
                imageRes = imageRes,
                contentDescriptionRes = titleRes,
                modifier = Modifier.weight(1f)
            )
            ArtworkDescriptor(
                titleRes = titleRes,
                artistRes = artistRes,
                yearRes = yearRes,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            DisplayController(
                onPreviousClick = onPreviousClick,
                onNextClick = onNextClick
            )
        }
    }

}

@Composable
fun ArtworkWall(
    @DrawableRes imageRes: Int,
    @StringRes contentDescriptionRes: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            shadowElevation = 8.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = stringResource(contentDescriptionRes),
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}

@Composable
fun ArtworkDescriptor(
    @StringRes titleRes: Int,
    @StringRes artistRes: Int,
    @StringRes yearRes: Int,
    modifier: Modifier = Modifier
) {
    val title = stringResource(titleRes)
    val artist = stringResource(artistRes)
    val year = stringResource(yearRes)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Light
        )

        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(artist)
                }
                append(" (")
                append(year)
                append(")")
            },
            fontSize = 16.sp
        )
    }

}

@Composable
fun DisplayController(
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onPreviousClick,
            modifier = Modifier.width(130.dp)
        ) {
            Text(text = stringResource(R.string.previous))
        }
        Button(
            onClick = onPreviousClick,
            modifier = Modifier.width(130.dp)
        ) {
            Text(text = stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}