package com.example.data.local

import com.example.data.model.*

object KnowledgeBase {

    // Preset Emotions
    val EMOTIONS_MAP = mapOf(
        "Yalnızlık" to "Kendini yalnız hissettiğinde Allah sana şah damarından daha yakındır.",
        "Korku" to "Kalpleri ancak Allah'ı anmak doyuma ulaştırır ve huzura kavuşturur.",
        "Üzüntü" to "Gevşemeyin, üzülmeyin! Eğer gerçekten inanıyorsanız en üstün sizsiniz.",
        "Umutsuzluk" to "Allah'ın rahmetinden ümidinizi kesmeyin. Şüphesiz Allah bütün günahları bağışlar.",
        "Öfke" to "O takva sahipleri ki, öfkelerini yutarlar ve insanları affederler.",
        "Pişmanlık" to "Ey kendi aleyhlerine haddi aşan kullarım! Allah'ın rahmetinden ümidinizi kesmeyin.",
        "Haset" to "Allah'ın kiminizi kiminize üstün kıldığı şeyleri özlemeyin.",
        "Gurur & Kibir" to "Yeryüzünde böbürlenerek yürüme! Çünkü sen ne yeri yarabilirsin, ne de dağlara ulaşabilirsin.",
        "Nankörlük" to "Bana şükredin, sakın nankörlük etmeyin.",
        "Şükür" to "Eğer şükrederseniz, elbette size olan nimetimi artırırım.",
        "Sabır" to "Şüphesiz Allah sabredenlerle beraberdir.",
        "Kaygı & Endişe" to "De ki: Allah'ın bizim için yazdığından başkası bize asla erişmez.",
        "Ölüm Korkusu" to "Her can ölümü tadacaktır. Sonra bize döndürüleceksiniz.",
        "Borç Stresi" to "Şüphesiz her güçlükle beraber bir kolaylık vardır.",
        "İşsizlik & Rızık" to "Yeryüzünde rızkı Allah'a ait olmayan hiçbir canlı yoktur.",
        "Hastalık" to "Hastalandığım zaman bana şifa veren O'dur.",
        "Evlilik Sorunları" to "Onlarla güzellikle geçinin. Eğer onlardan hoşlanmıyorsanız..."
    )

