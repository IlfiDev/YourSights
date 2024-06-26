package com.ilfidev.yoursights.models.repository

import com.ilfidev.yoursights.R
import com.ilfidev.yoursights.models.data.MapPoint
import com.ilfidev.yoursights.models.data.Route
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.osmdroid.util.GeoPoint
import java.util.UUID

class MapOnlineRepository : MapInterface {

    private val _routesSharedFlow = MutableSharedFlow<Route>(replay = 1)
    override val routesSharedFlow: SharedFlow<Route> get() = _routesSharedFlow
    override suspend fun sendRoute(newRoute: Route) {
        TODO("Not yet implemented")
    }

    override suspend fun sendMapPoint(newPoint: MapPoint) {
        TODO("Not yet implemented")
    }

    override suspend fun addMapPoint(newPoint: MapPoint) {
        TODO("Not yet implemented")
    }

    override suspend fun getMapPoint(pointId: UUID){
//        return MapPoint()
    }

    override suspend fun getRoute(routeId: UUID){
        _routesSharedFlow.emit(
            Route(
                stops = listOf(
                    MapPoint(images = listOf(R.drawable.cremlino_moskovsky_kreml2, R.drawable.main_gate_to_the_kremlin, R.drawable.moscow_a_very_beautiful), position = GeoPoint(55.741556, 37.620028), city = "Москва", name = "Кремль",
                        allData = "Моско́вский Кремль[1] — крепость в центре Москвы и древнейшая её часть, главный общественно-политический и историко-художественный комплекс города, официальная резиденция Президента Российской Федерации, вплоть до распада СССР в декабре 1991 года была официальной резиденцией Генерального секретаря ЦК КПСС (в 1990—1991 годах — Президента СССР). Одно из самых известных архитектурных сооружений в мире.\n" +
                                "\n" +
                                "Расположен на высоком левом берегу Москвы-реки — Боровицком холме, при впадении в неё реки Неглинной. В плане Кремль — неправильный треугольник площадью 27,5 гектара. Южная стена обращена к Москве-реке, северо-западная — к Александровскому саду, восточная — к Красной площади[2]. "),
                    MapPoint(images = listOf(R.drawable.caption, R.drawable.tretyakov_gallery_in),
                        position = GeoPoint(55.752004, 37.617734),
                        name = "Третьяковская галерея", city = "Москва",
                        allData = "Третьяко́вская галере́я, Госуда́рственная Третьяко́вская галере́я (сокр. ГТГ; разг. Третьяко́вка) — российский государственный художественный музей в Москве, созданный на основе исторических коллекций купцов братьев Павла и Сергея Михайловичей Третьяковых; одно из крупнейших в мире собраний русского изобразительного искусства.\n" +
                                "\n" +
                                "История галереи традиционно отсчитывается с 1856 года — времени первых документированных приобретений П. М. Третьякова; в 1867 году галерея была открыта для посещения, а в 1892 году передана в собственность Москве. На момент передачи коллекция музея насчитывала 1276 картин, 471 рисунок, 10 скульптур русских художников, а также 84 картины иностранных мастеров. После революции 1917 года галерея была национализирована, коллекция стала пополняться из конфискованных частных собраний и музеев. В 1985 году Государственная картинная галерея на Крымском Валу была объединена с Третьяковской галереей и образовала единый музейный комплекс — Новая Третьяковка вместе с Центральным домом художника. В здании в Лаврушинском переулке была размещена коллекция живописи с древнейших времён до 1910-х годов, а в отделе на Крымском Валу — искусство XX века. "),
                    MapPoint(images = listOf(R.drawable.img_20190614_110857_largejpg, R.drawable.photo5jpg), position = GeoPoint(55.747224, 37.605240),
                        name = "Государственный музей изобразительных искусств имени А. С. Пушкина", city = "Москва",
                        allData = "Пу́шкинский музе́й — российский государственный художественный музей в Москве, одно из крупнейших в современной России собраний западного искусства. Созданный по инициативе историка и археолога, профессора Московского университета И. В. Цветаева, музей был открыт в 1912 году под названием «Музей изящных искусств имени императора Александра III при Императорском Московском университете». Главное здание музея было построено по проекту архитектора Романа Клейна в неоклассическом стиле в виде античного храма. Изначально музей был задуман как учебный, однако после революции 1917 года учреждение было преобразовано в Государственный музей изобразительных искусств. В 1937 году музей получил имя поэта Александра Пушкина. В 1991 году ГМИИ имени Пушкина внесли в Государственный свод особо ценных объектов культурного наследия народов Российской Федерации.\n" +
                                "\n" +
                                "По состоянию на 2018 год экспозиция состоит из более чем 670 тысяч предметов и включает в себя коллекцию слепков с античных статуй, художественные произведения, археологические находки, а также собрание предметов из Древнего Египта и Древнего Рима. В 2018 году музей посетили 1,3 млн человек, благодаря чему он занял 47-е место в числе самых посещаемых художественных музеев мира.\n",
                    ),
//                    MapPoint(position = GeoPoint(55.624251, 37.514248)),
                    MapPoint(images = listOf(R.drawable.cosmos, R.drawable.cosmos2), position = GeoPoint(55.822751, 37.639752),
                        name = "Музей космонавтики", city = "Москва",
                        allData = "Музей космона́втики в Москве (ранее Мемориальный музей космонавтики) — музей космической тематики в стилобате монумента «Покорителям космоса» на Аллее Космонавтов ВДНХ. Музей был открыт 10 апреля 1981 года — к 20-летию полёта в космос Юрия Гагарина[2]. Предметный фонд музея на январь 2017-го насчитывает более 100 тыс. единиц хранения. Общая площадь музея — 8400 м², из них 3720 м² занимает непосредственно экспозиция. Среди экспонатов находятся образцы ракетно-космической техники, личные вещи космонавтов и конструкторов, архивные документы и предметы нумизматики и филателии. Музею принадлежит филиал — Дом-музей академика Сергея Павловича Королёва на 1-й Останкинской улице. Помимо выставочной работы, музей занимается культурно-просветительской деятельностью и проводит масштабную научную работу. По данным на 2016 год, ежегодное число посетителей достигло 510 тысяч человек, а к 2020-му выросла до 750 тысяч"),
                ),

            )
        )
    }

    override suspend fun getRoutesWithSight(sightId: UUID) {
        TODO("Not yet implemented")
    }
}