package com.matin.happystore.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.matin.happystore.core.designsystem.baselineHeight
import com.matin.happystore.core.designsystem.theme.HappyStoreTheme

@Composable
fun ProfileScreen() {
    val nestedScrollInteropConnection: NestedScrollConnection =
        rememberNestedScrollInteropConnection()

    val scrollState = rememberScrollState()
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollInteropConnection)
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            HeaderSection(scrollState, containerHeight = this@BoxWithConstraints.maxHeight)
            Spacer(Modifier.height(20.dp))
            UserInfo(this@BoxWithConstraints.maxHeight)
        }
    }
}

@Composable
private fun UserInfo(containerHeight: Dp) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Name("Yousef Matinfard")
        Spacer(Modifier.height(10.dp))
        ProfileField("Job", "Android Developer")
        ProfileField("Sex", "Male")
        ProfileField("Hobby", "Reading book, Sport, Traveling")
        ProfileField("Address", "Italy")

        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))

    }
}

@Composable
fun Name(name: String) {
    Text(text = name, style = MaterialTheme.typography.displaySmall)
}

@Composable
fun ProfileField(label: String = "Sex", value: String = "Male") {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        HorizontalDivider()
        Text(
            text = label,
            modifier = Modifier.baselineHeight(24.dp),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            modifier = Modifier.baselineHeight(24.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun HeaderSection(scrollState: ScrollState, containerHeight: Dp) {
    val offset = scrollState.value / 2
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

    Image(
        modifier = Modifier
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = offsetDp)
            .clip(CircleShape),

        contentScale = ContentScale.Crop,
        painter = rememberAsyncImagePainter(R.drawable.profile_photo),
        contentDescription = "profile photo",
    )
}

@Preview
@Composable
fun ProfileScreenPreview() {
    HappyStoreTheme {
        Surface {
            ProfileScreen()
        }
    }
}