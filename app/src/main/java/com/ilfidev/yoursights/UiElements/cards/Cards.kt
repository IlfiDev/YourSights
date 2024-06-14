package com.ilfidev.yoursights.UiElements.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.models.data.MapPoint
import org.osmdroid.util.GeoPoint
import java.util.UUID

@Preview
@Composable
fun SearchSightCardPreview() {
    SearchSightCard( onClick = {}, cardInfo = MapPoint(UUID.randomUUID(), "Test", "Moscow", GeoPoint(0.0, 0.0), "aa", "aaa"))
}


@Composable
fun SearchSightCard(cardInfo: MapPoint, onClick: () -> Unit) {
    Card(elevation = CardDefaults.elevatedCardElevation(10.dp), modifier = Modifier.clickable(onClick = onClick)) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "test",
        )
        Text(cardInfo.name)
        Text(cardInfo.city)
    }
}


@Preview("RouteCards")
@Composable
fun RouteStopsCardPreview() {
    RouteStopsCard(cardInfo = MapPoint(UUID.randomUUID(), "Test", "Moscow", GeoPoint(0.0, 0.0), "aa", "aaa"))
}
@Composable
fun RouteStopsCard(cardInfo: MapPoint) {
    val images = listOf(R.drawable.ic_launcher_background, R.drawable.museum_big, R.drawable.baseline_search_24)
    val pagerState = rememberPagerState(pageCount = {
        3
    })
    Card(elevation = CardDefaults.elevatedCardElevation(10.dp)) {
        HorizontalPager(state = pagerState) {page ->

            Image(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                painter = painterResource(id = images[page]),
                contentDescription = "test",
            )
        }
        Text("${cardInfo.name}, ${cardInfo.city}")
        Text(cardInfo.allData)
    }
}
