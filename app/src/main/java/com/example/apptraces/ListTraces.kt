package com.example.apptraces

import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListTraces : Fragment(R.layout.fragment_list){
    companion object {
        fun newInstance(difficulty: String): ListTraces {
            val fragment = ListTraces()
            val args = Bundle()
            args.putString("difficulty", difficulty)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val iconL = view.findViewById<ImageView>(R.id.iconL)
        iconL.setOnClickListener {
            val manager = (view.context as FragmentActivity).supportFragmentManager
            val fragmentTransaction = manager.beginTransaction()

            fragmentTransaction.replace(R.id.container, Information())
            fragmentTransaction.commit()
        }

        val names = arrayOf("Bieszczadzki Park Narodowy", "Morskie Oko - Czarny Staw","Morskie oko - Wodogrzmoty Mickiewicza",
            "Dolina Pięciu Stawów","Giewont","Gęsia Szyja","Wielka Siklawa","Jaskinia Raptawicka","Rusinowa Polana",
            "Żleb Kulczyńskiego","Kozi Wierch","Tatrzański szlak na Rysy")

        val stages = arrayOf("Pętla Wołosate - Tarnica - Halicz -Rozsypaniec (przez niebieski i Główny Szlak Beskidzki)",
            "Tatranska Javorina - Morskie oko - Czarny Staw" ,
            "Roztoka - Morskie oko - Wodogrzmoty Mickiewicza",
            "Palenica Białczańska - Dolina Białej Wody - Wodogrzmoty Mickiewicza - Dolina Roztoki - Wodospad Siklawa - Wielki Staw Polski - Mały Staw Polski",
            "Z Kuźnic - Kasprowy Wierch - Goryczkowe Czuby - Wyżyna Kondracka Przełęcz - Giewont",
            "Zazadnia - Rusinowa Polana - Gęsia Szyja",
            "Palenica Białczańska - Dolina Białej Wody - Wodogrzmoty Mickiewicza - Dolina Roztoki - Wodospad Siklawa",
            "Dolina Kościeliska - Hala Pisana - Jaskinia Raptawicka",
            "Wierchu Poroniec - Rusinowa Polana",
            "Kuźnice – Hala Gąsienicowa – Murowaniec – Czarny Staw Gąsienicowy – Zmarzły Staw – Kozia Dolinka – Żleb Kulczyńskiego",
            "Palenica Białczańska – Wodogrzmoty Mickiewicza – Dolina Roztoki – Schronisko PTTK w Dolinie Pięciu Stawów Polskich – Kozi Wierch",
            "Palenica Białczańska – Morskie Oko – Czarny Staw – Bula pod Rysami – Rysy i powrót do Palenicy Białczańskiej"
        )
        val descriptions = arrayOf("Trudna trasa piesza. Wymagany bardzo dobry poziom sprawności. Łatwo dostępne ścieżki. Odpowiednie dla każdego poziomu sprawności.",
            "Trudna trasa piesza. Wymagany bardzo dobry poziom sprawności. Wymagane stabilny krok, solidne buty i doświadczenie alpinistyczne.",
            "Średnio trudna trasa piesza. Wymagany dobry poziom sprawności. Przeważnie dobrze dostępne ścieżki. Wymagany stabilny krok.",
            "Szlak ten jest nad wyraz czarujący, a już samo przekroczenie progu Doliny Pięciu Stawów będzie wisienką na torcie. Otwiera się bowiem wyborny widok na Wielki Staw oraz otaczające okolicę skaliste szczyty. Po lewej stronie wznosi się Miedziane, następnie w rogu mamy magiczny Szpiglasowy Wierch. Na wprost piętrzą się Liptowskie Mury oraz Kotelnica. Natomiast po prawej mamy osławioną grań Orlej Perci. Całość uzupełnia potężny okruch skalny, który wystaje z toni Wielkiego Stawu tuż przy mostku. To chyba najbardziej obfotografowany głaz w całych Tatrach.",
            "Jeśli tylko mamy czas i siły, to szlak z Kasprowego Wierchu na Giewont to doskonały wybór. Lepszej opcji dotarcia na Śpiącego Rycerza nie znajdziemy! Jest to fantastyczna trasa, która wiedzie granią, miejscami nieco eksponowanymi odcinkami. Jest pięknie, widokowo i szalenie czarująco. I nieco zaskakująco, ponieważ na szlaku bywa relatywnie pusto.",
            "Gęsia Szyja to jedno z najpiękniejszych miejsc w Tatrach, które potrafi zawładnąć sercem nawet najmniej wrażliwego górskiego wędrowca. Na Gęsią Szyję wiedzie dość prosty szlak, którym chętnie do celu podążają rodziny z dziećmi. Jest on ciekawą opcją na wędrówkę o każdej porze roku. Bywa tutaj cudnie zimą, wiosną, jednak to jesień skrada zawsze największe ochy. Z naszej strony namawiamy, aby pognać do celu przez Rusinową Polanę i następnie zatoczyć pętlę przez Polanę pod Wołoszynem.",
            "Siklawa kryjąca się w Tatrach Wysokich w Dolinie Roztoki, jest największym wodospadem w Polsce. Spływa grzmiącymi kaskadami z progu wysokiego na około 65-70 metrów, tworząc piękną, często tęczową mgiełkę. Turyści chętnie przystają tutaj, siadają na kamieniu i kontemplują, bo rozmawiać przy takim nawale decybeli bywa po prostu ciężko. Najpiękniej jest tutaj przy słonecznej pogodzie, tuż po opadach deszczu.",
            "Jaskinia Raptawicka znajduje się 180 metrów nad dnem Doliny Kościeliskiej i można śmiało zwiedzać ją samodzielnie bez przewodnika. Wraz z Jaskinią Mylną oraz Jaskinią Obłazkową, jest częścią systemu Jaskinie Pawlikowskiego. Sama Jaskinia Raptawicka ma głębokość 15 metrów i długość 150 metrów. Aby się do niej dostać, będziecie musieli nieco wgramolić się po skale i następnie zejść do jaskini po kilkumetrowej, pionowej drabinie",
            "Rusinowa Polana to kultowe miejsce w Tatrach Wysokich. Wiele osób właśnie tutaj zaczyna swoją tatrzańską przygodę, a później częstokroć wraca, by podziwiać niebanalną panoramę górskich olbrzymów. Rusinowa Polana znajduje się pomiędzy Gęsią Szyją a Gołym Wierchem, na grzbiecie łączącym Tatry z Pogórzem Bukowińskim. Na miejscu krzyżują się dwa szlaki. Zielony z Wierchu Poroniec na Halę Gąsienicową oraz niebieski z drogi Oswalda Balzera (Zazadne) od Palenicy Białczańskiej.",
            "Żleb Kulczyńskiego jest jednym z najtrudniejszych i jednocześnie jednym z najciekawszych wariantów dotarcia na słynną Orlą Perć. Szlak jest relatywnie trudny. Znajdziecie tutaj klamry, łańcuchy, sporą ekspozycję i co gorsza, sypiący się piarg. Stopień trudności znacząco wzrasta przy mokrej skale oraz gdy schodzicie nim w dół. Trzeba przyznać, że zejście z Orlej przez Żleb Kulczyńskiego jest sporym wyzwaniem, nawet dla turysty obytego z ekspozycją i trudnościami.",
            "Kozi Wierch zawdzięcza swoja nazwę kozicom, które dość często hasają w jego okolicy. Natomiast pasterze z Doliny Gąsienicowej nazywali szczyt Czarnymi Ścianami. Co ciekawe, jest on najwyższą górą całkowicie ulokowaną na terenie Polski, a jego wysokość wynosi 2291 m n.p.m. Znajduje się na grani Orlej Perci, pomiędzy Doliną Gąsienicową i Doliną Pięciu Stawów Polskich. Latem większość turystów osiąga szczyt podążając trudną granią Orlej, natomiast zimą największy ruch generuje szlak od Piątki.",
            "Rysy są najwyższym tatrzańskim szczytem po polskiej stronie i upragnionym celem całej rzeszy turystów. Pewną ciekawostką dla niektórych z nich będzie fakt, że Rysy posiadają aż trzy wierzchołki. Nasz rodzimy przedstawiciel o 2499 m wysokości jest wierzchołkiem środkowym. Po stronie słowackiej znajdują się dwa pozostałe – ten najwyższy liczy sobie 2501 m, a najniższy, wyraźnie odstający od pozostałej dwójki – 2473 m."
            )
        val timeTraces = arrayOf("4:00:00","5:00:00","4:30:00","3:10:00","6:30:00","2:10:10","6:00:00","2:00:00","1:05:00","5:00:00","9:00:00","12:00:00")
        val difficultyOfTraces = arrayOf("difficult","difficult","easy","easy","difficult","easy","easy","easy","easy","difficult","difficult","difficult")
        val photos = arrayOf(R.drawable.szlak1,R.drawable.szlak2,R.drawable.szlak3,R.drawable.szlak4,R.drawable.szlak5,R.drawable.szlak6,R.drawable.szlak7,R.drawable.szlak8,R.drawable.szlak9,R.drawable.szlak10,R.drawable.szlak11,R.drawable.szlak12)

        val TracesList = ArrayList<Traces>()

        for (i in names.indices) {
            val trace = Traces(names[i], stages[i], descriptions[i], timeTraces[i], difficultyOfTraces[i], photos[i])
            TracesList.add(trace)
        }

        var TracesListDisplay = ArrayList<Traces>()

        val difficulty = this.arguments?.getString("difficulty")
        when (difficulty) {

            "easy" ->TracesListDisplay = getEasyRoutes(TracesList)
            "difficult" ->TracesListDisplay = getDifficultRoutes(TracesList)
            }


        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.adapter = TracesAdapter(TracesListDisplay)
    }

    private fun getDifficultRoutes(routes: ArrayList<Traces>): ArrayList<Traces> {
        val list = ArrayList<Traces>()
        routes.forEachIndexed{ i, v -> if(routes[i].type=="difficult") list.add(routes[i])}
        return list
    }

    private fun getEasyRoutes(routes: ArrayList<Traces>): ArrayList<Traces> {
        val list = ArrayList<Traces>()
        routes.forEachIndexed{ i, v -> if(routes[i].type=="easy") list.add(routes[i])}
        return list
    }
}