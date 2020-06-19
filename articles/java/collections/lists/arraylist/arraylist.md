# ArrayList

`List` veri yapısının bir boyutlandırılabilir-dizi/resizable-array implementasyonudur. `java.util.ArrayList` altında tanımı aşağıdaki 
gibidir.

```java
    public class ArrayList<E> extends AbstractList<E>
          implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

Şimdi özelliklerine bakalım ve kod örnekleri ile incelemeye çalışalım;

* `null` elementler saklayabilir/store edebilir.

* `Tekrarlayan/duplicate` elemanlara izin verir.

* `Sorted` değildir. 

* `ArrayList`, `synchronized` veya `thread-safe` değildir. Yani birden fazla `thread` arasında
 `shared-resource` olarak kullanılmamalıdır.

* `ArrayList`, `sorting` ve `data access` için idealdir.

* `Eleman ekleme ve çıkarma/Data Manipulation` görece yavaştır. Aslında buradaki görece kelimesi
  diğer list implementasyonlarındansa `LinkedList'i` referans eder. Yapacağınız online aramalarda
  bunu hep görürsünüz. Yavaşlığın nedeni `ArrayList'ten eleman silindiğinde` memory'de `shifting` 
  yapılmasıdır. 

* `ArrayList` boyutlandırılabilir-dizi/resizable-array oluğundan bahsetmiştim, bu nedenle içerisinde bulunan `array length'i` manipüle
edilerek bu `resizable` özelliği kazandırılır. Bu durumu yazının ilerleyen maddelerinde inceleyeceğiz.


### ArrayList Resizable Özellikleri

Birkaç ufak kod bloğu geliştirelim ve bunun üzerinden, `ArrayList capacity'de` yaşanacak değişiklileri gözlemlemeye çalışalım.

`Capacity` değeri, bir `ArrayList` objesinin elemanlarını saklamak/store etmek için kullandığı ve `java.util.ArrayList` kaynak kodunu görüntülediğinizde aşağıdaki gibi tanımlanmış olan `internal-array'in length` değeridir.

```java
    transient Object[] elementData;
```

Bu değişkenin/variable'ın değerine direkt olarak erişmemizi sağlayan bir `ArrayList` metodu maalesef yok, o yüzden aşağıdaki gibi bir metod yardımı ile `reflection` kullanarak `capacity` üzerinde yaptığımız her değişikliğin ardından, `array'in length` değerini görüntüleyerek
yaptığımız işlemin `capacity` üzerindeki sonucunu görmeye çalışacağız.

```java
    private static int getCapacity(ArrayList<?> l) throws Exception {
        Field dataField = ArrayList.class.getDeclaredField("elementData");
        dataField.setAccessible(true);
        return ((Object[]) dataField.get(l)).length;
    }
```
`ArrayList'e` ait `ensureCapacity` metodu ile `capacity'yi` artıralım ve az önce geliştirdiğimiz `getCapacity` metodu ile 
artırmadan önceki ve sonraki `capacity` değerlerini ekrana yazdıralım.
  
```java
    private static void increaseCapacity() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println("before the increasing: " + getCapacity(list));

        list.ensureCapacity(15);
        System.out.println("after the increasing: " + getCapacity(list));
    }
``` 
çıktı/output:

```java_holder_method_tree
    before the increasing: 0
    after the increasing: 15
```

> Not: Bir `ArrayList` instance'ına büyük miktarda eleman eklenmesi söz konusu ise performans artırmak adına önce `capacity` ihtiyaç kadar artırılıp sonra elemanlar eklenebilir. Bu performansta iyileşmeye sebep olacaktır.
  
Şimdi de `ArrayList'in capacity` değerini sakladığı/store ettiği eleman sayısına indirgemek için `trimToSize` metodunu kullanalım.

```java
    private static void decreaseCapacity() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.ensureCapacity(15);
        System.out.println("before the decreasing: " + getCapacity(list));


        // add some elements to list.
        list.add(1);
        list.add(2);

        // trimToSize will decrease capacity from 15 to current capacity, so it will be 2
        list.trimToSize();

        System.out.println("after the decreasing: " + getCapacity(list));
    }
```
çıktı/output:

```java_holder_method_tree
    before the decreasing: 15
    after the decreasing: 2
```

Çıktı da görüldüğü gibi `trimToSize` metodu, `capacity` değerini sadece eleman sayısı kadar olacak şekilde azalttı.


Peki bizim müdahale etmediğimiz durumlarda ve `ArrayList'e` her bir eleman eklendiğinde, `capacity` değeri nasıl yönetiliyor?

Bunu aşağıdaki kod bloğu ile incelemeye çalışalım:

```java
    private static void observeCapacity() {
        ArrayList<Integer> list = new ArrayList<>();

        // observe the add operation
        IntStream.range(0, 20)
                 .forEach(element -> {
                     list.add(element);
                     try {
                         System.out.print("added element: " + element);
                         System.out.print(" ## list size: " + list.size());
                         System.out.println(" ## capacity after add operation: " + getCapacity(list));
                     } catch (Exception e) {
                         throw new RuntimeException(e);
                     }
                 });
        
    }
```
çıktı/output: 

