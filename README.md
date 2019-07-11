## Amaç

15x15 boyutunda bir scrabble tahtası üzerinde yeni oyun tahtaları oluşturulabilmesi, seçilen bir tahta üzerine kelimeler yerleştirilebilmesi, tahta üzerindeki mevcut kelimelere eklemeler yapılabilmesi, kelimelerin Türkçe sözlükte karşılığının olduğunun kontrol edilmesi, eklenen her kelime sonrasında kelime puanının hesaplanması ve yapılan işlemlerin tarihçesinin tutulması istenmektedir.

Bu istekler gerçekleştirilirken, sadece aşağıda yazılı kuralların mutlaka yapılması beklenmektedir. Orjinal scrabble oyunundaki kuralların tamamı gerçekleştirilmek zorunlu değildir.

##  Fonksiyonel Gereksinimler

* Yeni oyun tahtası oluşturulabilmelidir (bkz: Servis 1).
* Aynı anda 1 veya daha fazla oyun tahtası oluşturulup, tahtalar üzerinde paralel olarak oyun oynanabilmelidir.
* Bir tahta üzerinde hangi hücrelere hangi harflerin yerleştirileceği belirlenebilmelidir (bkz: Servis 2).
* Bir tahtaya bir seferde birden fazla kelime yerleştirilebilmelidir (yatay, dikey, vs).
* Dolu olan bir hücreye yeni bir harf yerleştirilemeMElidir.
* Eklenen kelimelerden 1 veya daha fazlası Türkçe sözlükte bulunamadıysa, kelimelerin hiçbiri tahtaya yerleştirilmeMElidir (metin dosyası şeklinde Türkçe Sözlük ektedir).
* Başarıyla eklenen kelimelerin puanları hesaplanmalı ve kaydedilmelidir (Puan listesi aşağıda görülebilir).
* Tahta üzerine eklenen kelimeler ve bunların puanları rapor olarak alınabilmelidir (bkz: Servis 3).
* Aktif durumdaki bir tahtanın üzerinde, hangı sıradan hangi harflerin bulunduğu görüntülenebilmelidir. (bkz: Servis 4).
* Tahtalar kelime eklenebilmesi için aktiflenmek zorundadır. Tahtalar istenen bir zamanda deaktif edilebilmelidir (Deaktif edilen bir tahta tekrar aktif hale gelmeMEli, üzerine yeni kelime eklenemeMElidir) (bkz: Servis 5). 
* İlk kelime yerleştirilirken tahtanın herhangi bir yerinden başlanabilir. Daha sonra eklenecek kelimeler, mevcut kelimelere ait harflerin bulunduğu konumlardan başlamalı, tahtanın istenen boş bir yerine eklenemeMElidir.

## Teknik Gereksinimler

* Java 8 kullanılmalıdır.
* Sadece back-end uygulaması olmalı, rest servisleriyle sonuç dönmelidir.
* spring-boot kullanılmaMAlıdır.
* Hibernate kullanılmalıdır.
* Testler yazılmalıdır.
* Rest servislerine yapılan istekler loglanmalıdır.
* Uygulama ek konfigürasyon kullanılmadan Java 8 yüklü bir bilgisayarda çalışabilmelidir.
* Uygulamanın nasıl kurulacağını ve kullanılacağını anlatan bir teknik tasarım dökümanı hazırlanmalıdır.
* Yukarıda sıralananlar dışında farklı teknolojiler, kullanılma sebebi belirtilerek kullanılabilir. Kullanılan teknolojilerin gerekiyorsa nereden download edileceği, nasıl kullanılacağı da dökümanda belirtilmelidir.

## Örnek Rest Servis Methodları

* Servis 1: Long createBoard() -> oluşturulan yeni tahtanın unique identifier bilgisini dönmeli
* Servis 2: ? play(Long boardId, ? moves)
* Servis 3: ? getWords(Long boardId)
* Servis 4: ? getBoardContent(Long boardId, Integer sequence) -> 15x15 hücre üzerinde hangi harflerin olduğu görüntülenebilecek. sequence = 0 tahtanın boş hali, 1 ilk turda eklenen kelime(ler) sonrası, 2 ikinci turda eklenen kelime(ler) sonrası, vs şeklinde.
* Servis 5: ? setStatus(Long boardId, ? status)
* Soru işareti olan objeler development esnasında karar verilmesi gereken obje modelleridir.
* Yukarıdaki 5 servisin çalışır durumda olması beklenmektedir. Çalışma mantığı değişmemek kaydıyla request/response nesne modellerine developer karar verebilir.

## Harf Puanları

* A: 1 Puan
* B: 3 Puan
* C: 4 Puan
* Ç: 4 Puan
* D: 3 Puan
* E: 1 Puan
* F: 7 Puan
* G: 5 Puan
* Ğ: 8 Puan
* H: 5 Puan
* I: 2 Puan
* İ: 1 Puan
* J: 10 Puan
* K: 1 Puan
* L: 1 Puan
* M: 2 Puan
* N: 1 Puan
* O: 2 Puan
* Ö: 7 Puan
* P: 5 Puan
* R: 1 Puan
* S: 2 Puan
* Ş: 4 Puan
* T: 1 Puan
* U: 2 Puan
* Ü: 3 Puan
* V: 7 Puan
* Y: 3 Puan
* Z: 4 Puan

NOT : Scrabble sözlüğünü buradan indirebilirsiniz.