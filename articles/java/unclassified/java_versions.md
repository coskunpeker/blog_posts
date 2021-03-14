
# Java Versiyon Stratejisi

Oracle, Java 9 ile birlikte versiyonlama stratejisini değiştirdi. Artık her bir versiyon `LTS (Long-Term-Support)` veya `Non-LTS` olmak üzere ikiye ayrılıyor. `LTS` olarak adlandırlan versiyonlar `her 3 yılda bir`, Non-LTS sürümler ise her `6 ayda bir` kullanıma sunuluyor. 

Oracle'ın bu stratejiye geçmesindeki en büyük etken, şu ana kadar çıkan versiyonlar arası sürenin çok uzun olması ve bu nedenle bir sürümden bir diğerine çok büyük değişikliklerin olması, dolayısıyla uygulamaları yeni versiyonlara geçirmekte yaşanan sorunlar. Örneğin Java 6 2006'da, Java 7 2011'de, Java 8 ise 2014'de yayınlandı.

Oracle yeni stratejisi ile LTS olarak adlandırdığı versiyonlar arası geçişi her 6 ayda bir yapacağı ara versiyonlarla daha efektif yapmayı ve sektörün daha kolay entegre olmasını umuyor.

LTS ve Non-LTS sürümler arasındaki en büyük farklardan birisi ise bir Non-LTS Java versiyonunun `en fazla 6 ay support alması diyebiliriz`. Bir sonraki Non-LTS versiyonu yayınlandığında eskisi daha fazla support almayacak. Ancak LTS versiyonları uzun süre support almaya devam edecekler.

| Release<!--<th-->                | GA Date<!--<th-->                | Premier Support Until<!--<th-->  | Extended Support Until<!--<th--> | Sustaining Support<!--<th-->     |
| -------------------------------- | -------------------------------- | -------------------------------- | -------------------------------- | -------------------------------- |
| 7<!--<td-->                      | July 2011                        | July 2019                        | July 2022\*\*\*\*\*              | Indefinite                       |
| 8﻿\*\*<!--<td-->                 | March 2014                       | March 2022                       | December 2030                    | Indefinite                       |
| 9 (non‑LTS)<!--<td-->            | September 2017                   | March 2018                       | Not Available                    | Indefinite                       |
| 10 (non‑LTS)<!--<td-->           | March 2018                       | September 2018                   | Not Available                    | Indefinite                       |
| 11 (LTS)<!--<td-->               | September 2018                   | September 2023                   | September 2026                   | Indefinite                       |
| 12 (non‑LTS)<!--<td-->           | March 2019                       | September 2019                   | Not Available                    | Indefinite                       |
| 13 (non‑LTS)<!--<td-->           | September 2019                   | March 2020                       | Not Available                    | Indefinite                       |
| 14 (non‑LTS)<!--<td-->           | March 2020\*\*\*                 | September 2020                   | Not Available                    | Indefinite                       |
| 15 (non‑LTS)<!--<td-->           | September 2020\*\*\*             | March 2021                       | Not Available                    | Indefinite                       |

## Java Versiyonları ile Gelen Özellikler

### Java 8

* Lambda Expresisions ve Stream Apı
* Functional Interfaces
* Interface'lerde Default ve Static metodlar
* Optionals
* Datetime API
* Concurrency iyileştirmeleri ve CompletableFuture

### Java 9