    // Verse Repository
    val VERSES = listOf(
        QuranVerse(
            surahName = "İnşirah",
            surahNumber = 94,
            ayahNumber = 5,
            arabicText = "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا",
            turkishTranslation = "Demek ki, zorlukla beraber bir kolaylık vardır.",
            tefsirSummary = "Diyanet Tefsiri: İnsan hayatındaki her darlık ve keder sonsuz değildir. İman ve sabırla hareket eden kimse için Allah mutlaka bir çıkış kapısı yaratır.",
            keywords = listOf("zorluk", "kolaylık", "ferahlık", "stres", "inşirah"),
            relatedVerseRefs = listOf("İnşirah:6", "Duha:3", "Bakara:286")
        ),
        QuranVerse(
            surahName = "İnşirah",
            surahNumber = 94,
            ayahNumber = 6,
            arabicText = "إِنَّ مَعَ الْعُسْرِ يُسْرًا",
            turkishTranslation = "Evet, hiç şüphesiz zorlukla beraber bir kolaylık vardır.",
            tefsirSummary = "Ayetin peş peşe iki kez tekrar edilerek vurgulanması, kolaylığın zorluktan daha baskın ve kesin olduğunu müjdeler.",
            keywords = listOf("umut", "kolaylık", "ferahlık"),
            relatedVerseRefs = listOf("İnşirah:5", "Talak:7")
        ),
        QuranVerse(
            surahName = "Zümer",
            surahNumber = 39,
            ayahNumber = 53,
            arabicText = "قُلْ يَا عِبَادِيَ الَّذِينَ أَسْرَفُوا عَلَى أَنفُسِهِمْ لَا تَقْنَطُوا مِن رَّحْمَةِ اللَّهِ إِنَّ اللَّهَ يَغْفرُ الذُّنُوبَ جَمِيعًا",
            turkishTranslation = "De ki: 'Ey kendi aleyhlerine olarak haddi aşan kullarım! Allah'ın rahmetinden ümidinizi kesmeyin. Şüphesiz Allah bütün günahları bağışlar. Çünkü O, çok bağışlayandır, çok merhamet edendir.'",
            tefsirSummary = "Elmalılı Hamdi Yazır Tefsiri: Kur'an-ı Kerim'in en ümit verici ayetlerinden biridir. Kul ne kadar günah işlemiş olursa olsun, samimi tövbe ile Allah'ın af kapısı her an açıktır.",
            keywords = listOf("pişmanlık", "tövbe", "bağışlanma", "af", "rahmet", "günah"),
            relatedVerseRefs = listOf("Tevbe:104", "Şûrâ:25", "Nisa:110")
        ),
        QuranVerse(
            surahName = "Bakara",
            surahNumber = 2,
            ayahNumber = 153,
            arabicText = "يَا أَيُّهَا الَّذِينَ آمَنُوا اسْتَعِينُوا بِالصَّبْرِ وَالصَّلَاةِ إِنَّ اللَّهَ مَعَ الصَّابِرِينَ",
            turkishTranslation = "Ey iman edenler! Sabır ve namaz ile Allah'tan yardım isteyin. Şüphesiz Allah sabredenlerle beraberdir.",
            tefsirSummary = "Diyanet Tefsiri: İnsanın ruhsal direnç kazanması ve çaresizlik duygusunu aşması için sabır ve namaz en güçlü manevi sığınaktır.",
            keywords = listOf("sabır", "namaz", "dua", "yardım", "metanet"),
            relatedVerseRefs = listOf("Bakara:155", "Âl-i İmrân:200", "A'râf:128")
        ),
        QuranVerse(
            surahName = "Bakara",
            surahNumber = 2,
            ayahNumber = 286,
            arabicText = "لَا يُكَلِّفُ اللَّهُ نَفْسًا إِلَّا وُسْعَهَا",
            turkishTranslation = "Allah hiç kimseye gücünün yettiğinden fazlasını yüklemez.",
            tefsirSummary = "Taberi Tefsiri: Başınıza gelen imtihanlar ne kadar ağır görünürse görünsün, sizde onu göğüsleyecek potansiyel güç vardır. Allah adildir ve kulunu taşıyamayacağı yükle sınamaz.",
            keywords = listOf("güç", "imtihan", "kapasite", "stres", "kaygı"),
            relatedVerseRefs = listOf("Talak:7", "İnşirah:5")
        ),
        QuranVerse(
            surahName = "Kâf",
            surahNumber = 50,
            ayahNumber = 16,
            arabicText = "وَنَحْنُ أَقْرَبُ إِلَيْهِ مِنْ حَبْلِ الْوَرِيدِ",
            turkishTranslation = "Biz insana şah damarından daha yakınız.",
            tefsirSummary = "Râzî Tefsiri: Yalnızlık hissettiğinde Allah'ın ilmi, kudreti ve merhameti sana senin öz benliğinden bile yakındır. Hiç kimse yalnız değildir.",
            keywords = listOf("yalnızlık", "yakınlık", "dost", "şah damarı"),
            relatedVerseRefs = listOf("Tevbe:40", "Ankebût:69")
        ),
        QuranVerse(
            surahName = "Duha",
            surahNumber = 93,
            ayahNumber = 3,
            arabicText = "مَا وَدَّعَكَ رَبُّكَ وَمَا قَلَى",
            turkishTranslation = "Rabbin seni terk etmedi ve sana darılmadı.",
            tefsirSummary = "İbn Kesir Tefsiri: Peygamberimize vahy kesildiği zaman inen bu ayet, terk edilmişlik ve keder hissi yaşayan her mümine ilahi bir teselli ve sevgidir.",
            keywords = listOf("üzüntü", "terk edilmek", "yalnızlık", "teselli", "duha"),
            relatedVerseRefs = listOf("Duha:4", "Duha:5")
        ),
        QuranVerse(
            surahName = "Âl-i İmrân",
            surahNumber = 3,
            ayahNumber = 139,
            arabicText = "وَلَا تَهِنُوا وَلَا تَحْزَنُوا وَأَنتُمُ الْأَعْلَوْنَ إِن كُنتُم مُّؤْمِنِينَ",
            turkishTranslation = "Gevşemeyin, üzülmeyin! Eğer gerçekten inanıyorsanız, en üstün sizlersiniz.",
            tefsirSummary = "Uhud savaşı sonrası inen bu ayet, başarısızlık veya üzüntü anında müminin özgüvenini ve imanına olan inancını tazelemektedir.",
            keywords = listOf("üzüntü", "keder", "moral", "üstünlük", "iman"),
            relatedVerseRefs = listOf("Yûsuf:87", "A'râf:156")
        ),
        QuranVerse(
            surahName = "Râd",
            surahNumber = 13,
            ayahNumber = 28,
            arabicText = "أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ",
            turkishTranslation = "Haberiniz olsun ki, kalpler ancak Allah'ı anmakla huzur bulur.",
            tefsirSummary = "İçsel sıkıntı, vesvese ve kaygının tek gerçek ilacı, kalbin yaratıcısıyla kurduğu samimi zikir ve tefekkür bağıdır.",
            keywords = listOf("huzur", "kalp", "zikir", "kaygı", "stres", "iç sıkıntısı"),
            relatedVerseRefs = listOf("Tâhâ:124", "Fetih:4")
        ),
        QuranVerse(
            surahName = "Hûd",
            surahNumber = 11,
            ayahNumber = 6,
            arabicText = "وَمَا مِن دَابَّةٍ فِي الْأَرْضِ إِلَّا عَلَى اللَّهِ رِزْقُهَا",
            turkishTranslation = "Yeryüzünde rızkı Allah'a ait olmayan hiçbir canlı yoktur.",
            tefsirSummary = "Gelecek kaygısı ve iş/geçim stresi yaşayanlar için Allah'ın Rezzâk sıfatının güvencesidir.",
            keywords = listOf("rızık", "işsizlik", "geçim", "borç", "gelecek kaygısı"),
            relatedVerseRefs = listOf("Zâriyât:58", "Ankebût:60")
        )
    )