```java_holder_method_tree
    added element: 0 ## list size: 1 ## capacity after add operation: 10
    added element: 1 ## list size: 2 ## capacity after add operation: 10
    added element: 2 ## list size: 3 ## capacity after add operation: 10
    added element: 3 ## list size: 4 ## capacity after add operation: 10
    added element: 4 ## list size: 5 ## capacity after add operation: 10
    added element: 5 ## list size: 6 ## capacity after add operation: 10
    added element: 6 ## list size: 7 ## capacity after add operation: 10
    added element: 7 ## list size: 8 ## capacity after add operation: 10
    added element: 8 ## list size: 9 ## capacity after add operation: 10
    added element: 9 ## list size: 10 ## capacity after add operation: 10
    added element: 10 ## list size: 11 ## capacity after add operation: 15
    added element: 11 ## list size: 12 ## capacity after add operation: 15
    added element: 12 ## list size: 13 ## capacity after add operation: 15
    added element: 13 ## list size: 14 ## capacity after add operation: 15
    added element: 14 ## list size: 15 ## capacity after add operation: 15
    added element: 15 ## list size: 16 ## capacity after add operation: 22
    added element: 16 ## list size: 17 ## capacity after add operation: 22
    added element: 17 ## list size: 18 ## capacity after add operation: 22
    added element: 18 ## list size: 19 ## capacity after add operation: 22
    added element: 19 ## list size: 20 ## capacity after add operation: 22
```
Yukarıda da görüleceği üzere `capacity` değeri otomatik olarak artırıldı. Bu iş için biz geliştiricilerin uğraşması gerekmiyor. Aslında, `ArrayList` zaten bunun için var. :smiley:


### ArrayList Iteration Fail-Fast Durumu 

`ArrayList`, iterate edilirken (bir döngü veya iterator yardımı ile elamanları gezilirken) 
eğer arraylist objesi manipule edilirse yani `add` veya `remove` metodları kullanılarak eleman
eklenir veya çıkarılırsa `ConcurrentModificationException` fırlatılır ve iteration başarısız/failed 
olur. Bu duruma `iterator'un fail-fast yaklaşımı` denir. Ancak yine de `fail-fast` yaklaşımının
her zaman uygulanabiliceğine dair bir garanti yoktur. Iterator'ler `javadoc'da da belirtildiği` 
üzere `best-effort` ile `ConcurrentModificationException` fırlatmaya/throw etmeye çalışsa da 
fırlatılmama ihtimali düşünülmelidir.
   
```java
    public class ArrayListTraining {
  
        public static void main(String[] args) {
  
           try {
                List<Integer> list = new ArrayList<>();
  
                list.add(1);
                list.add(2);
                list.add(3);
                list.add(4);
                list.add(5);
  
                for (Integer value : list) {
                    System.out.println("List Value:" + value);
                    if (value.equals(3)) {
                        list.remove(value);
                    }
                }
            } catch (ConcurrentModificationException e) {
                // do not apply a logic here. 
                // There is no guarantee that this exception will be thrown
                // just do logging etc.
            }
  
        }
  
    }
```
   
    
### ArrayList Constructor Metodları   

Üç tip `constructor` bulunmakta ve bunlar aşağıdaki gibi;
   
Boş bir `ArrayList` instance'ı oluşurmak için kullanılan contructor:

```java
    List<Integer> list = new ArrayList<>();
```

Bir başka `Collection'ın` elemanları ile instance oluşurmak için kullanılan constructor:

```java
    Set<Integer> set = new HashSet<>();
    set.add(1);
    set.add(2);

    List<Integer> list = new ArrayList<>(set);

    list.forEach(System.out::println);
```

Başlangıç kapasitesini vererek instance oluşurmak için kullanılan contructor:

```java
    List<Integer> list = new ArrayList<>(20);
``` 

### Arrays.asList(T... a) Metodu

`java.util.Arrays` içerisinde `asList` adlı bir metod ile klasik `array'ler` bir `ArrayList'e` dönüştürülebilir. Çok yaygın kullanılan bir metoddur.

Metodun yapısı şöyledir:

```java
    public static <T> List<T> asList(T... a) {
        return new ArrayList<>(a);
    }
```

Tabi `java.util.Arrays` class'ının açıp yukarıdaki metod yapısını görünce hemen "hmm tamam bir ArrayList dönüyor" diye düşünebilirsiniz. Ancak biraz daha meraklı olup class'ı gezmeye devam ederseniz aşağılarda bir yerlerde 
şu tanımı görürsünüz:

```java
    private static class ArrayList<E> extends AbstractList<E>
        implements RandomAccess, java.io.Serializable
```

İşte bu tam bir sürpriz! Neden `bir başka ArrayList tanım'ı` var hem de `Arrays` içerisinde?

İşin açığı ben bu durumu biraz kafa karıştırıcı buldum, en azından ayırt etmek ve olası karmaşayı önlemek için geliştiriciler, isimlendirmesini farklı yapabilirlerdi gibi geliyor bana. :smiley:

Bu ArrayList'in özelliği, `fixed-size` olması. Yani yeni bir eleman ekleyemezsiniz. `Resizable` değildir. Aşağıdaki gibi oluşturulmuş bir list'e
eleman ekleyip çıkarmaya çalışırsanız `UnsupportedOperationException` ile karşılaşırsınız.

```java
    List<String> list = Arrays.asList("a", "b", "c");
```

Doğal olarak aşağıdaki `casting` işleminin sonucu da `ClassCastException'dır`.

```java
    ArrayList<String> list = (ArrayList<String>) Arrays.asList("a", "b", "c");
```

### Kaynaklar

* <https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html>