* [Module System (Project Jigsaw) ](https://www.journaldev.com/13106/java-9-modules)
* List, Set, Map için Factory metodlar
* JShell
* Interface'lerde private metodlar

```java
        public interface Test{

        private Long nonstaticPrivateMethod(){
           // do something
        }

        private static void staticprivateMethod(){
          // do something
        }

}
```

* CompletableFuture API İyileştirmeleri (Timeout ve Delay)
* Reactive Streams

### Java 10

* Local-Variable Type Inference, `var` keyword'u yalnızca local variable olarak kullanılabilir. `Instance variable, metod parametresi veya return type` olarak kullanılamazlar. `null` ataması yapılamaz. Ayrıca kullanımdan önce `initialize` edilmelidir.
```java
        // var kullanımı
        var numbers = List.of(1, 2, 3, 4, 5);
        
        for (var number : numbers) {
            System.out.println(number);
        }
        
        for (var i = 0; i < numbers.size(); i++) {
            System.out.println(numbers.get(i));
        }

```

* [Experimental Java-Based JIT Compiler (JEP 317)](https://www.journaldev.com/20395/java-10-features#experimental-java-based-jit-compiler-jep-317) Java'da geliştirilmiş `jit compiler (grall)`. Bir java uygulamasını bu yöntemle çalıştırmak için aşağıdaki parametreler ile başlatmak yeterli.

```command
        -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler
```

* [Application Class-Data Sharing (JEP 310)](https://www.journaldev.com/20395/java-10-features#application-class-data-sharing-jep-310) Bir java uygulaması çalıştırıldığından class'ların `JVM'e yüklenmesi` en önemli ve `maliyetli` aşamalardan biridir. Bu özellik `boot-time'ın kritik olduğu` uygulamalarda boot-time'ı aşağı çekmek için geliştirilmiştir. Bu yöntemdeki fikir farklı java process'leri tarafından ihtiyaç duyulan aynı class metadata'larını paylaşmak üzerine kuruludur.

### Java 11

* Java Class'ını çalıştırmak için `javac` ile compile etmeye gerek kalmıyor. 

```command
        // java 11 öncesi
        $ javac HelloWorld.java
        $ java HelloWorld

        // java 11
        $ java HelloWorld.java

```

* String metod'larında yapılan iyileştirmeler.

```java

        // isBlank()
        System.out.println(" ".isBlank()); //true
        
        String s = "Test";
        System.out.println(s.isBlank()); //false
        String s1 = "";
        System.out.println(s1.isBlank()); //true


        // lines()

        String str = "test1\ntest2\ntest3";
        System.out.println(str.lines().collect(Collectors.toList()); // [test1, test2, test3] yazar

        // repeat()
        String str = "!".repeat(3);
        System.out.println(str); // !!! yazar
```

* Java EE ve CORBA Module'leri silinmiştir.
* Dosya okuma yazma işlemleri için gelen metodlar.
```java

        // yazma
        Path path = Files.writeString(Files.createTempFile("test", ".txt"), "Hello World!!!");
        System.out.println(path);

        // okuma
        String s = Files.readString(path);
        System.out.println(s);
```

### Java 12

* [JEP 341 Default CDS Archives](https://www.journaldev.com/28666/java-12-features#5-jep-341-default-cds-archives) default olarak akitf özellik haline getirildi. İstenmeyen durumlarda Java 10'daki durumun aksine bu özelliği kapatmak gerekiyor.
* [Switch-case iyileştirmesi](https://www.journaldev.com/28666/java-12-features#5-jep-341-default-cds-archives)

* Compact Number Formatting

```java

       NumberFormat fmt = NumberFormat
                .getCompactNumberInstance(Locale.US, NumberFormat.Style.LONG);
 
        System.out.println( fmt.format(100) ); // 100 yazar
        System.out.println( fmt.format(1000) ); // 1 thousand yazar
        System.out.println( fmt.format(10000) ); // 10 thousand yazar
        System.out.println( fmt.format(100000) ); // 100 thousand yazar
 
        NumberFormat fmtShort = NumberFormat
                .getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
 
        System.out.println( fmtShort.format(100) ); // y100 yazar
        System.out.println( fmtShort.format(1000) ); // 1K yazar
        System.out.println( fmtShort.format(10000) ); // 10K yazar
        System.out.println( fmtShort.format(100000) ); // 100k yazar

```

### Java 13

* [Switch-case'de yapılan iyileştirmeler](https://mkyong.com/java/java-13-switch-expressions/)

* Text bloklarında yapılan iyileştirmeler.

```java

        // java 13 öncesi
        String json ="{\n" +
                    "   \"name\":\"test\",\n" +
                    "   \"age\":1\n" +
                    "}\n";

        // java 13
        String json = """
                        {
                            "name":"test",
                            "age":1
                        }
                        """;                    

```

* Socket API iyileştirmeleri.


### Java 14

* Pattern Matching for instanceof

```java

        // java 12 öncesi
        if (obj instanceof String) {
            String s = (String) obj;
    
        }

        // java 12
        if (obj instanceof String s) {
            System.out.println(s);
        }
    
}

```

* NullPointerException hata açıklaması iyileştirmesi.

```java
        Integer test = null;
        System.out.println(test.toString());

        // hata açıklaması:
        // Cannot invoke "java.lang.Integer.toString()" because "test" is null
```

* `Records` özelliği ile auto-generated consturctor, getter, equals, hashcode ve toSring gibi metodları barındıran `immutable` class'lar oluşturulabiliyor. Bu class'larda `immutability özellikleri gereği` setter metod yok. Ancak custom constuctor'lar veya başka görevleri icra eden metodlar eklenebilir.

```java
        public record Person (String name, String address) {
        }
```