    // Sahih Hadiths
    val HADITHS = listOf(
        Hadith(
            source = "Buhârî, Edeb 76; Müslim, Birr 107",
            turkishText = "Güçlü kimse, güreşte rakibini yenen değil, öfke anında kendine hâkim olan kimsedir.",
            context = "Öfke kontrolü ve ahlaki olgunluk üzerine Peygamberimizin tavsiyesi."
        ),
        Hadith(
            source = "Müslim, Zikir 41",
            turkishText = "Hiçbir mümin yoktur ki, ayağına bir diken batmasına varıncaya kadar uğradığı her musibet sebebiyle Allah onun günahlarını bağışlamasın.",
            context = "Sıkıntı ve hastalıklara sabretmenin manevi mükafatı."
        ),
        Hadith(
            source = "Buhârî, Îmân 1",
            turkishText = "Kolaylaştırınız, zorlaştırmayınız; müdeleyiniz, nefret ettirmeyiniz.",
            context = "İslam anlayışında müjdeci ve yapıcı tutum."
        ),
        Hadith(
            source = "Tirmizî, Zühd 30",
            turkishText = "Dünyada bir garip gibi veya bir yolcu gibi ol.",
            context = "Dünya hayatının geçiciliği ve kalbi hırslardan arındırma rehberliği."
        )
    )

