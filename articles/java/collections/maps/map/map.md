# Java Maps

Map, bir java interface'dir. Store edilmek/saklanılmak istenilen verileri key-value/anahtar-değer
halinde belirli bir veri yapısı halinde tutmayı sağlar.

`java.util` package'ı altında tanımı aşağıdaki gibidir.

```java
public interface Map<K, V>
```

`Map` interface'ine ait bazı özellikleri aşağıdaki gibi sıralayabiliriz:

* `Duplicate/mükerrer` key içeremezler.

* Map interface'i, üç farklı `collection` görünümü sunar. Bunlar, `key'lerden` oluşan bir `set`
veya `value'lerden` oluşan bir `collection` veya asıl amacı olan `key-value` set'idir.

* `Map` implementasyonlarında order/eklenme sırasının korunup korunmaması farklılıklar gösterir.
Bazıları bu sırayı korurken, bazıları korumaz. 
Ayrıntılarına implementasyonları incelerken değineceğiz.

* Yukarıdaki duruma benzer şekilde bazı `Map` implementasyonları `null` key ve value'lere izin vermezler.

* Bazı `Map metodları` `recursive traversal/özyinelemeli geçiş` uygularlar. Bu nedenle, map eğer
`self-referential/kendi kendini elemanı olarak tutma` içeriyorsa metod `fırlatabilir`. En iyisi 
böyle bir kodu hiç geliştirmemek :smiley:

* `Mutable` objelerin `key` olarak kullanılması durumunda dikkatli olunmalıdır.


## Unmodifiable Maps

`Map.of()`, `Map.ofEntries()` ve `Map.copyOf()` factory metodları ile `unmodifiable` map implementasyonları
oluşturulabilir. Bu metodlar ile oluşturulan maplerin aşağıdaki gibi özellikleri vardır.

* Herhangi bir yeni değer eklenemez, silinemez ya da güncellenemez. 

* Herhangi bir `mutator metod (map'i elemanlarını değiştirecek bir metod)` çağrımı `UnsupportedOperationException` ile sonuçlanır.

* Ancak yukarıdaki key ve value'lar eğer immutable değilse, onlarda yaşanacak bir değişim dolaylı yoldan
map'i de etkiler ve `unmodifiable` kavramı ile bağdaşmaz. Bu noktada dikkatli olmak gerekiyor.

* `Null` key ve value'lere `izin verilmez`.

* Eğer bütün key ve value'ler `serializable` ise map instance'ı da `serializable` olacaktır.

* `Map` yaratılırken `duplicate key'lere` izin verilmez.

* Iterasyon order belirsizdir.

* `Factory'ler metodları` yeni bir instance yaratabilirler veya mecvut instance'ları kullanabilirler.
Bu nedenle `identity-sensitive operation'larda( referans ile == işlemi gibi)` veya  `synchronization` 
yapılacaksa bu map'lerin `kullanımından kaçınılmalıdır`.

