# *Android Lifecycle (Yaşam Döngüsü)*

---

## *Android Lifecycle Nedir ?*

***Android Lifecycle***, yazılım geliştiricilerin uygulamada gezinen bir kullanıcının hangi aktivite durumlarından geçtiğini anlamasına yardımcı olur. Bu sayede uygulama çökmesi ve başka hatalardan kaçınarak doğru zamanda uygun işlemleri yapabiliriz.

---

## *Android Lifecycle Nasıl Yönetilir ?*

Android uygulamaların bir yaşam döngüsü vardır. Kullanıcı bir  uygulamayı açtığında onu kapatıncaya kadar uygulama çeşitli durumlardan geçer. Uygulamanın durumu kullanıcı onu başlattığı, duraklattığı, durdurduğu veya kapattığında neler yapacağını yönetmenizi sağlar. Bu durumları `callback` metodları aracılığıyla yönetiriz. Kullanıcıya doğru çıktıyı vermek amacıyla belirli işlemler yapmak için bu metodlar `override` edilebilirler. Diyelim ki uygulamanız arka planda çalışıyorken bazı verileri kaydetmek istiyorsunuz. Bu durumda aktivite yaşam döngüsünü bilmeniz gerekir. Bu size işi halletmeniz için gerekli doğru `callback` metodunu `implement` etmenizi sağlar.

---

## *Callback Methodları*

Bir Android aktivitesi, 6 ana yaşam döngüsü aşamasından veya `callback`den oluşur. Bunlar:

1. *onCreate()*
2. *onStart()*
3. *onResume()*
4. *onPause()*
5. *onStop()*
6. *onDestroy()*

Uygulamanızda bunların hepsini birden kullanmaya ihtiyacınız olmadığına dikkat edin. Yazılım geliştirici olarak her bir `callback`i uygulamanızın ihtiyaçlarına bağlı olarak nerede kullanacağınızı bilmelisiniz. Aşağıdaki diyagramda kullanıcıların, aktivite yaşam döngüsüyle nasıl bir etkileşime girdiği basitçe gösterilmiştir.