    // Pre-curated Life Scenarios
    val LIFE_SCENARIOS = listOf(
        LifeScenario(
            id = "is_kaybi",
            title = "İşten Çıkarıldım / Geçim Kaygısı",
            category = "İş & Geçim",
            description = "İşini kaybetmek veya rızık daralması yaşamak manevi bir imtihandır. Kur'an-ı Kerim rezzâk olanın yalnızca Allah olduğunu hatırlatır.",
            verses = listOf(VERSES[9], VERSES[0], VERSES[4]),
            hadiths = listOf(HADITHS[1], HADITHS[2]),
            tefsirOverview = "İslam bilginleri rızkın sebeplere bağlı olduğunu ancak asıl verenin Allah olduğunu vurgular. İş kapısının kapanması, daha hayırlı bir kapının açılmasına vesile olabilir.",
            recommendedPrayer = "Rabbi inni limâ enzelte ileyye min hayrin fakîr. (Rabbim! Bana indireceğin her hayra muhtacım - Kasas Suresi, 24)",
            moralAdvices = listOf(
                "Rızkın kefili Allah'tır, pes etmeden meşru helal yollarla aramaya devam edin.",
                "Eski alışkanlıklarınızı gözden geçirip yeni beceriler kazanmaya odaklanın.",
                "Sıkıntılı anlarda sadaka vererek bereketi arayın."
            )
        ),
        LifeScenario(
            id = "borc_stresi",
            title = "Ağır Borç Yükü altındayım",
            category = "Maddi Sıkıntı",
            description = "Borç stresi insanın uykularını kaçırabilir. Peygamberimiz borçtan Allah'a sığınmış ve borçlulara özel dualar öğretmiştir.",
            verses = listOf(VERSES[0], VERSES[1], VERSES[4]),
            hadiths = listOf(HADITHS[2]),
            tefsirOverview = "Kur'an-ı Kerim borçların yazılmasını, dürüstlüğü ve zora düşen borçluya mühlet tanınmasını emreder. Borçlunun samimi niyeti yardımı celbeder.",
            recommendedPrayer = "Allahümme kfinî bi-helâlike an harâmike ve ağninî bi-fadlike ammen sivâke. (Allah'ım! Bana helalinden yetir, haramdan koru; lütfunla beni başkasına muhtaç etme.)",
            moralAdvices = listOf(
                "Borç verenlerle samimi ve dürüst iletişim kurun.",
                "Bütçenizi sadelik ilkelerine göre yeniden planlayın.",
                "Peygamberimizin öğrettiği borç ödeme duasını her gün okuyun."
            )
        ),
        LifeScenario(
            id = "yakin_kaybi",
            title = "Yakınımı Kaybettim (Taziye & Yas)",
            category = "Kayıp & Yas",
            description = "Sevdiğin birini kaybetmek kalbi yakar. Kur'an, ölümün bir son değil, hakiki aleme geçiş olduğunu müjdeler.",
            verses = listOf(
                QuranVerse(
                    surahName = "Bakara", surahNumber = 2, ayahNumber = 156,
                    arabicText = "الَّذِينَ إِذَا أَصَابَتْهُم مُّصِيبَةٌ قَالُوا إِنَّا لِلَّهِ وَإِنَّا إِلَيْهِ رَاجِعُونَ",
                    turkishTranslation = "Onlar ki; başlarına bir musibet geldiği zaman 'Biz Allah'a aitiz ve şüphesiz O'na döneceğiz' derler.",
                    tefsirSummary = "İstirca cümlesi (İnnâ lillâhi ve innâ ileyhi râciûn), müminin teslimiyet sembolüdür.",
                    keywords = listOf("ölüm", "istirca", "yas", "musibet")
                ),
                VERSES[3]
            ),
            hadiths = listOf(HADITHS[1]),
            tefsirOverview = "Göz yaşarır, kalp üzülür fakat mümin Rabbini razı etmeyecek söz söylemez. Sabredenlere mükafatları hesapsız verilecektir.",
            recommendedPrayer = "İnnâ lillâhi ve innâ ileyhi râciûn. Allahümme'curnî fî musîbetî ve-ahlif lî hayran minhâ.",
            moralAdvices = listOf(
                "Duygularınızı bastırmayın, dua ve hayır hasenat ile vefat edene hediye gönderin.",
                "Akraba ve dostlarınızla teselli paylaşımında bulunun."
            )
        ),
        LifeScenario(
            id = "aldatilma_haksizlik",
            title = "Haksızlığa ve İhanete Uğradım",
            category = "İlişkiler & Adalet",
            description = "Kırılmışlık ve haksızlığa uğrama hissi insanı yakar. Kur'an adaletle hareket etmeyi ve affetmenin yüceliğini öğretir.",
            verses = listOf(
                QuranVerse(
                    surahName = "Şûrâ", surahNumber = 42, ayahNumber = 40,
                    arabicText = "وَجَزَاءُ سَيِّئَةٍ سَيِّئَةٌ مِّثْلُهَا فَمَنْ عَفَا وَأَصْلَحَ فَأَجْرُهُ عَلَى اللَّهِ",
                    turkishTranslation = "Bir kötülüğün karşılığı, ona denk bir kötülüktür. Fakat kim affeder ve arayı düzeltirse, onun mükafatı Allah'a aittir.",
                    tefsirSummary = "İslam adalet sınırları içinde hakkı aramayı meşru görür ancak af ve ıslahta yüce bir ahlak görselliği sunar.",
                    keywords = listOf("affetmek", "adalet", "haksızlık", "kötülük")
                )
            ),
            hadiths = listOf(HADITHS[0]),
            tefsirOverview = "Mazlumun duası ile Allah arasında perde yoktur. Haksızlık yapan cezasını çeker, sabreden mükafatını alır.",
            recommendedPrayer = "Hasbunallâhu ve ni'mel vekîl. (Allah bize yeter, O ne güzel vekildir.)",
            moralAdvices = listOf(
                "İntikam hissiyle kendi ruhunuzu zehirlemeyin, olayı Allah'ın adaletine havale edin.",
                "Sizi zehirleyen toksik bağları sınırlandırın.",
                "Kalbinize yük olan kırgınlıkları hafifletmek için dua edin."
            )
        )
    )

