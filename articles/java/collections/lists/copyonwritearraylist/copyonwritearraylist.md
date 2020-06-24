# CopyOnWriteArrayList

`ArrayList'i` önceki yazımızda incelemiştik. Hatırlayacak olursanız `ArrayList thread-safe değildir.` Yani birden fazla `thread'in` bir `ArrayList` instance'ına erişmesi durumunda `ArrayList` instance'ı artık `thread'ler için shared-resource` olur ve bu durum da uygulamada beklenmedik sorunlar yaşanabilir.

Bu nedenle `List` veri yapısının, `java.util.concurrent.CopyOnWriteArrayList` altında tanımı aşağıdaki 
gibi olan bir implementasyonu bulunmaktadır.

```java
public class CopyOnWriteArrayList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

Şimdi özelliklerine bakalım ve kod örnekleri ile incelemeye çalışalım;

* `null` elementler saklayabilir/store edebilir.

* `Tekrarlayan/duplicate` elemanlara izin verir.

* `Sorted` değildir.

* `Thread-safe` bir yapıya sahiptir.

* Elemanlarını bir `internal-array'de` tutar.

* Her bir `add`, `set` ve benzeri metod kullanımlarında `internal-array'in` bir kopyası oluşturulur ve işlem onun üzerinde yapılır.  

* Kopyalanmış `array` üzerinde `add` işlemi yapılıp `internal-array'e set` edildiği için 
`ArrayList'teki` gibi `capacity` işlemleri söz konusu değildir.

---------------	

### CopyOnWriteArrayList Thread-Safety

`CopyOnWriteArrayList` yukarıda da açıklandığı gibi `data manipulation (mutative operations)` işlemlerini, tanımı aşağıdaki gibi olan `internal-array'in` bir kopyasını oluşturup, kopyayı güncelleme yöntemi ile yapar.

```java
    private transient volatile Object[] array;
```

Örnek olarak `java.util.concurrent.CopyOnWriteArrayList` altında bulunan `add` metodunu inceleyelim;

```java
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
```

`add` metodu bir `thread` tarafından `invoke edildiğinde/çağırıldığında`, `ReentrantLock` kullanılarak sırası ile;

* `internal-array'in` kopyalanması
* eklenecek elemanın `kopya array'in` son elamanı olarak atanması
* `kopya-array'in internal array'e` setlenmesi

işlemleri yapılır. `Locking mekanizması` kullanıldığı için `finally` bloğundaki `lock.unlock()` çalışmadan, başka bir `thread`  `CopyOnWriteArrayList instance'ında add` metodunu çalıştırmak isterse `lock.unlock()` çalışana kadar beklemek durumunda kalır.

Ancak `CopyOnWriteArrayList instance'ından` eleman `get`etmekte herhangi bir sorun yoktur. 

```java
    public E get(int index) {
        return get(getArray(), index);
    }

    private E get(Object[] a, int index) {
        return (E) a[index];
    }
```

Yukarıdaki metodlar `CopyOnWriteArrayList instance'ından` elaman `get` edilirken kullanılır. Görüldüğü gibi herhangi bir `locking mekanizması` bulunmamakta. Dolayısıyla her `thread` istediği gibi okuma yapabilir.

>Not: Yukarıda bir `thread'in add` yaparken diğer `thread'lerin` de `add` yapmaya çalışması durumunu konuştuk. Peki bir `thread add` yaparken diğerleri de `set ya da remove` yapmaya çalışırsa ne olur? `CopyOnWriteArrayList` class'ının `source code'unda` da görüleceği üzere, `set ve remove` metodları da `locking mekanizması` ile çalışır. `add` metoduna ait `finally` bloğundaki `lock.unlock()` çalışmadan `set ve remove` metoduna ait `logic'ler` icra edilemez. Tabii ki tam tersi şekilde `set veya remove` çalışırken `add` de beklemelidir.


---------------

### CopyOnWriteArrayList Iteration Fail-Safe Durumu 

`Fail-Safe` iteration `internal-array'in` bir kopyası üzerinde `iteration` işlemini yürütür. Böylece orjinal `array` değişmeden kalır. Hatırlarsanız `ArrayList` de böyle bir durum söz konusu değildi. `ArrayList` `Fail-Fast` iteration kullandığından direkt `internal-array` yani orjinal `array` üzerinde çalıştığı için iteration yapılırken `data manipulation` yapılırsa 
`ConcurrentModificationException`  ile karşılaşılma durmu vardı.

`CopyOnWriteArrayList` ise `Fail-Safe` iteration kullandığı için `internal-array` değişmeden kalır ve `ConcurrentModificationException` ile karşılaşılma durumu oluşamaz.

`Fail-Safe` iteration bizi `ConcurrentModificationException'dan` kurtarır ama iki dezavantajı da bize getirir;

* `Internal-array'in` kopyası ile çalışılma durumundan dolayı `memory` kullanımının artması

* `CopyOnWriteArrayList` instance'ında okuma yapılırken anda bir başka `thread'in` veriyi değiştirmiş olma ihtimali. Bu durumda okunan değer güncel olmayabilir.

---------------

### CopyOnWriteArrayList Constructor Metodları

| Constructor   | Açıklama   
| ------------- |:-------------:| 
| new CopyOnWriteArrayList()                    | Boş bir instance oluşturur.| 
| new CopyOnWriteArrayList(Collection obj)      | Başka bir `Collection` elamanları ile bir instance oluşturur.| 
|  new CopyOnWriteArrayList(Object[] obj)       | Verilen `array'in` kopyasını tutan bir instance oluşturulur.| 

---------------

### Kaynaklar

* <https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CopyOnWriteArrayList.html>