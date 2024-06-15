package com.ilfidev.yoursights.UiElements.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.UiElements.cards.RouteStopsCard
import com.ilfidev.yoursights.UiElements.screens.RoutesScreen
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.data.Route
import org.osmdroid.util.GeoPoint

@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
) {
    var nameValue by remember {
        mutableStateOf("")
    }
    var descriptionValue by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Добавить точку", style = TextStyle(fontSize = 20.sp))
//                Image(
//                    painter = painter,
//                    contentDescription = imageDescription,
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier
//                        .height(160.dp)
//                )
                Text(text = "Название")
                TextField(value = nameValue, onValueChange = {nameValue = it} )
                Text(text = "Описание")
                TextField(value = descriptionValue, onValueChange = {descriptionValue = it})
                Button(onClick = { onDismissRequest()}) {
                    Text("Добавить")
                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center,
//                ) {
//                    TextButton(
//                        onClick = { onDismissRequest() },
//                        modifier = Modifier.padding(8.dp),
//                    ) {
//                        Text("Dismiss")
//                    }
//                    TextButton(
//                        onClick = { onConfirmation() },
//                        modifier = Modifier.padding(8.dp),
//                    ) {
//                        Text("Confirm")
//                    }
//                }
            }
        }
    }
}

@Composable
fun AnotherDialog(cardInfo: MapPoint, onDismissRequest: () -> Unit) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(modifier = Modifier.verticalScroll(enabled = true, state = ScrollState(0))) {
            RouteStopsCard(cardInfo = cardInfo)
            Button(onClick = { onDismissRequest()}) {
                Text("Выбрать")
            }

        }
    }
}

@Composable
fun SendRoute(route: Route, onDismissRequest: () -> Unit) {
    val route = Route(
        stops = listOf(MapPoint(name = "1", position = GeoPoint(55.5, 37.5), allData = "text", city = "Москва", images = listOf()),
            MapPoint(name = "2", position = GeoPoint(55.6, 37.6), allData = "text", city = "Москва", images = listOf()),
            MapPoint(images = listOf(R.drawable.img_20190614_110857_largejpg, R.drawable.photo5jpg), position = GeoPoint(55.747224, 37.605240),
                name = "Государственный музей изобразительных искусств имени А. С. Пушкина", city = "Москва",
                allData = "Пу́шкинский музе́й — российский государственный художественный музей в Москве, одно из крупнейших в современной России собраний западного искусства. Созданный по инициативе историка и археолога, профессора Московского университета И. В. Цветаева, музей был открыт в 1912 году под названием «Музей изящных искусств имени императора Александра III при Императорском Московском университете». Главное здание музея было построено по проекту архитектора Романа Клейна в неоклассическом стиле в виде античного храма. Изначально музей был задуман как учебный, однако после революции 1917 года учреждение было преобразовано в Государственный музей изобразительных искусств. В 1937 году музей получил имя поэта Александра Пушкина. В 1991 году ГМИИ имени Пушкина внесли в Государственный свод особо ценных объектов культурного наследия народов Российской Федерации.\n" +
                        "\n" +
                        "По состоянию на 2018 год экспозиция состоит из более чем 670 тысяч предметов и включает в себя коллекцию слепков с античных статуй, художественные произведения, археологические находки, а также собрание предметов из Древнего Египта и Древнего Рима. В 2018 году музей посетили 1,3 млн человек, благодаря чему он занял 47-е место в числе самых посещаемых художественных музеев мира.\n",
            ),
        ),
        name = "Музеи Москвы" )
    var text by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { onDismissRequest()}) {
        Card() {
            Column (){
                Text(text = "Название маршрута:", modifier = Modifier.fillMaxWidth())
                TextField(value = text, onValueChange = {text = it})
                Button(onClick = {onDismissRequest()}) {
                    Text(text = "Поделиться")
                }
                RoutesScreen(route = route)

            }
        }
    }

}