    // Pre-curated Verse Chains (Ayet Zincirleri)
    val VERSE_CHAINS = listOf(
        VerseChain(
            id = "sabir_zinciri",
            topic = "Sabır ve Ruhsal Metanet Zinciri",
            description = "Kur'an-ı Kerim sabrı pasif bir bekleme değil, aktif bir direniş ve manevi duruş olarak ele alır.",
            verses = listOf(VERSES[3], VERSES[4], VERSES[0], VERSES[7]),
            connectionExplanation = "1. Bakara 153 sabır ve namazı yardım vasıtası yapar. -> 2. Bakara 286 kimseye gücünün üstünde yük yüklenmeyeceğini bildirir. -> 3. İnşirah 5 zorlukla kolaylığın bir arada olduğunu müjdeler. -> 4. Âl-i İmrân 139 ise müminin imanından doğan manevi üstünlüğünü tamamlar.",
            synthesisResult = "Sonuç: Kur'an perspektifinde sabır; zorluk karşısında yılmamak, zihinsel ve manevi dengesini koruyarak Allah'ın takdirine ve yardımına güvenmektir."
        ),
        VerseChain(
            id = "tevekkul_rızık_zinciri",
            topic = "Tevekkül, İş ve Rızık Zinciri",
            description = "Gelecek kaygısı ve rızık arayışının Kur'ani mantık zinciri.",
            verses = listOf(VERSES[9], VERSES[0], VERSES[1]),
            connectionExplanation = "1. Hûd 6 tüm canlıların rızkının Allah garantisinde olduğunu belirtir. -> 2-3. İnşirah 5-6 çalışıp çabalayan insana ilahi kolaylığın mutlaka geleceğini pekiştirir.",
            synthesisResult = "Sonuç: Kul gayret etmekle mükelleftir, neticeyi takdir eden ise Allah'tır. Çalışmak ibadet, tevekkül huzurdur."
        )
    )