![lifecycle](https://www.section.io/engineering-education/understanding-and-implementing-the-android-lifecycle/activity-lifecycle.png)

---

### *onCreate()*

Tüm Android uygulamalarında kullanılması zorunludur. Ana ekrandan veya `intent` aracılığıyla bir aktivite başlatıldığında çağrılan ilk `callback`dir. Yazılım geliştiricilerin, `ViewModel` başlatmak gibi yalnızca bir kez gerçekleşmesi gereken aktivite lojiğini `implement` etmek için ihtiyaç duyduğu tek yöntemdir.

Android Studio bir proje dosyası oluşturulduğunda otomatik olarak `MainActivity.java` dosyası adında bir sınıf üretir. Bu sınıf bir `onCreate()` `callback`i içerir. Bir kullanıcı uygulamayı ilk kez açtığında çağrılır. 

Bir cihaza uygulama yüklendiğinde `doesn't exist` durumundadır. Yani aktivite ölüdür. Kullanıcı uygulamayı bir kez başlattığında yaşam döngüsü başlar. Etkinlik ön plana çıkarılır. Bu durumda, uygulamayı başlatmak için hemen `onCreate()` çağrılır. Etkinlik kullanıcı arayüzü gibi bileşenler içerebilir. Aşağıda `onCreate()` metodunun kullanımını gösteren örnek bir kod verilmiştir.

````
private static final String TAG = "MainActivity";

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toast.makeText(this, "onCreate MainActivity", Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onCreate MainActivity");
}
````

Bu adımda aktiviteniz henüz görünmez. `onCreate()` işlevi bitene kadar bu durumda kalır, ardından hızla bir sonraki duruma geçer.

---

### *onStart()*

Bir uygulama başlatıldığında, sistem `onStart()` methodunu çağırır. Bu `callback` aktiviteyi kullanıcıya görünür kılmak için çağrılır.

````
@Override
protected void onStart() {

    Toast.makeText(this, "onStart MainActivity", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart MainActivity");

    super.onStart();
}
````

`onStart()` metodu, uygulamanın yaşam döngüsü boyunca birden fazla kez çağrılabilir. Örneğin bu metod, kullanıcı başka bir aktivite açıp tekrar önceki aktiviteye döndüğünde çalışır. Aktivitenin yaşam döngüsü boyunca `onStop()` metodu çağrılır. Bu, bazı kaynakların serbest bırakıldığı anlamına gelir. Bu tür kaynakları başlatmak için `onStart()` yöntemi çağrılabilir.

---

### *onResume()*

`onStart()` metodu çağrıldıktan hemen sonra `onResume()` metodu çağrılır. Bu aktiviteyle ilgili tüm bileşenler ön plana getirilir ve aktivitenin artık etkileşimli olduğu kabul edilir.

````
@Override
protected void onResume() {

    Toast.makeText(this, "onResume MainActivity", Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onResume MainActivity");
    super.onResume();

}
````

Bu noktada uygulamaya bir şey olmadıkça aktivite ön planda kalır. Telefon görüşmesi veya kullanıcı başka bir etkinliğe gittiğinde diğer uygulamalardan gelen aşırı (çoklu pencere modu uygulaması) etkileşim bu durumu değiştirebilir.

---

### *onPause()*

`onPause()`, kullanıcı başka bir etkinliğe veya çoklu pencere modu uygulamasına geçtiğinde çağrılır. Bu noktada etkinlik odağını kaybettiği için arka planda çalışır. Bu `callback`, etkinliği duraklatacak ve bu etkinliğin tükettiği bazı kaynakları serbest bırakacaktır. Gerekli olmayan tüm işlemler duraklatılır.

````
@Override
protected void onPause() {

    Toast.makeText(this, "onPause MainActivity", Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onPause MainActivity");

    super.onPause();
}
````

`onPause()` çağrıldığında, bellekten bazı kaynakları serbest bırakabilirsiniz. Ancak, `onResume()` `callback`i sırasında bunları yeniden başlattığınızdan emin olun.

`onPause()`, diğer etkinliklere geçişe izin veren kısa bir `callback`dir. Dolayısıyla bu aşamada yoğun hesaplamalar yapılmamalıdır. Bu, uygulamanın diğer etkinliklere geçişini geciktirerek kötü bir kullanıcı deneyimine yol açabilir.

---

### *onStop()*

Bu noktada faaliyet süreçlerinin çoğu durdurulmuştur. Ancak, etkinlik hala arka planda çalışır. Bu yaşam döngüsü genellikle kullanıcının diğer etkinliklere geçmesi veya ana sayfa düğmesine basması nedeniyle `onPause()` metodunun yürütülmesinden sonra gerçekleşir. Bu gibi durumlarda, aktivite görünmezken ağır kaynakları serbest bırakmak ve gerekli olmayan yoğun operasyonları durdurmak için kullanılır. `onPause()` kısa sürdüğünden veritabanları gibi diğer kanallara veri kaydetmek için `onStop()` kullanılabilir.

````
@Override
protected void onStop() {

    Toast.makeText(this, "onStop MainActivity", Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onStop MainActivity");

    super.onStop();
}
````

Not: Bu esnada aktivite yok edilmez. Aktivite `instance`ları bir arka yığına kaydedilir. Bu, görünümleri içeren durumların hala aktif olduğu anlamına gelir. Kullanıcı uygulamayı tekrar açtığında tüm `instance`lar yeniden yüklenmek yerine bellekten alınırlar. `TextView` gibi UI bileşenleri bu duruma örnek olarak verilebilir.

---

### *onRestart()*

Aktivitenin durumları hala mevcut olduğundan, kullanıcı aktiviteyi yeniden başlattığında `onRestart()` metodu çağrılabilir. Bu, etkinliğin ana ekrana geri döneceği ve kullanıcının bileşenleriyle etkileşime devam edebileceği anlamına gelir. Tartışıldığı gibi, `onCreate()` metodu aktivitenin yaşam döngüsünde yalnızca bir kez çağrılır. Bu nedenle, `onRestart()` metodu yürütüldüğünde aktivite `onStart()`, ardından da `onResume()` metodlarını yürütülerek devam edecektir.

````
@Override
protected void onRestart() {

    Toast.makeText(this, "onRestart MainActivity", Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onRestart MainActivity");

    super.onRestart();
}
````

---

### *onDestroy()*

Bu, aktivitenin durdurulduğunda alacağı son `callback`dir. Ekran döndürme veya dil ayarları gibi yapılandırma durumlarında bir değişiklik olduğunda çağrılır. Android sistemi etkinliği yok edecek, ardından ayarlanan yapılandırmalarla yeniden oluşturacaktır.

````
@Override
protected void onDestroy() {

    Toast.makeText(this, "onDestroy MainActivity", Toast.LENGTH_SHORT).show();
    Log.d(TAG, "onDestroy MainActivity");

    super.onDestroy();
}
````

---

## *Android Lifecycle'ın Handle Edilmesi ve Lifecycle-Aware Bileşenler*

Aktiviteler ve fragementlar gibi ***lifecycle-aware*** bileşenler başka bir bileşenin yaşam döngüsü durumundaki değişikliğine yanıt olarak eylemler gerçekleştirir. Bu bileşenler, bakımı daha kolay olan daha iyi organize edilmiş ve genellikle daha hafif kodlar üretmenize yardımcı olur. 

Aktivitelerin ve fragmentların yaşam döngüsü metodlarında, bağımlı bileşenlerin eylemlerinin `implement` edilmesi yaygın bir yöntemdir. Fakat bu yöntem, zayıf bir kod düzenine ve hataların çoğalmasına sebep olur. Yaşam döngüsü duyarlı bileşenler kullanarak koddaki bağımlı bileşenleri, yaşam döngüsü methodlarından kendi içlerine taşıyabilirsiniz.

`androidx.lifecycle` paketi, bir aktivite veya fragmentın mevcut yaşam döngüsü durumuna göre davranışlarını otomatik olarak ayarlayabilen bileşenler olan yaşam döngüsüne duyarlı bileşenler oluşturmanıza olanak tanıyan `class`lar ve `interface`ler sağlar.

Android Framework'te tanımlanan uygulama bileşenlerinin çoğunun kendilerine bağlı yaşam döngüleri vardır. Yaşam döngüleri, işleminizde çalışan işletim sistemi veya ***frame kod***u tarafından yönetilir. Android'in nasıl çalıştığının özüdür ve uygulamanızın bunlara uyması gerekir. Bunu yapmadığında bellek sızıntılarını, hatta uygulama çökmelerini tetikleyebilir.

Cihazın konumunu ekranda gösteren bir aktivitemiz olduğunu hayal edin. Bu uygulama aşağıdaki gibi olabilir:

````
internal class MyLocationListener(
        private val context: Context,
        private val callback: (Location) -> Unit
) {

    fun start() {
        // connect to system location service
    }

    fun stop() {
        // disconnect from system location service
    }
}

class MyActivity : AppCompatActivity() {
    private lateinit var myLocationListener: MyLocationListener

    override fun onCreate(...) {
        myLocationListener = MyLocationListener(this) { location ->
            // update UI
        }
    }

    public override fun onStart() {
        super.onStart()
        myLocationListener.start()
        // manage other components that need to respond
        // to the activity lifecycle
    }

    public override fun onStop() {
        super.onStop()
        myLocationListener.stop()
        // manage other components that need to respond
        // to the activity lifecycle
    }
}
````

Bu örnek iyi görünse de gerçek bir uygulamada yaşam döngüsünün mevcut durumuna yanıt olarak UI ve diğer bileşenleri yöneten çok fazla çağrı oluşur. Birden çok bileşeni yönetmek `onStart()` ve `onStop()` gibi yaşam döngüsü yöntemlerine önemli miktarda kod yerleştirir ve bu da bunların bakımını zorlaştırır. Ayrıca, bileşenin aktivite veya fragment durdurulmadan önce başlayacağının garantisi yoktur. Bu, özellikle `onStart()` içindeki bazı yapılandırma kontrolleri gibi uzun süreli bir işlem gerçekleştirmemiz gerektiğinde geçerlidir. Bu, `onStop()` metodunun `onStart()`'dan önce bittiği ve bileşeni gerekenden daha uzun süre canlı tuttuğu bir yarış durumuna neden olabilir.

````
class MyActivity : AppCompatActivity() {
    private lateinit var myLocationListener: MyLocationListener

    override fun onCreate(...) {
        myLocationListener = MyLocationListener(this) { location ->
            // update UI
        }
    }

    public override fun onStart() {
        super.onStart()
        Util.checkUserStatus { result ->
            // what if this callback is invoked AFTER activity is stopped?
            if (result) {
                myLocationListener.start()
            }
        }
    }

    public override fun onStop() {
        super.onStop()
        myLocationListener.stop()
    }

}
````

`androidx.lifecycle` paketi, bu sorunları esnek ve yalıtılmış bir şekilde çözmenize yardımcı olan `class`lar ve `interface`ler sağlar.

---

## *Lifecycle*

`Lifecycle`, bir bileşenin (aktivite veya fragment gibi) yaşam döngüsü durumu hakkındaki bilgileri tutan ve diğer nesnelerin bu durumu gözlemlemesine izin veren bir `class`tır.

### *Event*

Frame ve Lifecycle sınıfından gönderilen yaşam döngüsü olaylarına denir. Bu olaylar, aktivite ve fragmentlardaki `callback` olaylarıyla eşlenir.

### *State*

Lifecycle nesnesi tarafından izlenen bileşenin mevcut durumudur.

Aşağıdaki şekilde bir Android aktivite yaşam döngüsündeki `state` ve `event`lerin karşılaştırılması yapılmıştır.

![lifecycle2](https://developer.android.com/static/images/topic/libraries/architecture/lifecycle-states.svg)

Durumları grafiğin düğümleri ve olayları bu düğümler arasındaki kenarlar olarak düşünün.

Bir `class`, `DefaultLifecycleObserver`ı `implement` ederek bileşenlerin yaşam döngüsünü görüntüleyebilir ve `onCreate`, `onStart` gibi ilgili metodları `override` edebilir. Ardından `Lifecycle` sınıfının `addObserver()` metodunu çağırarak bir `observer` ekleyebilir ve `instance`ını iletebilirsiniz. Aşağıda bunun bir örneğini gösterilmiştir.

````
class MyObserver : DefaultLifecycleObserver {
    override fun onResume(owner: LifecycleOwner) {
        connect()
    }

    override fun onPause(owner: LifecycleOwner) {
        disconnect()
    }
}

myLifecycleOwner.getLifecycle().addObserver(MyObserver())
````

Yukarıda verilen örnekte `myLifecycleOwner` nesnesinin `implement` ettiği `LifecycleOwner` `interface`i aşağıda açıklanmıştır.

### *LifecycleOwner*

`LifecycleOwner` bir `class`ın `Lifecycle`a sahip olduğunu belirten tek metodlu bir `interface`dir. Bu metod `getLifecycle()`'dır. Bunun yerine tüm uygulama sürecinin yaşam döngüsünü yönetmeye çalışıyorsanız, bkz. [ProcessLifecycleOwner](https://developer.android.com/reference/androidx/lifecycle/ProcessLifecycleOwner).

Bu `interface`, ***Fragment*** ve ***AppCompatActivity*** gibi bireysel sınıflardan bir `Lifecycle` sahipliğini soyutlar ve bunlarla çalışan bileşenlerin yazılmasına izin verir. Herhangi bir özel uygulama sınıfı, `LifecycleOwner` `interface`ini uygulayabilir.

`DefaultLifecycleObserver`'ı `implement` eden bileşenler, `LifecycleOwner`'ı `implement` eden bileşenlerle sorunsuz bir şekilde çalışır çünkü `owner`, bir `observer`ın izlemek için kaydolabileceği bir lifecycle sağlayabilir.

Konum izleme örneği için, `MyLocationListener` sınıfının `DefaultLifecycleObserver` `implement` etmesini ve ardından `onCreate()` metodunda etkinliğin lifecycle ile başlatmasını sağlayabiliriz. Bu, `MyLocationListener` sınıfının kendi kendine yeterli olmasını sağlar, yani yaşam döngüsü durumundaki değişikliklere tepki verme mantığı, aktivite yerine `MyLocationListener`'da bildirilir. Bireysel bileşenlerin kendi lojiklerini depolaması, aktivitelerin ve fragment lojiklerinin yönetilmesini kolaylaştırır.

````
class MyActivity : AppCompatActivity() {
    private lateinit var myLocationListener: MyLocationListener

    override fun onCreate(...) {
        myLocationListener = MyLocationListener(this, lifecycle) { location ->
            // update UI
        }
        Util.checkUserStatus { result ->
            if (result) {
                myLocationListener.enable()
            }
        }
    }
}
````

Yaygın bir kullanım şekli, lifecycle şu anda iyi durumda değilse belirli callbackleri başlatmaktan kaçınmaktır. Örneğin, callback, etkinlik durumu kaydedildikten sonra bir fragment işlemi çalıştırırsa, bir çökmeyi tetikler, bu nedenle bu callbacki asla çağırmak istemeyiz.

Bu kullanım durumunu kolaylaştırmak için `Lifecycle` sınıfı, diğer nesnelerin mevcut durumu sorgulamasına izin verir.

````
internal class MyLocationListener(
        private val context: Context,
        private val lifecycle: Lifecycle,
        private val callback: (Location) -> Unit
): DefaultLifecycleObserver {

    private var enabled = false

    override fun onStart(owner: LifecycleOwner) {
        if (enabled) {
            // connect
        }
    }

    fun enable() {
        enabled = true
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        // disconnect if connected
    }
}
````

Bu implementasyon ile `LocationListener` sınıfımız tamamen yaşam döngüsüne duyarlıdır. `LocationListener`'ımızı başka bir aktiviteden veya fragmentdan kullanmamız gerekirse, onu başlatmamız yeterlidir. Tüm kurulum ve ayırma işlemleri sınıfın kendisi tarafından yönetilir.

Bir kütüphane, Android yaşam döngüsüyle çalışması gereken sınıflar sağlıyorsa, yaşam döngüsüne duyarlı bileşenleri kullanmanızı öneririz. Kütüphane istemcileriniz, istemci tarafında manuel yaşam döngüsü metodu olmadan bu bileşenleri kolayca entegre edebilir.

---

### *Custom LifecycleOwner İmplementasyonu*

Destek Kitaplığı 26.1.0 ve sonraki sürümlerindeki fragmentlar ve aktiviteler, `LifecycleOwner` `interface`ini zaten uygular.

`LifecycleOwner` yapmak istediğiniz özel bir sınıfınız varsa, `LifecycleRegistry` sınıfını kullanabilirsiniz, ancak aşağıdaki kod örneğinde gösterildiği gibi olayları o sınıfa iletmeniz gerekir:

````
class MyActivity : Activity(), LifecycleOwner {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    public override fun onStart() {
        super.onStart()
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}
````

---

### *Lifecycle-Aware Bileşenleri İçin Best Practiseler*

- UI denetleyicilerinizi (aktiviteler ve fragmentlar) mümkün olduğunca yalın tutun. Kendi verilerini elde etmeye çalışmamalılar; bunun yerine, bunu yapmak için bir `ViewModel` kullanın ve değişiklikleri görünümlere geri yansıtmak için `LiveData` nesnesini gözlemleyin.
- UI denetleyicinizin sorumluluğunun, veri değiştikçe görünümleri güncellemek veya kullanıcı eylemlerini `ViewModel`'e geri bildirmek olduğu, veriye dayalı UI'ler yazmaya çalışın.
- Veri lojiğinizi `ViewModel` sınıfınıza koyun. `ViewModel`, UI denetleyiciniz ve uygulamanızın geri kalanı arasında bağlayıcı görevi görmelidir. Yine de dikkatli olun, verileri almak (örneğin bir ağdan) `ViewModel`'in sorumluluğunda değildir. Bunun yerine, `ViewModel` verileri almak için uygun bileşeni çağırmalı ve ardından sonucu UI denetleyicisine geri sağlamalıdır.
- Görünümleriniz ve UI denetleyicisi arasında temiz bir `interface` sağlamak için `Data Binding` kullanın. Bu, görüşlerinizi daha açıklayıcı hale getirmenize, aktivite ve fragmentlarınıza yazmanız gereken güncelleme kodunu en aza indirmenize olanak tanır. Bunu Java programlama dilinde yapmayı tercih ederseniz, ortak koddan kaçınmak ve daha iyi bir soyutlamaya sahip olmak için `Butter Knife` gibi bir kitaplık kullanın.
- UI'ınız karmaşıksa UI değişikliklerini işlemek için bir sunucu sınıfı oluşturmayı düşünün. Bu zahmetli bir görev olabilir, ancak UI bileşenlerinizin test edilmesini kolaylaştırabilir.
- `ViewModel`'inizde bir `View` veya `Activity` `context`ine referans vermekten kaçının. `ViewModel` aktiviteden daha uzun sürerse (yapılandırma değişiklikleri durumunda), etkinliğiniz sızdırır ve çöp toplayıcı tarafından uygun şekilde atılmaz.
- Uzun süren görevleri ve zaman uyumsuz olarak çalışabilen diğer işlemleri yönetmek için ***Kotlin Coroutines*** kullanın.

---

### *Lifecycle-Aware Bileşenler İçin Kullanım Senaryoları*

Yaşam döngüsüne duyarlı bileşenler, çeşitli durumlarda yaşam döngülerini yönetmenizi çok daha kolay hale getirebilir. Birkaç örnek:

- ***Kaba ve hassas konum güncellemeleri arasında geçiş yapma:*** Konum uygulamanız görünür durumdayken ayrıntılı konum güncellemelerini etkinleştirmek ve uygulama arka plandayken ayrıntılı güncellemelere geçmek için yaşam döngüsüne duyarlı bileşenleri kullanın. Yaşam döngüsüne duyarlı bir bileşen olan `LiveData`, kullanıcınız konum değiştirdiğinde uygulamanızın kullanıcı arayüzünü otomatik olarak güncellemesine olanak tanır.
- ***Video arabelleğe almayı durdurma ve başlatma:*** Video arabelleğe almayı mümkün olan en kısa sürede başlatmak için yaşam döngüsüne duyarlı bileşenleri kullanın, ancak uygulama tamamen başlatılana kadar oynatmayı erteleyin. Uygulamanız yok edildiğinde arabelleğe almayı sonlandırmak için yaşam döngüsüne duyarlı bileşenleri de kullanabilirsiniz.
- ***Ağ bağlantısını başlatma ve durdurma:*** Bir uygulama ön plandayken ağ verilerinin canlı güncellenmesini (akışını) etkinleştirmek ve ayrıca uygulama arka plana geçtiğinde otomatik olarak duraklatmak için yaşam döngüsüne duyarlı bileşenleri kullanın.
- ***Animasyonlu çizimleri duraklatma ve sürdürme:*** Uygulama arka plandayken animasyonlu çizimleri duraklatmak ve uygulama ön planda olduktan sonra çizimleri sürdürmek için yaşam döngüsüne duyarlı bileşenleri kullanın.

---

### *On Stop Eventleri Handle Etme*

`Lifecycle` bir `AppCompatActivity` veya `Fragment`'e ait olduğunda, `Lifecycle`'ın durumu `CREATED` olarak değişir ve `AppCompatActivity` veya `Fragment`'in `onSaveInstanceState()` çağrıldığında `ON_STOP` olayı gönderilir.

Bir `Fragment` veya `AppCompatActivity`'nin durumu `onSaveInstanceState()` aracılığıyla kaydedildiğinde, UI'si `ON_START` çağrılana kadar değişmez olarak kabul edilir. Durum kaydedildikten sonra UI'ını değiştirmeye çalışmak, uygulamanızın gezinme durumunda tutarsızlıklara neden olabilir; bu nedenle, durum kaydedildikten sonra uygulama bir `FragmentTransaction` çalıştırırsa `FragmentManager` bir `exception` atar.

`LiveData`, gözlemcinin ilişkili `Lifecycle`ı en azından `STARTED` olmadığında, gözlemcisini aramaktan kaçınarak bu uç durumun kutudan çıkmasını önler. Perde arkasında, gözlemcisini çağırmaya karar vermeden önce `isAtLeast()`'i çağırır.

Ne yazık ki, `AppCompatActivity`'nin `onStop()` metodu `onSaveInstanceState()`'den sonra çağrılır ve bu, UI durumu değişikliklerine izin verilmediği ancak `Lifecycle`'ın henüz `CREATED` durumuna taşınmadığı bir boşluk bırakır.

Bu sorunu önlemek için, `beta2` ve daha düşük sürümlerdeki `Lifecycle` sınıfı, olayı göndermeden durumu `CREATED` olarak işaretler, böylece mevcut durumu kontrol eden herhangi bir kod, olay `onStop()` sistem tarafından çağrılıncaya kadar olay gönderilmese bile gerçek değeri alır.

Ne yazık ki, bu çözümün iki büyük sorunu var:

- API düzeyi 23 ve altında, Android sistemi, kısmen başka bir etkinlik tarafından kapsanmış olsa bile aslında bir etkinliğin durumunu kaydeder. Başka bir deyişle, Android sistemi `onSaveInstanceState()` öğesini çağırır, ancak mutlaka `onStop()` öğesini çağırmaz. Bu, gözlemcinin UI durumu değiştirilemese bile yaşam döngüsünün hala etkin olduğunu düşündüğü potansiyel olarak uzun bir aralık oluşturur.
- `LiveData` sınıfına benzer bir davranış sergilemek isteyen herhangi bir sınıf, `Lifecycle` sürüm `beta 2` ve daha düşük tarafından sağlanan geçici çözümü uygulamalıdır.

---

## *Kaynaklar*

1. [*How to Implement the Android LifeCycle Callback Methods* by Antony Gitau](https://www.section.io/engineering-education/understanding-and-implementing-the-android-lifecycle/#:~:text=An%20Android%20activity%20goes%20through,activity%20enters%20a%20new%20state.)
2. [Android Developer Docs](https://developer.android.com/topic/libraries/architecture/lifecycle)