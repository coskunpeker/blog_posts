# Vector

Bu yazıyı okumadan önce `ArrayList` yazısını okumanızı tavsiye ederim.
Çünkü veri store etme/saklama yaklaşımlarından, `Fai-fast behavior'larına/davranışlarına` kadar aynıdır diyebiliriz. 

`Vector`, List veri yapısının bir boyutlandırılabilir-dizi/resizable-array implementasyonudur. Metodları `synchronized` olan bir `legacy class'tır`.
`ArrayList'in thread-safe` karşılıklarından biri diyebiliriz. 
java.util.Vector altında tanımı aşağıdaki gibidir.

```java
public class Vector<E>  extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

Şimdi özelliklerine bakalım ve kod örnekleri ile incelemeye çalışalım;

* `null` elementler saklayabilir/store edebilir.

* `Tekrarlayan/duplicate` elemanlara izin verir.

* `Sorted` değildir. 

* `Thread-safe` bir yapıya sahiptir. `shared-resource` olarak kullanılmasında sakınca yoktur.

* `Vector` bir `legacy` list implementasyonudur. Artık çoğunlukla kullanılmıyor.

* `Vector` aynı `ArrayList` gibi store ettiği/sakladığı elemanları bir `internal-array'de` tutar. Bu yüzden `ArrayList'teki` eleman ekleme çıkarma işlemlerindeki yavaşlık, performans zayıflığı burada da geçerlidir.

> Not: Performans yavaşlığı veya zayıflığı demek için neye veya hangi data structure'a/veri yapısına göre konuşulduğunu da belirtmek gerekiyor. `ArrayList'de` bu `data structure` `LinkedList` idi. 
Ancak burada belki de yukarıdaki maddeyi eklememek dahi gerek çünkü ``Vector'ün` çözüm sunduğu sorun `thread-safety'dir`, performans değil. Ancak ben farkındalık artırmak adına eklemeyi seçiyorum :smiley:  