    // Verse Mindmap Engine
    fun getMindmapForVerse(surahName: String, ayahNumber: Int): VerseMindmap {
        return VerseMindmap(
            verseRef = "$surahName:$ayahNumber",
            verseText = "Demek ki zorlukla beraber bir kolaylık vardır.",
            rootWords = listOf("عُسْر (Usr - Zorluk)", "يُسْر (Yusr - Kolaylık)", "شَرَحَ (Şeraha - Göğsü Genişletmek)"),
            relatedVerses = listOf(VERSES[0], VERSES[1], VERSES[6]),
            nodes = listOf(
                MindmapNode("İnşirah Suresi", "SURAH", "Mekke döneminde Peygamberimizin göğsünün ferahlatılması için inmiştir."),
                MindmapNode("Zorluk-Kolaylık Dengesi", "TEFSIR_THEME", "Her zorluk bünyesinde bir kolaylık çekirdeği taşır."),
                MindmapNode("Duha Suresi", "RELATED_AYAH", "Duha 3: Rabbin seni terk etmedi."),
                MindmapNode("Talak Suresi 7", "RELATED_AYAH", "Talak 7: Allah zorluktan sonra kolaylık yaratacaktır.")
            )
        )
    }

    // Emotion Query Processor (KnowledgeBase Fallback)
    fun findGuidanceForInput(userQuery: String): EmotionAnalysisResult {
        val lowercaseQuery = userQuery.lowercase()
        
        var matchedEmotion = "İç Sıkıntısı & Tefekkür"
        if (lowercaseQuery.contains("yalnız") || lowercaseQuery.contains("kimse yok")) matchedEmotion = "Yalnızlık"
        else if (lowercaseQuery.contains("kork") || lowercaseQuery.contains("endişe")) matchedEmotion = "Korku & Kaygı"
        else if (lowercaseQuery.contains("üzgün") || lowercaseQuery.contains("kırıldım") || lowercaseQuery.contains("ağlıyorum")) matchedEmotion = "Üzüntü"
        else if (lowercaseQuery.contains("günah") || lowercaseQuery.contains("pişman") || lowercaseQuery.contains("hata")) matchedEmotion = "Pişmanlık & Tövbe"
        else if (lowercaseQuery.contains("öfke") || lowercaseQuery.contains("kızgın")) matchedEmotion = "Öfke"
        else if (lowercaseQuery.contains("borç") || lowercaseQuery.contains("para")) matchedEmotion = "Borç Stresi"
        else if (lowercaseQuery.contains("iş") || lowercaseQuery.contains("çalış")) matchedEmotion = "İşsizlik & Rızık"

        val relevantVerses = when {
            lowercaseQuery.contains("günah") || lowercaseQuery.contains("pişman") -> listOf(VERSES[2], VERSES[0], VERSES[3])
            lowercaseQuery.contains("yalnız") || lowercaseQuery.contains("kırıl") -> listOf(VERSES[5], VERSES[6], VERSES[7])
            lowercaseQuery.contains("iş") || lowercaseQuery.contains("borç") -> listOf(VERSES[9], VERSES[0], VERSES[1])
            else -> listOf(VERSES[0], VERSES[8], VERSES[3])
        }

        return EmotionAnalysisResult(
            detectedEmotion = matchedEmotion,
            emotionalSummary = "Girdiğiniz hissiyat analiz edildi. Kur'an-ı Kerim insan kalbinin halet-i ruhiyesini en iyi bilen Yaratıcının kelamıdır.",
            verses = relevantVerses,
            tefsirSummary = "Seçilen ayetlerde vurgulandığı üzere, insanın yaşadığı hiçbir sıkıntı kalıcı değildir. Sabır, tövbe ve duayla Allah'a yönelmek ruha ferahlık verir.",
            hadiths = listOf(HADITHS[1], HADITHS[2]),
            reliability = ReliabilitySource(
                verseRef = "${relevantVerses.first().surahName} Suresi, ${relevantVerses.first().ayahNumber}. Ayet",
                hadithRef = HADITHS[1].source,
                tefsirSource = "Diyanet İşleri Başkanlığı Kur'an Yolu Tefsiri ve Elmalılı Hamdi Yazır Tefsiri",
                neutralityNotice = "Açıklamalar temel muteber tefsirlerden özetlenmiştir. Yapital zekâ fetva niteliği taşımaz."
            ),
            spiritualReflection = "Bugün bu ayet üzerinde 5 dakika sessizce tefekkür etmeyi deneyebilirsiniz."
        )
    }
}
