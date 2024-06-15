package com.ilfidev.yoursights.UiElements.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.data.Route
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
            painter = painterResource(id = cardInfo.images[0]),
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
    val images = cardInfo.images
    val pagerState = rememberPagerState(pageCount = {
        cardInfo.images.size
    })
    Card(elevation = CardDefaults.elevatedCardElevation(10.dp), modifier = Modifier.fillMaxWidth()) {
        if (cardInfo.images.size > 0) {
            HorizontalPager(state = pagerState) {page ->

                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    painter = painterResource(id = images[page]),
                    contentDescription = "test",
                )
            }
        }
        Text("${cardInfo.name}, ${cardInfo.city}", style = TextStyle(fontSize = 20.sp, fontStyle = FontStyle.Italic))
        Text(cardInfo.allData)
    }
}

@Composable
fun RouteCard (route: Route,  onClick: () -> Unit) {
   Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),elevation = CardDefaults.elevatedCardElevation(10.dp)) {
       Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
           Icon(painter = painterResource(id = R.drawable.museum_big), contentDescription = "Museums")
           route.name?.let { Text(it) }
           Text("Остановок: " + route.stops.size.toString())
       }

   }
}
